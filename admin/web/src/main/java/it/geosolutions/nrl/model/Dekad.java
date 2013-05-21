package it.geosolutions.nrl.model;

import it.geosolutions.nrl.utils.DekadUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dekad {
	private static final String REG_DEK= "^[0-9]{4}[0-1]{1}[0-9]{1}[0-3]{0,1}[1-3]{1}$";
	private static final String REG_TIME_SERIES = ""; //TODO
	private int absoluteDekad;
	
	public Dekad(String time){
		setTime(time);
		
	}
	public void setTime(String time){
		Pattern pattern = Pattern.compile(REG_DEK);
		Matcher match = pattern.matcher(time);
		if(match.matches()){
			int year = Integer.parseInt(time.substring(0,4));
			int month = Integer.parseInt(time.substring(4,6));
			int dek =Integer.parseInt(time.substring(6));
			absoluteDekad = DekadUtils.absouluteFromYearMonthDek(year, month, dek);

		}
	}
	public int getSeasonalDekad(){
		
		return (getDekadInYear() + 5 ) %36 + 1;
	}
	
	public int getDekad(){
		return (absoluteDekad - 1 )% 3 + 1;
	}
	
	public int getDekadInYear(){
		return (absoluteDekad -1) %36 +1;
	}
	
	public int getAbsoluteDekad(){
		return this.absoluteDekad;
	}
	
}
