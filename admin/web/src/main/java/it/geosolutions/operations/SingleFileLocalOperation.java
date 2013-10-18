/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2011, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.operations;

import it.geosolutions.geobatch.services.rest.model.RESTRunInfo;
import it.geosolutions.nrl.utils.ControllerUtils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Admin
 *
 */
public abstract class SingleFileLocalOperation implements LocalOperation {
    protected String basedirString;

    protected String jspName="singleFile";
    
    public String getBasedirString() {
        return basedirString;
    }
    public void setBasedirString(String basedirString) {
        this.basedirString = basedirString;
    }
    public String getJspName() {
        return jspName;
    }
    public void setJspName(String jspName) {
        this.jspName = jspName;
    }

    @Override
    public String getJsp() {
        return jspName;
    }
    
    @Override
    public Object getBlob(Object inputParam, HttpServletRequest request) {
        
        String fileName = request.getParameter("fileName");
           
        if( fileName==null){
            fileName = (String)inputParam;
        }
        fileName=ControllerUtils.preventDirectoryTrasversing(fileName);
        RESTRunInfo runInfo = new RESTRunInfo();
        List<String> flist = new ArrayList<String>();
        // TODO: more flexible
        flist.add(basedirString+fileName);
        runInfo.setFileList(flist);
        return runInfo;
    }
    
    @Override
    public String getJsp(ModelMap model, HttpServletRequest request, List<MultipartFile> files) {
        
        if(model.containsKey("gotParam")){
            String gotParam =(String) model.get("gotParam");
            String d = request.getParameter("d");
            if(d!=null){
                gotParam = ControllerUtils.preventDirectoryTrasversing(d)+gotParam;
                
            }
            model.addAttribute("fileName",gotParam );
        } else {
            model.addAttribute("fileName", "Insert File Name");
        }
        
        return jspName;

    }
}
