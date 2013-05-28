package it.geosolutions.nrl.model;

public class CropData {
	  Long rowid ;
	  String crop;// character varying(255) //NOT NULL,
	  String district ; //district character varying(255) NOT NULL,
	  String province ;// character varying(255) NOT NULL,
	  int year;//; integer NOT NULL,
	  String years;// character varying(15),
	  Double area;// double precision,
	  Double production;// double precision,
	  Double yield;// double precision,
	  //CONSTRAINT cropdata_pkey PRIMARY KEY (crop , district , province , year )
	public Long getRowid() {
		return rowid;
	}
	public void setRowid(Long rowid) {
		this.rowid = rowid;
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
