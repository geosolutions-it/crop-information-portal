/*
 *  OpenSDI Manager
 *  Copyright (C) 2013 GeoSolutions S.A.S.
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
package it.geosolutions.operations;

import it.geosolutions.geobatch.services.rest.model.RESTRunInfo;
import it.geosolutions.nrl.dto.StatsBean;
import it.geosolutions.nrl.mvc.model.statistics.InputSelectorConfig;
import it.geosolutions.nrl.mvc.model.statistics.StatisticsConfigList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class NDVIStatisticsOperation implements LocalOperation {
	
	/**
	 * The name of this Operation
	 */
	public static String name = "NDVIStatistics";
	
	/**
	 * The path were to GET the form and POST the request
	 * Typically all lower case
	 */
	private String path = "ndvistats";
	
	/**
	 * File extension this Operation will work on
	 */
	private String[] extensions = {"tiff"};
	
	/**
	 * Directory where to scan for files
	 */
	private String basedirString = "G:/OpenSDIManager/test_geotiff/";

	private String gbinputdirString = "G:/OpenSDIManager/gbinputdir/";

	private String geobatchRestUrl = "http://localhost:8081/geobatch/rest/";

	private String geobatchUsername = "admin";

	private String geobatchPassword = "admin";
	
	private String flowID = "ndvistats";

	@Autowired
	StatisticsConfigList statisticsConfigs;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(NDVIStatisticsOperation.class);
	
	@Override
	public String getJsp(ModelMap model, HttpServletRequest request, List<MultipartFile> files) {
		
		System.out.println("getJSP di NDVIStatistics");
		
		// TODO: How make this config? parametric xml or request parameters?
		setRegions("regions",model);
		setMask("masks",model);
		//setGranule(granule,model);

		return "statistics";
		
	}

	@Override
	public Object getBlob(Object inputParam,HttpServletRequest request) {

		@SuppressWarnings("unchecked")
		Map<String, String[]> parameters = (Map<String, String[]>) inputParam;
		RESTRunInfo runInfo = null;

		{
			
			//model.addAttribute("messageType", "POSTED");
			
			//@SuppressWarnings("unchecked")
			//Map<String, String[]> parameters = request.getParameterMap();
	        
			// create statsBean to create XML
			StatsBean sb = new StatsBean();
	        
	        //String message = "Received: ";
	        
	        String granule_month = "";
	        String granule_dekad = "";
			
	        for(String key : parameters.keySet()) {
				
				String[] vals = parameters.get(key);
		        
				//for(String val : vals)
		        //    message = message.concat(key + " -> "+val+ " \n ");
		        
		        if(key.equalsIgnoreCase("region")) {
		        	if(vals[0].equalsIgnoreCase("default:Province")) {
		    	        sb.setClassifier(StatsBean.CLASSIFIER_TYPE.PROVINCE);
		        	}else if(vals[0].equalsIgnoreCase("default:Districts")) {
		    	        sb.setClassifier(StatsBean.CLASSIFIER_TYPE.DISTRICT);
		        	}else if(vals[0].equalsIgnoreCase("file")) {
		    	        // TODO: get the file from the values
		    	        sb.setClassifier(StatsBean.CLASSIFIER_TYPE.CUSTOM);
		    	        sb.setClassifierFullPath("/this/is/a/full/path");
		        	}
		        }else if(key.equalsIgnoreCase("mask")){
		        	if(vals[0].equalsIgnoreCase("default:CropMask")) {
		    	        sb.setForestMask(StatsBean.MASK_TYPE.STANDARD);
		        	}else if(vals[0].equalsIgnoreCase("file")) {
		    	        sb.setForestMask(StatsBean.MASK_TYPE.CUSTOM);
		    	        // TODO: get the file from the values
		    	        sb.setForestMaskFullPath("/a/full/path/for/forest/mask");
		        	}else{
		    	        sb.setForestMask(StatsBean.MASK_TYPE.DISABLED);
		        	}
				}else if(key.equalsIgnoreCase("month")) {
					// TODO: do this filtering client side
					granule_month = vals[0].substring(2).replace("-", "");
				}else if(key.equalsIgnoreCase("dekad")) {
					granule_dekad = vals[0];					
				}
		    }
			
			if(granule_month.length() == 4 && granule_dekad.length() == 1) {
				sb.setNdviFileName(getNDVIFileName(granule_month.concat(granule_dekad)));
			}else {
				// TODO throw catch exception
				sb.setNdviFileName("00000");
			}

	        JAXB.marshal(sb, System.out);
	        
	        try {
	        	
				File outputFile = File.createTempFile("input_", ".xml", new File(getGbinputdirString()));
				JAXB.marshal(sb, outputFile);
				runInfo = new RESTRunInfo();
				List<String> fList = new ArrayList<String>();
				// TODO: absolute or relative?
				fList.add(outputFile.getAbsolutePath());
				runInfo.setFileList(fList);
				
	        } catch (IOException e) {
				e.printStackTrace();
			}

			//model.addAttribute("notLocalizedMessage", message);
			//return "common/messages";
		}

		// TODO: create the input xml from a template
		//parameters.get("param1")
		//parameters.get("param2")
		//parameters.get("param3")
		//runInfo.setFileList( List 1,2,3 );
		
        return runInfo; 
	}
	
	public static final String NDVI_FILE_NAME_PREFIX = "dv_";
	public static final String NDVI_FILE_NAME_EXTENSION = ".tif";
	
	/**
	 * Obtain file name for a NDVI for a String formatted as 'yyMmDecad'.<br />
	 * For example, for the input String <code>13011</code> the result it's <code>dv_20130101_20130110.tiff</code>  
	 * 
	 * @param yyMmDekad String with year in the positions (0,1), month in the positions 2 and 3 and dekad in the last position
	 * 
	 * @return NDVI file name for the parameters
	 */
	private String getNDVIFileName(String yyMmDekad){
		Integer year = 2000 + Integer.decode(yyMmDekad.substring(0, 2));
		String mm = yyMmDekad.substring(2, 4);
		Integer month = Integer.decode(mm);
		String dekadS = yyMmDekad.substring(4);
		Dekad dekad = dekadS.equals("2") ? 
				Dekad.SECOND : dekadS.equals("3") 
				? Dekad.THIRD : Dekad.FIRST;
		// First day: can be 01, 11 or 21
		String ddStart = ((dekad.ordinal() * 10) + 1) + "";
		if(ddStart.length() == 1){
			ddStart = "01";
		}
		// End day: can be: 11, 21 or the last day of the month
		Integer endDay = (((dekad.ordinal() + 1) * 10));
		if(endDay > 29){
			// last day of the month
			Calendar cal = new GregorianCalendar(year, month, 1);
			endDay = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		}
		String ddEnd = endDay.toString();
		
		// Final name dv_20130101_20130110.tiff  
		return NDVI_FILE_NAME_PREFIX + year + mm +  ddStart + "_" + year + mm + ddEnd + NDVI_FILE_NAME_EXTENSION;
	}
	
	/**
	 * Simple enum for dekads in a month
	 * 
	 * @author adiaz
	 *
	 */
	private enum Dekad{
		FIRST, SECOND, THIRD
	}


	private void setMask(String mask, ModelMap model) {
		if (statisticsConfigs.getConfigs()== null ){
			LOGGER.error("couldn't find statistics configs");
			return ;
		}
		for(InputSelectorConfig config : statisticsConfigs.getConfigs()){
			if(mask.equals(config.getId())){
				model.addAttribute("masks",config);	
				return;
			}
		}
		LOGGER.warn("Statistics config not found: " + mask);
	}

	private void setRegions(String regions, ModelMap model) {
		if (statisticsConfigs.getConfigs()== null ){
			LOGGER.error("couldn't find statistics configs");
			return ;
		}
		for(InputSelectorConfig config : statisticsConfigs.getConfigs()){
			if(regions.equals(config.getId())){
				model.addAttribute("regions",config);	
				return;
			}
		}
		LOGGER.warn("Statistics config not found: " + regions);

		
	}
	
	private void setGranule(String granule, ModelMap model) {
		if (statisticsConfigs.getConfigs()== null ){
			LOGGER.error("couldn't find statistics configs");
			return ;
		}
		for(InputSelectorConfig config : statisticsConfigs.getConfigs()){
			if(granule.equals(config.getId())){
				model.addAttribute("granule",config);	
				return;
			}
		}
		LOGGER.warn("Statistics config not found: " + granule);

		
	}

	/**
	 * Getter
	 * @return the basedirString
	 */
	public String getBasedirString() {
		return basedirString;
	}

	/**
	 * Setter
	 * @param basedirString the basedirString to set
	 */
	public void setBasedirString(String basedirString) {
		this.basedirString = basedirString;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	@Override
	public String getRESTPath() {
		return getPath();
	}

	@Override
	public List<String> getExtensions() {
		List<String> l = new ArrayList<String>();
		for (String s : extensions) {
			l.add(s);
		}
		return l;
	}

	@Override
	public boolean isMultiple() {
		return false;
	}

	// TODO: This jsp should be placed in a common folder, set in the OperationManager (OperationMapping)
	@Override
	public String getJsp() {
		return "NDVIStats";
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the geostoreRestUrl
	 */
	public String getGeobatchRestUrl() {
		return geobatchRestUrl;
	}

	/**
	 * @param geobatchRestUrl the geostoreRestUrl to set
	 */
	public void setGeobatchRestUrl(String geobatchRestUrl) {
		this.geobatchRestUrl = geobatchRestUrl;
	}

	/**
	 * @return the geostoreUsername
	 */
	public String getGeobatchUsername() {
		return geobatchUsername;
	}

	/**
	 * @param geobatchUsername the geostoreUsername to set
	 */
	public void setGeobatchUsername(String geobatchUsername) {
		this.geobatchUsername = geobatchUsername;
	}

	/**
	 * @return the geostorePassword
	 */
	public String getGeobatchPassword() {
		return geobatchPassword;
	}

	/**
	 * @param geobatchPassword the geostorePassword to set
	 */
	public void setGeobatchPassword(String geobatchPassword) {
		this.geobatchPassword = geobatchPassword;
	}

	@Override
	public String getFlowID() {
		return flowID;
	}

	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}

	/**
	 * @return the gbinputdirString
	 */
	public String getGbinputdirString() {
		return gbinputdirString;
	}

	/**
	 * @param gbinputdirString the gbinputdirString to set
	 */
	public void setGbinputdirString(String gbinputdirString) {
		this.gbinputdirString = gbinputdirString;
	}

}