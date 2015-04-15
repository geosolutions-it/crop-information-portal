/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://geobatch.geo-solutions.it/
 *  Copyright (C) 2007-2012 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geobatch.opensdi.csvingest;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.annotations.CheckConfiguration;
import it.geosolutions.geobatch.flow.event.action.ActionException;
import it.geosolutions.geobatch.flow.event.action.BaseAction;
import it.geosolutions.geobatch.opensdi.csvingest.processor.CSVProcessException;
import it.geosolutions.geobatch.opensdi.csvingest.processor.CSVProcessor;
import it.geosolutions.geobatch.opensdi.csvingest.utils.CSVSchemaHandler;
import it.geosolutions.opensdi.service.UnitOfMeasureService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import au.com.bytecode.opencsv.CSVReader;


@Action(configurationClass=CSVIngestConfiguration.class)
public class CSVIngestAction extends BaseAction<EventObject> implements InitializingBean {

    protected final static Logger LOGGER = LoggerFactory.getLogger(CSVIngestAction.class);
    
    @Autowired
    private UnitOfMeasureService unitOfMeasureService;

    @Autowired
    private List<CSVProcessor> processors;
	
    private static final Character DEFAULT_SEPARATOR = ',';
    private static final long AVG_ROW_BYTE_SIZE = 50;
    private static final String CSV_LOCATION_KEY = "CSVlocation";

    public CSVIngestAction(final CSVIngestConfiguration configuration) throws IOException {
        super(configuration);
    }

    @Override
    @CheckConfiguration
    public boolean checkConfiguration() {
        return true;
    }

    private void checkInit() {
        if(unitOfMeasureService == null)
            throw new IllegalStateException("unitOfMeasureService is null");
    }

    /**
     *
     */
    public Queue<EventObject> execute(Queue<EventObject> events) throws ActionException {

        listenerForwarder.setTask("Check flowConfig");

        // @autowired fields are injected *after* the checkConfiguration() is called
        checkInit();

        listenerForwarder.started();

        CSVIngestConfiguration configuration = getConfiguration();
        if (configuration == null) {
            throw new IllegalStateException("ActionConfig is null.");
        }

        while(! events.isEmpty()) {
            EventObject event = events.poll();
            if(event instanceof FileSystemEvent) {
                FileSystemEvent fse = (FileSystemEvent) event;
                File file = fse.getSource();
                Map flowParamsMap = new HashMap<String, String>();
                file = processInputFile(file, flowParamsMap);
                processCSVFile(file, configuration, flowParamsMap);
//                    throw new ActionException(this, "Could not process " + event);
            } else {
                throw new ActionException(this, "EventObject not handled " + event);
            }
        }

        return new LinkedList<EventObject>();
    }


    @Transactional(value = "opensdiTransactionManager")
    private void processCSVFile(File file, CSVIngestConfiguration config, Map flowParamsMap) throws ActionException {
        LOGGER.info("Processing input file " + file);

        String[] headers = null;
        CSVReader reader = null;
        try {
            Character separator = config.getCsvSeparator();
            separator = (separator != null)?separator:DEFAULT_SEPARATOR; 
            reader = new CSVReader(new FileReader(file), separator);
            headers = reader.readNext();
        } catch (IOException e) {
        	try{
        		reader.close();
			}catch(Exception ee){
				LOGGER.error("Unable to close the CSV reader",ee);
			}
            throw new ActionException(this, "Error in reading CSV file", e);
        }

        List<String> headersList = sanitizeHeaders(headers);

        CSVProcessor processor = null;
        for (CSVProcessor p : processors) {
            if(p.canProcess(headersList)) {
                processor = p;
                processor.setFlowConfig(config);
                processor.setFlowExecutionParametersMap(flowParamsMap);
                break;
            }
        }

        if(processor == null) {
            LOGGER.warn("No processors found for file " + file.getName() + "; headers: " + headersList);
            try{
        		reader.close();
			}catch(Exception e){
				LOGGER.error("Unable to close the CSV reader",e);
			}
            throw new ActionException(this, "No processors found for file " + file.getName() +  "; headers: " + headersList);
        }
        listenerForwarder.completed();
        listenerForwarder.setTask("CSV Ingestion");
        listenerForwarder.setProgress(0);
        long rowEstimation = file.length()/AVG_ROW_BYTE_SIZE;
        LOGGER.info("Processing CSV " + file.getName() + " with " + processor.getClass().getSimpleName());
        try {
            processor.process(reader,listenerForwarder,rowEstimation);
            String successMsg =   
                  "\n***************************************************" 
                + "\n********** SUCCESS: CSV ingestion resume **********" 
                + "\n***************************************************"
                + "\n* Records inserted: "+ processor.getInsertCount()
                + "\n* Records updated: "+ processor.getUpdateCount()
                + "\n* Records removed: "+ processor.getRemoveCount()
                + "\n* Falied records: "+ processor.getFailCount()
                + "\n***************************************************\n";
            LOGGER.info(successMsg);
            listenerForwarder.progressing(100f, successMsg);
            if(processor.getFailCount()>0){
            	throw new ActionException(this, "Some record failed when processing CSV file" + file.getName());
            }
        } catch (CSVProcessException ex) {
            throw new ActionException(this, "Error processing " + file.getName()+"."+ ex.getLocalizedMessage(), ex);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(processors== null || processors.isEmpty()){
            throw new IllegalStateException("No CSV Processors have been found... at least one processor is needed in order to use this action...");
        }
        LOGGER.info("List of CSV processor found in the application Context:");
        for(CSVProcessor processor : processors){
            LOGGER.info("--> Processor: '" + processor.getClass().toString() + "'");
        }
    }

    public void setUnitOfMeasureService(UnitOfMeasureService unitOfMeasureService) {
        this.unitOfMeasureService = unitOfMeasureService;
    }

    /**
     * Check if null header fields are present and trim trailing and leading whitespaces
     * 
     * @param headers the CSV header array extracted from a CSV file
     * @return the CSV headers as a List
     * @throws ActionException id a null header is found
     */
    private List<String> sanitizeHeaders(String[] headers) throws ActionException {

        List<String> ret = new ArrayList<String>();

        boolean emptyFound = false;

        for (String h : headers) {
            if(h == null || h.isEmpty()) {
                emptyFound = true;
            } else {
                if(emptyFound) {
                    throw new ActionException(this, "Header value found after blank header");
                }
                ret.add(h.trim());
            }
        }

        return ret;
    }
    
    /**
     * Process the input file: 
     * If the file is a CSV returns that File instance; 
     * If the file is a properties file this method extracts from the property CSVlocation the path of the file and returns the file instances, the others properties are saved in the input Map
     * The only property needed is the CSV file path (key = CSVlocation) the validation of all the other properties is delegated to the specific CSV processor.
     *  
     * @param propertiesFile a properties file where the informations needed for the CSV processing are stored
     * @param flowParameters an empty Map that will be filled with the properties found in the propertiesFile
     * @return a file to be used as input
     */
     private File processInputFile(File propertiesFile, Map<String, String> flowParameters) throws ActionException{
        
        if(propertiesFile == null || flowParameters == null){
            throw new ActionException(this, "Invalid input parameters for the method processInputFile, one or both of the two parameters are null...");
        }
        String[] fileParts = propertiesFile.getName().split("\\.");
        String ext = fileParts[fileParts.length-1];
        if("csv".equalsIgnoreCase(ext)){
            return propertiesFile;
        }
        else if("properties".equalsIgnoreCase(ext)){
            Map<String,String> map = CSVSchemaHandler.loadProperties(propertiesFile);
            String s = map.get(CSV_LOCATION_KEY);
            if(s == null || s.isEmpty() || StringUtils.containsWhitespace(s)){
                throw new ActionException(this, "Invalid input parameters for the method processInputFile, one or both of the two parameters are null...");
            }
            File f = new File(s);
            if(f == null || !f.exists() || !f.isFile() || !f.canRead()){
                throw new ActionException(this, "Invalid input file for the flow... file path is: '" + s + "'");
            }
            Map tmp = new HashMap(map);
            tmp.keySet().removeAll(flowParameters.keySet());
            tmp.keySet().remove(CSV_LOCATION_KEY);
            flowParameters.putAll(tmp);
            return f;
        }
        throw new ActionException(this, "the provided input file is nor a properties nor a csv file... the extension found is '" + ext + "'");
    }

}

