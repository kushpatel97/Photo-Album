package model;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;
	// public Image image;
	public String photoname;
	public File pic;
	public ArrayList<Tag> taglist;
	public String caption;
	public String date = "No Date";
	public String filepath;
	
	public Photo(File pic, String photoname) {
		this.photoname = photoname; 
		this.pic = pic;
	}
	
	public void setFilePath(String fp) {
		this.filepath = fp;
	}
	
	public String getFilePath() {
		return filepath;
	}
	
	public void setPic(File pic) {
		this.pic = pic;
	}
	
	public File getPic() {
		return this.pic;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public String getName() {
		return photoname;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
