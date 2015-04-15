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

import it.geosolutions.geobatch.opensdi.csvingest.utils.CSVSchemaHandler;
import it.geosolutions.opensdi.persistence.dao.MarketPriceDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author DamianoG
 *
 */
public class CSVMarketPricesProcessor40 extends CSVMarketPricesProcessor100 {

    private final static Logger LOGGER = LoggerFactory.getLogger(CSVMarketPricesProcessor40.class);
    
    @Autowired
    private MarketPriceDAO dao;

    public CSVMarketPricesProcessor40() {
        schemaHandler = new CSVSchemaHandler("marketprices40");
    }

    public MarketPriceDAO getDao() {
        return dao;
    }
    
    public void setDao(MarketPriceDAO dao) {
        this.dao = dao;
    }

}
