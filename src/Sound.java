import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;


public class Sound {
	private static final int EXTERNAL_BUFFER_SIZE = 128000;  //���� SIZE ����
	 public void play(int kind){ //�� ������ �Ҹ�
		 File soundFile = null;   
		 try {
		    	if(kind==1){
		    		soundFile = new File("src\\sound\\move.wav");  // File �� �ޱ�
		    	}
		    	if(kind==2){
		    		soundFile = new File("src\\sound\\move2.wav");  // File �� �ޱ�
				}
		    	if(kind==3){
				    soundFile = new File("src\\sound\\click.wav");  // File �� �ޱ�
				}
		    	if(kind==4){
				    soundFile = new File("src\\sound\\dice.wav");  // File �� �ޱ�
				}
		      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		      AudioFormat audioFormat = audioInputStream.getFormat();
		      DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);
		      SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
		      line.open(audioFormat);
		      line.start();

		      int nBytesRead = 0;
		      byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
		      while (nBytesRead != -1) {
		        nBytesRead = audioInputStream.read(abData, 0, abData.length);
		        if (nBytesRead >= 0) {
		          int nBytesWritten = line.write(abData, 0, nBytesRead);
		        }
		      }
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		      System.exit(1);
		    }

		  }
	 
	 public static void playBack(){ //������� ����
		    try {
		    	AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src\\sound\\music.wav")); // File �� �ޱ�
		     // AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		      // �޾ƿ� File ������ ����� �Է� ��Ʈ���� ��ȯ -> ���ڵ��� ����� �Է� ��Ʈ���� �����´�.
		     // AudioFormat audioFormat = audioInputStream.getFormat();
		      // �����Ӽ��� ��Ÿ������ ��Ʈ���� ���̸� �����´�.
		     // DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);
		      // ������ ����� ������ ������ ������ �����κ��� ������ ������ ���� ������Ʈ�� ����
		     // SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
		      // ������ Line.Info ������Ʈ�� ����� ��ġ�ϴ� ������ �����´�.
		      //line.open(audioFormat);
		      Clip clip = AudioSystem.getClip();
		      clip.open(inputStream);
		      clip.loop(Clip.LOOP_CONTINUOUSLY);
		      Thread.sleep(100000); // looping as long as this thread is alive
		      // ������ ���İ� ������ ���� ������� ������ ����, ������ �ʿ��� system resource�� ������ �����Ѵ�.
		      //line.start();
		      // ���ο����� ������ ������� ����
		      
		      
		      /*
		      int nBytesRead = 0;
		      byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
		      while (nBytesRead != -1) {
		        nBytesRead = audioInputStream.read(abData, 0, abData.length);
		        if (nBytesRead >= 0) {
		          int nBytesWritten = line.write(abData, 0, nBytesRead);
		          
		        }
		      }*/
		 
		    }
		 
		 catch (Exception e) {
		      e.printStackTrace();
		      System.exit(1);
		    }

		  }
		  
	 
}
