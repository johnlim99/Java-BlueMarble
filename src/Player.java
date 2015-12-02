import java.io.Serializable;

public class Player implements Serializable{

	private static final long serialVersionUID = 1965063654167675352L;
	String name;				// 이름
	int asset;					// 플레이어 자산
	private int position;				// 말의 위치
	int status;					// 유저 상태
	private int bankGold;				// 은행자산
	int bankSpace;				// 우주은행
	
	Player(String name, int asset){
		this.name = name;		// 이름
		this.asset = asset;	    // 자산
		position = 0;			// 시작 위치를 0으로 초기화
		status = 3;			    // 플레이 가능 상태 = 3
	}
	void plusMoney(int money){   // 입금
		asset += money;			// (int money) 금액이 더해진다.
	}
	
	void bankPlus(int money){   // 사회복지기금 입금
		bankGold += money;		// (int money) 금액이 더해진다.
	}
	
	public void bankSpace(int money){  // 컴럼비아호 소지자가 없다면 은행에 입금
		bankSpace += money; 
	}
	
	int bankMinus(){ 			// 누군가 모아둔 은행 사회복지 기금을 출금한다.
		return bankGold; 
		// 
	}
	void bankReset(){
		bankGold = 0;
	}
	
	void minusMoney(int money){ // 출금
		if(asset >= money)		// 뺄 금액(int money)보다 보유 금액(asset)이 많으면
			asset -= money;		// 자산(asset)에서 해당 금액(int money)을 뺀다
		else{					
			System.exit(1);		// 게임종료 (파산)
		}
	}
	void setPos(int move){
		if( (position+move) >= 40)			// 이동한 칸 수가 마지막 칸을 넘어서면
			position = (position+move)%40;	// 위치정보를 초기화 해준다. 
		else
			position += move;
	}
	
	int getPos(){						// 현재 위치 리턴
		return position;
	}
}