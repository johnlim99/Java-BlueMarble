import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.File;
import javax.sound.sampled.*;

////int pos = player[nowPlayer].getPos(); ������ġ
public class Game extends JFrame implements ActionListener, Serializable{
	
	private static final long serialVersionUID = 7743785439592198738L;
	//�ǹ� ���� �ϱ� ����	
	
	private int Byul = 0;
	private int Building = 1;
	private int Hotel = 2;
	
	//�÷��̾� ���� ����
	private int Start = 0;
	private int Run = 1;
	private int Finish  = 2;
	
	String what;					// � Ȳ�ݿ��� ����
	int keyX = 300;  			    // Ȳ�ݿ��� ��� ��ǥ ����
	int keyY = 100;
	int keyW = 400;
	int keyH = 50;
	int x;									// ��ǥ��
	int y;
	int tunrInfo;							// �� ����
	
	static int now = 0;
	private int numOfPlayer;				// �÷��̾� ��
	private int pos=0;						// Ȳ�ݿ��� ���� ��ġ
	private int plus=0;						// Ȳ�ݿ��� �߰� ��ġ
	private int[][] landXY;					// �ش� �������� xy��ǥ�� ������ �迭
	private static int nowPlayer;			// �ش� ���� ����
	
	private boolean flagDraw;
	private int isMoving = Start; 		    // ������ ���� ����
	private int dice1; 						// �ֻ��� ����� ���� 
	private int dice2;

	private Player player[];				// Player ��ü��
	private Land square[];					// Land ��ü��
	private Icon userFace[];				// �÷��̾� ������ ��ü
	private Icon think;
	private Icon button;
	private Icon button2;
	private Icon buy;
	private Icon byulB;
	private Icon buildingB;
	private Icon hotelB;
	
	private JPanel jpDisplaySquareInfo;		// ���� ����
	private JPanel jpPieceUserFace[];		// �����ǿ��� �̵��ϴ� Player �� Icon
	private JLabel jlLandInfo[];			// �������� ���� �迭
	private JLabel jlKeyInfo;				// Ȳ�ݿ��� ���� ���� �迭
	private JLabel jlUpInfo;				    // ���׷��� ���� ���
	private JLabel jlDonaInfo;				// ��ȸ���� ��� ���
	private JLabel jlAsset[];				// �ڻ� ����
	private JLabel jlthink;					// ���� �÷��̾� ǥ�� �׸�
	JPanel jpDice1;					// �ֻ��� ��� 
	JPanel jpDice2;
	JLabel jlDice1;					// �ֻ��� ���
	JLabel jlDice2;
	private JPanel jpDisplayLandOfOwner[];	// ������ ���� on �׸�
	
	private JButton deal;					// ���Ź�ư
	private JButton jbRolling;				// ���� ���� ��ư, ����, ����, ȣ��
	private JButton jbByul;
	private JButton jbBuilding;
	
	private JButton jbByulUP;				// ���׷��̵� ��ư
	private JButton jbBuildingUP;
	private JButton jbHotelUP;
	
	private JButton jbHotel;
	private JButton jbSave;
	private JButton jbLoad;

	public Game(int numOfPlayer){
		this.numOfPlayer = numOfPlayer; 	     	// �÷��� �ο� �� 
		nowPlayer = 0;					   			// ���� ���� 
		jpPieceUserFace = new JPanel[numOfPlayer];  // �ο� �� ��ŭ �� �׸� ����
		
		jlLandInfo = new JLabel[9];			   		// �������� ���
		for(int i=0; i<9; i++)jlLandInfo[i] = new JLabel();
		
		jlKeyInfo = new JLabel();
		
		jlAsset = new JLabel[numOfPlayer];
		
		initPlayer();				 // �÷��̾� �ʱ�ȭ
		initLand();					 // �� ���� �ʱ�ȭ
		
		drawPlayer(); 				 // �÷��̾� �׸���
		drawBoard();				 // �� �׸���
		drawOwner(); 				 // �÷��̾� �������� �׸���
		
		displayLandInfoInit(); 		 // ���� ���� ���� ���
		initPeace();			 	 // �÷��̾� �� ���� ����	
		
		JPanel jp = new JPanel();
		jbRolling = new JButton("�ֻ��� ������");
		button = new ImageIcon("src\\image\\play.png"); //�ֻ��� ��ư �׸� ����
		jbRolling.setIcon(button);
		
		jp.add(jbRolling);
		jbRolling.addActionListener(this);
		jp.setBounds(300, 500, 200, 200); 		//�ֻ��� ������Ʈ ��ġ (x��ǥ, y��ǥ, ����, ����);
		getContentPane().add(jp, 1);		

		deal = new JButton("���� ����");		    // ��ư �̸� ����
		jbByul = new JButton("���� ����");
		jbBuilding = new JButton("���� ����");
		jbHotel = new JButton("ȣ�� ����");
		
		jbByulUP = new JButton("���� ���׷��̵�");
		jbBuildingUP = new JButton("���� ���׷��̵�");
		jbHotelUP = new JButton("ȣ�� ���׷��̵�");
		
		jbSave = new JButton("���� ����");
		jbLoad = new JButton("���� �ε�");
		
		buy = new ImageIcon("src\\image\\buy.png"); //��ư �׸� ����
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
		
		deal.addActionListener(this); // �� ��ư�� ���� �׼Ǹ����� ����
		jbByul.addActionListener(this);
		jbBuilding.addActionListener(this);
		jbHotel.addActionListener(this);
		jbByulUP.addActionListener(this);
		jbBuildingUP.addActionListener(this);
		jbHotelUP.addActionListener(this);
		jbSave.addActionListener(this);
		jbLoad.addActionListener(this);

		madeComponent(deal, 580, 500, 220, 50); // ��ư ��ġ �� ũ�� ����
		madeComponent(jbByul, 580, 550, 220, 50);
		madeComponent(jbBuilding, 580, 600, 220, 50);
		madeComponent(jbHotel, 580, 650, 220, 50);
		
		madeComponent(jbSave, 580, 450, 220, 50);
		madeComponent(jbLoad, 580, 400, 220, 50);
		
		madeComponent(jbByulUP, 580, 550, 220, 50);
		madeComponent(jbBuildingUP, 580, 580, 220, 50);
		madeComponent(jbHotelUP, 580, 650, 220, 50);
		
		deal.setVisible(false); // �ʱ� �� ���̴� ���� ����
		jbByul.setVisible(false);
		jbBuilding.setVisible(false);
		jbHotel.setVisible(false);
		jbSave.setVisible(false);
		jbLoad.setVisible(false);
		jbByulUP.setVisible(false);
		jbBuildingUP.setVisible(false);
		jbHotelUP.setVisible(false);
		
		think = new ImageIcon("src\\image\\think.png"); // ���� �� �׸�
		jlthink = new JLabel(think);

	}
	
	public void paint(Graphics g){
		super.paint(g);
		madeComponent(jlthink, 600, 750-(50*nowPlayer), think.getIconWidth(), think.getIconHeight(), 0); //���� �� �׸� ��ġ ����

		if(isMoving == Run) movePlayer(); //run ���¶�� ���� �����δ�.
		
		int pos = player[nowPlayer].getPos();
		//player+nowPlayer�� +1�ϸ� ������ ����
		if(square[pos].owner == 0 && isMoving != Start){ // ���� �� ��ġ�� �� ������ ���ٸ�
			deal.setVisible(true); // ���� ���Ź�ư Ȱ��ȭ
			jbByul.setVisible(false);
			jbBuilding.setVisible(false);
			jbHotel.setVisible(false);
			
		}
		else if(square[pos].owner == nowPlayer+1 && isMoving != Start){ // �ƴ϶�� ���� ��ư Ȱ��ȭ
			
			deal.setVisible(false);
			jbBuilding.setVisible(true);
			jbByul.setVisible(true);
			jbHotel.setVisible(true);
			
			if(square[pos].byulUP == nowPlayer+1 && isMoving != Start)
			{jbByul.setVisible(false);jbByulUP.setVisible(true);} //������ �̹� ���������� ��ư�� �����, ���׷��̵� ��ư�� ǥ���Ѵ�.
		
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
				jbByul.setText("���� ���׷��̵�");
				sound.play(buy);
				}
			else{
			jbByul.setText("���� ����");
	 */
	
	void movePlayer() // �� ������ ����
	{
		int x=0, y=0; // �� ��ġ
		
		if(nowPlayer==1) x=40; //2���� ���� (current 1) 1���� ���� ���� ���� 
		
		player[nowPlayer].setPos(1);
		
		int pos = player[nowPlayer].getPos(); //������ġ ��������
		jpPieceUserFace[nowPlayer].setBounds(landXY[pos][0]+x, landXY[pos][1]+y, 40, 40); //�� ��ġ ũ�� ����
		
		DisplayLandInfo();
	}
	
	void initPeace(){ // �� �ʱ�ȭ
		for(int i=0; i<numOfPlayer; i++){
			int pos = player[i].getPos();	// �� ������ ��ġ
			int x=0, y=0;
			if(i==1) x=40;
		
			jpPieceUserFace[i] = new JPanel();      // �ʱ� ��ġ ����
			jpPieceUserFace[i].setLayout(new BorderLayout(0, 0));
			jpPieceUserFace[i].add(new JLabel(userFace[i]));
			jpPieceUserFace[i].setBounds(landXY[pos][0]+x, landXY[pos][1]+y, 40, 40);
			getContentPane().add(jpPieceUserFace[i], 2);
		}
	}
	
	void drawDiceInfo(int dice1, int dice2){ // �ֻ��� ���� ���
		int diceY = 280;
		//�ֻ��� ���� ���߾� �ش� �׸� ǥ��
		Icon dice = new ImageIcon("src\\image\\dice"+dice1+".png");
		JLabel jlDice = new JLabel(dice);
		madeComponent(jlDice, 300, diceY, dice.getIconWidth(), dice.getIconHeight(), 3);
		
		Icon _dice = new ImageIcon("src\\image\\dice"+dice2+".png");
		JLabel jlDice2 = new JLabel(_dice);
		madeComponent(jlDice2, 450, diceY, dice.getIconWidth(), dice.getIconHeight(), 3);
	}
	
	public void actionPerformed(ActionEvent ae){ //��ư �׼�
		int normalDice = 1; //���� ���� ó��
		int doubleDice = 2;
		int buy = 3;
		//int rolling = 4;
		Sound sound = new Sound();
		
		if("�ֻ��� ������".equals(ae.getActionCommand()))
		{
			if(player[nowPlayer].status != 3) { //0,1,2 ���
				if(dice1==dice2){player[nowPlayer].status = 3;} // Ż�� ���� ��ȯ
				else{
				player[nowPlayer].status = player[nowPlayer].status + 1; // 0���� �����ؼ� 3ȸ° Ż�� ����
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
			
			if((player[nowPlayer].getPos() + (dice1+dice2)) >= 40){	// �������� ������
				player[nowPlayer].plusMoney(20);							// 20���� ����
				jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"����");
			}
			
			
			for(int i=0; i<dice1+dice2; i++){ //�ֻ��� ����� ��ŭ �����δ�.
				try {
					Thread.sleep(30); //�� �����̴� �ӵ� ����
					sound.play(normalDice);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				this.paint(this.getGraphics());
			}
			
			if(dice1==dice2){
				sound.play(doubleDice);
				try { // ���� ������
					for(int i=0; i<dice1+dice2; i++){ // �ѹ� ��!
					Thread.sleep(30);	
					this.paint(this.getGraphics());
				}
				
				jlDonaInfo = new JLabel(player[nowPlayer].name+" ���� �ֻ��� ������ ���� 2�� ���������ϴ�.");
				madeComponent(jlDonaInfo, 300, 150, 400, 50, 3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}

			isMoving = Finish; // �� ������ '��' ���·� �ٲپ� �ش�.
		
			// Ȳ�ݿ��� ���� 
			// Ư�� ĭ üũ ����
			if(square[player[nowPlayer].getPos()] == square[2]) { // Ȳ�ݿ��� 
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
			
			//////////////////////////////////////////////////////////////// Ȳ�ݿ��� ��
		
			if(square[player[nowPlayer].getPos()] == square[20]) // ���ε� ���¶�� 0  ���·� ��ȯ
				player[nowPlayer].status = 0; 			      
			
			if(square[player[nowPlayer].getPos()] == square[38]) { // ��ȸ������� ����
				player[nowPlayer].minusMoney(15); // 15���� ����
				player[nowPlayer].bankPlus(15);   // 15���� ���� �Ա�
				jlDonaInfo = new JLabel("��ȸ���� ������� 15������ ���� �Ͽ����ϴ�.");
				madeComponent(jlDonaInfo, keyX, keyY, keyW, keyH, 3);
				setTitle(""+player[nowPlayer].asset);
				jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"����");
			}
			
			if(square[player[nowPlayer].getPos()] == square[30]) { // ��ȸ���� ��� �ޱ�
				int bank = player[nowPlayer].bankMinus();
				player[nowPlayer].bankReset(); //�ʱ�ȭ
				jlDonaInfo = new JLabel("��ȸ���� ������� ��Ƶ�"+bank+"������ ���� �޾ҽ��ϴ�.");
				madeComponent(jlDonaInfo, keyX, keyY, keyW, keyH, 3);
				setTitle(""+player[nowPlayer].asset);
				jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"����");
			}
			
			if(square[player[nowPlayer].getPos()] == square[10]) { // ���� ������
				if((square[32].owner > 0) && (square[32].owner != nowPlayer+1)) // �÷���� ������ �ְ� && ���� ������ �ƴ϶��
				{
					int visiterPos = 32; //�÷���� ĭ
					int ownerPos = (square[32].owner) - 1;
					passFee(visiterPos, ownerPos);
					
					jlDonaInfo = new JLabel("���� �����忡 �Խ��ϴ�. �����ڿ��� ���ֿ��� �� �����մϴ�");
					madeComponent(jlDonaInfo, keyX, keyY, keyW, keyH, 3);
					setTitle(""+player[nowPlayer].asset);
					jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"����");
				}
				
				if((square[32].owner == 0)) // �÷����ȣ ������ ���ٸ�
				{
					player[nowPlayer].minusMoney(50); // 50���� ����
					player[nowPlayer].bankSpace(50);   // 50���� ���� �Ա�
					jlDonaInfo = new JLabel("���ֿ����� 50������ �����Ͽ����ϴ�.");
					madeComponent(jlDonaInfo, keyX, keyY, keyW, keyH, 3);
					setTitle(""+player[nowPlayer].asset);
					jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"����");
				}
				
			}
			
			if((square[player[nowPlayer].getPos()].owner > 0) // ���� ������ �ְ� && ���� ������ �ƴ϶��
					&& (square[player[nowPlayer].getPos()].owner != nowPlayer+1)) {

				int visiterPos = player[nowPlayer].getPos();
				int ownerPos = (square[player[nowPlayer].getPos()].owner) - 1;
				passFee(visiterPos, ownerPos);
			}
			
			int pos = player[nowPlayer].getPos();
			
			if(square[pos].owner == nowPlayer+1){
				button2 = new ImageIcon("src\\image\\timer.png"); //�� ��ư �׸� ����
				jbRolling.setIcon(button2);
				jbRolling.setText("�� �ѱ��");
				isMoving = Start;
				return;
			}
			if(square[pos].owner == 0){
				button2 = new ImageIcon("src\\image\\timer.png"); //�� ��ư �׸� ����
				jbRolling.setIcon(button2);
				jbRolling.setText("�� �ѱ��");
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
		} // "�ֻ��� ������ if�� ��
		
		jbRolling.setIcon(button); //��->�÷��� ��ư ���󺹱�

		if("���� ����".equals(ae.getActionCommand())){ //���Ź�ư Ŭ����
			int pos = player[nowPlayer].getPos();  // ���� ��ġ
			player[nowPlayer].minusMoney(square[pos].landValue()); // ���� �÷��̾��� ���� ���űݾ� ����
			square[pos].owner = nowPlayer+1; // ���� ����  �ο� p1 = 1, p2=2
			sound.play(buy);
			ShowOwner(false); // ���� �׸� ǥ��
			drawOwner();
		}

		if("���� ����".equals(ae.getActionCommand())){ 
			int pos = player[nowPlayer].getPos();
			square[pos].byulUP = nowPlayer+1; // ���� ���Ÿ�� �߰�
			sound.play(buy);
			player[nowPlayer].minusMoney(square[pos].buyBuilding(Byul));
			
		}
		if("���� ����".equals(ae.getActionCommand())){
			int pos = player[nowPlayer].getPos(); 
			square[pos].buildingUP = nowPlayer+1;
			sound.play(buy);
			player[nowPlayer].minusMoney(square[pos].buyBuilding(Building));
		}
		if("ȣ�� ����".equals(ae.getActionCommand())){
			int pos = player[nowPlayer].getPos();
			square[pos].hotelUP = nowPlayer+1;
			sound.play(buy);
			player[nowPlayer].minusMoney(square[pos].buyBuilding(Hotel));
		}
		if("���� ���׷��̵�".equals(ae.getActionCommand())){
			int pos = player[nowPlayer].getPos();
			square[pos].upgrade(square[pos].passFee);
			sound.play(buy);
			jlUpInfo = new JLabel(""+square[pos].passFee+"�������� ������ ���׷��̵� �Ͽ����ϴ�. ����� "+square[pos].passFee+"������ �����մϴ�.");
			madeComponent(jlUpInfo, keyX, keyY, keyW, keyH, 3);
			player[nowPlayer].minusMoney(square[pos].passFee);
		}
		if("���� ���׷��̵�".equals(ae.getActionCommand())){
			int pos = player[nowPlayer].getPos();
			square[pos].upgrade(square[pos].passFee);
			sound.play(buy);
			jlUpInfo = new JLabel(""+square[pos].passFee+"�������� ������ ���׷��̵� �Ͽ����ϴ�. ����� "+square[pos].passFee+"������ �����մϴ�.");
			madeComponent(jlUpInfo, keyX, keyY, keyW, keyH, 3);
			player[nowPlayer].minusMoney(square[pos].passFee);
		}
		if("ȣ�� ���׷��̵�".equals(ae.getActionCommand())){
			int pos = player[nowPlayer].getPos();
			square[pos].upgrade(square[pos].passFee);
			sound.play(buy);
			jlUpInfo = new JLabel(""+square[pos].passFee+"�������� ȣ���� ���׷��̵� �Ͽ����ϴ�. ����� "+square[pos].passFee+" ������ �����մϴ�.");
			madeComponent(jlUpInfo, keyX, keyY, keyW, keyH, 3);
			player[nowPlayer].minusMoney(square[pos].passFee);
		}
		
		
		repaint();
		jbRolling.setText("�ֻ��� ������");
		jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"����");
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
	
	void passFee(int givenPosV, int givenPosO){ //����� ����
		
		//�湮��, square ���� ��ġ��
		int visiterPos = givenPosV;
		int ownerPos = givenPosO;

		//�����
		player[nowPlayer].minusMoney(square[visiterPos].passFee); // ����� ����
		jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"����");

		player[ownerPos].plusMoney(square[ownerPos].passFee); // ���� ������ �ڻ� ����
		jlAsset[ownerPos].setText("Asset : "+player[ownerPos].asset+"����");
		
		/*���� ������ player0 �� ������ ������ ���� (1)�� ���� �� ���
		 *���� player0�� ������ ���� ����
		 *1>0&&1!=1:���� ������
		 */
		/*���� ������ player0 �� ������ ������ ���� (1)�� ���� �� ���
		 *���� player1�� ������ ���� ����
		 *1>0&&1!=2:���� ����
		 *player1 �� �ڻ꿡�� ����Ḧ ���� ����.
		 *player0 ���� ����� �����Ѵ�.
		 */
	}

	private void displayKey() { //Ȳ�ݿ��� ���
		Key key = new Key();
		int or = key.randomKey(); //0����, 1���� Ȳ�ݿ���
		
		if(or==0){ // ���� Ȳ�ݿ��� ����
			int result = key.randomMinus();

			
			if(result==100 | result==101){ //������ Ȳ�ݿ��� 100=-3 101=����ø���
				isMoving = Run;
				pos = player[nowPlayer].getPos(); //38, 40-2, 101Ÿ������
				if(result==100){
					jlKeyInfo = new JLabel("Ȳ�ݿ���. �ڷ� ��ĭ ���ÿ�");
					plus=-1; //�ڷ� 3ĭ
				}
				else if(result==101){
					jlKeyInfo = new JLabel("Ȳ�ݿ���. �ø��� ������ ����� ���ÿ� 200������ �����մϴ�.");
					player[nowPlayer].minusMoney(200); // ��������
					plus=39;
				}
				for(int i=0; i<(40-pos)+plus; i++){ // ���ǿ� �°� �����̱�.
					this.paint(this.getGraphics());
				}
				
				isMoving = Finish;
			}
			
			else if(result!=100 && result!=101){
			what = key.minusText(result); // Ȳ�ݿ��� ���� ��������,
			player[nowPlayer].minusMoney(result); // ��������
			jlKeyInfo = new JLabel("Ȳ�ݿ��踦 ���̽��ϴ�. "+what+"�� "+result+"���� �� �����մϴ�.");
			}
			madeComponent(jlKeyInfo, keyX, keyY, keyW, keyH, 3); // ��� ��ġ
		}
		
		if(or==1){ // ���� Ȳ�ݿ���
			int result = key.randomPlus();
			
			if(result==100 | result==101 | result==102){
				isMoving = Run;
				pos = player[nowPlayer].getPos(); //38, 40-2, 101Ÿ������
				if(result==100){//ó����ġ��
					plus=0;
					jlKeyInfo = new JLabel("Ȳ�ݿ���. ��ӵ��θ� Ÿ�� ó������ ���ÿ�");
				}else if(result==101){ //Ÿ�����̷�
					plus=1;
					jlKeyInfo = new JLabel("Ȳ�ݿ���. Ÿ�����̷� ���ÿ�");
				}
				else if(result==102){ //�λ�����
					plus=25;
					jlKeyInfo = new JLabel("Ȳ�ݿ���. �λ����� ���ÿ�");
				}
				System.out.println(40-pos);
				for(int i=0; i<(40-pos)+plus; i++){ // ��ü�� ũ�� - ������ġ = x, x��ŭ ������ �̵� = ó����ġ
				this.paint(this.getGraphics()); // ó������ �̵�
				}

				isMoving = Finish;
			}
			
			else if(result!=100 && result!=101 && result==102){
			player[nowPlayer].plusMoney(result); // �������, 100ó��, 101Ÿ������, 102�λ�
			what = key.plusText(result);
			jlKeyInfo = new JLabel("Ȳ�ݿ��踦 ���̽��ϴ�. "+what+"�� "+result+"���� �� �����մϴ�.");
			}
			madeComponent(jlKeyInfo, keyX, keyY, keyW, keyH, 3);
		}
		
		setTitle(""+player[nowPlayer].asset);
		jlAsset[nowPlayer].setText("Asset : "+player[nowPlayer].asset+"����");
		
	}
	void initPlayer(){
		player = new Player[numOfPlayer];
		String name[] = new String[4];
		name[0] = "�̹ο�"; 
		name[1] = "������";
		
		for(int i=0; i<numOfPlayer; i++)
			player[i] = new Player(name[i], 2000);	// �÷��̾� ����, �ʱ��ڱ� 2000����
		}
	
	void initLand(){ // ���� �Ӽ���  �迭�� ����
		// Ȳ�ݿ���, 2,7,12,22,35,17
		// -1 ���� �Ұ�
		// �����̸�, �������Ժ�, ������ �����, ����, ����, ȣ��, ���ۺ��
		
		square = new Land[40]; // ���� 39��
		square[0] = new Land("�����", 0, -1, 0, 0, 0, 0);	
		square[1] = new Land("�븸", 50, 0, 50, 50, 50, 50);
		square[2] = new Land("Ȳ�ݿ���", 0, -1, 0, 0, 0, 0); //Ư��ĭ
		square[3] = new Land("ȫ��", 50, 0, 50, 50, 50, 50);
		square[4] = new Land("���Ҷ�", 50, 0, 50, 50, 50, 50);
		square[5] = new Land("���ֵ�", 50, 0, 50, 50, 50, 50);
		square[6] = new Land("�̰�����", 50, 0, 50, 50, 50, 50);
		square[7] = new Land("Ȳ�ݿ���", 0, -1, 0, 0, 0, 0); //Ư��ĭ
		square[8] = new Land("ī�̷�", 50, 0, 50, 50, 50, 50);
		square[9] = new Land("�̽�ź��", 50, 0, 50, 50, 50, 50);
		
		square[10] = new Land("���ֿ���", 0, -1, 0, 0, 0, 0);  // ����������
		square[11] = new Land("���׳�", 50, 0, 50, 50, 50, 50);
		square[12] = new Land("Ȳ�ݿ���", 0, -1, 0, 0, 0, 0); //Ư��ĭ
		square[13] = new Land("�����ϰ�", 50, 0, 50, 50, 50, 50);
		square[14] = new Land("����Ȧ��", 50, 0, 50, 50, 50, 50);
		square[15] = new Land("���ڵ� ������", 0, -1, 0, 0, 0, 0);
		square[16] = new Land("�븮��", 50, 0, 50, 50, 50, 50);
		square[17] = new Land("Ȳ�ݿ���", 0, -1, 0, 0, 0, 0); //Ư��ĭ
		square[18] = new Land("������", 50, 0, 50, 50, 50, 50);
		square[19] = new Land("��Ʈ����", 50, 0, 50, 50, 50, 50);
		
		square[20] = new Land("���ε�", 0, -1, 0, 0, 0, 0);
		square[21] = new Land("�ο��뽺 ���̷���", 50, 0, 50, 50, 50, 50);
		square[22] = new Land("Ȳ�ݿ���", 0, -1, 0, 0, 0, 0); //Ư��ĭ
		square[23] = new Land("���Ŀ��", 50, 0, 50, 50, 50, 50);
		square[24] = new Land("�õ��", 50, 0, 50, 50, 50, 50);
		square[25] = new Land("�λ�", 50, 0, 50, 50, 50, 50);
		square[26] = new Land("�Ͽ���", 50, 0, 50, 50, 50, 50);
		square[27] = new Land("������", 50, 0, 50, 50, 50, 50);		
		square[28] = new Land("�� �����ں��� ȣ", 0, -1, 0, 0, 0, 0);
		square[29] = new Land("���帮��", 50, 0, 50, 50, 50, 50);
		
		square[30] = new Land("��ȸ���", 0, -1, 0, 0, 0, 0);
		square[31] = new Land("����", 50, 0, 50, 50, 50, 50);
		square[32] = new Land("�÷���� ȣ", 200, 0, 0, 0, 0, 200); //
		square[33] = new Land("�ĸ�", 50, 0, 50, 50, 50, 50);
		square[34] = new Land("�θ�", 50, 0, 50, 50, 50, 50);
		square[35] = new Land("Ȳ�ݿ���", 0, -1, 0, 0, 0, 0); //Ư��ĭ
		square[36] = new Land("����", 50, 0, 50, 50, 50, 50);
		square[37] = new Land("����", 50, 0, 50, 50, 50, 50);
		square[38] = new Land("��ȸ������� ���°�", 0, -1, 0, 0, 0, 0);
		square[39] = new Land("����", 50, 0, 50, 50, 50, 50);
		
	}
	void ShowOwner(boolean isVisible){
		for(int i=0; i<40; i++){
			jpDisplayLandOfOwner[i].setVisible(isVisible);
		}
	}
	void drawOwner(){ // ���� ���� ǥ���ϱ�
		jpDisplayLandOfOwner = new JPanel[40];
		JLabel jl;
		flagDraw = false;
		int x=800, y=800; // �� ũ�� ����
		getContentPane().setLayout(null);
		
		for(int i=1; i<=40; i++){ //square 40��
			jpDisplayLandOfOwner[i-1] = new JPanel(); //0 ���� �����ؼ� �����׸��� ����ش�.
			jpDisplayLandOfOwner[i-1].setLayout(new BorderLayout());
			int ownCount = square[i-1].owner;
			Icon image = new ImageIcon("src\\image\\mine"+ownCount+".jpg"); // ���� �׸� ���
			jl = new JLabel(image);
			jpDisplayLandOfOwner[i-1].add(jl);
			jpDisplayLandOfOwner[i-1].setBounds(x+1, y, image.getIconWidth()-2, image.getIconHeight()); //���� ǥ�� ��ġ
			
			if(x > 0 && flagDraw == false) //ó�� ����(����)
				x -= image.getIconWidth(); // ���� ��ġ�� �׸�ũ�� �� ��ŭ ���ְ�, �� ��� �׸��� �׸���
			
			else if(x == 0 && y != 0){ // ����Ʈ �׸���
				y -= image.getIconHeight();
				flagDraw = true;
			}
			
			else if(x < 800 && y == 0) // ���� �׸���
				x += image.getIconWidth();
			else					   // ���� �׸���
				y += image.getIconHeight();
			
			getContentPane().add(jpDisplayLandOfOwner[i-1]);
		}
	}
	void drawBoard(){ // ���� �׸� �׸���
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
			jp.setBounds(x+1, y, image.getIconWidth()-2, image.getIconHeight()-12); // ��ġ
			landXY[i-1][0] = x; 
			landXY[i-1][1] = y; 
			
			if(x > 0 && flagDraw == false) //����
				x -= image.getIconWidth();
			else if(x == 0 && y != 0){ // ����
				y -= image.getIconHeight();
				flagDraw = true;
			}
			else if(x < 800 && y == 0) // ����
				x += image.getIconWidth();
			else					   // ����
				y += image.getIconHeight();
			getContentPane().add(jp, 2);
		}
		
		y = 750;// �ϴ� player 1, player 2 �ڽ�
		for(int i=1; i<=numOfPlayer; i++){
			JPanel jpBox = new JPanel();
			Icon boxIcon = new ImageIcon("src\\image\\playerinfo"+i+".jpg");
			jpBox.add(new JLabel(null, boxIcon, SwingConstants.RIGHT));
			jpBox.setBounds(80, y, 200, 50);
			y -= 50; // ���� �÷��̾� ��ġ
			getContentPane().add(jpBox, 3);
		}
		
		//Ȳ�ݿ��� �׸�
		ImageIcon jpKeyIcon = new ImageIcon("src\\image\\key.png");
		JLabel jlKeyBack = new JLabel(jpKeyIcon);
		madeComponent(jlKeyBack, 80, 80, jpKeyIcon.getIconWidth(), jpKeyIcon.getIconHeight());

		//���ֿ��� �׸�
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
	
		jlAsset[0] = new JLabel("Asset : " + player[0].asset + "����", SwingConstants.RIGHT);
		madeComponent(jlAsset[0], 390, 750, 150, 40);

		//player 1
		JPanel jp3 = new JPanel();
		userFace[1] = new ImageIcon("src\\image\\player1.gif");
				
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.add(new JLabel("Name : " + player[1].name, userFace[1], SwingConstants.RIGHT));
		jp3.setBounds(280, 700, 150, 50);
		getContentPane().add(jp3);
		
		jlAsset[1] = new JLabel("Asset : " + player[1].asset + "����", SwingConstants.RIGHT);
		madeComponent(jlAsset[1], 390, 710, 150, 40);
	}
	
	void drawListProperty(){ // �ε��� ��� �׸����� ���
		ImageIcon byul = new ImageIcon("src\\image\\house.png");
		ImageIcon building = new ImageIcon("src\\image\\building.png");
		ImageIcon hotel = new ImageIcon("src\\image\\hotel.png");
		
		for(int i=0; i<40; i++){
			if(square[i].selecByul){
				JLabel jl = new JLabel(byul);
				madeComponent(jl, landXY[i][0]+0, landXY[i][1]+40, byul.getIconWidth(), byul.getIconHeight(), 1); //���� ��ġ ũ�� ����
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
	
	void displayLandInfoInit(){ // ����â ����
		jpDisplaySquareInfo = new JPanel();
		jpDisplaySquareInfo.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		for(int i=0; i<9; i++) //9��
			jpDisplaySquareInfo.add(jlLandInfo[i]);

		jpDisplaySquareInfo.setBounds(80, 490, 190, 250); // ���� ���� ��ġ, �ʵ�ũ��
		getContentPane().add(jpDisplaySquareInfo);
		
		DisplayLandInfo();
	}
	void DisplayLandInfo(){
		int pos = player[nowPlayer].getPos();	// ���� ���� ���� ��ġ
		String owner = "������ ����";
		switch(square[player[nowPlayer].getPos()].owner) // �ش� ������ ���� ������ üũ
		{
		case 0: owner = "������ ����"; break;
		case 1: owner = player[0].name; break;
		}
		
		jlLandInfo[0].setText("���� ��� :   "+square[pos].name);
		jlLandInfo[1].setText("���� ���Ժ�  :   "+square[pos].dealPrice+" ����");
		jlLandInfo[2].setText("���� ����  :   "+owner);
		jlLandInfo[3].setText("----------------------------------");
		jlLandInfo[4].setText("���� ���Ժ� : "+square[pos].byul_up+" ����");
		jlLandInfo[5].setText("���� ���Ժ� : "+square[pos].building_up+" ����");
		jlLandInfo[6].setText("ȣ�� ���Ժ� : "+square[pos].hotel_up+" ����");
		jlLandInfo[7].setText("----------------------------------");
		jlLandInfo[8].setText("["+player[nowPlayer].name+"] �� ���� �Դϴ�");
		
	}

	//����� ���� ������Ʈ �޼���.
	void madeComponent(Component comp, int x, int y, int width, int height)
	{
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout(0, 0));
		jp.add(comp);
		jp.setBounds(x, y, width, height);
		getContentPane().add(jp);
	}
	
	//�ε��� �߰�
	void madeComponent(Component comp, int x, int y, int width, int height, int index)
	{
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout(0, 0));
		jp.add(comp);
		jp.setBounds(x, y, width, height);
		getContentPane().add(jp, index);
	}
}