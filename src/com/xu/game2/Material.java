package com.xu.game2;
import java.util.*;
import java.awt.*;
import javax.swing.*;
/**
 * @version  TankGame1.2
 */

//��ը��
class Bomb{
	
	int x, y;
	Image imag1,imag2,imag3;
	int life = 9;
    boolean isAlive = true;	
	public Bomb(int x, int y){
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 *  ����bomb��life
	 */
	public void lifeDown(){
		
		if(life > 0){
			
			life--;
		}else{
			
			isAlive = false;
		}
	} 
	
	
	
}
//̹����
class Tank{
	
	 int x; //̹�˵ĺ�����
	 int y; //̹�˵�������
	 int speed = 5; //̹���ƶ��ٶ�
	 int direct = 0;//̹�˷���
	 boolean isAlive = true;
     //Bullet bullet;
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
	 * ����ӵ��Ƿ����̹��
	 * @param bullet
	 */
	public Bomb crashBullet(Bullet bullet){
		
	   Bomb bomb = null;
	   switch(direct){
	   
	   case 0:
	   case 1:
		   //̹�˴�������λ��
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

//����̹��
class EnemyTank extends Tank{
	
	public EnemyTank(int x,int y){
		
		super(x,y);
		direct = 1;
	}

	
	
}

//�ҵ�̹��

class MyTank extends Tank{
	
	
	public MyTank(int x,int y){
		super(x, y);
		
	}
	

}


class Bullet implements Runnable{
	
	 int x;
	 int y;
	 int direct;
	 int speed =1;
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

