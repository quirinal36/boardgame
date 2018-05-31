package kr.coding.team.db.bean;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import kr.coding.team.util.ImageUtil;

public class GameCharacter extends JLabel{
	String img;
	int where;
	String name;
	
	public GameCharacter(int where, String img){
		Image characterImg01 = new ImageUtil().getImageFromFile(img);
		Image scaledImage = characterImg01.getScaledInstance(60,60,Image.SCALE_SMOOTH);
		ImageIcon characterIcon01 = new ImageIcon(scaledImage);
		this.setBounds(20, 540, 70, 70);
		this.setIcon(characterIcon01);
		this.setWhere(where);
	}
	public void moveToCity(City city){
		this.setLocation(city.getxLoc(), city.getyLoc());
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getWhere() {
		return where>32 ? where-32 : where;
	}
	public void setWhere(int where) {
		this.where = where;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
