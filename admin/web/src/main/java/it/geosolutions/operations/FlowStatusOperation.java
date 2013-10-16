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

import it.geosolutions.geobatch.services.rest.RESTFlowService;
import it.geosolutions.geobatch.services.rest.exception.NotFoundRestEx;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FlowStatusOperation implements GeoBatchOperation {
	
	/**
	 * The name of this Operation
	 */
	public static String name = "FlowStatus";
	
	/**
	 * The path were to GET the form and POST the request
	 * Typically all lower case
	 */
	private String path = "flowstatus";
	
	/**
	 * File extension this Operation will work on
	 */
	private String[] extensions = {};
	
	/**
	 * Directory where to scan for files
	 */
	private String basedirString = "G:/OpenSDIManager/test_shapes/";

	private String geobatchRestUrl = "http://localhost:8081/geobatch/rest/";

	private String geobatchUsername = "admin";

	private String geobatchPassword = "admin";

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
		return "flowstatus";
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
	public String getJsp(ModelMap model, HttpServletRequest request, List<MultipartFile> files) {
		
		System.out.println("getJSP di flowStatus");
		
		String id = request.getParameter("id");
		if(id != null) {
			model.addAttribute("consumer_id", id);
		}else {
			model.addAttribute("consumer_id", "");
		}

		return "flowstatus";

	}

	@Override
	public Object getBlob(Object inputParam) {
		
		// TODO: refactor this
		RESTFlowService service = (RESTFlowService) ((Object[])inputParam)[0];
		HttpServletRequest request = (HttpServletRequest) ((Object[])inputParam)[1];
		ModelMap model = (ModelMap) ((Object[])inputParam)[2];
		
		String id = request.getParameter("id");
		if(id != null) {
			
			try {
				it.geosolutions.geobatch.services.rest.model.RESTConsumerStatus.Status status = service.getConsumerStatus(id).getStatus();
				String log =  service.getConsumerLog(id);
				switch (status) {
					case SUCCESS:
						model.addAttribute("messageType", "success");
						model.addAttribute("messageJsp", "flow_success");
						model.addAttribute("operationMessage", "Success!");
						model.addAttribute("flowMessage", "Operation Ended Successfully");
						break;
					case FAIL:
						model.addAttribute("messageType", "error");
						model.addAttribute("messageJsp", "flow_general");
						model.addAttribute("operationMessage", "Failed!");
						model.addAttribute("flowMessage", "Operation Failed");
						break;
					case RUNNING:
						model.addAttribute("messageType", "block");
						model.addAttribute("messageJsp", "flow_success");
						model.addAttribute("operationMessage", "Success!");
						model.addAttribute("flowMessage", "Operation Running");
						break;
					default:
						break;
				}
				model.addAttribute("flowLog", log);
				
			} catch (NotFoundRestEx e) {
				model.addAttribute("messageType", "error");
				model.addAttribute("messageJsp", "flow_general");
				model.addAttribute("operationMessage", "Error");
				model.addAttribute("flowMessage", "Consumer not found, NotFoundRestEx");

			}catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("messageType", "error");
				model.addAttribute("messageJsp", "flow_general");
				model.addAttribute("operationMessage", "Error");
				model.addAttribute("flowMessage", "Consumer not found");
			}
			
		}else {
			model.addAttribute("messageType", "info");
			model.addAttribute("messageJsp", "flow_general");
			model.addAttribute("operationMessage", "Error!");
			model.addAttribute("flowMessage", "No Consumer ID passed");
			return "common/static_messages";
		}
		        
		return "common/static_messages";
	}

	@Override
	public String getFlowID() {
		// TODO: parametric!!!
		return "ds2ds_zip2pg";
	}

}