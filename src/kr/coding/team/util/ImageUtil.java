package kr.coding.team.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

	/**
	 * 이미지를 파일로부터 가져오는 역할
	 * 
	 * @param fileName
	 * @return
	 */
	public Image getImageFromFile(String fileName){
		Image image = null;
		
		try{
			image = ImageIO.read(new File(fileName));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return image;
	}
}
