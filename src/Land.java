import java.io.Serializable;

public class Land implements Serializable{

	private static final long serialVersionUID = 7943511435547216172L;
	private int Byul = 0;
	private int Building = 1;
	private int Hotel = 2;

	String name;				// 이름
	int dealPrice;				// 계약금
	int owner;					// 건물주인
	int passFee;				// 통행료
	int byul_up;				// 별장 구입 비용
	int building_up;			// 빌딩 구입 비용
	int hotel_up;				// 호텔 구입 비용

	boolean selecByul;			// 해당 건물 소유 여부
	boolean selectBuilding;
	boolean selectHotel;
	
	int byulUP;			// 해당 건물 소유 여부
	int buildingUP;
	int hotelUP;
	

	//Game 내부에서 Land 초기화 하면서 가져온 정보값을 새로 입력.
	Land(String name, int dealPrice, int owner, int byul, int building, int hotel, int givenPassFee){
		
		this.name = name;
		this.dealPrice = dealPrice;
		this.owner = owner;
		this.byul_up = byul;
		this.building_up = building;
		this.hotel_up = hotel;
		this.passFee = givenPassFee;
		
		selecByul = false;
	    selectBuilding = false;
		selectHotel = false;
	}
	
	int buyBuilding(int givenRequest){
		if(givenRequest == Byul){
			selecByul = true;
			this.passFee += Byul; //통행료 증가
			return byul_up; //Game에서 초기화 한 값을 리턴하고 그 만큼의 금액을 빼준다.
		}
		else if(givenRequest == Building){
			selectBuilding = true;
			this.passFee += Building;
			return building_up;
		}
		else if(givenRequest == Hotel){
			selectHotel = true;
			this.passFee += Hotel;
			return hotel_up;
		}
		return 0;
	}

	int landValue(){				 		// 토지 구입비
		return this.dealPrice;
	}
	
	int upgrade(int up){
		this.passFee += up;
		return 0;
	}
	
}