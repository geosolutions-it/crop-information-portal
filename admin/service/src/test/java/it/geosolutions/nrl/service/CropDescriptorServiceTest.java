/**
 * 
 */
package it.geosolutions.nrl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import it.geosolutions.nrl.model.CropData;
import it.geosolutions.nrl.persistence.dao.CropDescriptorDAO;
import it.geosolutions.nrl.persistence.dao.impl.BaseDAOTest;
import it.geosolutions.nrl.persistence.dao.impl.BaseTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Admin
 *
 */
public class CropDescriptorServiceTest extends BaseTest{
	@Autowired
	public CropDescriptorDAO cropDescriptorDao;
	
	@Before
    public void before() {
	        removeAll();
	    }

	    
	    protected void removeAll() {
	        removeAllCropData();
	    }

	    protected void removeAllCropData() {
	       

	    }

	@Test
	public void crationTest(){
		
		new CropDescriptorService();
		
		
	}
}
