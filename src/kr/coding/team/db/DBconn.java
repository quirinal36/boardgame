package kr.coding.team.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import kr.coding.team.db.bean.City;

public class DBconn {
	private String userName 	= "root";
	private String password 	= "789gagul";
	private String dbms 		= "mysql";
	private String dbName 		= "new_schema";
	private String serverName 	= "35.194.236.5";
	private int portNumber 		= 3306;
	
	public void getMapsFromURL(String getMapJsp) throws ParseException{
		String html = GetStringUtil.getStringFromUrl(getMapJsp);
		System.out.println(html);
		
		JsonUtil jsonUtil = new JsonUtil();
		JSONObject json = jsonUtil.parseToJson(html);
		
		
	}
	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
	    Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", this.userName);
	    connectionProps.put("password", this.password);

	    if (this.dbms.equals("mysql")) {
	    	final String url = "jdbc:" + this.dbms + "://" +
	                   this.serverName +
	                   ":" + this.portNumber + "/" +
	                   this.dbName + "?" +
	                   "useSSL=false";
	    	System.out.println("url :: " + url);
	    	
	        conn = DriverManager.getConnection(
	                   url,
	                   connectionProps);
	    } else if (this.dbms.equals("derby")) {
	        conn = DriverManager.getConnection(
	                   "jdbc:" + this.dbms + ":" +
	                   this.dbName +
	                   ";create=true",
	                   connectionProps);
	    }
	    System.out.println("Connected to database");
	    return conn;
	}
	public ArrayList<City> getData(){
		ArrayList<City> result = new ArrayList<>();
		
		Connection conn = null;
		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("SELECT * FROM Map");

			while(rs.next()){
				City map = new City();
				map.setId(rs.getInt(City.ID_KEY));
				map.setName(rs.getString(City.NAME_KEY));
				map.setxLoc(rs.getInt(City.XLOC_KEY));
				map.setyLoc(rs.getInt(City.YLOC_KEY));
				
				result.add(map);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public HashMap<Integer, City> getDataToHashMap(){
		HashMap<Integer, City> result = new HashMap<>();
		
		Connection conn = null;
		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("SELECT * FROM Map");

			while(rs.next()){
				City map = new City();
				map.setId(rs.getInt(City.ID_KEY));
				map.setName(rs.getString(City.NAME_KEY));
				map.setxLoc(rs.getInt(City.XLOC_KEY));
				map.setyLoc(rs.getInt(City.YLOC_KEY));
				
//				result.add(map);
				System.out.println(map.toString());
				result.put(map.getId(), map);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
