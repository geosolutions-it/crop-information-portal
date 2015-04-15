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
import it.geosolutions.opensdi.model.Irrigation;
import it.geosolutions.opensdi.persistence.dao.GenericNRLDAO;
import it.geosolutions.opensdi.persistence.dao.IrrigationDAO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author DamianoG
 * 
 */
public class CSVIrrigationProcessor extends GenericCSVProcessor<Irrigation, Long> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CSVIrrigationProcessor.class);

    @Autowired
    private IrrigationDAO dao;

    public CSVIrrigationProcessor() {
        schemaHandler = new CSVSchemaHandler("irrigation");
    }

    @Override
    public GenericNRLDAO<Irrigation, Long> getGenericDao() {
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
    public Irrigation merge(Irrigation old, Object[] properties) {
        Irrigation irrigation;
        if (old != null) {
            irrigation = (Irrigation) old;
        } else {
            irrigation = new Irrigation();
        }
        int idx = 1;
        
        irrigation.setYear((Integer)properties[idx++]);        
        irrigation.setMonth((String)properties[idx++]);
        irrigation.setDecade((Integer)properties[idx++]);
        irrigation.setProvince((String)properties[idx++]);
        irrigation.setDistrict((String)properties[idx++]);
        irrigation.setRiver((String)properties[idx++]);
        
        Object withdrawal = properties[idx++];
        Object waterflow = properties[idx++];
        Integer withdrawalInteger=(withdrawal == null || ((String)withdrawal).isEmpty() || ((String)withdrawal).trim().length()==0)?null:Integer.parseInt((String)withdrawal);
        Integer waterflowInteger=(waterflow == null || ((String)waterflow).isEmpty() || ((String)waterflow).trim().length()==0)?null:Integer.parseInt((String)waterflow);
        irrigation.setWithdrawal(withdrawalInteger);
        irrigation.setWaterflow(waterflowInteger);
        
        try {
            irrigation.setDecadeYear(CSVIngestUtils.getDecad(irrigation.getMonth(), irrigation.getDecade()));
            irrigation.setDecadeAbsolute(irrigation.getYear()*36 + irrigation.getDecadeYear());            
        } catch (CSVProcessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return irrigation;
    }

    @Override
    public void save(Irrigation entity) {
        dao.merge(entity);
    }

    @Override
    public void persist(Irrigation entity) {
        dao.persist(entity);
    }

    public IrrigationDAO getDao() {
        return dao;
    }
    
    public void setDao(IrrigationDAO dao) {
        this.dao = dao;
    }
}
