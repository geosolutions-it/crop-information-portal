package it.geosolutions.nrl.mvc;

import it.geosolutions.geostore.core.model.User;
import it.geosolutions.geostore.services.rest.AdministratorGeoStoreClient;
import it.geosolutions.geostore.services.rest.model.RESTUser;
import it.geosolutions.geostore.services.rest.model.UserList;
import it.geosolutions.nrl.utils.ControllerUtils;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Users {
	@Autowired
	AdministratorGeoStoreClient geoStoreClient;
	
	Integer pageSize=10;
	
	@RequestMapping(value="/users/{page}", method = RequestMethod.GET)
	public String printWelcome(@PathVariable(value = "page") Integer page,ModelMap model, Principal principal ) {
		UserList ul = geoStoreClient.getUsers(page, pageSize);
		if(ul !=null){
			List<RESTUser> users = ul.getList();
			model.addAttribute("users",users );
		}
		ControllerUtils.setCommonModel(model);
		model.addAttribute("context", "users");
		return "template";
 
	}
	//GETTERS AND SETTERS 
	public AdministratorGeoStoreClient getGeoStoreClient() {
		return geoStoreClient;
	}

	public void setGeoStoreClient(AdministratorGeoStoreClient geoStoreClient) {
		this.geoStoreClient = geoStoreClient;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}