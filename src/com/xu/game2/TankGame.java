package com.xu.game2;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * @version  TankGame1.2
 *   �ҷ�̹�˻��ез�̹��
 *      ����ʱ���б�ըЧ��
 */
public class TankGame extends JFrame {
 
	MyPanel myPanel ;
	public static void main(String[] args){
		
		new TankGame();
	}
	
	//���캯��
	public TankGame(){
		
	   myPanel = new MyPanel();
	   new Thread(myPanel).start();
	   //ע�������
	   this.addKeyListener(myPanel);
	   
	   //������
	   this.add(myPanel);
	   
	   
	   //��������������
	   this.setTitle("̹�˴�ս  ��������");
	   this.setSize(500, 400);
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   this.setVisible(true);
	}


	
	
}

//�ҵ����
class MyPanel extends JPanel implements KeyListener,Runnable{
	
	
	MyTank myTank ;
	Vector<EnemyTank> enemyTanks;
	int enemySize =3; // ����̹�˵�����
    Vector<Bomb> bombs = new Vector<Bomb>() ;
	Image image1,image2,image3;
	
	public MyPanel(){
		
		myTank = new MyTank(250,300);
	    enemyTanks = new Vector<EnemyTank>();
		for(int i=0; i<enemySize; i++){
			
			EnemyTank enemyTank = new EnemyTank((i+1)*50, 0);
			enemyTanks.add(enemyTank);
		}
		
	   //��ʼ��ͼƬ
		image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.png"));
		image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.png"));
		image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.png"));
		
	}
	
	public void paint(Graphics g){
		
		super.paint(g);
		g.fillRect(0, 0, 500, 400);
		
		//�����ҵ�̹��
		this.drawTank(myTank.x, myTank.y, g, myTank.direct, 0);
		
        //������ըЧ��
		for(int i=0; i<bombs.size(); i++){
			
			Bomb bomb = bombs.get(i);
			if(bomb.life >6){
				
				g.drawImage(image1, bomb.x, bomb.y, 30, 30, this);
			}else if(bomb.life>3 && bomb.life<7){
				
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
			if(enemyTank.isAlive == true){
				
				this.drawTank(enemyTank.x, enemyTank.y, g, enemyTank.direct, 1);
			}
			else{
				
				enemyTanks.remove(enemyTank);

			}
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
		switch(e.getKeyChar()){
           
           case 'w':
        	   myTank.goUp();
        	   break;
           case 's':
        	   myTank.goDown();
        	   break;
           case 'a':
        	   myTank.goLeft();;
        	   break;
           case 'd':
        	   myTank.goRight();;
        	   break;
           }
		
		if(e.getKeyChar() == 'j'){
			
			if(myTank.bullets.size() < 5){
				
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
			
			//����crashBullet()����
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


