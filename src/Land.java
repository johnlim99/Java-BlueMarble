import java.io.Serializable;

public class Land implements Serializable{

	private static final long serialVersionUID = 7943511435547216172L;
	private int Byul = 0;
	private int Building = 1;
	private int Hotel = 2;

	String name;				// �̸�
	int dealPrice;				// ����
	int owner;					// �ǹ�����
	int passFee;				// �����
	int byul_up;				// ���� ���� ���
	int building_up;			// ���� ���� ���
	int hotel_up;				// ȣ�� ���� ���

	boolean selecByul;			// �ش� �ǹ� ���� ����
	boolean selectBuilding;
	boolean selectHotel;
	
	int byulUP;			// �ش� �ǹ� ���� ����
	int buildingUP;
	int hotelUP;
	

	//Game ���ο��� Land �ʱ�ȭ �ϸ鼭 ������ �������� ���� �Է�.
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
			this.passFee += Byul; //����� ����
			return byul_up; //Game���� �ʱ�ȭ �� ���� �����ϰ� �� ��ŭ�� �ݾ��� ���ش�.
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

	int landValue(){				 		// ���� ���Ժ�
		return this.dealPrice;
	}
	
	int upgrade(int up){
		this.passFee += up;
		return 0;
	}
	
}