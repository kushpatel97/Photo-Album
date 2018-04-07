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
	public int photoCount = 0;
	
	public Album(String albumName) {
		this.albumName = albumName; 
		photoslist = new ArrayList<Photo>();
	}
	
	public String getName() {
		return this.albumName;
	}
	
	public void rename(String name) {
		this.albumName = name;
	}
	
	public void addPhoto() {
		photoCount++;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
