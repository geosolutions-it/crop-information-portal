package it.geosolutions.nrl.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileBrowser {
	private String baseDir="";
	public List<String> getFiles(String regex){
		File dir = new File(baseDir);
		if( !dir.isDirectory() ) return null;
		File[] children = dir.listFiles();
		List<String> ret = new ArrayList<String>();
		for (int i=0;i<children.length;i++){
			String name = children[i].getName();
			Pattern pattern = Pattern.compile(regex);
			Matcher match = pattern.matcher(name);
			if(match.matches()){
				ret.add(children[i].getName());
			}
		}
		return ret;
	}
	public List<String> getDirs(){
		File dir = new File(baseDir);
		if( !dir.isDirectory() ) return null;
		File[] children = dir.listFiles();
		List<String> ret = new ArrayList<String>();
		for (int i=0;i<children.length;i++){
			if(children[i].isDirectory()){
				ret.add(children[i].getName());
			}
		}
		return ret;
	}
	public String getBaseDir() {
		return baseDir;
	}
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
		
	}
	
}
