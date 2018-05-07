package kr.coding.team;

import javax.swing.SwingUtilities;

import kr.coding.team.view.IndexView;

public class MainClass {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				IndexView gameView = new IndexView("보드게임");
				gameView.setVisible(true);
			}
		});
	}
}
