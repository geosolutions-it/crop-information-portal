/*
 *  Copyright (C) 2007 - 2012 GeoSolutions S.A.S.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEventType;
import it.geosolutions.geobatch.opensdi.csvingest.processor.CSVProcessor;
import it.geosolutions.opensdi.model.CropDescriptor;
import it.geosolutions.opensdi.model.Season;
import it.geosolutions.opensdi.model.UnitOfMeasure;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.io.FileUtils;
import org.geotools.test.TestData;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class CSVIngestActionTest extends BaseDAOTest{

    private static boolean isDBInitiated = false;
    
    public CSVIngestActionTest() {
    }
    
    @Before
    public void initCropDBDatabase(){
        if(!isDBInitiated){
            createCropDescriptors();
            createUnitOfMeasures();
            isDBInitiated = true;
        }
    }
    
    @Test
    public void irrigation_waterflow_IngestionFlowTest() throws Exception{
        List<File> list = gatherTestInputCSVFile("all\\irrigationwaterflow");
        assertNotNull(list);
        assertFalse(list.isEmpty());        
        runFlow(list, true);
        assertEquals(52,waterflowDAO.findAll().size());
    }
    
    @Test
    public void irrigation_withdrawal_IngestionFlowTest() throws Exception{
        List<File> list = gatherTestInputCSVFile("all\\irrigationwithdrawal");
        assertNotNull(list);
        assertFalse(list.isEmpty());        
        runFlow(list, true);
        assertEquals(28,withdrawalDAO.findAll().size());
    }
    
    @Test
    public void agrometIngestionFlowTest() throws Exception{
        List<File> list = gatherTestInputCSVFile("all\\agromet");
        assertNotNull(list);
        assertFalse(list.isEmpty());
        runFlow(list, true);
        assertEquals(610, agrometDAO.findAll().size());
    }
    
    @Test
    public void cropdataIngestionFlowTest() throws Exception{
        List<File> list = gatherTestInputCSVFile("all\\cropdata");
        Collections.sort(list);
        assertNotNull(list);
        assertFalse(list.isEmpty());
        runFlow(list, true);
        assertEquals(1504, cropDataDAO.findAll().size());
    }
    
    @Test
    public void cropdataPropertiesIngestionFlowTest() throws Exception{
        List<File> list = gatherTestInputPropertiesFile("properties\\all\\crop");
        assertNotNull(list);
        assertFalse(list.isEmpty());        
        runFlow(list, false);
        assertEquals(12, cropDataDAO.findAll().size());
    }
    
    @Test
    public void fertilizerIngestionFlowTest() throws Exception{
        List<File> list = gatherTestInputPropertiesFile("properties\\all\\fertilizer");
        assertNotNull(list);
        assertFalse(list.isEmpty());       
        runFlow(list, false);
        assertEquals(284,fertilizerDAO.findAll().size());
    }
    
    @Test
    public void marketpricesIngestionFlowTest() throws Exception{
        List<File> list = gatherTestInputPropertiesFile("properties\\all\\marketprices");
        assertNotNull(list);
        assertFalse(list.isEmpty());        
        runFlow(list, false);
        assertEquals(29,marketPriceDAO.findAll().size());
    }
    
    private List<File> gatherTestInputCSVFile(String directory){
        File dirWithInputCSVFile = loadFile(directory);
        assertNotNull(dirWithInputCSVFile);
        assertTrue(dirWithInputCSVFile.isDirectory());
        List<File> inputCSVFile = (List<File>) FileUtils.listFiles(dirWithInputCSVFile, new String[]{"csv"}, true);
        Collections.sort(inputCSVFile);
        return inputCSVFile;
    }
    
    private List<File> gatherTestInputPropertiesFile(String directory){    
        File dirWithInputPropertiesFile = loadFile(directory);
        assertNotNull(dirWithInputPropertiesFile);
        assertTrue(dirWithInputPropertiesFile.isDirectory());
        List<File> inputPropertiesFiles = (List<File>) FileUtils.listFiles(dirWithInputPropertiesFile, new String[]{"properties"}, true);
        Collections.sort(inputPropertiesFiles);
        return inputPropertiesFiles;
    }
    
    public void runFlow(List<File> inputFlowFiles, boolean isCSV) throws Exception {

        Queue<EventObject> events = new LinkedList<EventObject>();
        
        CSVIngestAction action = new CSVIngestAction(new CSVIngestConfiguration(null, null, null));
        action.setUnitOfMeasureService(unitOfMeasureService);
        action.setProcessors(processors);
        action.afterPropertiesSet();

        Map<String, String> map = new HashMap<String, String>();
        map.put("src", "testSrc");
        for(CSVProcessor p : processors){
            p.setFlowExecutionParametersMap(map);
        }
        
        if(!isCSV){ //so the files are properties... we need to replace the placeholder with the absolute path of the csv
            List<File> tempList = new ArrayList<File>();
            for(File f : inputFlowFiles){
                String fileContent = FileUtils.readFileToString(f);
                String path = f.getAbsolutePath().substring(0,f.getAbsolutePath().lastIndexOf(File.separator));
                path = path.replace("\\", "\\\\");
                fileContent=fileContent.replaceAll("_DIRECTORY_", path);
                File tmp = TestData.temp(this, f.getName().substring(0, f.getName().lastIndexOf("."))+".properties");
                fileContent=fileContent.replace("\\", "\\\\");
                FileUtils.write(tmp, fileContent, false);
                tempList.add(tmp);
            }
            inputFlowFiles = null;
            inputFlowFiles = tempList;
        }
        
        for(File file : inputFlowFiles) {
            LOGGER.info("Loading " + file);
            FileSystemEvent event = new FileSystemEvent(file, FileSystemEventType.FILE_ADDED);
            events.add(event);
            action.addListener(new DummyProgressListener());
            action.execute(events);
        }

    }

    private static void createUnitOfMeasures() {
        createUom("000_tons", "production", 1);
        createUom("000_bales", "production", 170);
        createUom("000_ha", "area", 1);
        createUom("kg_ha", "yield", 1);

    }

    private static void createCropDescriptors() {
        cropDescriptorDAO.persist(createCropDescriptor("Maize", "maize", Season.RABI));
        cropDescriptorDAO.persist(createCropDescriptor("Wheat", "wheat", Season.RABI));
        cropDescriptorDAO.persist(createCropDescriptor("Sugarcane", "Sugarcane", Season.RABI));
        cropDescriptorDAO.persist(createCropDescriptor("Cotton", "Cotton", Season.RABI));
        cropDescriptorDAO.persist(createCropDescriptor("Rice", "rice", Season.KHARIF));

    }
    
    /**
     * Create a crop descriptor with default unit of measures set
     * @param label
     * @param id
     * @param season
     * @return
     */
    private static CropDescriptor createCropDescriptor (String label,String id,Season season){
    	CropDescriptor c = new CropDescriptor(label,id,season);
    	c.setArea_default_unit("000_ha");
    	c.setProd_default_unit("Cotton".equals(id) ? "000_bales":"000_tons");
    	c.setYield_default_unit("kg_ha");
    	return c;
    	
    }

    protected static UnitOfMeasure createUom(String id, String cls, double factor) {
        UnitOfMeasure u = new UnitOfMeasure();
        u.setCls(cls);
        u.setDescription("Test unit of measure for unit tests");
        u.setId(id);
        u.setName(id);
        u.setShortname(id);
        u.setCoefficient(factor);
        unitOfMeasureService.persist(u);
        return unitOfMeasureService.get(id);
    }

}