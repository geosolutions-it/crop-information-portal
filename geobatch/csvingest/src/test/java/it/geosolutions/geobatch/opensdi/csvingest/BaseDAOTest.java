/*
 *  Copyright (C) 2013 GeoSolutions S.A.S.
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.geosolutions.geobatch.opensdi.csvingest.processor.CSVProcessor;
import it.geosolutions.opensdi.model.CropData;
import it.geosolutions.opensdi.model.Waterflow;
import it.geosolutions.opensdi.persistence.dao.AgrometDAO;
import it.geosolutions.opensdi.persistence.dao.CropDataDAO;
import it.geosolutions.opensdi.persistence.dao.CropDescriptorDAO;
import it.geosolutions.opensdi.persistence.dao.CropStatusDAO;
import it.geosolutions.opensdi.persistence.dao.FertilizerDAO;
import it.geosolutions.opensdi.persistence.dao.MarketPriceDAO;
import it.geosolutions.opensdi.persistence.dao.WaterflowDAO;
import it.geosolutions.opensdi.persistence.dao.WithdrawalDAO;
import it.geosolutions.opensdi.service.UnitOfMeasureService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public abstract class BaseDAOTest extends BaseContextTest {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    protected static CropDescriptorDAO cropDescriptorDAO;
    protected static CropDataDAO cropDataDAO;
    protected static AgrometDAO agrometDAO;
    protected static CropStatusDAO cropStatusDAO;
    protected static UnitOfMeasureService unitOfMeasureService;
    protected static List<CSVProcessor> processors;
    
    protected static MarketPriceDAO marketPriceDAO;
    protected static FertilizerDAO fertilizerDAO;
    protected static WaterflowDAO waterflowDAO; 
    protected static WithdrawalDAO withdrawalDAO;
   
    public BaseDAOTest() {
        cropDescriptorDAO = ctx.getBean("cropDescriptorDao", CropDescriptorDAO.class);
        cropDataDAO = ctx.getBean("cropDataDao", CropDataDAO.class);
        agrometDAO = ctx.getBean("agrometDao", AgrometDAO.class);
        cropStatusDAO = ctx.getBean("cropStatusDao",CropStatusDAO.class);
        unitOfMeasureService = ctx.getBean("unitOfMeasureService",UnitOfMeasureService.class);
        processors = ctx.getBean("processors",ArrayList.class);
        
        marketPriceDAO = ctx.getBean("marketPriceDao",MarketPriceDAO.class);
        fertilizerDAO = ctx.getBean("fertilizerDao",FertilizerDAO.class);
        waterflowDAO = ctx.getBean("waterflowDao",WaterflowDAO.class);
        withdrawalDAO = ctx.getBean("withdrawalDao",WithdrawalDAO.class);
    }

    @Before
    public void before() {
        removeAll();
    }

    
    protected void removeAll() {
        removeAllCropData();
    }

    protected void removeAllCropData() {
        List<CropData> list = cropDataDAO.findAll();
        LOGGER.info("Removing " + list.size() + " crop entries");
        for (CropData item : list) {
//            LOGGER.info("Removing " + item);
            boolean ret = cropDataDAO.remove(item);
            assertTrue("CropData not removed", ret);
        }
        LOGGER.info(list.size() + " crop entries removed");


        assertEquals("CropData has not been properly deleted", 0, cropDataDAO.count(null));
    }

    @Test
    public void testCheckDAOs() {

        assertNotNull(cropDescriptorDAO);
        assertNotNull(cropDataDAO);
        assertNotNull(agrometDAO);
        assertNotNull(cropStatusDAO);
        assertNotNull(unitOfMeasureService);
    }

//    protected final static String MULTIPOLYGONWKT = "MULTIPOLYGON(((48.6894038 62.33877482, 48.7014874 62.33877482, 48.7014874 62.33968662, 48.6894038 62.33968662, 48.6894038 62.33877482)))";
//    protected final static String POLYGONWKT = "POLYGON((48.6894038 62.33877482, 48.7014874 62.33877482, 48.7014874 62.33968662, 48.6894038 62.33968662, 48.6894038 62.33877482))";
//
//    protected MultiPolygon buildMultiPolygon() {
//        try {
//            WKTReader reader = new WKTReader();
//            MultiPolygon mp = (MultiPolygon) reader.read(MULTIPOLYGONWKT);
//            mp.setSRID(4326);
//            return mp;
//        } catch (ParseException ex) {
//            throw new RuntimeException("Unexpected exception: " + ex.getMessage(), ex);
//        }
//    }
//
//    protected Polygon buildPolygon() {
//        try {
//            WKTReader reader = new WKTReader();
//            Polygon mp = (Polygon) reader.read(POLYGONWKT);
//            mp.setSRID(4326);
//            return mp;
//        } catch (ParseException ex) {
//            throw new RuntimeException("Unexpected exception: " + ex.getMessage(), ex);
//        }
//    }
    //==========================================================================
}
