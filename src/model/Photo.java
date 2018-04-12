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

public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	// public Image image;
	public String photoname;
	public File pic;
	public ArrayList<Tag> taglist;
	public String caption;
	public String filepath;
	public Calendar cal;
	public Date date;
	public boolean isStock = false;
	
	
	public Photo(File pic, String photoname) {
		this.photoname = photoname; 
		if (pic != null) this.pic = new File(photoname);
		else this.pic = pic;
		this.taglist = new ArrayList<Tag>();
		cal = new GregorianCalendar();
		cal.set(Calendar.MILLISECOND, 0);
		this.date = cal.getTime();
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public void addTag(String name, String value) {
		taglist.add(new Tag(name,value));
	}
	
	public void removeTag(String name, String value) {
		for(int i = 0; i < taglist.size(); i++) {
			Tag cur = taglist.get(i);
			if(cur.name.toLowerCase().equals(name.toLowerCase()) && cur.value.toLowerCase().equals(value.toLowerCase())) {
				taglist.remove(i);
			}
		}
	}
	
	public boolean tagExists(String name, String value) {
		for(int i = 0; i < taglist.size(); i++) {
			Tag cur = taglist.get(i);
			if(cur.name.toLowerCase().equals(name.toLowerCase()) && cur.value.toLowerCase().equals(value.toLowerCase())) {
				return true;
			}
		}
		return false;
		
	}
	

	public Date getDate() {
		return date;
	}


	public ArrayList<Tag> getTagList(){
		return taglist;
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
	
	public static void save(Photo pdApp) throws IOException {
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
