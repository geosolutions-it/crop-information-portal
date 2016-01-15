
-- ***********************
-- *  Table: fertilizer  *
-- ***********************

CREATE TABLE fertilizer
(
  id serial NOT NULL,
  district character varying(255),
  month character varying(255) NOT NULL,
  nutrient character varying(255) NOT NULL,
  offtake_tons double precision NOT NULL,
  province character varying(255),
  year integer NOT NULL,
  month_num integer,
  CONSTRAINT fertilizer_pkey PRIMARY KEY (id),
  CONSTRAINT fertilizer_province_key UNIQUE (province, district, year, month, nutrient)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE fertilizer
  OWNER TO geoserver;

  
-- ***********************
-- *  Table: irrigation  *
-- ***********************

CREATE TABLE irrigation
(
  id serial NOT NULL,
  decade integer NOT NULL,
  decade_absolute integer NOT NULL,
  decade_year integer NOT NULL,
  district character varying(255) NOT NULL,
  month character varying(255) NOT NULL,
  province character varying(255) NOT NULL,
  river character varying(255) NOT NULL,
  waterflow integer,
  withdrawal integer,
  year integer NOT NULL,
  CONSTRAINT irrigation_pkey PRIMARY KEY (id),
  CONSTRAINT irrigation_year_key UNIQUE (year, month, decade, province, district, river)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE irrigation
  OWNER TO geoserver;

  
-- *************************
-- *  Table: market_price  *
-- *************************

CREATE TABLE market_price
(
  id serial NOT NULL,
  crop character varying(255) NOT NULL,
  decade integer NOT NULL,
  decade_absolute integer NOT NULL,
  decade_year integer NOT NULL,
  district character varying(255) NOT NULL,
  market_price double precision NOT NULL,
  market_price_unit double precision NOT NULL,
  month character varying(255) NOT NULL,
  province character varying(255) NOT NULL,
  year integer NOT NULL,
  CONSTRAINT market_price_pkey PRIMARY KEY (id),
  CONSTRAINT market_price_year_key UNIQUE (year, month, decade, province, district, crop)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE market_price
  OWNER TO geoserver;

