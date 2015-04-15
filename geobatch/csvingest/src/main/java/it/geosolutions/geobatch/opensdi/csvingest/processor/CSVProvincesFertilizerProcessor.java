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
package it.geosolutions.geobatch.opensdi.csvingest.processor;

import it.geosolutions.geobatch.opensdi.csvingest.utils.CSVPropertyType;
import it.geosolutions.geobatch.opensdi.csvingest.utils.CSVSchemaHandler;
import it.geosolutions.opensdi.model.Fertilizer;
import it.geosolutions.opensdi.persistence.dao.FertilizerDAO;
import it.geosolutions.opensdi.persistence.dao.GenericNRLDAO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author DamianoG
 *
 */
public class CSVProvincesFertilizerProcessor extends GenericCSVProcessor<Fertilizer, Long> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CSVProvincesFertilizerProcessor.class);
    
    @Autowired
    private FertilizerDAO dao;
    
    public CSVProvincesFertilizerProcessor(){
        schemaHandler = new CSVSchemaHandler("fertilizerProvinces");
    }

    @Override
    public List<String> getHeaders() {
        return schemaHandler.getHeaderList();
    }

    @Override
    public List<CSVPropertyType> getTypes() {
        return schemaHandler.getTypeList();
    }

    @Override
    public List<Integer> getPkProperties() {
        return schemaHandler.getPrimaryKeysIndexes();
    }

    @Override
    public GenericNRLDAO<Fertilizer, Long> getGenericDao() {
        return dao;
    }

    @Override
    public Fertilizer merge(Fertilizer old, Object[] properties) {
        Fertilizer fertilizer;
        if (old != null) {
            fertilizer = (Fertilizer) old;
        } else {
            fertilizer = new Fertilizer();
        }
        int idx = 1;
        // pk
        //prov;year;mon;factor;value;(offtake tons)
        fertilizer.setDistrict(null);
        fertilizer.setProvince((String) properties[idx++]);
        fertilizer.setYear((Integer) properties[idx++]);
        fertilizer.setMonth((String) properties[idx++]);
        fertilizer.setNutrient((String) properties[idx++]);
        fertilizer.setOfftakeTons((Double) properties[idx++]);
        return fertilizer;
    }

    @Override
    public void save(Fertilizer entity) {
        dao.merge(entity);
    }

    @Override
    public void persist(Fertilizer entity) {
        dao.persist(entity);
    }

    public FertilizerDAO getDao() {
        return dao;
    }

    public void setDao(FertilizerDAO dao) {
        this.dao = dao;
    }
}
