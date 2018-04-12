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

import controller.UserController;


/**
 * 
 * Album class that users can add pictures too
 * @author Kush Patel
 * @author Alex Louie
 *
 */
public class Album implements Serializable{
	

	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	/**
	 * Name of the album
	 */
	public String albumName;
	
	/**
	 * List of photos stored in the current albums
	 */
	public ArrayList<Photo> photoslist;
	
	/**
	 * Number of photos in a album
	 */
	public int photoCount = 0;
	
	/**
	 * Current photo in the album
	 */
	public Photo currentPhoto;
	
	/**
	 * Constructor for Album
	 * @param albumName
	 */
	public Album(String albumName) {
		this.albumName = albumName; 
		photoslist = new ArrayList<Photo>();
	}
	
	/**
	 * Comparator for sorting alphabetically
	 */
	public static Comparator<Album> sortByAZ = new Comparator<Album>() {
		public int compare(Album a1, Album a2) {
			return a1.albumName.compareTo(a2.albumName);
		}
	};
	
	/**
	 * Comparator for sorting alphabetically (reverse)
	 */
	public static Comparator<Album> sortByZA = new Comparator<Album>() {
		public int compare(Album a1, Album a2) {
			return a1.albumName.compareTo(a2.albumName)*-1;
		}
	};
	
	/**
	 * Comparator for sorting by size of albums - increasing
	 */
	public static Comparator<Album> sortByIP = new Comparator<Album>() {
		public int compare(Album a1, Album a2) {
			int num1 = a1.photoCount;
			int num2 = a2.photoCount;
			
			if (num1 > num2) return -1;
			if (num2 > num1) return 1;
			return 0;
		}
	};
	
	/**
	 * Comparator for sorting by size of albums - decreasing
	 */
	public static Comparator<Album> sortByDP = new Comparator<Album>() {
		public int compare(Album a1, Album a2) {
			int num1 = a1.photoCount;
			int num2 = a2.photoCount;
			
			if (num1 > num2) return 1;
			if (num2 > num1) return -1;
			return 0;
		}
	};
	
	/**
	 * Comparator for sorting by image by date added
	 */
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
			} else if (date1 == null && date2 !=null) {
				return 1;
			} else if (date1 != null && date2 == null) {
				return -1;
			}
			
			return 0;
		}
	};
	
	/**
	 * Comparator for sorting by image by date added - reversed
	 */
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
			} else if (date1 == null && date2 !=null) {
				return 1;
			} else if (date1 != null && date2 == null) {
				return -1;
			}
			
			return 0;
		}
	};
	
	/**
	 * Checks if a photo exists in an album
	 * @param fp
	 * @return true if it exists, false if not
	 */
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
	
	/**
	 * 
	 * @return Date of when photo was first added
	 */
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
	
	/**
	 * 
	 * @return Date of last altered
	 */
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
	
	
	
	/**
	 * 
	 * @return album name
	 */
	public String getAlbumName() {
		return albumName;
	}

	/**
	 * Sets album name
	 * @param albumName
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	/**
	 * 
	 * @return name of album
	 */
	public String getName() {
		return this.albumName;
	}
	
	/**
	 * Renames album
	 * @param name
	 */
	public void rename(String name) {
		this.albumName = name;
	}
	
	/**
	 * Adds photot to album
	 * @param photo
	 */
	public void addPhoto(Photo photo) {
		photoslist.add(photo);
		photoCount++;
	}
	
	/**
	 * remove photos from album
	 * @param index
	 */
	public void deletePhoto(int index) {
		photoslist.remove(index);
		photoCount--;
	}
	
	/**
	 *  
	 * @return list of photos in array
	 */
	public ArrayList<Photo> getPhotos() {
		return photoslist;
	}
	
	/**
	 * 
	 * @return current photo
	 */
	public Photo getCurrentPhoto() {
		return currentPhoto;
	}
	
	/**
	 * Sets current photo
	 * @param currentPhoto
	 */
	public void setCurrentPhoto(Photo currentPhoto) {
		this.currentPhoto = currentPhoto;
	}

	/**
	 * to String
	 */
	@Override
	public String toString() {
		return getName();
	}
	
	/**
	 * Save's state to .dat file
	 * @param pdApp
	 * @throws IOException
	 */
	public static void save(Album pdApp) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(pdApp);
		oos.close();
	}
	
	
	/**
	 * Loads from dat file
	 * @return lit of users
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Superuser load() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		Superuser userList = (Superuser) ois.readObject();
		ois.close();
		return userList;
	}
}
