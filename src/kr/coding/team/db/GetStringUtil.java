package kr.coding.team.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetStringUtil {
	/**
	 * HTML 파일을 열어서 안의 내용물을 조회하는 함수
	 * @param uri
	 * @return
	 */
	public static String getStringFromUrl(String uri){
		StringBuilder sb = new StringBuilder();
		try {
	        // Create a URL for the desired page
	        URL url = new URL(uri);
	        // Read all the text returned by the server
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        
	        while (in.readLine() != null) {
	        	sb.append(in.readLine());
	        }
	        in.close();
	    } catch (MalformedURLException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		Document doc = Jsoup.parse(sb.toString()); 
		String text = doc.body().text();
		return text;
	}
}
