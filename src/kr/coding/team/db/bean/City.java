package kr.coding.team.db.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.simple.JSONObject;

public class City {
	public static final String ID_KEY = "id";
	public static final String NAME_KEY = "name";
	public static final String XLOC_KEY = "xLoc";
	public static final String YLOC_KEY = "yLoc";
	
	int id;
	String name;
	int xLoc;
	int yLoc;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(Long id) {
		this.id = id.intValue();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getxLoc() {
		return xLoc;
	}
	public void setxLoc(int xLoc) {
		this.xLoc = xLoc;
	}
	public void setxLoc(Long xLoc) {
		this.xLoc = xLoc.intValue();
	}
	public int getyLoc() {
		return yLoc;
	}
	public void setyLoc(int yLoc) {
		this.yLoc = yLoc;
	}
	public void setyLoc(Long yLoc) {
		this.yLoc = yLoc.intValue();
	}
	public static City parseMap(JSONObject json){
		City result = new City();
		
		if(json.get(ID_KEY) != null) result.setId((Long)json.get(ID_KEY));
		if(json.get(NAME_KEY) != null) result.setName((String)json.get(NAME_KEY));
		if(json.get(XLOC_KEY) != null) result.setxLoc((long)json.get(XLOC_KEY));
		if(json.get(YLOC_KEY) != null) result.setyLoc((long)json.get(YLOC_KEY));
		
		return result;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
