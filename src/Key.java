import java.io.Serializable;
import java.util.Random;


public class Key implements Serializable {
	private String what;
	private static final long serialVersionUID = 2409242317861643592L;

	public int randomKey(){ //부정, 긍정 리턴
		Random ran = new Random();
		int result = (ran.nextInt(2)); //1~2
		return result;
	}
	
	public int randomMinus(){ //1~8값 리턴
		Random ran = new Random();
		int a = (ran.nextInt(9)+1); //1~4
		int result = howMuchMinus(a);
		return result;
	}
	
	public String minusText(int given){ //랜덤 1~8값 들어옴

		if(given==1){//병원비
			what = new String("병원비");
		}
		
		else if(given==2){//무전기
			what = new String("무전기값");
		}
		
		else if(given==3){//등록금
			what = new String("등록금");
		}

		else if(given==4){//과속
			what = new String("과속비");
		}
		
		else if(given==5){//건물 수리비
			what = new String("건물수리비");
		}
		
		else if(given==6){//방범비
			what = new String("방범비");
		}
		
		else if(given==7){//소득세
			what = new String("소득세");
		}
		
		else if(given==8){//이사
			what = new String("이사가시오");
		}
			
		
		return what;
	}
	
	public int howMuchMinus(int a){
		
		if(a==1){//병원비
			a=5;
		}
		
		if(a==2){//무전기
			a=20;
		}
		
		if(a==3){//등록금
			a=10;
		}

		if(a==4){//과속
			a=15;
		}
		
		if(a==5){//건물 수리비
			a=3;
		}
		
		if(a==6){//방범비
			a=6;
		}
		
		if(a==7){//소득세
			a=13;
		}
		
		if(a==8){//뒤로세칸
			a=100;
		}
		
		if(a==9){//뒤로세칸
			a=101;
		}
		
		return a;
	}
	
	//긍정 열쇠 디스프레이
	public String plusText(int givenP){

		if(givenP==1){//연금
			what = new String("국민연금");
		}
		
		else if(givenP==2){//우승 챔피온
			what = new String("경주챔피온상금");
		}
		
		else if(givenP==3){
			what = new String("장학금");
		}

		else if(givenP==4){
			what = new String("노벨상상금");
		}
		
		else if(givenP==5){
			what = new String("생일축하금");
		}
		
		else if(givenP==6){
			what = new String("새출발격려금");
		}
		
		else if(givenP==7){
			what = new String("보너스");
		}
		else if(givenP==8){
			what = new String("고속도로");
		}
		else if(givenP==9){
			what = new String("타이페이 도착");
		}
		else if(givenP==10){
			what = new String("부산 도착");
		}
		return what;
	}
	
	//긍정 부분
	public int randomPlus(){
		
		Random ran = new Random();
		int a = (ran.nextInt(10)+1); //1~4

		int result = howMuchPlus(a);
		
		return result;
		}
		
	public int howMuchPlus(int a){
			
			if(a==1){//연금
				a=5;
			}
			
			if(a==2){//자동차 우승 챔피온 상금
				a=20;
			}
			
			if(a==3){//장학금
				a=10;
			}
			
			if(a==4){//노벨상
				a=30;
			}
			
			if(a==5){//생일
				a=1;
			}
			
			if(a==6){//출발지점
				a=20;
			}
			
			if(a==7){//격려금
				a=15;
			}
			
			if(a==8){//출발지점으로
				a=100;
			}
			
			if(a==9){//타이페이로
				a=101;
			}
			
			if(a==10){//부산으로
				a=102;
			}
			
			
			return a;
		}
}
