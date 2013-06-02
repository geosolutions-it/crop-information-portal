package it.geosolutions.nrl.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CropDescriptor {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
	String id;
    
    @Column(updatable=true,nullable=false)
	String label;
    
    @Column(updatable=true,nullable=false)
	String[] seasons;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String[] getSeasons() {
		return seasons;
	}
	public void setSeasons(String[] seasons) {
		this.seasons = seasons;
	}
	
}
