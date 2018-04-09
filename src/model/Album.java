package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Album implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	public String albumName;
	public ArrayList<Photo> photoslist;
	public int photoCount = 0;
	public String firstDate = "No Date";
	public String lastDate = "No Date";
	
	public Album(String albumName) {
		this.albumName = albumName; 
		photoslist = new ArrayList<Photo>();
	}
	
	public boolean exists(String fp) {
		if (photoslist.size() > 0 && !fp.isEmpty()) {
			for(Photo photos : photoslist) {
				if(photos.getFilePath().equals(fp)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String getName() {
		return this.albumName;
	}
	
	public void rename(String name) {
		this.albumName = name;
	}
	
	public void addPhoto(Photo photo) {
		photoslist.add(photo);
		photoCount++;
	}
	
	public void deletePhoto(int index) {
		photoslist.remove(index);
		photoCount--;
	}
	
	public ArrayList<Photo> getPhotos() {
		return photoslist;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public static void save(Album pdApp) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(pdApp);
		oos.close();
	}

	public static Superuser load() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		Superuser userList = (Superuser) ois.readObject();
		ois.close();
		return userList;
	}
}
