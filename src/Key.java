import java.io.Serializable;
import java.util.Random;


public class Key implements Serializable {
	private String what;
	private static final long serialVersionUID = 2409242317861643592L;

	public int randomKey(){ //����, ���� ����
		Random ran = new Random();
		int result = (ran.nextInt(2)); //1~2
		return result;
	}
	
	public int randomMinus(){ //1~8�� ����
		Random ran = new Random();
		int a = (ran.nextInt(9)+1); //1~4
		int result = howMuchMinus(a);
		return result;
	}
	
	public String minusText(int given){ //���� 1~8�� ����

		if(given==1){//������
			what = new String("������");
		}
		
		else if(given==2){//������
			what = new String("�����Ⱚ");
		}
		
		else if(given==3){//��ϱ�
			what = new String("��ϱ�");
		}

		else if(given==4){//����
			what = new String("���Ӻ�");
		}
		
		else if(given==5){//�ǹ� ������
			what = new String("�ǹ�������");
		}
		
		else if(given==6){//�����
			what = new String("�����");
		}
		
		else if(given==7){//�ҵ漼
			what = new String("�ҵ漼");
		}
		
		else if(given==8){//�̻�
			what = new String("�̻簡�ÿ�");
		}
			
		
		return what;
	}
	
	public int howMuchMinus(int a){
		
		if(a==1){//������
			a=5;
		}
		
		if(a==2){//������
			a=20;
		}
		
		if(a==3){//��ϱ�
			a=10;
		}

		if(a==4){//����
			a=15;
		}
		
		if(a==5){//�ǹ� ������
			a=3;
		}
		
		if(a==6){//�����
			a=6;
		}
		
		if(a==7){//�ҵ漼
			a=13;
		}
		
		if(a==8){//�ڷμ�ĭ
			a=100;
		}
		
		if(a==9){//�ڷμ�ĭ
			a=101;
		}
		
		return a;
	}
	
	//���� ���� ��������
	public String plusText(int givenP){

		if(givenP==1){//����
			what = new String("���ο���");
		}
		
		else if(givenP==2){//��� è�ǿ�
			what = new String("����è�ǿ»��");
		}
		
		else if(givenP==3){
			what = new String("���б�");
		}

		else if(givenP==4){
			what = new String("�뺧����");
		}
		
		else if(givenP==5){
			what = new String("�������ϱ�");
		}
		
		else if(givenP==6){
			what = new String("����߰ݷ���");
		}
		
		else if(givenP==7){
			what = new String("���ʽ�");
		}
		else if(givenP==8){
			what = new String("��ӵ���");
		}
		else if(givenP==9){
			what = new String("Ÿ������ ����");
		}
		else if(givenP==10){
			what = new String("�λ� ����");
		}
		return what;
	}
	
	//���� �κ�
	public int randomPlus(){
		
		Random ran = new Random();
		int a = (ran.nextInt(10)+1); //1~4

		int result = howMuchPlus(a);
		
		return result;
		}
		
	public int howMuchPlus(int a){
			
			if(a==1){//����
				a=5;
			}
			
			if(a==2){//�ڵ��� ��� è�ǿ� ���
				a=20;
			}
			
			if(a==3){//���б�
				a=10;
			}
			
			if(a==4){//�뺧��
				a=30;
			}
			
			if(a==5){//����
				a=1;
			}
			
			if(a==6){//�������
				a=20;
			}
			
			if(a==7){//�ݷ���
				a=15;
			}
			
			if(a==8){//�����������
				a=100;
			}
			
			if(a==9){//Ÿ�����̷�
				a=101;
			}
			
			if(a==10){//�λ�����
				a=102;
			}
			
			
			return a;
		}
}
