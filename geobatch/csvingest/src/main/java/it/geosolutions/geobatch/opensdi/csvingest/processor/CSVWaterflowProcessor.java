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

import it.geosolutions.geobatch.opensdi.csvingest.utils.CSVIngestUtils;
import it.geosolutions.geobatch.opensdi.csvingest.utils.CSVPropertyType;
import it.geosolutions.geobatch.opensdi.csvingest.utils.CSVSchemaHandler;
import it.geosolutions.opensdi.model.Waterflow;
import it.geosolutions.opensdi.persistence.dao.GenericNRLDAO;
import it.geosolutions.opensdi.persistence.dao.WaterflowDAO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author DamianoG
 * 
 */
public class CSVWaterflowProcessor extends GenericCSVProcessor<Waterflow, Long> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CSVWaterflowProcessor.class);

    @Autowired
    private WaterflowDAO dao;

    public CSVWaterflowProcessor() {
        schemaHandler = new CSVSchemaHandler("waterflow");
    }

    @Override
    public GenericNRLDAO<Waterflow, Long> getGenericDao() {
        return dao;
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
    public Waterflow merge(Waterflow old, Object[] properties) {
        Waterflow waterflow;
        if (old != null) {
            waterflow = (Waterflow) old;
        } else {
            waterflow = new Waterflow();
        }
        int idx = 1;
        
        waterflow.setRiver((String)properties[idx++]);
        waterflow.setYear((Integer)properties[idx++]);        
        waterflow.setMonth((String)properties[idx++]);
        waterflow.setDecade((Integer)properties[idx++]);
        
        Object waterflowValue = properties[idx++];
        Double waterflowInteger=(waterflow == null)?null:(Double)waterflowValue;
        waterflow.setWaterflow(waterflowInteger);
        
        try {
            waterflow.setDecadeYear(CSVIngestUtils.getDecadJanDec(waterflow.getMonth(), waterflow.getDecade()));
            waterflow.setDecadeAbsolute(waterflow.getYear()*36 + waterflow.getDecadeYear());            
        } catch (CSVProcessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return waterflow;
    }

    @Override
    public void save(Waterflow entity) {
        dao.merge(entity);
    }

    @Override
    public void persist(Waterflow entity) {
        dao.persist(entity);
    }

    public WaterflowDAO getDao() {
        return dao;
    }
    
    public void setDao(WaterflowDAO dao) {
        this.dao = dao;
    }
}
