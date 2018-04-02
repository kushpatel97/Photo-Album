package model;

import java.io.Serializable;
import java.util.*;

public class Album implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String albumName;
	public ArrayList<Photo> photoslist;
	
	public Album(String albumName) {
		this.albumName = albumName; 
		photoslist = new ArrayList<Photo>();
	}
	
	public void addPhoto() {
		
	}
}
