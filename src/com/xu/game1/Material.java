package com.xu.game1;
import java.util.*;
/**
 * @version  TankGame1.1
 */
//̹����
class Tank{
	
	 int x; //̹�˵ĺ�����
	 int y; //̹�˵�������
	 int speed = 1; //̹���ƶ��ٶ�
	 int direct = 0;//̹�˷���
     //Bullet bullet;
     Vector<Bullet> bullets = new Vector<Bullet>();
   


    
    //���췽��
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

