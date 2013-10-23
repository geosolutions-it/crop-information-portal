package it.geosolutions.nrl.security;

import it.geosolutions.geostore.core.model.User;
import it.geosolutions.geostore.services.rest.AdministratorGeoStoreClient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
/**
 * Wrap geostore Rest Services to allow Authentication using Geostore Users
 * @author Lorenzo Natali
 *
 */
public class GeoStoreAuthenticationProvider implements AuthenticationProvider {
	/**
	 * The rest service 
	 */
	String geoStoreRestURL;
	
	/**
	 * a list of allowed Roles
	 */
	List<String> allowedRoles;
	
	/**
	 * GeoStoreClient in the applicationContext 
	 */
	@Autowired
	AdministratorGeoStoreClient geoStoreClient;

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	@Override
	public Authentication authenticate(Authentication authentication) {
		String pw = (String) authentication.getCredentials();
		String us = (String) authentication.getPrincipal();
		// We use the credentials for all the session in the GeoStore client
		geoStoreClient.setUsername(us);
		geoStoreClient.setPassword(pw);
		geoStoreClient.setGeostoreRestUrl(geoStoreRestURL);
		User user = null;
		try {
			user = geoStoreClient.getUserDetails();
		} catch (Exception e) {
			return null;
		}
		if (user != null) {
			String role = user.getRole().toString();
			if (!roleAllowed(role))
				return null;
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			Authentication a = new UsernamePasswordAuthenticationToken(us, pw,
					authorities);
			// a.setAuthenticated(true);
			return a;
		} else {
			return null;
		}

	}

	private boolean roleAllowed(String role) {
		for (String allowed : allowedRoles) {
			if (allowed != null) {
				if (allowed.equals(role))
					return true;
			}
		}
		return false;
	}

	// GETTERS AND SETTERS
	public List<String> getAllowedRoles() {
		return allowedRoles;
	}

	public void setAllowedRoles(List<String> roleFilter) {
		this.allowedRoles = roleFilter;
	}

	public String getGeoStoreRestURL() {
		return geoStoreRestURL;
	}

	public void setGeoStoreRestURL(String geoStoreRestURL) {
		this.geoStoreRestURL = geoStoreRestURL;
	}

}
