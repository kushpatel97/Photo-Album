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
	
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !( o instanceof Tag) ) {
			return false;
		}
		
		Tag tag = (Tag) o;
		
		
		return tag.name.equals(this.name) && tag.value.equals(this.value);
	}
}
