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
import it.geosolutions.opensdi.model.Withdrawal;
import it.geosolutions.opensdi.persistence.dao.GenericNRLDAO;
import it.geosolutions.opensdi.persistence.dao.WithdrawalDAO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author DamianoG
 * 
 */
public class CSVWithdrawalProcessor extends GenericCSVProcessor<Withdrawal, Long> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CSVWithdrawalProcessor.class);

    @Autowired
    private WithdrawalDAO dao;

    public CSVWithdrawalProcessor() {
        schemaHandler = new CSVSchemaHandler("withdrawal");
    }

    @Override
    public GenericNRLDAO<Withdrawal, Long> getGenericDao() {
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
    public Withdrawal merge(Withdrawal old, Object[] properties) {
        Withdrawal withdrawal;
        if (old != null) {
            withdrawal = (Withdrawal) old;
        } else {
            withdrawal = new Withdrawal();
        }
        int idx = 1;

        withdrawal.setDistrict((String)properties[idx++]);
        withdrawal.setProvince((String)properties[idx++]);
        withdrawal.setYear((Integer)properties[idx++]);        
        withdrawal.setMonth((String)properties[idx++]);
        withdrawal.setDecade((Integer)properties[idx++]);
        
        Object withdrawalValue = properties[idx++];
        Double withdrawalDouble=(withdrawal == null)?null:(Double)withdrawalValue;
        withdrawal.setWithdrawal(withdrawalDouble);
        
        try {
            withdrawal.setDecadeYear(CSVIngestUtils.getDecadJanDec(withdrawal.getMonth(), withdrawal.getDecade()));
            withdrawal.setDecadeAbsolute(withdrawal.getYear()*36 + withdrawal.getDecadeYear());            
        } catch (CSVProcessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return withdrawal;
    }

    @Override
    public void save(Withdrawal entity) {
        dao.merge(entity);
    }

    @Override
    public void persist(Withdrawal entity) {
        dao.persist(entity);
    }

    public WithdrawalDAO getDao() {
        return dao;
    }
    
    public void setDao(WithdrawalDAO dao) {
        this.dao = dao;
    }
}
