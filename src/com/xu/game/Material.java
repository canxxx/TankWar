package com.xu.game;
import java.util.*;
/**
 * 
 * @version TankeGame1.0
 */
//坦克类
class Tank{
	
	private int x; //坦克的横坐标
	private int y; //坦克的纵坐标
	private int speed = 1; //坦克移动速度
    private	int direct = 0;//坦克方向
    private Bullet bullet;
   


	//get set方法
	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public int getDirect() {
		return direct;
	}


	public void setDirect(int direct) {
		this.direct = direct;
	}


	public Bullet getBullet() {
		return bullet;
	}


	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}


 
    
    //构造方法
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
      new Thread(bullet).start();
	}

}

//敌人坦克
class EnemyTank extends Tank{
	
	public EnemyTank(int x,int y){
		
		super(x,y);
		setDirect(1);
	}

	
	
}

//我的坦克

class MyTank extends Tank{
	
	
	public MyTank(int x,int y){
		super(x, y);
		
	}
	

}


class Bullet implements Runnable{
	
	private int x;
	private int y;
	private int direct;
	private int speed =5;
	private boolean isAlive = true;
	

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	
	
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

