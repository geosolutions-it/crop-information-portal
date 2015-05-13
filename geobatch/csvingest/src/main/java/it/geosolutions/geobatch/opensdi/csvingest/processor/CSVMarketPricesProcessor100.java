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
import it.geosolutions.opensdi.model.MarketPrice;
import it.geosolutions.opensdi.persistence.dao.GenericNRLDAO;
import it.geosolutions.opensdi.persistence.dao.MarketPriceDAO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author DamianoG
 *
 */
public class CSVMarketPricesProcessor100 extends GenericCSVProcessor<MarketPrice, Long> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CSVMarketPricesProcessor100.class);
    
    protected final static String DENOMINATOR = "denominator";
    protected final static String EXCHANGE_RATE = "exchangeRate";

    @Autowired
    private MarketPriceDAO dao;

    public CSVMarketPricesProcessor100() {
        schemaHandler = new CSVSchemaHandler("marketprices100");
    }
    
    @Override
    public GenericNRLDAO<MarketPrice, Long> getGenericDao() {
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
    public MarketPrice merge(MarketPrice old, Object[] properties) {
        
        int denominator = 0;
        double exchangeRate = 0;
        try{
            String denominatorString = flowExecutionParametersMap.get(DENOMINATOR);
            String exchangeRateString = flowExecutionParametersMap.get(EXCHANGE_RATE);
            denominator = Integer.parseInt(denominatorString);
            if(denominator<=0){
                throw new IllegalArgumentException("The provided denominator is equals or less than 0 and it's not a valid value..");
            }
            exchangeRate = Double.parseDouble(exchangeRateString);
        }
        catch(Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException("A parsing error occurred while parsing exchange rate or denominator");
        }
        
        MarketPrice marketPrice;
        if (old != null) {
            marketPrice = (MarketPrice) old;
        } else {
            marketPrice = new MarketPrice();
        }
        int idx = 1;
        
        marketPrice.setDistrict((String)properties[idx++]);
        marketPrice.setProvince((String)properties[idx++]);
        marketPrice.setYear((Integer)properties[idx++]);
        marketPrice.setMonth((String)properties[idx++]);
        marketPrice.setDecade((Integer)properties[idx++]);
        marketPrice.setCrop((String)properties[idx++]);
        
        idx+=2;
        double value = (Double)properties[idx++];
        
        double marketPriceDollars = (100*value/denominator)*exchangeRate; //Compute it in dollars per 100kg
        double marketPriceRupiee = (100*value/denominator); 
        
        marketPrice.setMarketPriceUSD(marketPriceDollars);
        marketPrice.setMarketPriceKPR(marketPriceRupiee);
        try {
            marketPrice.setDecadeYear(CSVIngestUtils.getDecadJanDec(marketPrice.getMonth(), marketPrice.getDecade()));
            marketPrice.setDecadeAbsolute(marketPrice.getYear()*36 + marketPrice.getDecadeYear());            
        } catch (CSVProcessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        
        return marketPrice;
    }

    @Override
    public void save(MarketPrice entity) {
        dao.merge(entity);
    }

    @Override
    public void persist(MarketPrice entity) {
        dao.persist(entity);
    }

    public MarketPriceDAO getDao() {
        return dao;
    }
    
    public void setDao(MarketPriceDAO dao) {
        this.dao = dao;
    }

}
