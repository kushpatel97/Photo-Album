package model;

import java.io.Serializable;
import java.util.*;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String username;
	public ArrayList<Album> albums;
	public Album currentAlbum;

	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
	}
	
	public void addAlbum(String aName) {
		Album album = new Album(aName);
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

	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}

	public Album getCurrentAlbum() {
		return currentAlbum;
	}

	public void setCurrentAlbum(Album currentAlbum) {
		this.currentAlbum = currentAlbum;
	}


}
