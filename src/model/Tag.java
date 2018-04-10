package model;

import java.io.Serializable;

public class Tag implements Serializable {
	public String name;
	public String value;
	
	public Tag(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	// more functions
}
