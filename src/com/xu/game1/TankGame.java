package com.xu.game1;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * @version  TankGame1.1
 *   1我的坦克能够连发五颗子弹  
 */
public class TankGame extends JFrame {
 
	MyPanel myPanel ;
	public static void main(String[] args){
		
		new TankGame();
	}
	
	//构造函数
	public TankGame(){
		
	   myPanel = new MyPanel();
	   new Thread(myPanel).start();
	   //注册监听器
	   this.addKeyListener(myPanel);
	   
	   //添加组件
	   this.add(myPanel);
	   
	   
	   //设置主窗体属性
	   this.setTitle("坦克大战  老徐制作");
	   this.setSize(500, 400);
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   this.setVisible(true);
	}


	
	
}

//我的面板
class MyPanel extends JPanel implements KeyListener,Runnable{
	
	//定义一个我的坦克
	MyTank myTank ;
	Vector<EnemyTank> enemyTanks;
	int enemySize =3; // 敌人坦克的数量
	
	
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
		
		//画出我的坦克
		this.drawTank(myTank.x, myTank.y, g, myTank.direct, 0);
		//画出敌人坦克
		for(int i=0; i<enemyTanks.size(); i++){
		
	       this.drawTank(enemyTanks.get(i).x, enemyTanks.get(i).y, g, enemyTanks.get(i).direct, 1);
		}
		
		//画出子弹
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
	 * 画出子弹
	 * @param x
	 * @param y
	 * @param g
	 */
	public void drawBullet(int x, int y, Graphics g){
		
		g.setColor(Color.white);
		g.draw3DRect(x, y, 1, 1, false);
	}
	
	/**
	 * 画出坦克 
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
    	
    	//坦克方向朝上
    	case 0:
    		g.fill3DRect(x, y, 5, 30, false);
    		g.fill3DRect(x+5, y+5, 10, 20, false);
    		g.fill3DRect(x+15, y, 5, 30, false);
    	    g.fillOval(x+5, y+10, 8, 8);
    	    g.drawLine(x+9, y+14, x+9, y);
    		break;
       //坦克方向朝下
    	case 1:
    		g.fill3DRect(x, y, 5, 30, false);
    		g.fill3DRect(x+5, y+5, 10, 20, false);
    		g.fill3DRect(x+15, y, 5, 30, false);
    	    g.fillOval(x+5, y+10, 8, 8);
    	    g.drawLine(x+9, y+14, x+9, y+28);
    		break;
       //坦克方向朝左
    	case 2:
    		g.fill3DRect(x, y, 30, 5, false);
    		g.fill3DRect(x, y+15, 30, 5, false);
    		g.fill3DRect(x+5, y+5, 20, 10, false);
    		g.fillOval(x+10, y+5, 8, 8);
    		g.drawLine(x+14, y+9, x, y+9);
    		break;
       //坦克方向朝右
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
        //监听按键并做出反应
		
		//我的坦克
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
		
		//重绘
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


