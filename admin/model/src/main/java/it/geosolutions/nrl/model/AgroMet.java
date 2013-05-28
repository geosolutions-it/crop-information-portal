package it.geosolutions.nrl.model;

public class AgroMet {
	Long rowid;// integer,
    String district ;//character varying(255) NOT NULL,
    String province;// character varying(255) NOT NULL,
    int year;// integer NOT NULL,
    String month;// character varying(3) NOT NULL,
    int dec;// TODO to dek if possible in table"dec" bigint NOT NULL,
    String factor; //factor character varying(255) NOT NULL,
    double value;//; double precision,
    int s_yr;// integer,
    int s_dec;// integer
}
