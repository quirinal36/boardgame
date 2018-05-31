package kr.coding.team.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import kr.coding.team.db.GetStringUtil;
import kr.coding.team.db.JsonUtil;
import kr.coding.team.db.bean.Dice;
import kr.coding.team.db.bean.GameCharacter;
import kr.coding.team.db.bean.City;
import kr.coding.team.util.ImageUtil;
import kr.coding.team.util.SoundUtil;

public class GameView extends JFrame implements ActionListener{
	private final String getMapJsp = "http://dev.bacoder.kr/temp/getCity.jsp";
	Logger logger = Logger.getLogger(getClass().getSimpleName());
	// 캐릭터들 이 저장되는 공간
	ArrayList<GameCharacter> gamerList;
	// 주사위 및 숫자가 표시되는 공간
	Dice dice;
	// 주사위 던지기 버튼
	JButton btnRolldice;
	// 소리 관련된 함수가 저장된 공간
	SoundUtil sound;
	// 주사위가 움직이는 시간
	Timer timer;
	// 현재 움직여야하는 캐릭터
	int gamerOrder=0;
	// 맵이 저장되어있는 해쉬맵
	private HashMap<Integer, City> maps;
	
	public GameView(String title) {
		this.setTitle(title);
		gamerList = new ArrayList<>();
		sound = new SoundUtil();
		maps = new HashMap<Integer, City>();
		
		sound.bgmPlay(new File("bgm/bgm.wav"));
		
		setMapInfo();
		
		setView();
	}
	
	public int getGamerOrder() {
		return gamerOrder;
	}

	public void setGamerOrder(int gamerOrder) {
		if(gamerOrder > 1)gamerOrder = 0;
		this.gamerOrder = gamerOrder;
	}

	private void setView(){
		// 닫힘 버튼 클릭 할 때 실행되는 기능을 적어줌
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 프레임의 위치와 크기를 정해줌
		setBounds(100, 100, 700, 800);
		// 프레임의 가장 최상위 컨테이너를 받아온다
		Container contentPane = getContentPane();
		// 동서남북에 배치를 할 수 있도록 레이아웃을 정해준다.
		contentPane.setLayout(new BorderLayout());

		dice = new Dice();
		
		JPanel gamezone = setGamePanel(new ImageUtil().getImageFromFile("img/map.png"));
		setCityNames(gamezone);
		setGamers(gamezone);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, setTopPane(), gamezone);
		// 컨트롤러부분과 맵 부분을 추가한뒤, 나누는 패널을 만들어
		splitPane.setDividerLocation(100);
		// 중간에 벽을 없앤다.
		splitPane.setDividerSize(0);
		contentPane.add(splitPane);
	}
	
	private void setCityNames(JPanel gamezone){
		SortedSet<Integer> keys = new TreeSet<>(maps.keySet());
		for(Integer mapId : keys){
			City map = maps.get(mapId);
			
			JLabel lblNewLabel = new JLabel("", SwingConstants.CENTER);
			lblNewLabel.setText(map.getName());
			lblNewLabel.setBounds(map.getxLoc(), map.getyLoc(), 98, 30);
			lblNewLabel.setFont(new Font("굴림", Font.BOLD, 15));
			gamezone.add(lblNewLabel);
		}
	}
	// 게임이 이뤄지는곳 (맵, 주사위)
	private JPanel setGamePanel(final Image image){
		final JPanel gamezone = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// 배경화면에 그림을 그린다.
				// 현재 떠 있는 창의 가로/세로 길이와 비례하도록 배경그림을 조절한뒤
				// 화면에 배경을 그려준다.
				Image scaledImage = image.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
				g.drawImage(scaledImage, 0, 0, null);
			}
		};
		
		gamezone.setLayout(null);
		
		return gamezone;
	}
	// 게임 컨트롤러 (버튼, 음량, 점수등)
	private JPanel setTopPane(){
		JPanel topPane = new JPanel();
		topPane.setLayout(new BorderLayout());
		setBtnRollDice(topPane);
		topPane.add(dice, BorderLayout.CENTER);
		return topPane;
	}
	private void setBtnRollDice(JPanel topPane){
		btnRolldice = new JButton("주사위");
		topPane.add(btnRolldice, BorderLayout.WEST);
		btnRolldice.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnRolldice){ // 클릭된 객체가 주사위버튼 이라면
			btnRolldice.setEnabled(false);
			
			if(!dice.isVisible()){ // 만일 주사위 그림이 보이지 않는다면
				dice.setVisible(true); // 주사위 그림을 올릴 라벨을 보이게 만들어주고
			}
			dice.setRolling();
			
			ActionListener taskPerformer = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					timer.stop();
					btnRolldice.setEnabled(true);
					int rollingResult = dice.makeDiceNumber();
					
					GameCharacter curGamer = gamerList.get(getGamerOrder());
					
					curGamer.setWhere(rollingResult + curGamer.getWhere());
					
					City city = maps.get(curGamer.getWhere());
					curGamer.moveToCity(city);
					
					setGamerOrder(getGamerOrder() + 1);
				}
			};
			
			int delay = 1000; //milliseconds 즉 1초
			// 1초간 딜레이를 준 뒤 캐릭터를 움직이자.
			// 주사위가 구르고, 결과값이 나오는 시간동안 기다렸다가
			// 캐릭터가 움직여야 한다.
			timer = new Timer(1000, taskPerformer);
			timer.start();
		}
	}
	private void setGamers(JPanel gamezone){
		// 캐릭터를 추가합니다.
		GameCharacter superman = new GameCharacter(32, "img/c_01.png");
		GameCharacter batman = new GameCharacter(32, "img/c_02.png");
		gamerList.add(superman);
		gamerList.add(batman);
		gamezone.add(superman.getLabel());
		gamezone.add(batman.getLabel());
	}
	private void setMapInfo(){
		String mapJsonStr = GetStringUtil.getStringFromUrl(getMapJsp);
		JsonUtil jsonUtil = new JsonUtil();
		try {
			JSONObject jsonMap = jsonUtil.parseToJson(mapJsonStr);
			JSONArray array = (JSONArray)jsonMap.get("list");
			if(array != null){
				for(int i=0; i<array.size(); i++){
					JSONObject item = (JSONObject)array.get(i);
					City map = City.parseMap(item);
					
					maps.put(map.getId(), map);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
