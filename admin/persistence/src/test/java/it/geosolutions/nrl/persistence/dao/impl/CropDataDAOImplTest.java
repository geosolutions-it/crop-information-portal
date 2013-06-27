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
package it.geosolutions.nrl.persistence.dao.impl;

import it.geosolutions.nrl.model.CropData;
import it.geosolutions.nrl.model.CropDescriptor;
import static it.geosolutions.nrl.persistence.dao.impl.BaseDAOTest.cropDataDAO;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class CropDataDAOImplTest extends BaseDAOTest {

    public CropDataDAOImplTest() {
    }

    @Test
    public void testPersistAndMerge() {

        Long id;

        {
            CropData cd = new CropData();
            cd.setCrop("crop");
            cd.setDistrict("distr");
            cd.setProvince("prov");
            cd.setYear(2013);
            
            cd.setArea(100d);
            cd.setProduction(200d);
            cd.setYears("2000-2013");
            cd.setYield(300d);

            cropDataDAO.persist(cd);
            id = cd.getId();
        }

        assertNotNull(id);
        LOGGER.info("Saved CropData " + id);

        {
            // test insert
            CropData loaded = cropDataDAO.find(id);
            assertNotNull(loaded);
            assertEquals("crop", loaded.getCrop());
            assertEquals(Double.valueOf(100d), loaded.getArea());

            // and modify
            loaded.setArea(111d);
            cropDataDAO.merge(loaded);
        }

        {
            // test merge
            CropData loaded = cropDataDAO.find(id);
            assertNotNull(loaded);
            assertEquals(Double.valueOf(111d), loaded.getArea());
        }

    }

    @Test
    public void testUpdatability() {

        Long id;

        {
            CropData cd = new CropData();
            cd.setCrop("crop");
            cd.setDistrict("distr");
            cd.setProvince("prov");
            cd.setYear(2013);

            cd.setArea(100d);
            cd.setProduction(200d);
            cd.setYears("2000-2013");
            cd.setYield(300d);

            cropDataDAO.persist(cd);
            id = cd.getId();
        }

        assertNotNull(id);
        LOGGER.info("Saved CropData " + id);

        {
            // test insert
            CropData loaded = cropDataDAO.find(id);
            assertNotNull(loaded);

            // and modify
            loaded.setCrop("anUpdateShouldNotBeAllowed");
            try {
                cropDataDAO.merge(loaded);
//                fail("Update should not be allowed for crop field");
                LOGGER.error("Update should not be allowed for crop field");
            } catch (Exception e) {
                LOGGER.info("Expected exception thrown : " + e.getMessage());
            }
        }

        {
            // test insert
            CropData loaded = cropDataDAO.find(id);
            assertNotNull(loaded);
            assertEquals("crop", loaded.getCrop());
        }
    }

    @Test
    public void testUniqueness() {

        Long id;

        {
            CropData cd = new CropData();
            cd.setCrop("crop");
            cd.setDistrict("distr");
            cd.setProvince("prov");
            cd.setYear(2013);

            cd.setArea(100d);
            cd.setProduction(200d);
            cd.setYears("2000-2013");
            cd.setYield(300d);

            cropDataDAO.persist(cd);
            id = cd.getId();
        }

        assertNotNull(id);
        LOGGER.info("Saved CropData " + id);

        {
            // test insert
            CropData loaded = cropDataDAO.find(id);
            assertNotNull(loaded);

            // insert Crop with same data
            try {
                CropData cd = new CropData();
                cd.setCrop("crop");
                cd.setDistrict("distr");
                cd.setProvince("prov");
                cd.setYear(2013);

                cd.setArea(0d);
                cd.setProduction(0d);
                cd.setYears("-");
                cd.setYield(0d);

                cropDataDAO.persist(cd);

                fail("Persist() should not allow PK duplicates");
            } catch (Exception e) {
                LOGGER.info("Expected exception thrown : " + e.getMessage());
            }
        }
    }
}