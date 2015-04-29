package com.chartadvisor.model;

public class Property {
	
	public Property(String propertyCompleteName, String propertyName,
			String propertyType) {
		super();
		this.propertyCompleteName = propertyCompleteName;
		this.propertyName = propertyName;
		this.propertyType = propertyType;
	}

	private String propertyCompleteName;
	private String propertyName;
	private String propertyType;
	private String propertyValue;
	

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getPropertyCompleteName() {
		return propertyCompleteName;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public void setPropertyCompleteName(String propertyName) {
		this.propertyCompleteName = propertyName;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public Property() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Property(String propertyName, String propertyType) {
		super();
		this.propertyName = propertyName;
		this.propertyType = propertyType;
	}
	
	public boolean equals(Object o){
		if(!(o instanceof Property))
			return false;
		Property p = (Property)o;
		if(p.getPropertyName().equalsIgnoreCase(propertyName))
			if(p.getPropertyType().equalsIgnoreCase(propertyType))
				return true;
		return false;
	}
	
	public String toString(){
		return new String(propertyName+":"+propertyType);
		//return new String(propertyName);
	}

}
