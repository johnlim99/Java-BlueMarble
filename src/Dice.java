import java.util.Random;

import javax.swing.JFrame;
public class Dice extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int CastDice(){
		Random ran = new Random();
		int a = (ran.nextInt(6)+1);
		return a; // 1~6 ∏Æ≈œ
		
	}
	
	
}
