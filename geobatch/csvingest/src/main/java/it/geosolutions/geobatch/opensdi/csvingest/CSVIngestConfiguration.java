/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://geobatch.geo-solutions.it/
 *  Copyright (C) 2007-2008-2012 GeoSolutions S.A.S.
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
package it.geosolutions.geobatch.opensdi.csvingest;

import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;

/**
 */
public class CSVIngestConfiguration extends ActionConfiguration {

    private Character csvSeparator;
    private Boolean emptyFieldsAsZero;
    private Boolean rowByRow;

    public Character getCsvSeparator() {
        return csvSeparator;
    }

    public void setCsvSeparator(Character csvSeparator) {
        this.csvSeparator = csvSeparator;
    }

    public Boolean getEmptyFieldsAsZero() {
        return emptyFieldsAsZero;
    }

    public void setEmptyFieldsAsZero(Boolean emptyFieldsAsZero) {
        this.emptyFieldsAsZero = emptyFieldsAsZero;
    }

    public CSVIngestConfiguration(String id, String name, String description) {
        super(id, name, description);
    }
    
    public Boolean getRowByRow() {
        return rowByRow;
    }

    public void setRowByRow(Boolean rowByRow) {
        this.rowByRow = rowByRow;
    }

    @Override
    public CSVIngestConfiguration clone() {
        final CSVIngestConfiguration configuration = (CSVIngestConfiguration) super.clone();
        return configuration;
    }

}
