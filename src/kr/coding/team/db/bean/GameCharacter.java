package kr.coding.team.db.bean;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import kr.coding.team.util.ImageUtil;

public class GameCharacter {
	String img;
	int where;
	String name;
	JLabel label;
	
	public GameCharacter(int where, String img){
		Image characterImg01 = new ImageUtil().getImageFromFile(img);
		Image scaledImage = characterImg01.getScaledInstance(60,60,Image.SCALE_SMOOTH);
		ImageIcon characterIcon01 = new ImageIcon(scaledImage);
		this.label = new JLabel();
		this.label.setBounds(20, 540, 70, 70);
		this.label.setIcon(characterIcon01);
		this.setWhere(where);
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getWhere() {
		return where>31 ? where-31 : where;
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
	public JLabel getLabel() {
		return label;
	}
	public void setLabel(JLabel label) {
		this.label = label;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
