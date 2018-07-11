package com.xu.game;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * @version TankeGame1.0
 *  1 �����ҵ�̹��
 *  2 �ҵ�̹�����������ƶ�
 *  3 ��������̹��
 *  4 �ҷ�̹�˷����ӵ�
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
	
	//����һ���ҵ�̹��
	MyTank myTank ;
	Vector<EnemyTank> enemyTanks;
	int enemySize =3; // ����̹�˵�����
	public MyPanel(){
		
		myTank = new MyTank(250,300);
	    enemyTanks = new Vector<EnemyTank>();
		for(int i=0; i<enemySize; i++){
			
			EnemyTank enemyTank = new EnemyTank((i+1)*50, 0);
			enemyTanks.add(enemyTank);
		}
	}
	
	public void paint(Graphics g){
		
		super.paint(g);
		g.fillRect(0, 0, 500, 400);
		
		//�����ҵ�̹��
		this.drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 0);
		//��������̹��
		for(int i=0; i<enemyTanks.size(); i++){
		
	       this.drawTank(enemyTanks.get(i).getX(), enemyTanks.get(i).getY(), g, enemyTanks.get(i).getDirect(), 1);
		}
		
		
		if(myTank.getBullet() != null && myTank.getBullet().isAlive() == true){
			
			drawBullet(myTank.getBullet().getX(), myTank.getBullet().getY(), g);
		}
	}
 
	//�����ӵ�
	public void drawBullet(int x, int y, Graphics g){
		
		g.setColor(Color.white);
		g.draw3DRect(x, y, 1, 1, false);
	}
	//����̹�˺���
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
			
			myTank.shot();
		}
		repaint();
        	   
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		//test
		for(int i=0; i<enemySize; i++){
			
			enemyTanks.get(i).shot();
		}

		while(true){
		
			
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	
			repaint();
		}
	}
	

}


