package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
	public Photo currentPhoto;
	
	public Album(String albumName) {
		this.albumName = albumName; 
		photoslist = new ArrayList<Photo>();
	}
	
	public static Comparator<Album> sortByAZ = new Comparator<Album>() {
		public int compare(Album a1, Album a2) {
			return a1.albumName.compareTo(a2.albumName);
		}
	};
	
	public static Comparator<Album> sortByZA = new Comparator<Album>() {
		public int compare(Album a1, Album a2) {
			return a1.albumName.compareTo(a2.albumName)*-1;
		}
	};
	
	public static Comparator<Album> sortByIP = new Comparator<Album>() {
		public int compare(Album a1, Album a2) {
			int num1 = a1.photoCount;
			int num2 = a2.photoCount;
			
			if (num1 > num2) return -1;
			if (num2 > num1) return 1;
			return 0;
		}
	};
	
	public static Comparator<Album> sortByDP = new Comparator<Album>() {
		public int compare(Album a1, Album a2) {
			int num1 = a1.photoCount;
			int num2 = a2.photoCount;
			
			if (num1 > num2) return 1;
			if (num2 > num1) return -1;
			return 0;
		}
	};
	
	public static Comparator<Album> sortByID = new Comparator<Album>() {
		public int compare(Album a1, Album a2) {
			Date date1 = null; 
			if (!a1.photoslist.isEmpty()) {
				date1 = a1.getPhotos().get(0).date;
				for (Photo photo: a1.photoslist) {
					if (photo.date.before(date1)) {
						date1 = photo.date;
					}
				}
			}
			
			Date date2 = null; 
			if (!a2.photoslist.isEmpty()) {
				date2 = a2.getPhotos().get(0).date;
				for (Photo photo: a2.photoslist) {
					if (photo.date.before(date2)) {
						date2 = photo.date;
					}
				}
			}
			
			if (date1 != null && date2 != null) {
				if (date1.before(date2)) return 1;
				if (date2.before(date1)) return -1;	
			}
			
			return 0;
		}
	};
	
	public static Comparator<Album> sortByDD = new Comparator<Album>() {
		public int compare(Album a1, Album a2) {
			Date date1 = null; 
			if (!a1.photoslist.isEmpty()) {
				date1 = a1.getPhotos().get(0).date;
				for (Photo photo: a1.photoslist) {
					if (photo.date.after(date1)) {
						date1 = photo.date;
					}
				}
			}
			
			Date date2 = null; 
			if (!a2.photoslist.isEmpty()) {
				date2 = a2.getPhotos().get(0).date;
				for (Photo photo: a2.photoslist) {
					if (photo.date.after(date2)) {
						date2 = photo.date;
					}
				}
			}
			
			if (date1 != null && date2 != null) {
				if (date1.before(date2)) return 1;
				if (date2.before(date1)) return -1;	
			}
			
			return 0;
		}
	};
	
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
	
	public String getFirstDate() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("E, M-d-y 'at' h:m:s a");
		Date date = null; 
		String dateStr = "No Date";
		if (!photoslist.isEmpty()) {
			date = this.getPhotos().get(0).date;
			for (Photo photo: photoslist) {
				if (photo.date.before(date)) {
					date = photo.date;
				}
			}
			dateStr = dateFormatter.format(date);
		}
		
		return dateStr;
	}
	
	public String getLastDate() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("E, M-d-y 'at' h:m:s a");
		Date date = null; 
		String dateStr = "No Date";
		if (!photoslist.isEmpty()) {
			date = this.getPhotos().get(0).date;
			for (Photo photo: photoslist) {
				if (photo.date.after(date)) {
					date = photo.date;
				}
			}
			dateStr = dateFormatter.format(date);
		}
		
		return dateStr;
	}
	
	
	
	
	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
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
	
	
	public Photo getCurrentPhoto() {
		return currentPhoto;
	}

	public void setCurrentPhoto(Photo currentPhoto) {
		this.currentPhoto = currentPhoto;
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
