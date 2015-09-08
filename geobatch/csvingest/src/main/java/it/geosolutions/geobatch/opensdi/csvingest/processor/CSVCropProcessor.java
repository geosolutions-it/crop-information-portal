/*
 *  Copyright (C) 2007 - 2013 GeoSolutions S.A.S.
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
import it.geosolutions.opensdi.model.CropData;
import it.geosolutions.opensdi.model.CropDescriptor;
import it.geosolutions.opensdi.model.UnitOfMeasure;
import it.geosolutions.opensdi.persistence.dao.CropDataDAO;
import it.geosolutions.opensdi.persistence.dao.CropDescriptorDAO;
import it.geosolutions.opensdi.persistence.dao.GenericNRLDAO;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ETj (etj at geo-solutions.it)
 * @author adiaz refactor to use GenericCSVProcessor
 * @author Lorenzo natali, GeoSolutions
 * Processor for import of CropData. This processor suppose to receive data
 * from the CSV file in the default unit of measure configured in the system
 * for each crop.
 */
public class CSVCropProcessor extends GenericCSVProcessor<CropData, Long> {

    @Autowired
    private CropDescriptorDAO cropDescriptorDAO;
    
	private final static Logger LOGGER = LoggerFactory
	        .getLogger(CSVCropProcessor.class);
	
	protected final static String SRC = "src";
	
private final static List<String> HEADERS = Collections.unmodifiableList(Arrays
        .asList("*", "crop", "distr", "prov", "year", "years", "area", "prod",
                "yield"));

@Autowired
private CropDataDAO dao;

static List<CSVPropertyType> TYPES;
static {
    TYPES = new LinkedList<CSVPropertyType>();
    // "*",
    TYPES.add(CSVPropertyType.IGNORE);
    // "crop"
    TYPES.add(CSVPropertyType.STRING);
    // "distr"
    TYPES.add(CSVPropertyType.STRING);
    // "prov"
    TYPES.add(CSVPropertyType.STRING);
    // "year"
    TYPES.add(CSVPropertyType.INTEGER);
    // "years"
    TYPES.add(CSVPropertyType.STRING);
    // "area"
    TYPES.add(CSVPropertyType.DOUBLE);
    // "prod"
    TYPES.add(CSVPropertyType.DOUBLE);
    // "yield"
    TYPES.add(CSVPropertyType.DOUBLE);
}

static List<Integer> PK_PROPERTIES;
static {
    PK_PROPERTIES = new LinkedList<Integer>();
    // crop , district , province , year
    PK_PROPERTIES.add(1);
    PK_PROPERTIES.add(2);
    PK_PROPERTIES.add(3);
    PK_PROPERTIES.add(4);
}

@Override
public List<Integer> getPkProperties() {
    return PK_PROPERTIES;
}

@Override
public List<String> getHeaders() {
    return HEADERS;
}

@Override
public GenericNRLDAO<CropData, Long> getGenericDao() {
    String src = flowExecutionParametersMap.get(SRC);
    dao.setSrc(src);
    return dao;
}

@Override
public List<CSVPropertyType> getTypes() {
    return TYPES;
}

public CropData merge(CropData old, Object[] properties) {
    CropData cropData;
    if (old != null) {
        cropData = (CropData) old;
    } else {
        cropData = new CropData();
    }
    int idx = 1;
    String cropDescrId = (String) properties[idx++];
    cropData.setCropDescriptor(getCropDescriptor(cropDescrId));
    if(cropData.getCropDescriptor() == null){
    	throw new IllegalArgumentException("couldn't find Crop with id:\"" + cropDescrId + "\" in the database. Please create an entry for this crop before ingest" );
    }
    cropData.setDistrict((String) properties[idx++]);
    cropData.setProvince((String) properties[idx++]);
    cropData.setYear((Integer) properties[idx++]);
    cropData.setYears((String) properties[idx++]);
    cropData.setArea((Double) properties[idx++]);
    cropData.setProduction((Double) properties[idx++]);
    cropData.setYield((Double) properties[idx++]);
    cropData.setSrc(flowExecutionParametersMap.get(SRC));
    return cropData;
}

public void save(CropData entity) {
    dao.merge(entity);
}

public void persist(CropData entity) {
    dao.persist(entity);
}
@Override 
protected void preProcess(CropData entity){
	/**
	 * Convert using default unit of measure
	 */
	if(unitOfMeasureService!=null){
		UnitOfMeasure areaUnit = unitOfMeasureService.getDefaultAreaUnitOfMeasure(entity.getCropDescriptor());
		UnitOfMeasure prodUnit = unitOfMeasureService.getDefaultProductionUnitOfMeasure(entity.getCropDescriptor());
		UnitOfMeasure yieldUnit = unitOfMeasureService.getDefaultYieldUnitOfMeasure(entity.getCropDescriptor());
		if(areaUnit != null){
			double areaFactor = areaUnit.getCoefficient();
			entity.setArea(entity.getArea()/areaFactor);
		}
		if(prodUnit != null){
			double prodFactor = prodUnit.getCoefficient();
			entity.setProduction(entity.getProduction()/prodFactor);

		}
		if(yieldUnit != null){
			double yieldFactor = yieldUnit.getCoefficient();
			entity.setYield(entity.getYield()/yieldFactor);		
		}
		if(areaUnit == null || prodUnit == null || yieldUnit == null){
			LOGGER.warn("The units of measure for crop" + entity.getCropDescriptor().getId() + " are not defined. Supposing a conversion is not needed");
		}

	}else{
		throw new IllegalArgumentException("couldn't update crops before the unit of measure is not well configured");
	}
}

protected CropDescriptor getCropDescriptor(String id){
    return cropDescriptorDAO.find(id);
}

public CropDataDAO getDao() {
    return dao;
}

public void setDao(CropDataDAO dao) {
    this.dao = dao;
}

protected Map<String, CropDescriptor> getCropDescriptors() {
    List<CropDescriptor> descList = cropDescriptorDAO.findAll();
    Map<String, CropDescriptor> ret = new HashMap<String, CropDescriptor>();
    for (CropDescriptor cropDescriptor : descList) {
        ret.put(cropDescriptor.getId(), cropDescriptor);
    }
    return ret;
}

}
