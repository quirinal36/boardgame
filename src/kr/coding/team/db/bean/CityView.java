package kr.coding.team.db.bean;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CityView extends JFrame {
	City city;
	
	public CityView(City paramCity){
		this.city = paramCity;
		this.setTitle(paramCity.getName());
		this.setVisible(true);
		this.setBounds(100, 100, 300, 200);
		
		setTitleView();
		
		setButtonView();
	}
	
	private void setTitleView(){
		Container mainPanel = getContentPane();
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel("이름: ");
		JLabel label2 = new JLabel(city.getName());
		
		panel.add(label1);
		panel.add(label2);
		panel.setLayout(new FlowLayout());
		mainPanel.add(panel);
	}
	private void setButtonView(){
		Container mainPanel = getContentPane();
		
	}
}
