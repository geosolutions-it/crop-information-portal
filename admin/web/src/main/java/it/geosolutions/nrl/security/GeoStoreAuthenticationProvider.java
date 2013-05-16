package it.geosolutions.nrl.security;

import it.geosolutions.geostore.core.model.User;
import it.geosolutions.geostore.core.model.enums.Role;
import it.geosolutions.geostore.services.rest.AdministratorGeoStoreClient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

public class GeoStoreAuthenticationProvider implements AuthenticationProvider{
		String geoStoreRestURL;
		
	    public String getGeoStoreRestURL() {
			return geoStoreRestURL;
		}

		public void setGeoStoreRestURL(String geoStoreRestURL) {
			this.geoStoreRestURL = geoStoreRestURL;
		}

		@Override
	    public boolean supports(Class<? extends Object> authentication) {
	        return authentication.equals(UsernamePasswordAuthenticationToken.class);
	    }
	 
	    @Override
	    public Authentication authenticate(Authentication authentication) {
	        	String pw = (String) authentication.getCredentials();
	        	String us =(String) authentication.getPrincipal();
	        	AdministratorGeoStoreClient gsc= new AdministratorGeoStoreClient();
	        	gsc.setUsername(us);
	        	gsc.setPassword(pw);
	        	gsc.setGeostoreRestUrl(geoStoreRestURL);
	        	User user = null;
	        	try{
	            user = gsc.getUserDetails();
	        	}catch(Exception e){
	        		return null;
	        	}
	        	if(user!=null){
	        		String role = user.getRole().toString();
	        		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        		authorities.add(new GrantedAuthorityImpl("ROLE_" + role ));
	        		Authentication a = new UsernamePasswordAuthenticationToken(us,pw,authorities);
	        		//a.setAuthenticated(true);
	        		return a;
	        	}else{
	        		return null;
	        	}
	       
	    }
	
}
