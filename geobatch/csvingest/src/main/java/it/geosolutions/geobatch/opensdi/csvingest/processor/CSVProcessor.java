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
package it.geosolutions.geobatch.opensdi.csvingest.processor;

import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;
import it.geosolutions.geobatch.opensdi.csvingest.CSVIngestConfiguration;
import it.geosolutions.opensdi.service.UnitOfMeasureService;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import au.com.bytecode.opencsv.CSVReader;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public abstract class CSVProcessor {

    private final static Logger LOGGER = LoggerFactory.getLogger(CSVProcessor.class);

    @Autowired
    protected UnitOfMeasureService unitOfMeasureService;
    protected CSVIngestConfiguration flowConfig;
    protected Map<String, String> flowExecutionParametersMap;
    
	public abstract List<String> getHeaders();

    public boolean canProcess(List<String> ingestHeaders) {
        if (ingestHeaders == null) {
            return false;
        }
        if (ingestHeaders.size() != getHeaders().size()) {
            LOGGER.debug("Processor " + getClass().getSimpleName() + " skipped because of headers size ["+getHeaders().size()+"!="+ingestHeaders.size()+"]");
            return false;
        }
        for (int i = 0; i < getHeaders().size(); i++) {
            String exp = getHeaders().get(i);
            if ("*".equals(exp)) {
                continue;
            }
            if (!exp.equals(ingestHeaders.get(i))) {
                LOGGER.debug("Processor " + getClass().getSimpleName() + " skipped because of ["+exp+"]!=["+ingestHeaders.get(i)+"]");
                return false;
            }
        }

        return true;
    }

    abstract public void process(CSVReader reader,ProgressListenerForwarder listenerForwarder,long rowsEstimation) throws CSVProcessException;

    public abstract int getInsertCount();
    public abstract int getFailCount();
    public abstract int getRemoveCount();
    public abstract int getUpdateCount();

    public void setUnitOfMeasureService(UnitOfMeasureService unitOfMeasureService) {
        this.unitOfMeasureService = unitOfMeasureService;
    }

    public CSVIngestConfiguration getFlowConfig() {
        return flowConfig;
    }

    public void setFlowConfig(CSVIngestConfiguration flowConfig) {
        this.flowConfig = flowConfig;
    }

    public void setFlowExecutionParametersMap(Map<String, String> flowExecutionParametersMap) {
        if(this.flowExecutionParametersMap == null || flowExecutionParametersMap.size() != 0 ){
            this.flowExecutionParametersMap = flowExecutionParametersMap;
        }
    }
    
    
}