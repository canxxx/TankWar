package com.xu.game8;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.*;
/**
 * @version  TankGame1.8
 */

//读取时 将坦克信息封装到node中
class Node{
	
	int x;
	int y;
	int direct;
	int type ;
	public Node(int x, int y, int direct, int type){
		
		this.x = x;
		this.y = y;
		this.direct = direct;
		this.type = type;
	}
}

//Application 的设置信息
class Config implements ActionListener{
	
    static int myTankNums = 1;
    static int enemyTankNums = 9;
    static int deadEnemyTankNums = 0;
    static int deadmyTankNums = 0;
    static Vector<Tank> tanks = new Vector<Tank>();
    static Vector<Node> nodes = new Vector<Node>();
    static boolean isGoOnPrivious = false; // 是否进行上一局
   
    FileReader fileReader;
    FileWriter fileWriter;
    BufferedReader buffReader;
    BufferedWriter buffWriter;
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	// TODO Auto-generated method stub
    
    	//存盘退出功能
    	if(e.getActionCommand().equals("saveAndExit")){
    		
    	     try {
				fileWriter = new FileWriter("gameInfo.txt");
				buffWriter = new BufferedWriter(fileWriter);
				String configInfo = myTankNums+" "+enemyTankNums+" "+deadmyTankNums+" "+deadEnemyTankNums;
				buffWriter.write(configInfo);
				buffWriter.newLine();
				for(int i=0; i<tanks.size(); i++){
					
					Tank tank = tanks.get(i);
					String info = tank.x+" "+tank.y+" "+tank.direct+" "+tank.type;
					buffWriter.write(info);
					buffWriter.newLine();
				}
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				
				try {
					buffWriter.close();
					fileWriter.close();
					System.exit(0); //只能放在这
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
    	     
    	}
    	//继续上一局游戏
 	   else if(e.getActionCommand().equals("goOnPrivious")){

 		   isGoOnPrivious = true;
			try {
                 
				fileReader = new FileReader("gameInfo.txt");
				buffReader = new BufferedReader(fileReader);
				String n = "";
				n = buffReader.readLine();
				String[] configInfo = n.split(" ");
				myTankNums = Integer.parseInt(configInfo[0]);
				enemyTankNums = Integer.parseInt(configInfo[1]);
				deadmyTankNums = Integer.parseInt(configInfo[2]);
				deadEnemyTankNums = Integer.parseInt(configInfo[3]);
				
				while((n=buffReader.readLine()) != null){
					
					String[] info = n.split(" ");
					Node node = new Node(Integer.parseInt(info[0]), Integer.parseInt(info[1]), Integer.parseInt(info[2]),Integer.parseInt(info[3]));
					nodes.addElement(node);
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}finally{
				
				try {
					buffReader.close();
					fileReader.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
 	   }
    	
    }
    /**
     * 敌人坦克数减一，击杀数加一
     */
    public static void enemyDead(){
    	
    	deadEnemyTankNums++;
    	enemyTankNums--;
    }
    
    /**
     * 我方坦克数量减一，死亡数加一
     */
    public static void myDead(){
    	
    	myTankNums--;
    	deadmyTankNums++;
    }
    
    
}
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
	 int type = 0;
	 boolean isAlive = true;
	 boolean lockAction = false;
     Vector<Bullet> bullets = new Vector<Bullet>();
     //坦克重叠问题代码
     Vector<Tank> tanks = new Vector<Tank>(); //外部所有坦克集合，包括本坦克自身，检测注意排除自身情况


     /**
      * 检测本坦克是否碰撞到其他坦克
      */
     public boolean TouchOther(){
    	 
    	 boolean flag = false;
    	 for(int i=0; i<tanks.size(); i++){
    		 
    		 Tank tank = tanks.get(i);
    		 if(tank != this){ //排除自身情况
    			  
    			 switch(direct){
    			 
    			 case 0:
    				 //第一种他坦克方向上下 (x,y) (x+20,y)
    				 if(tank.direct==0 || tank.direct==1){
    					 //左边
    					 if( x>tank.x && x<tank.x+20 && y>tank.y && y<tank.y+30){
    						 flag = true;
    					 }
    					 //右边
    					if( x+20>tank.x && x+20<tank.x+20 && y>tank.y && y<tank.y+30){
    						
    						flag = true;
    					}
    				 }
    				 //第二种他坦克方向左右
    				 if(tank.direct==2 || tank.direct==3){
    					 
    					 //左边
    					 if( x>tank.x && x<tank.x+30 && y>tank.y && y<tank.y+20){
    						 flag = true;
    					 }
    					 //右边
    					if( x+20>tank.x && x+20<tank.x+30 && y>tank.y && y<tank.y+20){
    						
    						flag = true;
    					}
    				 }
    				 break;
    			 case 1:
       				 //第一种他坦克方向上下 (x,y+30) (x+20,y+30)
    				 if(tank.direct==0 || tank.direct==1){
    					 //左边
    					 if(x>tank.x && x<tank.x+20 && y+30>tank.y && y+30<tank.y+30){
    						 flag = true;
    					 }
    					 //右边
    					if(x+20>tank.x && x+20<tank.x+20 && y+30>tank.y && y+30<tank.y+30){
    						
    						flag = true;
    					}
    				 }
    				 //第二种他坦克方向左右
    				 if(tank.direct==2 || tank.direct==3){
    					 
    					 //左边
    					 if(x>tank.x && x<tank.x+30 && y+30>tank.y && y+30<tank.y+20){
    						 flag = true;
    					 }
    					 //右边
    					if(x+20>tank.x && x+20<tank.x+30 && y+30>tank.y && y+30<tank.y+20){
    						
    						flag = true;
    					}
    				 }
    				 break;
    			 case 2:
       				 //第一种他坦克方向上下 (x,y) (x,y+20)
    				 if(tank.direct==0 || tank.direct==1){
    					 //左边
    					 if(x>tank.x && x<tank.x+20 && y>tank.y && y<tank.y+30){
    						 flag = true;
    					 }
    					 //右边
    					if(x>tank.x && x<tank.x+20 && y+20>tank.y && y+20<tank.y+30){
    						
    						flag = true;
    					}
    				 }
    				 //第二种他坦克方向左右
    				 if(tank.direct==2 || tank.direct==3){
    					 
    					 //左边
    					 if(x>tank.x && x<tank.x+30 && y>tank.y && y<tank.y+20){
    						 flag = true;
    					 }
    					 //右边
    					if(x>tank.x && x<tank.x+30 && y+20>tank.y && y+20<tank.y+20){
    						
    						flag = true;
    					}
    				 }
    				 break;
    			 case 3:
    				//第一种他坦克方向上下(x+30,y) (x+30,y+20)
    				 if(tank.direct==0 || tank.direct==1){
    					 //左边
    					 if(x+30>tank.x && x+30<tank.x+20 && y>tank.y && y<tank.y+30){
    						 flag = true;
    					 }
    					 //右边
    					if(x+30>tank.x && x+30<tank.x+20 && y+20>tank.y && y+20<tank.y+30 ){
    						
    						flag = true;
    					}
    				 }
    				 //第二种他坦克方向左右
    				 if(tank.direct==2 || tank.direct==3){
    					 
    					 //左边
    					 if(x+30>tank.x && x+30<tank.x+30 && y>tank.y && y<tank.y+20){
    						 flag = true;
    					 }
    					 //右边
    					if(x+30>tank.x && x+30<tank.x+30 && y+20>tank.y && y+20<tank.y+20){
    						
    						flag = true;
    					}
    				 }
    				 break;
    			 }
    		 }
    	 }
    	 
    	 return flag;
    	 
     }
     /**
      * 让该坦克熟悉外部环境
      * @param tanks Panel中我方坦克和敌方坦克中的集合
      */
     public void setTanks(Vector<Tank> tanks){
    	 
    	 this.tanks = tanks;
     }
   
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
			  if(type == 1){
				  
				  Config.enemyDead();
			  }else if(type == 0){
				  
				  Config.myDead();
			  }
			  bomb = new Bomb(x,y);
		   }
		   break;
	   case 2:
	   case 3:
			if (bullet.x>x && bullet.x<x + 30 && bullet.y>y && bullet.y<y+20) {

				isAlive = false;
				bullet.isAlive = false;
				if(type == 1){
					  
					  Config.enemyDead();
				  }else if(type == 0){
					  
					  Config.myDead();
				  }
				bomb = new Bomb(x,y);
			}
		   break;
	   }
	   
	   return bomb;
	}
	
}

//敌人坦克
class EnemyTank extends Tank implements Runnable{
	
	Random random ;
	public EnemyTank(int x,int y){
		
		super(x,y);
		direct = 1;
		speed = 1;
		random = new Random();
		type = 1;
	}

	/**
	 * 坦克自动移动
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
	
		while(true){
			
			     if( !lockAction ){
			    	 
					 //坦克自动射出子弹
					 if( bullets.size() < 2){
						 
						 shot();
					 }
					 //移动
					 
					 switch (direct) {
					 
					 case 0:
						 for (int i = 0; i < 30; i++) {
							 if (y > 0 && !TouchOther()) {
								 
								 if(!lockAction){
									 
									 goUp();
								 }
								 
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
							 if ( y < 370 && !TouchOther()) {

							if (!lockAction) {

								goDown();
							}
								 
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
							 if ( x > 0 && !TouchOther()) {

							if (!lockAction) {

								goLeft();
							}
								 
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
							 if ( x < 470 && !TouchOther()) {

							if (!lockAction) {

								goRight();
							}
								 
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
					 if(random != null && !lockAction){
						 
						 direct = random.nextInt(4);
					 }
					 //结束进程
					 if(isAlive == false){
						 
						 break;
					 }
			     }else {
			    	 
			    	 try {
						Thread.sleep(100);
					} catch (Exception e) {
						// TODO: handle exception
					    e.printStackTrace();
					}
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
	 boolean lockBullet = false;
		
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
			
			if(!lockBullet){
				
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
}

