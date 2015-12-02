import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;


public class Sound {
	private static final int EXTERNAL_BUFFER_SIZE = 128000;  //버퍼 SIZE 지정
	 public void play(int kind){ //말 움직임 소리
		 File soundFile = null;   
		 try {
		    	if(kind==1){
		    		soundFile = new File("src\\sound\\move.wav");  // File 값 받기
		    	}
		    	if(kind==2){
		    		soundFile = new File("src\\sound\\move2.wav");  // File 값 받기
				}
		    	if(kind==3){
				    soundFile = new File("src\\sound\\click.wav");  // File 값 받기
				}
		    	if(kind==4){
				    soundFile = new File("src\\sound\\dice.wav");  // File 값 받기
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
	 
	 public static void playBack(){ //배경음악 시작
		    try {
		    	AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src\\sound\\music.wav")); // File 값 받기
		     // AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		      // 받아온 File 지정된 오디오 입력 스트림을 변환 -> 인코딩의 오디오 입력 스트림을 가져온다.
		     // AudioFormat audioFormat = audioInputStream.getFormat();
		      // 프레임수로 나타내지는 스트림의 길이를 가져온다.
		     // DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);
		      // 단일의 오디오 형식을 포함한 지정한 정보로부터 데이터 라인의 정보 오브젝트를 구축
		     // SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
		      // 지정된 Line.Info 오브젝트의 기술에 일치하는 라인을 가져온다.
		      //line.open(audioFormat);
		      Clip clip = AudioSystem.getClip();
		      clip.open(inputStream);
		      clip.loop(Clip.LOOP_CONTINUOUSLY);
		      Thread.sleep(100000); // looping as long as this thread is alive
		      // 지정된 형식과 지정된 버퍼 사이즈로 라인을 열어, 라인이 필요한 system resource를 가져와 조작한다.
		      //line.start();
		      // 라인에서의 데이터 입출력을 가능
		      
		      
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
