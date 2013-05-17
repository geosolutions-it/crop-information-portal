package it.geosolutions.nrl.mvc;

import it.geosolutions.nrl.file.FileBrowser;
import it.geosolutions.nrl.file.FileBrowserManager;
import it.geosolutions.nrl.utils.ControllerUtils;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Statistics {
	
	@Autowired
	FileBrowserManager fileBrowserManager;
	
	@RequestMapping(value="/statistics/{regions}/{mask}", method = RequestMethod.GET)
	public String printWelcome(@PathVariable(value = "regions") String regions,@PathVariable(value = "mask") String mask, ModelMap model, Principal principal ) {
		FileBrowser fbregions = fileBrowserManager.getFileBrowser(regions);
		FileBrowser fbmask =fileBrowserManager.getFileBrowser(mask);
		
		if(fbregions != null && fbmask != null){
			Map<String,List<String>> files = new HashMap<String,List<String>>();
			files.put("regions",fbregions.getFiles(".*\\.shp"));
			files.put("mask",fbmask.getFiles(".*"));
			
			model.addAllAttributes(files);
			
		}else{
			
		}
		ControllerUtils.setCommonModel(model);
		model.addAttribute("context", "statistics");
		return "template";
 
	}
	
}