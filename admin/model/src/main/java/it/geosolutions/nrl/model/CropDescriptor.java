package it.geosolutions.nrl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "CropDescriptor")
@Table(name = "cropdescriptor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "cropdescriptor")
@XmlRootElement(name = "CropDescriptor")
public class CropDescriptor {
    @Id
    @GeneratedValue
	String id;
    
    @Column(updatable=true,nullable=false)
	String label;
    
    @Column(updatable=true,nullable=false)
	String[] seasons; // ???
	
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

	public void setSeasons(Season seasons) {
		this.seasons = seasons.toArray();
	}
	
}
