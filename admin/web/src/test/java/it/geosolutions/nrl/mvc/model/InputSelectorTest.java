package it.geosolutions.nrl.mvc.model;

import static org.junit.Assert.assertNotNull;

import it.geosolutions.nrl.mvc.model.statistics.InputSelectorConfig;
import it.geosolutions.nrl.mvc.model.statistics.StatisticsConfigList;
import it.geosolutions.nrl.mvc.model.statistics.InputSelectorElement;
import java.io.File;

import javax.xml.bind.JAXB;

import org.junit.Before;
import org.junit.Test;

public class InputSelectorTest {
	private File file;
	
	@Before
	public void getFile(){
		String url = getClass().getClassLoader().getResource("testInputSelectors.xml").getFile();
		file = new File(url);
		
		
		
	}
	
	@Test
	public void loadInputSelectorsTest(){
		assertNotNull(file);
		StatisticsConfigList list  = JAXB.unmarshal(file, StatisticsConfigList.class);
		System.out.println(list.getConfigs().size()) ;
		for(InputSelectorConfig isc : list.getConfigs()){
			System.out.print(isc.getId());
			System.out.print(" [");
			for(InputSelectorElement ise : isc.getElements()){
				System.out.print(ise.getId());
				System.out.print(',');
			}			
			System.out.print("]");


		}





		
	}
}
