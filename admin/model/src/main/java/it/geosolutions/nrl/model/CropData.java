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

@Entity(name = "CropData")
@Table(name = "cropdata", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"crop" , "district" , "province" , "year"})})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "cropdata")
@XmlRootElement(name = "CropData")
public class CropData {

    @GeneratedValue
    @Id
    private Long id;

//    private Long rowid ;
    @Column(nullable = false, updatable = false)
    private String crop;// character varying(255) //NOT NULL,
    @Column(nullable = false, updatable = false)
    private String district; //district character varying(255) NOT NULL,
    @Column(nullable = false, updatable = false)
    private String province;// character varying(255) NOT NULL,
    @Column(nullable = false, updatable = false)
    private int year;//; integer NOT NULL,

    @Column
    private String years;// character varying(15),

    @Column
    private Double area;// double precision,

    @Column
    private Double production;// double precision,

    @Column
    private Double yield;// double precision,

    //CONSTRAINT cropdata_pkey PRIMARY KEY (crop , district , province , year )
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getProduction() {
        return production;
    }

    public void setProduction(Double production) {
        this.production = production;
    }

    public Double getYield() {
        return yield;
    }

    public void setYield(Double yield) {
        this.yield = yield;
    }
}
