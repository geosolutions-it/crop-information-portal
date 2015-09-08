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
import it.geosolutions.opensdi.model.AgroMet;
import it.geosolutions.opensdi.persistence.dao.AgrometDAO;
import it.geosolutions.opensdi.persistence.dao.GenericNRLDAO;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ETj (etj at geo-solutions.it)
 * @author adiaz refactor to use GenericCSVProcessor
 */
public class CSVAgrometProcessor extends GenericCSVProcessor<AgroMet, Long> {

// ID_ndv;distr;prov;year;mon;dec;factor;NDVI_avg;
// last field is a value, but its name may change

private final static List<String> HEADERS = Collections.unmodifiableList(Arrays
        .asList("*", "distr", "prov", "year", "mon", "dec", "factor", "*"));

@Autowired
private AgrometDAO dao;

static List<CSVPropertyType> TYPES;
static {
    TYPES = new LinkedList<CSVPropertyType>();
    // "*",
    TYPES.add(CSVPropertyType.IGNORE);
    // "distr"
    TYPES.add(CSVPropertyType.STRING);
    // "prov"
    TYPES.add(CSVPropertyType.STRING);
    // "year"
    TYPES.add(CSVPropertyType.INTEGER);
    // "mon"
    TYPES.add(CSVPropertyType.STRING);
    // "dec"
    TYPES.add(CSVPropertyType.INTEGER);
    // "factor"
    TYPES.add(CSVPropertyType.STRING);
    // "*"
    TYPES.add(CSVPropertyType.DOUBLE);
}

static List<Integer> PK_PROPERTIES;
static {
    PK_PROPERTIES = new LinkedList<Integer>();
    // ID : "factor", "district", "province", "year", "month", "dec"
    PK_PROPERTIES.add(6);
    PK_PROPERTIES.add(1);
    PK_PROPERTIES.add(2);
    PK_PROPERTIES.add(3);
    PK_PROPERTIES.add(4);
    PK_PROPERTIES.add(5);
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
public GenericNRLDAO<AgroMet, Long> getGenericDao() {
    return dao;
}

@Override
public List<CSVPropertyType> getTypes() {
    return TYPES;
}

public AgroMet merge(AgroMet old, Object[] properties) {
    AgroMet agromet;
    if (old != null) {
        agromet = (AgroMet) old;
    } else {
        agromet = new AgroMet();
    }
    int idx = 1;
    agromet.setDistrict((String) properties[idx++]);
    agromet.setProvince((String) properties[idx++]);
    agromet.setYear((Integer) properties[idx++]);
    agromet.setMonth((String) properties[idx++]);
    agromet.setDec((Integer) properties[idx++]);
    agromet.setFactor((String) properties[idx++]);
    agromet.setValue((Double) properties[idx++]);
    setSVars(agromet);
    return agromet;
}

private void setSVars(AgroMet agromet) {
    Month3 emon = Month3.valueOf(agromet.getMonth().toUpperCase());
    Month4 emon2 = Month4.valueOf(agromet.getMonth().toUpperCase());
    agromet.setS_yr(agromet.getYear() + emon.getAdd());
    agromet.setS_dec(agromet.getDec() + emon.getPos() * 3);
    agromet.setDek_in_year(agromet.getDec() + emon2.getPos() * 3);
    agromet.setAbsolute_dek(agromet.getYear()*36 + agromet.getDek_in_year());
}

static enum Month3 {
    JAN("Jan", 2, 0), FEB("Feb", 3, 0), MAR("Mar", 4, 0), APR("Apr", 5, 0), MAY(
            "May", 6, 0), JUN("Jun", 7, 0), JUL("Jul", 8, 0), AUG("Aug", 9, 0), SEP(
            "Sep", 10, 0), OCT("Oct", 11, 0), NOV("Nov", 0, 1), DEC("Dec", 1, 1);

    private String lit;

    private int pos;

    private int add;

    private Month3(String lit, int pos, int add) {
        this.lit = lit;
        this.pos = pos;
        this.add = add;
    }

    public String getLit() {
        return lit;
    }

    public int getPos() {
        return pos;
    }

    public int getAdd() {
        return add;
    }

}
static enum Month4 {
    JAN("Jan", 0, 0), FEB("Feb", 1, 0), MAR("Mar", 2, 0), APR("Apr", 3, 0), MAY(
            "May", 4, 0), JUN("Jun", 5, 0), JUL("Jul", 6, 0), AUG("Aug", 7, 0), SEP(
            "Sep", 8, 0), OCT("Oct", 9, 0), NOV("Nov", 10, 1), DEC("Dec", 11, 1);

    private String lit;

    private int pos;

    private int add;

    private Month4(String lit, int pos, int add) {
        this.lit = lit;
        this.pos = pos;
        this.add = add;
    }

    public String getLit() {
        return lit;
    }

    public int getPos() {
        return pos;
    }

    public int getAdd() {
        return add;
    }

}

public void save(AgroMet entity) {
    dao.merge(entity);
}

public void persist(AgroMet entity) {
    dao.persist(entity);
}

public AgrometDAO getDao() {
    return dao;
}

public void setDao(AgrometDAO dao) {
    this.dao = dao;
}



}
