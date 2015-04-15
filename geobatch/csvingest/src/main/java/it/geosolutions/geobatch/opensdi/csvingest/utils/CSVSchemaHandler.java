/*
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
package it.geosolutions.geobatch.opensdi.csvingest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author DamianoG
 *
 * This class holds mapping information between input CSV file and the data entity used in the crop information portal.
 * It loads the information from a properties file.
 * 
 * The Properties file is loaded in memory in the class constructor execution, it must take into account in order to
 * avoid unwanted caching side-effects of the values.
 *
 */
public class CSVSchemaHandler {

    protected final static Logger LOGGER = LoggerFactory.getLogger(CSVSchemaHandler.class);
    
    private final static String TYPE_LIST = "types_list";
    
    private final static String UNIQUE_LIST = "unique_list";
    
    private final static String HEADERS_LIST = "headers_list";
    
    private final static String LIST_SEPARATOR = ";";
    
    private List<CSVPropertyType> typesList;
    
    private List<String> headersList;
    
    private List<Integer> uniqueList;
    
    private final String propertiesFileName;
    
    public CSVSchemaHandler(String propertiesFileName){
        typesList = new ArrayList<CSVPropertyType>();
        uniqueList = new ArrayList<Integer>();
        headersList = new ArrayList<String>();
        this.propertiesFileName = propertiesFileName;
        reload();
    }
    
    public List<CSVPropertyType> getTypeList(){
        return ListUtils.unmodifiableList(typesList);
    }
    
    public List<Integer> getPrimaryKeysIndexes(){
        return ListUtils.unmodifiableList(uniqueList);
    }
    
    public List<String> getHeaderList(){
        return ListUtils.unmodifiableList(headersList);
    }
    
    /**
     * Search and load for a properties file called as the entity. 
     *  
     * @param entityNames
     */
    public static Map<String, String> loadEntityProperties(String entityName){
        if(entityName == null || entityName.isEmpty()){
            throw new IllegalArgumentException("entityName is null or empty... this should never happen...");
        }
        URL fileURL = searchpropertiesFile(entityName);
        File propFile;
        try {
            propFile = new File(fileURL.toURI());
        } catch (URISyntaxException e1) {
            throw new IllegalArgumentException("Unable to find property file for this CSV Processor...");
        }
        return loadProperties(propFile);
    }
    
    
    /**
     * This method search for the properties file where the informations about the keyList and uniqueList are stored.
     * The approach is the following:
     * <ul>
     *    <li>First, search in the classpath root, so <b>WEB-INF/classes</b> if this method run in a webapp. This file is the override file and it could be useful in case we want to change some mapping at runtime, without redeploying nor restarting the server</li>
     *    <li>If the file is not found in the classpath root, search it in the <b>current package</b>. A file must be found since here are the default properties files for this class</li>
     * </ul>
     * 
     * TODO: Instead of search in the classpath root the method should be able to search the file in the Geobatch flowConfig directory. Unfortunately the interfaces of CSVGenericProcess doesn't allow to pass the path of that directory. (inject it with spring?) 
     * 
     */
    public static URL searchpropertiesFile(String entityName){
        String fileName = entityName+".properties";
        LOGGER.info("trying to load the properties file '" + fileName + "'");
        URL url = CSVSchemaHandler.class.getResource("/"+fileName);
        if(url == null){
            url = CSVSchemaHandler.class.getResource(fileName);
        }
        if(url == null){
            throw new IllegalStateException("the properties file cannot be found in the package... this should never happen...");
        }
        return url;
    }
    
    /**
     * Load a properties File and save the values in a Map
     * @param fileURL
     * @return
     */
    public static Map<String, String> loadProperties(File properties){
        
        
        InputStream inStream = null;
        Map<String, String> propertiesMap = null;
        try {
            inStream = new FileInputStream(properties);
            Properties p = new Properties();
            p.load(inStream);
            propertiesMap = new HashMap<String, String>((Map) p);
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        finally{
            if(inStream != null){
                try {
                    inStream.close();
                } catch (IOException e) {
                    inStream = null;
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return  propertiesMap;
    }
    
    public void reload(){
        typesList = new ArrayList<CSVPropertyType>();
        uniqueList = new ArrayList<Integer>();
        headersList = new ArrayList<String>();
        Map<String,String> configMap = loadEntityProperties(propertiesFileName);
        if(!configMap.keySet().contains(TYPE_LIST) || !configMap.keySet().contains(UNIQUE_LIST) || !configMap.keySet().contains(HEADERS_LIST)){
            throw new IllegalStateException("cannot find TYPE_LIST or HEADERS_LIST or UNIQUE_LIST in the properties file...");
        }
        String typeListString = configMap.get(TYPE_LIST);
        String uniqueListString = configMap.get(UNIQUE_LIST);
        String headersListString = configMap.get(HEADERS_LIST);
        
        if(typeListString == null || typeListString.isEmpty()){
            throw new IllegalStateException("TYPE_LIST cannot be null or empty...");
        }
        String[] typeListArray = typeListString.split(LIST_SEPARATOR);
        for(String type : typeListArray){
            try{
                typesList.add(CSVPropertyType.valueOf(type));
            }
            catch(Exception e){
                throw new IllegalStateException("TYPE_LIST contains a not valid value: '" + type + "'");
            }
        }
        
        if(headersListString == null || headersListString.isEmpty()){
            throw new IllegalStateException("HEADERS_LIST cannot be null or empty...");
        }
        String[] headersListArray = headersListString.split(LIST_SEPARATOR);
        if(headersListArray.length != typesList.size()){
            throw new IllegalStateException("HEADERS_LIST and TYPE_LIST have different size...");
        }
        for(String header : headersListArray){
                headersList.add(header);
        }
        
        if(!StringUtils.trimAllWhitespace(uniqueListString).isEmpty()){
            String[] uniqueListArray = uniqueListString.split(LIST_SEPARATOR);
            for(String index : uniqueListArray){
                try{
                    uniqueList.add(Integer.parseInt(index));
                }
                catch(Exception e){
                    throw new IllegalStateException("UNIQUE_LIST contains a not valid Integer value: '" + index + "'");
                }
            }
        }
    }
}
