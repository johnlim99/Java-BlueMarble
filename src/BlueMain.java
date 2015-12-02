import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.Serializable;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JFrame;
import javax.sound.sampled.Clip;

public class BlueMain implements Serializable{
	private static final long serialVersionUID = 4610713365276555690L;
	public static void main(String args[]){
		int numOfPlayer = 2; // 플레이어 수
		Game go = new Game(numOfPlayer);
		go.setTitle("이민영의 부루마블");
		go.setSize(890, 907);
		go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		go.setVisible(true);
		Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension coord = go.getSize();
		int xpos = (int)ds.getWidth()/2 - coord.width/2;
		int ypos = (int)ds.getHeight()/2 - coord.height/2 - 10;
		go.setLocation(xpos, ypos);
		go.setResizable(false);
		Sound.playBack();
	}
	
	
	/*
	public void actionPerformed(ActionEvent ae)
	{
		
		try {
			   FileOutputStream fos = new FileOutputStream("save.txt");
			   ObjectOutputStream oos = new ObjectOutputStream(fos);
			   oos.writeObject();
			   oos.close();
			} catch(Exception e) {
		}
		
		
		try {
			  
			   FileInputStream fis = new FileInputStream("");
			   ObjectInputStream ois = new ObjectInputStream(fis);
			   ois.readObject();
			   ois.close();
			} catch(Exception e) {
			}
		
	}
	*/
}
