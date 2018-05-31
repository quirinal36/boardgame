package kr.coding.team.db.bean;

import java.awt.Dimension;
import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import kr.coding.team.util.SoundUtil;

public class Dice extends JLabel{
	ImageIcon rollingDiceIcon;
	SoundUtil sound;
	ImageIcon numberOfDiceIcon;
	
	public Dice(){
		sound = new SoundUtil();
		
		rollingDiceIcon = new ImageIcon("img/dice.gif");
		numberOfDiceIcon = new ImageIcon();
		this.setPreferredSize(new Dimension(200, 100));
		this.setVisible(false);
	}
	public void setRolling(){
		this.setVisible(true);
		this.setIcon(rollingDiceIcon);
		sound.bgmPlay(new File("bgm/dice.wav"));
	}
	public int makeDiceNumber(){
		Random random = new Random();
		int rollingResult_01 = random.nextInt(6)+1; // 1~6 중 하나의 숫자를 만든다. (주사위1)
		int rollingResult_02 = random.nextInt(6)+1; // 1~6 중 하나의 숫자를 만든다. (주사위2)
		int rollingResult = rollingResult_01 + rollingResult_02; // 두개의 주사위의 합을 가져온다.
		
		numberOfDiceIcon  = new ImageIcon("img/"+rollingResult+".png");
		this.setIcon(numberOfDiceIcon);
		
		return rollingResult;
	}
}
