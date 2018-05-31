package kr.coding.team.db;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtil {

	/**
	 * String 형식의 데이터를 받아와서
	 * JSON 으로 파싱하는 메소드
	 * 
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	public JSONObject parseToJson(String data) throws ParseException{
		JSONParser parser = new JSONParser();
		
		JSONObject dataJSON = new JSONObject();
		
		dataJSON = (JSONObject) parser.parse(data);
		return dataJSON;
	}
	
}
