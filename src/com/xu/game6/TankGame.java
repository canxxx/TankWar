package com.xu.game6;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.*;
/**
 * @version  TankGame1.6
 *    1 ������ͣ����
 *    2 ������������
 *    bug �޸���ͣ�󲿷�̹���ӳٺ���ͣ����
 */
public class TankGame extends JFrame implements ActionListener{
 
	MyPanel myPanel ;
	FirstPanel firstPanel;
	JMenuBar bar;
	JMenu menu;
	JMenuItem start;
	JMenuItem pause;
	JMenuItem goOn;
	
	public static void main(String[] args){
		
		new TankGame();
	}
	
	//���캯��
	public TankGame(){
		
	   firstPanel = new FirstPanel();
	   bar = new JMenuBar();
	   menu = new JMenu("�˵�");
	   start = new JMenuItem("��ʼ��Ϸ");
	   pause = new JMenuItem("��ͣ");
	   goOn = new JMenuItem("����");
	   
	   start.addActionListener(this);
	   start.setActionCommand("start");
	   
	   new Thread(firstPanel).start();
	   
	   this.setJMenuBar(bar);
	   bar.add(menu);
	   menu.add(start);
	   menu.addSeparator();
	   this.add(firstPanel);
	   
	   
	   //��������������
	   this.setTitle("TankGame Asa");
	   this.setSize(700, 600);
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   this.setVisible(true);
	}

   @Override
  public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	   
	   if(e.getActionCommand().equals("start")){
		   
		   myPanel = new MyPanel();
		   
		   new Thread(myPanel).start();
		   //ע�������
		   this.addKeyListener(myPanel);
		   pause.addActionListener(myPanel);
		   pause.setActionCommand("pause");
		   goOn.addActionListener(myPanel);
		   goOn.setActionCommand("goOn");
		   //�Ƴ���ʼ��panel������µ�panel
		   this.remove(firstPanel);
		   this.add(myPanel);
		   menu.add(pause);
		   menu.add(goOn);
		   menu.addSeparator();
		   this.setVisible(true);
	   }
  }
	
	
}

class FirstPanel extends JPanel implements Runnable{
	
	int time;
	public void paint(Graphics g){
		
		super.paint(g);
        g.fillRect(0, 0, 500, 400);        
        Font font = new Font("����", Font.BOLD, 30);
        g.setFont(font);
        g.setColor(Color.yellow);
        if(time % 2 == 0){
        	g.drawString("��һ��", 200, 200);
        }
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(true){
			try {
				
				Thread.sleep(300);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			time++;
			repaint();
		}
	}
	

}


//ս������
class MyPanel extends JPanel implements KeyListener,Runnable,ActionListener{
	
	
	MyTank myTank ;
	Vector<EnemyTank> enemyTanks;
	int enemySize =10; // ����̹�˵�����
    Vector<Bomb> bombs = new Vector<Bomb>() ;
	Image image1,image2,image3;
	Vector<Tank> allTank = new Vector<Tank>();
	
	public MyPanel(){
		
		myTank = new MyTank(250,300);
		allTank.add(myTank);
	    enemyTanks = new Vector<EnemyTank>();
		for(int i=0; i<enemySize; i++){
			
			EnemyTank enemyTank = new EnemyTank((i+1)*50, 0);
			enemyTanks.add(enemyTank);
			//������̹����ӵ�����̹����
			allTank.add(enemyTank);
			new Thread(enemyTank).start();
		}
		
		try {
			image1 = ImageIO.read(new File("bomb_1.png"));
			image2 = ImageIO.read(new File("bomb_2.png"));
			image3 = ImageIO.read(new File("bomb_3.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//������̹����Ϥ�ⲿ����
		myTank.setTanks(allTank);
		for(int i=0; i<enemyTanks.size(); i++){
			
			EnemyTank enemyTank = enemyTanks.get(i);
			enemyTank.setTanks(allTank);
		}
	}
	
	public void paint(Graphics g){
		
		super.paint(g);
		g.fillRect(0, 0, 500, 400);
		
		//�����ҵ�̹��
		if(myTank.isAlive == true){
			
			this.drawTank(myTank.x, myTank.y, g, myTank.direct, 0);
		}
		
		//�����ӵ�
		for(int i=0; i<myTank.bullets.size(); i++){
			
			Bullet bullet = myTank.bullets.get(i);
			
			if( bullet.isAlive == true){
				
				drawBullet(bullet.x, bullet.y, g);
			}
			else{
				
				myTank.bullets.remove(bullet);
			}
			
		}
		
        //������ըЧ��
		for(int i=0; i<bombs.size(); i++){
			
			Bomb bomb = bombs.get(i);
			if(bomb.life >6){
				
				g.drawImage(image1, bomb.x, bomb.y, 30, 30, this);
			}else if(bomb.life>3 ){
				
				g.drawImage(image2, bomb.x, bomb.y, 30, 30, this);
			}else{
				
				g.drawImage(image3, bomb.x, bomb.y, 30, 30, this);
			}
			
			bomb.lifeDown();
			
			if(bomb.isAlive == false){
				
				bombs.remove(bomb);
			}
		}
		//��������̹��
		for(int i=0; i<enemyTanks.size(); i++){
		    
			EnemyTank enemyTank = enemyTanks.get(i);
			//���������ӵ�
			for(int j=0; j<enemyTank.bullets.size(); j++){
				
				Bullet bullet = enemyTank.bullets.get(j);
				
				if(bullet.isAlive == true){
					
					drawBullet(bullet.x, bullet.y, g);
				}else{
					
					enemyTank.bullets.remove(bullet);
				}
			}//
			if(enemyTank.isAlive == true){
				
				this.drawTank(enemyTank.x, enemyTank.y, g, enemyTank.direct, 1);
			}
			else{
				
				enemyTanks.remove(enemyTank);

			}
		}
		


	}
 
	/**
	 * �����ӵ�
	 * @param x
	 * @param y
	 * @param g
	 */
	public void drawBullet(int x, int y, Graphics g){
		
		g.setColor(Color.white);
		g.draw3DRect(x, y, 1, 1, false);
	}
	
	/**
	 * ����̹�� 
	 * @param x
	 * @param y
	 * @param g
	 * @param direct
	 * @param type
	 */
    public void drawTank(int x,int y,Graphics g, int direct,int type){
    
    	switch(type){
    	
    	case 0:
    		g.setColor(Color.yellow);
    		break;
    	case 1:
    		g.setColor(Color.cyan);
    		break;
    	}
    	
    	switch(direct){
    	
    	//̹�˷�����
    	case 0:
    		g.fill3DRect(x, y, 5, 30, false);
    		g.fill3DRect(x+5, y+5, 10, 20, false);
    		g.fill3DRect(x+15, y, 5, 30, false);
    	    g.fillOval(x+5, y+10, 8, 8);
    	    g.drawLine(x+9, y+14, x+9, y);
    		break;
       //̹�˷�����
    	case 1:
    		g.fill3DRect(x, y, 5, 30, false);
    		g.fill3DRect(x+5, y+5, 10, 20, false);
    		g.fill3DRect(x+15, y, 5, 30, false);
    	    g.fillOval(x+5, y+10, 8, 8);
    	    g.drawLine(x+9, y+14, x+9, y+28);
    		break;
       //̹�˷�����
    	case 2:
    		g.fill3DRect(x, y, 30, 5, false);
    		g.fill3DRect(x, y+15, 30, 5, false);
    		g.fill3DRect(x+5, y+5, 20, 10, false);
    		g.fillOval(x+10, y+5, 8, 8);
    		g.drawLine(x+14, y+9, x, y+9);
    		break;
       //̹�˷�����
    	case 3:
    		g.fill3DRect(x, y, 30, 5, false);
    		g.fill3DRect(x, y+15, 30, 5, false);
    		g.fill3DRect(x+5, y+5, 20, 10, false);
    		g.fillOval(x+10, y+5, 8, 8);
    		g.drawLine(x+14, y+9, x+28, y+9);
    		break;
    	}
    }
    
    
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
        //����������������Ӧ
		
		//�ҵ�̹��
		switch (e.getKeyChar()) {

		case 'w':
            if(myTank.y>0 && !myTank.lockAction){
            	
            	myTank.goUp();
            }
			break;
		case 's':
            if(myTank.y<330 && !myTank.lockAction){
            	
            	myTank.goDown();
            }
			break;
		case 'a':
            if(myTank.x>0 && !myTank.lockAction){
            	
            	myTank.goLeft();
            }
			break;
		case 'd':
            if(myTank.x<450 && !myTank.lockAction){
            	
            	myTank.goRight();
            }
			break;
		}
		
		if(e.getKeyChar() == 'j'){
			
			if(myTank.bullets.size() < 5 && !myTank.lockAction){
				
				myTank.shot();
			}
		}
		
		//�ػ�
		repaint();
        	   
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
	}
	
	/**
	 * ������̹���ӵ�ֹͣ���л
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		

		if(e.getActionCommand().equals("pause")){
			
			for(int i=0; i<allTank.size(); i++){
				
			   Tank tank = allTank.get(i);
			   tank.lockAction = true;
			   for(int j=0; j<tank.bullets.size(); j++){
				   
				   tank.bullets.get(j).lockBullet = true;
			   }
				
			}
		}
		else if(e.getActionCommand().equals("goOn")){
			
			for(int i=0; i<allTank.size(); i++){
				
				   Tank tank = allTank.get(i);
				   tank.lockAction = false;
				   for(int j=0; j<tank.bullets.size(); j++){
					   
					   tank.bullets.get(j).lockBullet = false;
				   }
		
			}
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		

		while(true){
		
			
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			//����crashBullet()��������ҷ�̹���Ƿ������ӵ�
			if(myTank.isAlive == true){
				
				for(int i=0; i<enemyTanks.size(); i++){
					
					EnemyTank enemyTank = enemyTanks.get(i);
					for(int j=0; j<enemyTank.bullets.size(); j++){
						
						Bullet bullet = enemyTank.bullets.get(j);
						Bomb bomb = myTank.crashBullet(bullet);
						if(bomb !=null){
							
							bombs.add(bomb);
						}
						
					}
				}
			}
			//����crashBullet()�������з�̹���Ƿ������ӵ�
	        for(int i=0; i<enemyTanks.size(); i++){
	        	
	        	EnemyTank enemyTank = enemyTanks.get(i);
	        	for(int j=0; j<myTank.bullets.size(); j++){
	        		
	        		Bullet bullet = myTank.bullets.get(j);
	        		Bomb bomb = enemyTank.crashBullet(bullet);
	        		if(bomb != null){
	        			
	        			bombs.addElement(bomb);
	        		}
	        		
	        	}
	        }
	
			repaint();
		}
	}
	
	

}


