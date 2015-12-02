import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.File;
import javax.sound.sampled.*;

////int pos = player[nowPlayer].getPos(); 현재위치
public class Game extends JFrame implements ActionListener, Serializable{
	
	private static final long serialVersionUID = 7743785439592198738L;
	//건물 구입 하기 설정	
	
	private int Byul = 0;
	private int Building = 1;
	private int Hotel = 2;
	
	//플레이어 상태 설정
	private int Start = 0;
	private int Run = 1;
	private int Finish  = 2;
	
	String what;					// 어떤 황금열쇠 정보
	int keyX = 300;  			    // 황금열쇠 출력 좌표 지정
	int keyY = 100;
	int keyW = 400;
	int keyH = 50;
	int x;									// 좌표값
	int y;
	int tunrInfo;							// 턴 정보
	
	static int now = 0;
	private int numOfPlayer;				// 플레이어 수
	private int pos=0;						// 황금열쇠 최초 위치
	private int plus=0;						// 황금열쇠 추가 위치
	private int[][] landXY;					// 해당 토지들의 xy좌표를 저장할 배열
	private static int nowPlayer;			// 해당 턴의 유저
	
	private boolean flagDraw;
	private int isMoving = Start; 		    // 움직임 상태 설정
	private int dice1; 						// 주사위 결과값 저장 
	private int dice2;

	private Player player[];				// Player 객체들
	private Land square[];					// Land 객체들
	private Icon userFace[];				// 플레이어 아이콘 객체
	private Icon think;
	private Icon button;
	private Icon button2;
	private Icon buy;
	private Icon byulB;
	private Icon buildingB;
	private Icon hotelB;
	
	private JPanel jpDisplaySquareInfo;		// 도시 정보
	private JPanel jpPieceUserFace[];		// 보드판에서 이동하는 Player 얼굴 Icon
	private JLabel jlLandInfo[];			// 도시정보 담을 배열
	private JLabel jlKeyInfo;				// 황금열쇠 정보 담을 배열
	private JLabel jlUpInfo;				    // 업그레이 정보 출력
	private JLabel jlDonaInfo;				// 사회복지 기금 출력
	private JLabel jlAsset[];				// 자산 정보
	private JLabel jlthink;					// 현재 플레이어 표시 그림
	JPanel jpDice1;					// 주사위 출력 
	JPanel jpDice2;
	JLabel jlDice1;					// 주사위 출력
	JLabel jlDice2;
	private JPanel jpDisplayLandOfOwner[];	// 소유한 토지 on 그림
	
	private JButton deal;					// 구매버튼
	private JButton jbRolling;				// 구매 하위 버튼, 별장, 빌딩, 호텔
	private JButton jbByul;
	private JButton jbBuilding;
	
	private JButton jbByulUP;				// 업그레이드 버튼
	private JButton jbBuildingUP;
	private JButton jbHotelUP;
	
	private JButton jbHotel;
	private JButton jbSave;
	private JButton jbLoad;

	public Game(int numOfPlayer){
		this.numOfPlayer = numOfPlayer; 	     	// 플레이 인원 수 
		nowPlayer = 0;					   			// 현재 유저 
		jpPieceUserFace = new JPanel[numOfPlayer];  // 인원 수 만큼 말 그림 생성
		
		jlLandInfo = new JLabel[9];			   		// 도시정보 출력
		for(int i=0; i<9; i++)jlLandInfo[i] = new JLabel();
		
		jlKeyInfo = new JLabel();
		
		jlAsset = new JLabel[numOfPlayer];
		
		initPlayer();				 // 플레이어 초기화
		initLand();					 // 땅 정보 초기화
		
		drawPlayer(); 				 // 플레이어 그리기
		drawBoard();				 // 판 그리기
		drawOwner(); 				 // 플레이어 소유토지 그리기
		
		displayLandInfoInit(); 		 // 도시 세부 정보 출력
		initPeace();			 	 // 플레이어 말 정보 셋팅	
		
		JPanel jp = new JPanel();
		jbRolling = new JButton("주사위 굴리기");
		button = new ImageIcon("src\\image\\play.png"); //주사위 버튼 그림 삽입
		jbRolling.setIcon(button);
		
		jp.add(jbRolling);
		jbRolling.addActionListener(this);
		jp.setBounds(300, 500, 200, 200); 		//주사위 컴포넌트 배치 (x좌표, y좌표, 넓이, 높이);
		getContentPane().add(jp, 1);		

		deal = new JButton("토지 구입");		    // 버튼 이름 설정
		jbByul = new JButton("별장 구입");
		jbBuilding = new JButton("빌딩 구입");
		jbHotel = new JButton("호텔 구입");
		
		jbByulUP = new JButton("별장 업그레이드");
		jbBuildingUP = new JButton("빌딩 업그레이드");
		jbHotelUP = new JButton("호텔 업그레이드");
		
		jbSave = new JButton("게임 저장");
		jbLoad = new JButton("게임 로드");
		
		buy = new ImageIcon("src\\image\\buy.png"); //버튼 그림 삽입
		deal.setIcon(buy);
		byulB = new ImageIcon("src\\image\\house.png");
		jbByul.setIcon(byulB);
		jbByulUP.setIcon(byulB);
		buildingB = new ImageIcon("src\\image\\building.png");
		jbBuilding.setIcon(buildingB);
		jbBuildingUP.setIcon(buildingB);
		hotelB = new ImageIcon("src\\image\\hotel.png");
		jbHotel.setIcon(hotelB);
		jbHotelUP.setIcon(hotelB);
		
		deal.addActionListener(this); // 각 버튼에 대한 액션리스너 설정
		jbByul.addActionListener(this);
		jbBuilding.addActionListener(this);
		jbHotel.addActionListener(this);
		jbByulUP.addActionListener(this);
		jbBuildingUP.addActionListener(this);
		jbHotelUP.addActionListener(this);
		jbSave.addActionListener(this);
		jbLoad.addActionListener(this);

		madeComponent(deal, 580, 500, 220, 50); // 버튼 위치 및 크기 설정
		madeComponent(jbByul, 580, 550, 220, 50);
		madeComponent(jbBuilding, 580, 600, 220, 50);
		madeComponent(jbHotel, 580, 650, 220, 50);
		
		madeComponent(jbSave, 580, 450, 220, 50);
		madeComponent(jbLoad, 580, 400, 220, 50);
		
		madeComponent(jbByulUP, 580, 550, 220, 50);
		madeComponent(jbBuildingUP, 580, 580, 220, 50);
		madeComponent(jbHotelUP, 580, 650, 220, 50);
		
		deal.setVisible(false); // 초기 안 보이는 상태 설정
		jbByul.setVisible(false);
		jbBuilding.setVisible(false);
		jbHotel.setVisible(false);
		jbSave.setVisible(false);
		jbLoad.setVisible(false);
		jbByulUP.setVisible(false);
		jbBuildingUP.setVisible(false);
		jbHotelUP.setVisible(false);
		
		think = new ImageIcon("src\\image\\think.png"); // 현재 턴 그림
		jlthink = new JLabel(think);

	}
	
	public void paint(Graphics g){
		super.paint(g);
		madeComponent(jlthink, 600, 750-(50*nowPlayer), think.getIconWidth(), think.getIconHeight(), 0); //현재 턴 그림 위치 지정

		if(isMoving == Run) movePlayer(); //run 상태라면 말을 움직인다.
		
		int pos = player[nowPlayer].getPos();
		//player+nowPlayer에 +1하면 소유권 가짐
		if(square[pos].owner == 0 && isMoving != Start){ // 현재 말 위치에 땅 주인이 없다면
			deal.setVisible(true); // 토지 구매버튼 활성화
			jbByul.setVisible(false);
			jbBuilding.setVisible(false);
			jbHotel.setVisible(false);
			
		}
		else if(square[pos].owner == nowPlayer+1 && isMoving != Start){ // 아니라면 세부 버튼 활성화
			
			deal.setVisible(false);
			jbBuilding.setVisible(true);
			jbByul.setVisible(true);
			jbHotel.setVisible(true);
			
			if(square[pos].byulUP == nowPlayer+1 && isMoving != Start)
			{jbByul.setVisible(false);jbByulUP.setVisible(true);} //별장을 이미 구입했으면 버튼을 숨기고, 업그레이드 버튼을 표시한다.
		
			if(square[pos].buildingUP == nowPlayer+1 && isMoving != Start)
			{jbBuilding.setVisible(false);jbBuildingUP.setVisible(true);}
			
			if(square[pos].hotelUP == nowPlayer+1 && isMoving != Start)
			{jbHotel.setVisible(false);jbHotelUP.setVisible(true);}
			
		}	
		
	}
	/*
	 * 	if(square[player[nowPlayer].getPos()].byulUP == 0){
				square[player[nowPlayer].getPos()].byulUP = nowPlayer+1;
				}
			if(square[player[nowPlayer].getPos()].byulUP  == nowPlayer+1){
				square[pos].upgrade(50);
				jbByul.setText("별장 업그레이드");
				sound.play(buy);
				}
			else{
			jbByul.setText("별장 구입");
	 */
	
	void movePlayer() // 말 움직임 구현
	{
		int x=0, y=0; // 말 위치
		
		if(nowPlayer==1) x=40; //2번말 지정 (current 1) 1번말 과의 차이 설정 
		
		player[nowPlayer].setPos(1);
		
		int pos = player[nowPlayer].getPos(); //현재위치 가져오기
		jpPieceUserFace[nowPlayer].setBounds(landXY[pos][0]+x, landXY[pos][1]+y, 40, 40); //말 위치 크기 지정
		
		DisplayLandInfo();
	}
	
	void initPeace(){ // 말 초기화
		for(int i=0; i<numOfPlayer; i++){
			int pos = player[i].getPos();	// 각 유저의 위치
			int x=0, y=0;
			if(i==1) x=40;
		
			jpPieceUserFace[i] = new JPanel();      // 초기 위치 셋팅
			jpPieceUserFace[i].setLayout(new BorderLayout(0, 0));
			jpPieceUserFace[i].add(new JLabel(userFace[i]));
			jpPieceUserFace[i].setBounds(landXY[pos][0]+x, landXY[pos][1]+y, 40, 40);
			getContentPane().add(jpPieceUserFace[i], 2);
		}
	}
	
	void drawDiceInfo(int dice1, int dice2){ // 주사위 정보 출력
		int diceY = 280;
		//주사위 수에 맞추어 해당 그림 표시
		Icon dice = new ImageIcon("src\\image\\dice"+dice1+".png");
		JLabel jlDice = new JLabel(dice);
		madeComponent(jlDice, 300, diceY, dice.getIconWidth(), dice.getIconHeight(), 3);
		
		Icon _dice = new ImageIcon("src\\image\\dice"+dice2+".png");
		JLabel jlDice2 = new JLabel(_dice);
		madeComponent(jlDice2, 450, diceY, dice.getIconWidth(), dice.getIconHeight(), 3);
	}
	
	public void actionPerformed(ActionEvent ae){ //버튼 액션
		int normalDice = 1; //사운드 종류 처리
		int doubleDice = 2;
		int buy = 3;
		//int rolling = 4;
		Sound sound = new Sound();
		
		if("주사위 굴리기".equals(ae.getActionCommand()))
		{
			if(player[nowPlayer].status != 3) { //0,1,2 경우
				if(dice1==dice2){player[nowPlayer].status = 3;} // 탈출 상태 변환
				else{
				player[nowPlayer].status = player[nowPlayer].status + 1; // 0부터 시작해서 3회째 탈출 가능
				nowPlayer = (nowPlayer+1)%numOfPlayer; //
				tunrInfo = nowPlayer;
				}
			}
			sound.play(buy);
			isMoving = Run;
			
			Dice castDice = new Dice();
			dice1 = castDice.CastDice();
			dice2 = castDice.CastDice();
			drawDiceInfo(dice1, dice2);
			
			if((player[nowPlayer].getPos() + (dice1+dice2)) >= 40){	// 시작점을 지나면
				player[nowPlayer].plusMoney(20);							// 20만원 지급
				jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"만원");
			}
			
			
			for(int i=0; i<dice1+dice2; i++){ //주사위 결과값 만큼 움직인다.
				try {
					Thread.sleep(30); //말 움직이는 속도 조절
					sound.play(normalDice);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				this.paint(this.getGraphics());
			}
			
			if(dice1==dice2){
				sound.play(doubleDice);
				try { // 더블 딜레이
					for(int i=0; i<dice1+dice2; i++){ // 한번 더!
					Thread.sleep(30);	
					this.paint(this.getGraphics());
				}
				
				jlDonaInfo = new JLabel(player[nowPlayer].name+" 님이 주사위 더블이 나와 2번 움직였습니다.");
				madeComponent(jlDonaInfo, 300, 150, 400, 50, 3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}

			isMoving = Finish; // 말 움직임 '끝' 상태로 바꾸어 준다.
		
			// 황금열쇠 시작 
			// 특수 칸 체크 시작
			if(square[player[nowPlayer].getPos()] == square[2]) { // 황금열쇠 
				displayKey();
			}
			
			if(square[player[nowPlayer].getPos()] == square[7]) { 
				displayKey();
			}
			
			if(square[player[nowPlayer].getPos()] == square[12]) { 
				displayKey();
			}
			
			if(square[player[nowPlayer].getPos()] == square[17]) { 
				displayKey();
			}
			
			if(square[player[nowPlayer].getPos()] == square[22]) { 
				displayKey();
			}
			
			if(square[player[nowPlayer].getPos()] == square[35]) { 
				displayKey();
			}
			
			//////////////////////////////////////////////////////////////// 황금열쇠 끝
		
			if(square[player[nowPlayer].getPos()] == square[20]) // 무인도 상태라면 0  상태로 변환
				player[nowPlayer].status = 0; 			      
			
			if(square[player[nowPlayer].getPos()] == square[38]) { // 사회복지기금 내기
				player[nowPlayer].minusMoney(15); // 15만원 빼기
				player[nowPlayer].bankPlus(15);   // 15만원 은행 입금
				jlDonaInfo = new JLabel("사회복지 기금으로 15만원을 지불 하였습니다.");
				madeComponent(jlDonaInfo, keyX, keyY, keyW, keyH, 3);
				setTitle(""+player[nowPlayer].asset);
				jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"만원");
			}
			
			if(square[player[nowPlayer].getPos()] == square[30]) { // 사회복지 기금 받기
				int bank = player[nowPlayer].bankMinus();
				player[nowPlayer].bankReset(); //초기화
				jlDonaInfo = new JLabel("사회복지 기금으로 모아둔"+bank+"만원을 지급 받았습니다.");
				madeComponent(jlDonaInfo, keyX, keyY, keyW, keyH, 3);
				setTitle(""+player[nowPlayer].asset);
				jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"만원");
			}
			
			if(square[player[nowPlayer].getPos()] == square[10]) { // 우주 정류장
				if((square[32].owner > 0) && (square[32].owner != nowPlayer+1)) // 컬럼비아 주인이 있고 && 내가 주인이 아니라면
				{
					int visiterPos = 32; //컬럼비아 칸
					int ownerPos = (square[32].owner) - 1;
					passFee(visiterPos, ownerPos);
					
					jlDonaInfo = new JLabel("우주 정류장에 왔습니다. 소유자에게 우주여행 비를 지급합니다");
					madeComponent(jlDonaInfo, keyX, keyY, keyW, keyH, 3);
					setTitle(""+player[nowPlayer].asset);
					jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"만원");
				}
				
				if((square[32].owner == 0)) // 컬럼비아호 주인이 없다면
				{
					player[nowPlayer].minusMoney(50); // 50만원 빼기
					player[nowPlayer].bankSpace(50);   // 50만원 은행 입금
					jlDonaInfo = new JLabel("우주여행비로 50만원을 지불하였습니다.");
					madeComponent(jlDonaInfo, keyX, keyY, keyW, keyH, 3);
					setTitle(""+player[nowPlayer].asset);
					jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"만원");
				}
				
			}
			
			if((square[player[nowPlayer].getPos()].owner > 0) // 토지 주인이 있고 && 내가 주인이 아니라면
					&& (square[player[nowPlayer].getPos()].owner != nowPlayer+1)) {

				int visiterPos = player[nowPlayer].getPos();
				int ownerPos = (square[player[nowPlayer].getPos()].owner) - 1;
				passFee(visiterPos, ownerPos);
			}
			
			int pos = player[nowPlayer].getPos();
			
			if(square[pos].owner == nowPlayer+1){
				button2 = new ImageIcon("src\\image\\timer.png"); //턴 버튼 그림 삽입
				jbRolling.setIcon(button2);
				jbRolling.setText("턴 넘기기");
				isMoving = Start;
				return;
			}
			if(square[pos].owner == 0){
				button2 = new ImageIcon("src\\image\\timer.png"); //턴 버튼 그림 삽입
				jbRolling.setIcon(button2);
				jbRolling.setText("턴 넘기기");
				isMoving = Start;
				return;
			}

			else{
				nowPlayer = (nowPlayer+1)%numOfPlayer;
				isMoving = Start;
				deal.setVisible(false);
				jbByul.setVisible(false);
				jbBuilding.setVisible(false);
				jbHotel.setVisible(false);
				update(this.getGraphics());
			}
			
			return;
		} // "주사위 굴리기 if문 끝
		
		jbRolling.setIcon(button); //턴->플레이 버튼 원상복귀

		if("토지 구입".equals(ae.getActionCommand())){ //구매버튼 클릭시
			int pos = player[nowPlayer].getPos();  // 현재 위치
			player[nowPlayer].minusMoney(square[pos].landValue()); // 현재 플레이어의 토지 구매금액 차감
			square[pos].owner = nowPlayer+1; // 오너 상태  부여 p1 = 1, p2=2
			sound.play(buy);
			ShowOwner(false); // 오너 그림 표시
			drawOwner();
		}

		if("별장 구입".equals(ae.getActionCommand())){ 
			int pos = player[nowPlayer].getPos();
			square[pos].byulUP = nowPlayer+1; // 별장 구매목록 추가
			sound.play(buy);
			player[nowPlayer].minusMoney(square[pos].buyBuilding(Byul));
			
		}
		if("빌딩 구입".equals(ae.getActionCommand())){
			int pos = player[nowPlayer].getPos(); 
			square[pos].buildingUP = nowPlayer+1;
			sound.play(buy);
			player[nowPlayer].minusMoney(square[pos].buyBuilding(Building));
		}
		if("호텔 구입".equals(ae.getActionCommand())){
			int pos = player[nowPlayer].getPos();
			square[pos].hotelUP = nowPlayer+1;
			sound.play(buy);
			player[nowPlayer].minusMoney(square[pos].buyBuilding(Hotel));
		}
		if("별장 업그레이드".equals(ae.getActionCommand())){
			int pos = player[nowPlayer].getPos();
			square[pos].upgrade(square[pos].passFee);
			sound.play(buy);
			jlUpInfo = new JLabel(""+square[pos].passFee+"만원으로 별장을 업그레이드 하였습니다. 통행료 "+square[pos].passFee+"만원이 증가합니다.");
			madeComponent(jlUpInfo, keyX, keyY, keyW, keyH, 3);
			player[nowPlayer].minusMoney(square[pos].passFee);
		}
		if("빌딩 업그레이드".equals(ae.getActionCommand())){
			int pos = player[nowPlayer].getPos();
			square[pos].upgrade(square[pos].passFee);
			sound.play(buy);
			jlUpInfo = new JLabel(""+square[pos].passFee+"만원으로 빌딩을 업그레이드 하였습니다. 통행료 "+square[pos].passFee+"만원이 증가합니다.");
			madeComponent(jlUpInfo, keyX, keyY, keyW, keyH, 3);
			player[nowPlayer].minusMoney(square[pos].passFee);
		}
		if("호텔 업그레이드".equals(ae.getActionCommand())){
			int pos = player[nowPlayer].getPos();
			square[pos].upgrade(square[pos].passFee);
			sound.play(buy);
			jlUpInfo = new JLabel(""+square[pos].passFee+"만원으로 호텔을 업그레이드 하였습니다. 통행료 "+square[pos].passFee+" 만원이 증가합니다.");
			madeComponent(jlUpInfo, keyX, keyY, keyW, keyH, 3);
			player[nowPlayer].minusMoney(square[pos].passFee);
		}
		
		
		repaint();
		jbRolling.setText("주사위 굴리기");
		jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"만원");
		drawListProperty();
		nowPlayer = (nowPlayer+1)%numOfPlayer;
		tunrInfo = nowPlayer;
		isMoving = Start;
		deal.setVisible(false);
		jbByul.setVisible(false);
		jbBuilding.setVisible(false);
		jbHotel.setVisible(false);
		jbByulUP.setVisible(false);
		jbBuildingUP.setVisible(false);
		jbHotelUP.setVisible(false);
		
		update(this.getGraphics());
	}
	
	void passFee(int givenPosV, int givenPosO){ //통행료 내기
		
		//방문자, square 오너 위치값
		int visiterPos = givenPosV;
		int ownerPos = givenPosO;

		//통행료
		player[nowPlayer].minusMoney(square[visiterPos].passFee); // 통행료 빼기
		jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"만원");

		player[ownerPos].plusMoney(square[ownerPos].passFee); // 원래 주인은 자산 증가
		jlAsset[ownerPos].setText("Asset : "+player[ownerPos].asset+"만원");
		
		/*현재 구역이 player0 이 토지를 구입한 상태 (1)인 상태 일 경우
		 *현재 player0이 역역에 들어온 상태
		 *1>0&&1!=1:조건 불충족
		 */
		/*현재 구역이 player0 이 토지를 구입한 상태 (1)인 상태 일 경우
		 *현재 player1이 영역에 들어온 상태
		 *1>0&&1!=2:조건 만족
		 *player1 의 자산에서 통행료를 제외 뺀다.
		 *player0 에게 통행료 지급한다.
		 */
	}

	private void displayKey() { //황금열쇠 출력
		Key key = new Key();
		int or = key.randomKey(); //0부정, 1긍정 황금열쇠
		
		if(or==0){ // 부정 황금열쇠 도입
			int result = key.randomMinus();

			
			if(result==100 | result==101){ //부정적 황금열쇠 100=-3 101=서울올릭픽
				isMoving = Run;
				pos = player[nowPlayer].getPos(); //38, 40-2, 101타이페이
				if(result==100){
					jlKeyInfo = new JLabel("황금열쇠. 뒤로 세칸 가시오");
					plus=-1; //뒤로 3칸
				}
				else if(result==101){
					jlKeyInfo = new JLabel("황금열쇠. 올림픽 개최지 서울로 가시오 200만원을 지불합니다.");
					player[nowPlayer].minusMoney(200); // 벌금지불
					plus=39;
				}
				for(int i=0; i<(40-pos)+plus; i++){ // 조건에 맞게 움직이기.
					this.paint(this.getGraphics());
				}
				
				isMoving = Finish;
			}
			
			else if(result!=100 && result!=101){
			what = key.minusText(result); // 황금열쇠 종류 가져오기,
			player[nowPlayer].minusMoney(result); // 벌금지불
			jlKeyInfo = new JLabel("황금열쇠를 고르셨습니다. "+what+"를 "+result+"만원 을 지불합니다.");
			}
			madeComponent(jlKeyInfo, keyX, keyY, keyW, keyH, 3); // 출력 위치
		}
		
		if(or==1){ // 긍정 황금열쇠
			int result = key.randomPlus();
			
			if(result==100 | result==101 | result==102){
				isMoving = Run;
				pos = player[nowPlayer].getPos(); //38, 40-2, 101타이페이
				if(result==100){//처음위치로
					plus=0;
					jlKeyInfo = new JLabel("황금열쇠. 고속도로를 타고 처음으로 가시오");
				}else if(result==101){ //타이페이로
					plus=1;
					jlKeyInfo = new JLabel("황금열쇠. 타이페이로 가시오");
				}
				else if(result==102){ //부산으로
					plus=25;
					jlKeyInfo = new JLabel("황금열쇠. 부산으로 가시오");
				}
				System.out.println(40-pos);
				for(int i=0; i<(40-pos)+plus; i++){ // 전체판 크기 - 현재위치 = x, x만큼 앞으로 이동 = 처음위치
				this.paint(this.getGraphics()); // 처음으로 이동
				}

				isMoving = Finish;
			}
			
			else if(result!=100 && result!=101 && result==102){
			player[nowPlayer].plusMoney(result); // 상금지급, 100처음, 101타이페이, 102부산
			what = key.plusText(result);
			jlKeyInfo = new JLabel("황금열쇠를 고르셨습니다. "+what+"를 "+result+"만원 을 지급합니다.");
			}
			madeComponent(jlKeyInfo, keyX, keyY, keyW, keyH, 3);
		}
		
		setTitle(""+player[nowPlayer].asset);
		jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"만원");
		
	}
	void initPlayer(){
		player = new Player[numOfPlayer];
		String name[] = new String[4];
		name[0] = "이민영"; 
		name[1] = "김태희";
		
		for(int i=0; i<numOfPlayer; i++)
			player[i] = new Player(name[i], 2000);	// 플레이어 생성, 초기자금 2000만원
		}
	
	void initLand(){ // 토지 속성을  배열에 삽입
		// 황금열쇠, 2,7,12,22,35,17
		// -1 소유 불가
		// 토지이름, 토지구입비, 구입한 사용자, 별장, 빌딩, 호텔, 업글비용
		
		square = new Land[40]; // 과제 39판
		square[0] = new Land("출발지", 0, -1, 0, 0, 0, 0);	
		square[1] = new Land("대만", 50, 0, 50, 50, 50, 50);
		square[2] = new Land("황금열쇠", 0, -1, 0, 0, 0, 0); //특수칸
		square[3] = new Land("홍콩", 50, 0, 50, 50, 50, 50);
		square[4] = new Land("마닐라", 50, 0, 50, 50, 50, 50);
		square[5] = new Land("제주도", 50, 0, 50, 50, 50, 50);
		square[6] = new Land("싱가포르", 50, 0, 50, 50, 50, 50);
		square[7] = new Land("황금열쇠", 0, -1, 0, 0, 0, 0); //특수칸
		square[8] = new Land("카이로", 50, 0, 50, 50, 50, 50);
		square[9] = new Land("이스탄불", 50, 0, 50, 50, 50, 50);
		
		square[10] = new Land("우주여행", 0, -1, 0, 0, 0, 0);  // 우주정류장
		square[11] = new Land("아테네", 50, 0, 50, 50, 50, 50);
		square[12] = new Land("황금열쇠", 0, -1, 0, 0, 0, 0); //특수칸
		square[13] = new Land("코펜하겔", 50, 0, 50, 50, 50, 50);
		square[14] = new Land("스톡홀름", 50, 0, 50, 50, 50, 50);
		square[15] = new Land("콩코드 여객기", 0, -1, 0, 0, 0, 0);
		square[16] = new Land("취리히", 50, 0, 50, 50, 50, 50);
		square[17] = new Land("황금열쇠", 0, -1, 0, 0, 0, 0); //특수칸
		square[18] = new Land("베를린", 50, 0, 50, 50, 50, 50);
		square[19] = new Land("몬트리올", 50, 0, 50, 50, 50, 50);
		
		square[20] = new Land("무인도", 0, -1, 0, 0, 0, 0);
		square[21] = new Land("부에노스 아이레스", 50, 0, 50, 50, 50, 50);
		square[22] = new Land("황금열쇠", 0, -1, 0, 0, 0, 0); //특수칸
		square[23] = new Land("상파울루", 50, 0, 50, 50, 50, 50);
		square[24] = new Land("시드니", 50, 0, 50, 50, 50, 50);
		square[25] = new Land("부산", 50, 0, 50, 50, 50, 50);
		square[26] = new Land("하와이", 50, 0, 50, 50, 50, 50);
		square[27] = new Land("리스본", 50, 0, 50, 50, 50, 50);		
		square[28] = new Land("퀸 엘리자베스 호", 0, -1, 0, 0, 0, 0);
		square[29] = new Land("마드리드", 50, 0, 50, 50, 50, 50);
		
		square[30] = new Land("사회기금", 0, -1, 0, 0, 0, 0);
		square[31] = new Land("도쿄", 50, 0, 50, 50, 50, 50);
		square[32] = new Land("컬럼비아 호", 200, 0, 0, 0, 0, 200); //
		square[33] = new Land("파리", 50, 0, 50, 50, 50, 50);
		square[34] = new Land("로마", 50, 0, 50, 50, 50, 50);
		square[35] = new Land("황금열쇠", 0, -1, 0, 0, 0, 0); //특수칸
		square[36] = new Land("런던", 50, 0, 50, 50, 50, 50);
		square[37] = new Land("뉴욕", 50, 0, 50, 50, 50, 50);
		square[38] = new Land("사회복지기금 내는곳", 0, -1, 0, 0, 0, 0);
		square[39] = new Land("서울", 50, 0, 50, 50, 50, 50);
		
	}
	void ShowOwner(boolean isVisible){
		for(int i=0; i<40; i++){
			jpDisplayLandOfOwner[i].setVisible(isVisible);
		}
	}
	void drawOwner(){ // 오너 구매 표시하기
		jpDisplayLandOfOwner = new JPanel[40];
		JLabel jl;
		flagDraw = false;
		int x=800, y=800; // 판 크기 지정
		getContentPane().setLayout(null);
		
		for(int i=1; i<=40; i++){ //square 40개
			jpDisplayLandOfOwner[i-1] = new JPanel(); //0 부터 시작해서 바탕그림을 깔아준다.
			jpDisplayLandOfOwner[i-1].setLayout(new BorderLayout());
			int ownCount = square[i-1].owner;
			Icon image = new ImageIcon("src\\image\\mine"+ownCount+".jpg"); // 오너 그림 경로
			jl = new JLabel(image);
			jpDisplayLandOfOwner[i-1].add(jl);
			jpDisplayLandOfOwner[i-1].setBounds(x+1, y, image.getIconWidth()-2, image.getIconHeight()); //오너 표시 위치
			
			if(x > 0 && flagDraw == false) //처음 상태(남쪽)
				x -= image.getIconWidth(); // 다음 위치에 그림크기 차 만큼 빼주고, 블럭 배경 그림을 그린다
			
			else if(x == 0 && y != 0){ // 웨스트 그리기
				y -= image.getIconHeight();
				flagDraw = true;
			}
			
			else if(x < 800 && y == 0) // 북쪽 그리기
				x += image.getIconWidth();
			else					   // 동쪽 그리기
				y += image.getIconHeight();
			
			getContentPane().add(jpDisplayLandOfOwner[i-1]);
		}
	}
	void drawBoard(){ // 나라 그림 그리기
		landXY = new int[40][2];
		JPanel jp;
		JLabel jl;
		flagDraw = false;
		int x=800, y=800;
		getContentPane().setLayout(null);
		
		for(int i=1; i<=40; i++){
			jp = new JPanel();
			jp.setLayout(new BorderLayout());
			Icon image = new ImageIcon("src\\image\\land"+i+".jpg");
			jl = new JLabel(image);
			jp.add(jl);
			jp.setBounds(x+1, y, image.getIconWidth()-2, image.getIconHeight()-12); // 위치
			landXY[i-1][0] = x; 
			landXY[i-1][1] = y; 
			
			if(x > 0 && flagDraw == false) //남쪽
				x -= image.getIconWidth();
			else if(x == 0 && y != 0){ // 서쪽
				y -= image.getIconHeight();
				flagDraw = true;
			}
			else if(x < 800 && y == 0) // 북쪽
				x += image.getIconWidth();
			else					   // 동쪽
				y += image.getIconHeight();
			getContentPane().add(jp, 2);
		}
		
		y = 750;// 하단 player 1, player 2 박스
		for(int i=1; i<=numOfPlayer; i++){
			JPanel jpBox = new JPanel();
			Icon boxIcon = new ImageIcon("src\\image\\playerinfo"+i+".jpg");
			jpBox.add(new JLabel(null, boxIcon, SwingConstants.RIGHT));
			jpBox.setBounds(80, y, 200, 50);
			y -= 50; // 다음 플레이어 위치
			getContentPane().add(jpBox, 3);
		}
		
		//황금열쇠 그림
		ImageIcon jpKeyIcon = new ImageIcon("src\\image\\key.png");
		JLabel jlKeyBack = new JLabel(jpKeyIcon);
		madeComponent(jlKeyBack, 80, 80, jpKeyIcon.getIconWidth(), jpKeyIcon.getIconHeight());

		//우주여행 그림
		Icon SpaceIcon = new ImageIcon("src\\image\\space.jpg");
		JLabel jlSpaceIcon = new JLabel(SpaceIcon);
		madeComponent(jlSpaceIcon, 90, 180, SpaceIcon.getIconWidth(), SpaceIcon.getIconHeight());
	}
	void drawPlayer(){
		//player 0
		userFace = new Icon[numOfPlayer];
		
		JPanel jp1 = new JPanel();
		userFace[0] = new ImageIcon("src\\image\\player0.gif");
		
		jp1.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp1.add(new JLabel("Name : " + player[0].name, userFace[0], SwingConstants.RIGHT));
		jp1.setBounds(280, 750, 150, 50);
		getContentPane().add(jp1);
	
		jlAsset[0] = new JLabel("Asset : " + player[0].asset + "만원", SwingConstants.RIGHT);
		madeComponent(jlAsset[0], 390, 750, 150, 40);

		//player 1
		JPanel jp3 = new JPanel();
		userFace[1] = new ImageIcon("src\\image\\player1.gif");
				
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.add(new JLabel("Name : " + player[1].name, userFace[1], SwingConstants.RIGHT));
		jp3.setBounds(280, 700, 150, 50);
		getContentPane().add(jp3);
		
		jlAsset[1] = new JLabel("Asset : " + player[1].asset + "만원", SwingConstants.RIGHT);
		madeComponent(jlAsset[1], 390, 710, 150, 40);
	}
	
	void drawListProperty(){ // 부동산 목록 그림으로 출력
		ImageIcon byul = new ImageIcon("src\\image\\house.png");
		ImageIcon building = new ImageIcon("src\\image\\building.png");
		ImageIcon hotel = new ImageIcon("src\\image\\hotel.png");
		
		for(int i=0; i<40; i++){
			if(square[i].selecByul){
				JLabel jl = new JLabel(byul);
				madeComponent(jl, landXY[i][0]+0, landXY[i][1]+40, byul.getIconWidth(), byul.getIconHeight(), 1); //별장 위치 크기 지정
			}
			if(square[i].selectBuilding){
				JLabel jl = new JLabel(building);
				madeComponent(jl, landXY[i][0]+26, landXY[i][1]+40, building.getIconWidth(), building.getIconHeight(), 1);
			}
			if(square[i].selectHotel){
				JLabel jl = new JLabel(hotel);
				madeComponent(jl, landXY[i][0]+52, landXY[i][1]+40, hotel.getIconWidth(), hotel.getIconHeight(), 1);
			}
		}
	}
	
	void displayLandInfoInit(){ // 상태창 설정
		jpDisplaySquareInfo = new JPanel();
		jpDisplaySquareInfo.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		for(int i=0; i<9; i++) //9줄
			jpDisplaySquareInfo.add(jlLandInfo[i]);

		jpDisplaySquareInfo.setBounds(80, 490, 190, 250); // 각종 정보 위치, 필드크기
		getContentPane().add(jpDisplaySquareInfo);
		
		DisplayLandInfo();
	}
	void DisplayLandInfo(){
		int pos = player[nowPlayer].getPos();	// 현재 턴의 유저 위치
		String owner = "소유자 없음";
		switch(square[player[nowPlayer].getPos()].owner) // 해당 토지에 대한 소유자 체크
		{
		case 0: owner = "소유자 없음"; break;
		case 1: owner = player[0].name; break;
		}
		
		jlLandInfo[0].setText("현재 장소 :   "+square[pos].name);
		jlLandInfo[1].setText("토지 구입비  :   "+square[pos].dealPrice+" 만원");
		jlLandInfo[2].setText("토지 주인  :   "+owner);
		jlLandInfo[3].setText("----------------------------------");
		jlLandInfo[4].setText("별장 구입비 : "+square[pos].byul_up+" 만원");
		jlLandInfo[5].setText("빌딩 구입비 : "+square[pos].building_up+" 만원");
		jlLandInfo[6].setText("호텔 구입비 : "+square[pos].hotel_up+" 만원");
		jlLandInfo[7].setText("----------------------------------");
		jlLandInfo[8].setText("["+player[nowPlayer].name+"] 님 차례 입니다");
		
	}

	//출력을 위한 컴포넌트 메서드.
	void madeComponent(Component comp, int x, int y, int width, int height)
	{
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout(0, 0));
		jp.add(comp);
		jp.setBounds(x, y, width, height);
		getContentPane().add(jp);
	}
	
	//인덱스 추가
	void madeComponent(Component comp, int x, int y, int width, int height, int index)
	{
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout(0, 0));
		jp.add(comp);
		jp.setBounds(x, y, width, height);
		getContentPane().add(jp, index);
	}
}