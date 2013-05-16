package it.geosolutions.geostore.services.rest;

import it.geosolutions.geostore.core.model.User;

public class AdministratorGeoStoreClient extends GeoStoreClient {
	public User getUser(long id){
		return getBaseWebResource("users","user",id).get(User.class);
		
	}
	
	public User getUserDetails(){
		return getBaseWebResource("users","user","details").get(User.class);
	}
}
