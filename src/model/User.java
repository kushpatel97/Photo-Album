package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String username;
	public ArrayList<Album> albums;
	public Album currentAlbum;
	
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";

	public void printAlbums() {
		for (Album album: albums) {
			System.out.println(album.albumName);
		}
	}
	
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
	}
	
	public void addAlbum(Album album) {
		albums.add(album);
	}
	
	public void deleteAlbum(int index) {
		albums.remove(index);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}
	
	public boolean exists(Album albumname) {
		for(Album album : albums) {
			if (album.getName().equals(albumname.albumName)) {
				return true;
			}
		}
		return false;
	}

	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}

	public Album getCurrentAlbum() {
		return currentAlbum;
	}
	
	public Album getAlbum(int index) {
		return albums.get(index);
	}

	public void setCurrentAlbum(Album currentAlbum) {
		this.currentAlbum = currentAlbum;
	}
	
	public ArrayList<Photo> getTaggedPhotos(ArrayList<Tag> taggedlist){
		ArrayList<Photo> photolist = new ArrayList<Photo>();
		//Used to make sure no duplicates
		HashSet<Photo> check = new HashSet<Photo>();
		for(Tag tag : taggedlist) {
			for(Album album : albums) {
				for(Photo photo : album.getPhotos()) {
					if(photo.tagExists(tag.name, tag.value)) {
						check.add(photo);
					}
				}
				
			}
		}
		photolist.addAll(check);
		return photolist;
	}
	
	/**
	 * Compares dates and returns a list of photos within the dated range
	 * @param fromDate start date
	 * @param toDate end date
	 * @return An ArrayList of type photo containing the list of photos within a certain range
	 */
	public ArrayList<Photo> getPhotosInRange(LocalDate fromDate, LocalDate toDate){
		ArrayList<Photo> inrange = new ArrayList<Photo>();
		Calendar startdate = Calendar.getInstance();
		startdate.set(fromDate.getYear(), fromDate.getMonthValue(), fromDate.getDayOfMonth());
		
		Calendar enddate = Calendar.getInstance();
		enddate.set(toDate.getYear(), toDate.getMonthValue(), toDate.getDayOfMonth());
		
		for(Album album : albums) {
			for(Photo photo : album.getPhotos()) {
				Date date = photo.getDate();
				Calendar pDate = Calendar.getInstance();
				pDate.setTime(date);
				
				Calendar today = Calendar.getInstance();
				
				int year = pDate.get(Calendar.YEAR);
				int month = pDate.get(Calendar.MONTH)+1;
				int dateOfMonth = pDate.get(Calendar.DAY_OF_MONTH);
				
				today.set(year, month, dateOfMonth);
				if((today.compareTo(startdate) > 0 && today.compareTo(enddate) < 0) || today.equals(startdate) || today.equals(enddate)) {
					inrange.add(photo);
				}
				
			}
		}
		return inrange;
	}

	public static void save(User pdApp) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(pdApp);
		oos.close();
	}

	public static User load() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		User userList = (User) ois.readObject();
		ois.close();
		return userList;
	}

}
