package kr.coding.team.view;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import kr.coding.team.db.DBconn;
import kr.coding.team.db.bean.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 
 * @author leehg
 *
 */
public class IndexView extends JFrame implements ActionListener{
	// logger 는 system.out.println 과 비슷한 역할을 하는 객체입니다.
	public static final Logger logger = Logger.getLogger(IndexView.class.getSimpleName());
	
	// 주사위 던지기 버튼
	JButton btnRolldice;
	// 주사위가 움직이는 공간
	JLabel labelRollingDice;
	// 주사위가 움직이는 시간
	Timer timer;
	
	
	// 게임 캐릭터
	private ArrayList<JLabel> characters;
	// 땅 (강릉, 오락실 ... )
	private ArrayList<JLabel> mapList;
	// 데이터베이스에서 얻어온 맵 리스트
	private ArrayList<Map> maps;
	
	/**
	 * Create the frame.
	 */
	public IndexView(String title) {
		this.setTitle(title);

		// 게임이 시작됨과 동시에 실행되는 배경음악
		bgmPlay(new File("bgm/bgm.wav"));

		// 닫힘 버튼 클릭 할 때 실행되는 기능을 적어줌
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 프레임의 위치와 크기를 정해줌
		setBounds(100, 100, 700, 800);
		
		// 캐릭터를 담을 (아직은 비어있는) 객체를 만든다
		characters = new ArrayList<>();
		// 도시들을 담을 (아직은 비어있는) 객체를 만든다
		mapList = new ArrayList<>();
		
		new Runnable() {
			public void run() {
				DBconn db = new DBconn();
				maps = db.getData();
			}
		}.run();
		
		// 프레임의 가장 최상위 컨테이너를 받아온다
		Container contentPane = getContentPane();
		// 동서남북에 배치를 할 수 있도록 레이아웃을 정해준다.
		contentPane.setLayout(new BorderLayout());
		// 게임 내의 모든 배치들을 정해주는 메소드
		setBorderLayout(contentPane);
	}
	
	/**
	 * 
	 * @param parent
	 */
	private void setBorderLayout(Container parent){
		// 배경화면에 나올 이미지를 파일로 가져온다.
		final Image image = getImageFromFile("img/map.png");
		
		// 게임 컨트롤러 (버튼, 음량, 점수등)
		JPanel topPane = new JPanel();
		topPane.setLayout(new BorderLayout());
		
		// 게임이 이뤄지는곳 (맵, 주사위)
		JPanel gamezone = new JPanel(){
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
		
		// 맵의 배경색을 정한다.
		gamezone.setBackground(Color.WHITE);
		
		// 컨트롤러부분과 맵 부분을 추가한뒤, 나누는 패널을 만들어
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPane, gamezone);
		// splitPane 은 위 - 아래를 구분하는 패널이며
		// 위 : topPane
		// 아래 : gamezone 
		// 이 추가된다.
		gamezone.setLayout(null);
		
		// 캐릭터 1번째
		JLabel character01 = new JLabel();
		Image characterImg01 = getImageFromFile("img/c_01.png");
		Image scaledImage = characterImg01.getScaledInstance(60,60,Image.SCALE_SMOOTH);
		ImageIcon characterIcon01 = new ImageIcon(scaledImage);
		character01.setBounds(20, 540, 70, 70);
		character01.setIcon(characterIcon01);
		gamezone.add(character01);
		characters.add(character01);
		
		// 캐릭터 2번째
		JLabel character02 = new JLabel();
		Image characterImg02 = getImageFromFile("img/c_02.png");
		Image scaledImage02 = characterImg02.getScaledInstance(60,60,Image.SCALE_SMOOTH);
		ImageIcon characterIcon02 = new ImageIcon(scaledImage02);
		character02.setBounds(40, 540, 70, 70);
		character02.setIcon(characterIcon02);
		gamezone.add(character02);
		characters.add(character02);
		
//		Iterator<Map> iter = maps.iterator();
//		while(iter.hasNext()){
		for(Map map : maps){
			logger.info(map.toString());
		
			JLabel lblNewLabel = new JLabel("", SwingConstants.CENTER);
			lblNewLabel.setText(map.getName());
			lblNewLabel.setBounds(map.getxLoc(), map.getyLoc(), 98, 30);
			lblNewLabel.setFont(new Font("굴림", Font.BOLD, 15));
			gamezone.add(lblNewLabel);
			mapList.add(lblNewLabel);
			
			//if(map.getId() > 25)break;
			
			logger.info("game zone added");
		}
		
		// 이 버튼을 클릭하면 주사위가 구르는 그림이 나오며
		// 게임이 진행된다.
		btnRolldice = new JButton("주사위");
		topPane.add(btnRolldice, BorderLayout.WEST);
		btnRolldice.addActionListener(this);
		
		// 구르는 주사위 이미지
		ImageIcon imageIcon = new ImageIcon("img/dice.gif");
		
		// 주사위 이미지가 실제로 보여지는 공간
		labelRollingDice = new JLabel("");
		labelRollingDice.setPreferredSize(new Dimension(200, 100));
		labelRollingDice.setIcon(imageIcon);
		labelRollingDice.setVisible(false);
		
		topPane.add(labelRollingDice, BorderLayout.CENTER);
		
		// 상단부터 100만큼은 버튼 및 주사위 컨트롤러, 그 아래는 맵을 그린다.
		splitPane.setDividerLocation(100);
		// 중간에 벽을 없앤다.
		splitPane.setDividerSize(0);
		// 부모 패널에 방금 만든 패널을 추가한다.
		parent.add(splitPane);
	}
	
	/**
	 * 이미지를 파일로부터 가져오는 역할
	 * 
	 * @param fileName
	 * @return
	 */
	private Image getImageFromFile(String fileName){
		Image image = null;
		
		try{
			image = ImageIO.read(new File(fileName));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return image;
	}
	
	/**
	 * 이미지를 URL 로부터 가져오는 역할
	 * 예) param url : "http://dev.bacoder.kr/temp/img/map.png"
	 * @return
	 */
	private Image getImageFromUrl(String imgUrl){
		Image image = null;
		
		try{
			image = ImageIO.read(new URL(imgUrl));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return image;
	}
	
	/**
	 * 버튼 클릭이 발생 했을 때 
	 * 어떤 일이 발생하는지 기술해준다.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnRolldice){ // 클릭된 객체가 주사위버튼 이라면
			btnRolldice.setEnabled(false);
			
			if(!labelRollingDice.isVisible()){ // 만일 주사위 그림이 보이지 않는다면
				labelRollingDice.setVisible(true); // 주사위 그림을 올릴 라벨을 보이게 만들어주고
			}
			
			// 주사위 라벨의 Icon 으로 움직이는 주사위 그림을 올려준다.
			ImageIcon imageIcon = new ImageIcon("img/dice.gif");
			labelRollingDice.setIcon(imageIcon);
			
			// console 에 System.out.println 처럼 뿌려주는 역할
			logger.info("roll dice button clicked");
			bgmPlay(new File("bgm/dice.wav")); // 주사위가 굴러가는소리
			
			ActionListener taskPerformer = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					timer.stop();
					
					// 임의의 값을 만드는 랜덤 객체
					Random random = new Random();
					int rollingResult_01 = random.nextInt(6)+1; // 1~6 중 하나의 숫자를 만든다. (주사위1)
					int rollingResult_02 = random.nextInt(6)+1; // 1~6 중 하나의 숫자를 만든다. (주사위2)
					logger.info("rollingResult_01::"+rollingResult_01 + "/rollingResult_02::"+rollingResult_02);
					int rollingResult = rollingResult_01 + rollingResult_02; // 두개의 주사위의 합을 가져온다.
					
					// 주사위 결과를 그림으로 보여준다.
					ImageIcon imageIcon = new ImageIcon("img/"+rollingResult+".png");
					labelRollingDice.setIcon(imageIcon);
					
					// 캐릭터의 제일 처음위치
					int moveY = 540;
					
					if(rollingResult < 8){
						moveY = moveY - (64 * rollingResult); // 주사위 결과에 따라 움직일 좌표값
						logger.info("moveY :: " + moveY);
						characters.get(0).setLocation(20, moveY);
					}
					
					btnRolldice.setEnabled(true);
				}
			};
			
			int delay = 1000; //milliseconds 즉 1초
			// 1초간 딜레이를 준 뒤 캐릭터를 움직이자.
			// 주사위가 구르고, 결과값이 나오는 시간동안 기다렸다가
			// 캐릭터가 움직여야 한다.
			timer = new Timer(delay, taskPerformer);
			timer.start();
		}
	}
	
	/**
	 * 파일을 받아와
	 * 시스템에서 음악을 재생시킨다
	 * 단 한번만 실행됨
	 * 
	 * @param file
	 */
	public void bgmPlay(File file){
	    try{
	        final Clip clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
	        clip.addLineListener(new LineListener(){
	            @Override
	            public void update(LineEvent event){
	                if (event.getType() == LineEvent.Type.STOP)
	                    clip.close();
	            }
	        });

	        clip.open(AudioSystem.getAudioInputStream(file));
	        clip.start();
	    }
	    catch (Exception exc){
	        exc.printStackTrace(System.out);
	    }
	}
}
