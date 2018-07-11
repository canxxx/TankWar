package com.xu.test;
import java.awt.*;
import javax.swing.*;
import java.util.*;


public class TestImage extends JFrame{
    
	MyPanel panel ;
	public static void main(String[] args){
		
		new TestImage();
	}
	
	public TestImage(){
		
		panel = new MyPanel();
		new Thread(panel).start();
		this.add(panel);
		this.setSize(500, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

class MyPanel extends JPanel implements Runnable{
	
	Image image1,image2;
	int flag;
	Random random = new Random();
	public MyPanel(){
		
		image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.png"));
		image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.png"));
		
	}
    public void paint(Graphics g) {
    	
    	super.paint(g);
    	g.fillRect(0, 0, 500, 400);

        while(true){
        	
        	if(flag == 1){
        		
        	   g.drawImage(image1, 14, 14, 200,200,this);	
        	}else{
        		
        		g.drawImage(image1, 14, 14, 200,200,this);
        	}
        }
    	
    }	
    
    @Override
    public void run() {
    	// TODO Auto-generated method stub
        	
    	while(true){
    		
    		try {
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO: handle exception
			}
    		
    		flag = random.nextInt(2);
    		repaint();
    	}
    }
}
