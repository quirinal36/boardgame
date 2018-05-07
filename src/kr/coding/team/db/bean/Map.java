package kr.coding.team.db.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.simple.JSONObject;

public class Map {
	public static final String ID_KEY = "idMap";
	public static final String NAME_KEY = "name";
	public static final String XLOC_KEY = "x_loc";
	public static final String YLOC_KEY = "y_loc";
	
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
	public int getyLoc() {
		return yLoc;
	}
	public void setyLoc(int yLoc) {
		this.yLoc = yLoc;
	}
	public static Map parseMap(JSONObject json){
		Map result = new Map();
		
		result.setId((int)json.get(ID_KEY));
		result.setName((String)json.get(NAME_KEY));
		result.setxLoc((int)json.get(XLOC_KEY));
		result.setyLoc((int)json.get(YLOC_KEY));
		
		return result;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
