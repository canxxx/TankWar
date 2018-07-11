package com.xu.game3;
import java.util.*;
import java.awt.*;
import javax.swing.*;
/**
 * @version  TankGame1.4
 */

//爆炸类
class Bomb{
	
	int x, y;
	int life = 9;
    boolean isAlive = true;	
	public Bomb(int x, int y){
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 *  减掉bomb的life
	 */
	public void lifeDown(){
		
		if(life > 0){
			
			life--;
		}else{
			
			isAlive = false;
		}
	} 
	
	
	
}
//坦克类
class Tank{
	
	 int x; //坦克的横坐标
	 int y; //坦克的纵坐标
	 int speed = 5; //坦克移动速度
	 int direct = 0;//坦克方向
	 boolean isAlive = true;
     Vector<Bullet> bullets = new Vector<Bullet>();
   
    public Tank(int x, int y){
    	
    	this.x = x;
    	this.y = y;
    }
    
   
	public void goUp(){
		
		direct = 0;
		y -= speed;
	}
	public void goDown(){
		direct = 1;
		y += speed;
	}
	public void goLeft(){
		direct = 2;
		x -= speed;
	}
	public void goRight(){
		direct = 3;
		x += speed;
	}
	
	/**
	 * 每执行一次根据坦克的方向创建一颗子弹，并启动该子弹的线程
	 */
	public void shot(){
		
		Bullet bullet = null;
		
        switch(direct){
        case 0:
        	bullet = new Bullet(x+10,y,0);
        	break;
        case 1:
        	bullet = new Bullet(x+10,y+30,1);
        	break;
        case 2:
        	bullet = new Bullet(x,y+10,2);
        	break;
        case 3:
        	bullet = new Bullet(x+30, y+10,3);
        	break;
         
        }
      bullets.add(bullet);
      new Thread(bullet).start();
	}
   
	/**
	 * 检测子弹是否击中坦克
	 * @param bullet
	 */
	public Bomb crashBullet(Bullet bullet){
		
	   Bomb bomb = null;
	   switch(direct){
	   
	   case 0:
	   case 1:
		   //坦克处于上下位置
		   if( bullet.x>x && bullet.x<x+20 && bullet.y>y && bullet.y<y+30){
			   
			  isAlive = false;
			  bullet.isAlive = false;
			  bomb = new Bomb(x,y);
		   }
		   break;
	   case 2:
	   case 3:
			if (bullet.x>x && bullet.x<x + 30 && bullet.y>y && bullet.y<y+20) {

				isAlive = false;
				bullet.isAlive = false;
				bomb = new Bomb(x,y);
			}
		   break;
	   }
	   
	   
	   return bomb;
	}
}

//敌人坦克
class EnemyTank extends Tank implements Runnable{
	
	public EnemyTank(int x,int y){
		
		super(x,y);
		direct = 1;
		speed = 1;
	}

	/**
	 * 坦克自动移动
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
	
		while(true){
			
			//坦克自动射出子弹
			if( bullets.size() < 2){
				
				shot();
			}
			//移动
			Random random = new Random();
			switch (direct) {

			case 0:
				for (int i = 0; i < 30; i++) {
					if (y > 0 ) {

						goUp();

					}
					try {
						
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				break;

			case 1:
				for (int i = 0; i < 30; i++) {
					if ( y < 400 ) {

						goDown();

					}
					try {
						
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				break;

			case 2:
				for (int i = 0; i < 30; i++) {
					if ( x > 0 ) {

						goLeft();

					}
					try {
						
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				break;

			case 3:
				for (int i = 0; i < 30; i++) {
					if ( x < 500) {

						goRight();

					}
					try {
						
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				break;

			}
			
			direct = random.nextInt(4);
			//结束进程
			if(isAlive == false){
				
				break;
			}
		}
	}
	
}

//我的坦克

class MyTank extends Tank{
	
	
	public MyTank(int x,int y){
		super(x, y);
		
	}
	

}


class Bullet implements Runnable{
	
	 int x;
	 int y;
	 int direct;
	 int speed =5;
	 boolean isAlive = true;
		
	public Bullet(int x, int y, int direct){
		
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
	
	public void run(){
		
		while(true){
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		   switch(direct){
		   
		   case 0:
			   y-=speed;
			   break;
		   case 1:
			   y+=speed;
			   break;
		   case 2:
			   x-=speed;
			   break;
		   case 3:
			   x+=speed;
			   break;
		   }
		    if(x<0 || x>500 || y<0 || y>400){
		    	
		    	isAlive  = false;
		    	break;
		    }
		}
		
		
	}
}

