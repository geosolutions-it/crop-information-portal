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

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author DamianoG
 *
 */
public class CSVSchemaHandlerTest extends Assert{

    @Test
    public void testLoadingPropertiesFileFromThePackage(){
        Map<String, String> m = CSVSchemaHandler.loadEntityProperties("loadingtest-test");
        assertTrue(m != null);
        assertEquals("package", m.get("position"));
    }
    
    @Test
    public void testLoadingPropertiesFileFromTheClasspathRoot(){
        Map<String, String> m = CSVSchemaHandler.loadEntityProperties("loadingtest-test2");
        assertTrue(m != null);
        assertEquals("root", m.get("position"));
    }
    
    // test the case with both lists properly configured and valorized    
    @Test
    public void testPropertiesParsingSuccess1(){
        CSVSchemaHandler test = new CSVSchemaHandler("parsingsuccess-1");
        assertTrue(test != null);
        assertTrue(test.getTypeList() != null);
        assertEquals(8, test.getTypeList().size());
        assertEquals(3, test.getPrimaryKeysIndexes().size());
        assertEquals(CSVPropertyType.IGNORE,test.getTypeList().get(0));
        assertEquals(CSVPropertyType.STRING,test.getTypeList().get(3));
        assertEquals(CSVPropertyType.DOUBLE,test.getTypeList().get(6));
    }
    
    // test the case with the uniqueList empty
    @Test
    public void testPropertiesParsingSuccess2(){
        CSVSchemaHandler test = new CSVSchemaHandler("parsingsuccess-2");
        assertTrue(test != null);
        assertTrue(test.getTypeList() != null);
        assertEquals(8, test.getTypeList().size());
        assertEquals(0, test.getPrimaryKeysIndexes().size());
    }
    
    // test the case with only uniqueList null
    @Test
    public void testPropertiesParsingFail1(){
        try{
            CSVSchemaHandler test = new CSVSchemaHandler("parsingfail-1");
        }
        catch(IllegalStateException e){
            assertEquals("cannot find TYPE_LIST or HEADERS_LIST or UNIQUE_LIST in the properties file...", e.getMessage());
            return;
        }
        fail();
    }
    
    // test the case with only typeList null
    @Test
    public void testPropertiesParsingFail2(){
        try{
            CSVSchemaHandler test = new CSVSchemaHandler("parsingfail-2");
        }
        catch(IllegalStateException e){
            assertEquals("cannot find TYPE_LIST or HEADERS_LIST or UNIQUE_LIST in the properties file...", e.getMessage());
            return;
        }
        fail();
    }
    
    //test the case with not valid values in uniqueList
    @Test
    public void testPropertiesParsingFail3(){
        try{
            CSVSchemaHandler test = new CSVSchemaHandler("parsingfail-3");
        }
        catch(IllegalStateException e){
            assertEquals("TYPE_LIST contains a not valid value: 'DOUTBLE'", e.getMessage());
            return;
        }
        fail();
    }
    
    //test the case with not valid values in typeList    
    @Test
    public void testPropertiesParsingFail4(){
        try{
            CSVSchemaHandler test = new CSVSchemaHandler("parsingfail-4");
        }
        catch(IllegalStateException e){
            assertEquals("UNIQUE_LIST contains a not valid Integer value: 's5'", e.getMessage());
            return;
        }
        fail();
    }
    
    //test the case with not valid values in typeList    
    @Test
    public void testPropertiesParsingFail5(){
        try{
            CSVSchemaHandler test = new CSVSchemaHandler("parsingfail-5");
        }
        catch(IllegalStateException e){
            assertEquals("HEADERS_LIST and TYPE_LIST have different size...", e.getMessage());
            return;
        }
        fail();
    }
}
