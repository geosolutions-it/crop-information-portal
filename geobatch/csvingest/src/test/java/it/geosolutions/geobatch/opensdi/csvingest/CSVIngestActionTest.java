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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEventType;
import it.geosolutions.geobatch.opensdi.csvingest.processor.CSVCropProcessor;
import it.geosolutions.opensdi.model.CropDescriptor;
import it.geosolutions.opensdi.model.Season;
import it.geosolutions.opensdi.model.UnitOfMeasure;

import java.io.File;
import java.io.Serializable;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class CSVIngestActionTest extends BaseDAOTest {

    public CSVIngestActionTest() {
    }

    @Test
    public void loadAll() throws Exception {

        createCropDescriptors();
        createUnitOfMeasures();

        Queue<EventObject> events = new LinkedList<EventObject>();
        File dir = loadFile("all");
        assertNotNull(dir);
        assertTrue(dir.isDirectory());

        CSVIngestAction action = new CSVIngestAction(new CSVIngestConfiguration(null, null, null));
        action.setCropDataDao(cropDataDAO);
        action.setCropDescriptorDao(cropDescriptorDAO);
        action.setAgrometDao(agrometDAO);
        action.setCropStatusDao(cropStatusDAO);
        action.setUnitOfMeasureService(unitOfMeasureService);
        action.afterPropertiesSet();


        for(File file : FileUtils.listFiles(dir, new String[]{"csv"}, true)) {
            LOGGER.info("Loading " + file);
            FileSystemEvent event = new FileSystemEvent(file, FileSystemEventType.FILE_ADDED);
            events.add(event);
            action.addListener(new DummyProgressListener());
            action.execute(events);
        }
        checkSampleData();

    }

    private void checkSampleData() {
    	checkCropDataConversion();
	}

	private void checkCropDataConversion() {
    	//TODO check conversions
	}

	private void createUnitOfMeasures() {
		createUom("000_tons", "production", 1);
		createUom("000_bales", "production",170);
		createUom("000_ha","area",1);
		createUom("kg_ha","yield",1);
		
	}

	private void createCropDescriptors() {
            cropDescriptorDAO.persist(createCropDescriptor("Rice",  "rice", Season.KHARIF));
            cropDescriptorDAO.persist(createCropDescriptor("Maize", "maize", Season.RABI));
            cropDescriptorDAO.persist(createCropDescriptor("Wheat", "wheat", Season.RABI));
            cropDescriptorDAO.persist(createCropDescriptor("Sugarcane", "Sugarcane", Season.RABI));
            cropDescriptorDAO.persist(createCropDescriptor("Cotton", "Cotton", Season.RABI));

    }
    /**
     * Create a crop descriptor with default unit of measures set
     * @param label
     * @param id
     * @param season
     * @return
     */
    private CropDescriptor createCropDescriptor (String label,String id,Season season){
    	CropDescriptor c = new CropDescriptor(label,id,season);
    	c.setArea_default_unit("000_ha");
    	c.setProd_default_unit("Cotton".equals(id) ? "000_bales":"000_tons");
    	c.setYield_default_unit("kg_ha");
    	return c;
    	
    }
    protected UnitOfMeasure createUom(String id, String cls, double factor) {
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