package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Allows users to manipulate photos
 * @author Kush Patel
 * @author Alex Louie
 *
 */
public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	// public Image image;
	
	/**
	 * Photo name
	 */
	public String photoname;
	
	/**
	 * file of the picture
	 */
	public File pic;
	
	/**
	 * List of tags
	 */
	public ArrayList<Tag> taglist;
	
	/**
	 * Photo caption
	 */
	public String caption;
	
	/**
	 * Filepath of photo
	 */
	public String filepath;
	
	/**
	 * Calendar instance
	 */
	public Calendar cal;
	
	/**
	 * Current date
	 */
	public Date date;
	
	/**
	 * Check if stock photo
	 */
	public boolean isStock = false;
	
	/**
	 * Photo constructor
	 * @param pic
	 * @param photoname
	 */
	public Photo(File pic, String photoname) {
		this.photoname = photoname; 
		if (pic != null) this.pic = new File(photoname);
		else this.pic = pic;
		this.taglist = new ArrayList<Tag>();
		cal = new GregorianCalendar();
		cal.set(Calendar.MILLISECOND, 0);
		this.date = cal.getTime();
	}
	
	/**
	 * Sets captions
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * Adds tags to photo
	 * @param name name of tag
	 * @param value value of tag
	 */
	public void addTag(String name, String value) {
		taglist.add(new Tag(name,value));
	}
	
	/**
	 * Removes tag
	 * @param name name of tag
	 * @param value value of tag
	 */
	public void removeTag(String name, String value) {
		for(int i = 0; i < taglist.size(); i++) {
			Tag cur = taglist.get(i);
			if(cur.name.toLowerCase().equals(name.toLowerCase()) && cur.value.toLowerCase().equals(value.toLowerCase())) {
				taglist.remove(i);
			}
		}
	}
	
	/**
	 * Check if tag exists
	 * @param name
	 * @param value
	 * @return
	 */
	public boolean tagExists(String name, String value) {
		for(int i = 0; i < taglist.size(); i++) {
			Tag cur = taglist.get(i);
			if(cur.name.toLowerCase().equals(name.toLowerCase()) && cur.value.toLowerCase().equals(value.toLowerCase())) {
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 *  
	 * @return Dates
	 */
	public Date getDate() {
		return date;
	}

	
	/**
	 * 
	 * @return list of tags
	 */
	public ArrayList<Tag> getTagList(){
		return taglist;
	}
	
	/**
	 * sets file path
	 * @param fp
	 */
	public void setFilePath(String fp) {
		this.filepath = fp;
	}
	
	/**
	 *  
	 * @return file path of photo
	 */
	public String getFilePath() {
		return filepath;
	}
	
	/**
	 * Sets picture
	 * @param pic
	 */
	public void setPic(File pic) {
		this.pic = pic;
	}
	
	/**
	 * 
	 * @return picture
	 */
	public File getPic() {
		return this.pic;
	}
	
	/**
	 *  
	 * @return caption of photo
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * 
	 * @return name of photo
	 */
	public String getName() {
		return photoname;
	}
	
	/**
	 * to string
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
	public static void save(Photo pdApp) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(pdApp);
		oos.close();
	}
	
	/**
	 * Loads from dat file
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static User load() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		User userList = (User) ois.readObject();
		ois.close();
		return userList;
	}
}
