import java.io.Serializable;

public class Player implements Serializable{

	private static final long serialVersionUID = 1965063654167675352L;
	String name;				// �̸�
	int asset;					// �÷��̾� �ڻ�
	private int position;				// ���� ��ġ
	int status;					// ���� ����
	private int bankGold;				// �����ڻ�
	int bankSpace;				// ��������
	
	Player(String name, int asset){
		this.name = name;		// �̸�
		this.asset = asset;	    // �ڻ�
		position = 0;			// ���� ��ġ�� 0���� �ʱ�ȭ
		status = 3;			    // �÷��� ���� ���� = 3
	}
	void plusMoney(int money){   // �Ա�
		asset += money;			// (int money) �ݾ��� ��������.
	}
	
	void bankPlus(int money){   // ��ȸ������� �Ա�
		bankGold += money;		// (int money) �ݾ��� ��������.
	}
	
	public void bankSpace(int money){  // �ķ����ȣ �����ڰ� ���ٸ� ���࿡ �Ա�
		bankSpace += money; 
	}
	
	int bankMinus(){ 			// ������ ��Ƶ� ���� ��ȸ���� ����� ����Ѵ�.
		return bankGold; 
		// 
	}
	void bankReset(){
		bankGold = 0;
	}
	
	void minusMoney(int money){ // ���
		if(asset >= money)		// �� �ݾ�(int money)���� ���� �ݾ�(asset)�� ������
			asset -= money;		// �ڻ�(asset)���� �ش� �ݾ�(int money)�� ����
		else{					
			System.exit(1);		// �������� (�Ļ�)
		}
	}
	void setPos(int move){
		if( (position+move) >= 40)			// �̵��� ĭ ���� ������ ĭ�� �Ѿ��
			position = (position+move)%40;	// ��ġ������ �ʱ�ȭ ���ش�. 
		else
			position += move;
	}
	
	int getPos(){						// ���� ��ġ ����
		return position;
	}
}