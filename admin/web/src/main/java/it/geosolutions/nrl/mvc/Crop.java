package it.geosolutions.nrl.mvc;

import it.geosolutions.geostore.core.model.User;
import it.geosolutions.geostore.core.model.UserAttribute;
import it.geosolutions.nrl.utils.ControllerUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Crop {

	@RequestMapping(value="/crops/", method = RequestMethod.GET)
	public String handleGet(ModelMap model) {
 
		ControllerUtils.setCommonModel(model);
		model.addAttribute("context", "crops");
		return "template";
 
	}
	@RequestMapping(value = "/crops/create", method = RequestMethod.GET)
	public String createUser(ModelMap model) {
		

		return "crops/create";

	}
}
