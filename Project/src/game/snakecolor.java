package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class snakecolor implements Runnable{

	Thread th = new Thread(this);
	public static Color rainbow = null, gold = null;
	 
	public snakecolor() {
		// TODO Auto-generated constructor stub
		th.start();
		tm.start();
	}
//	int i = 150;
	int i = 0;
	boolean ch = false;
	Timer tm = new Timer(10, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
//			gold = new Color(255,i,0);
//			if (ch) {
//				i--;
//			}else {
//				i++;
//			}
//			
//			if (i == 255) {
//				ch = true;
//			}else if (i == 150) {
//				ch = false;
//			}
			gold = new Color(i,i,i);
			if (ch) {
				i--;
			}else {
				i++;
			}
			
			if (i == 255) {
				ch = true;
			}else if (i == 0) {
				ch = false;
			}
		}
	});
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				
				
				for (int i = 0; i < 256; i++) {
					rainbow = new Color(255,i,0);
					th.sleep(5);
				}
				
				for (int i = 255; i > -1; i--) {
					rainbow = new Color(i,255,0);
					th.sleep(5);
				}
				
				for (int i = 0; i < 256; i++) {
					rainbow = new Color(0,255,i);
					th.sleep(5);
				}
				
				for (int i = 255; i > -1; i--) {
					rainbow = new Color(0,i,255);
					th.sleep(5);
				}
				
				for (int i = 0; i < 256; i++) {
					rainbow = new Color(i,0,255);
					th.sleep(5);
				}
				
				for (int i = 255; i > -1; i--) {
					rainbow = new Color(255,0,i);
					th.sleep(5);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
