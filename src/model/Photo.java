package model;

import java.io.Serializable;
import java.util.*;

public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;
	// public Image image;
	public String photoname;
	public ArrayList<Tag> taglist;
	public String caption;
	public String date = "No Date";
	
	public Photo(String photoname) {
		this.photoname = photoname; 
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
