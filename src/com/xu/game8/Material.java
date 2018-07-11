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

//��ȡʱ ��̹����Ϣ��װ��node��
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

//Application ��������Ϣ
class Config implements ActionListener{
	
    static int myTankNums = 1;
    static int enemyTankNums = 9;
    static int deadEnemyTankNums = 0;
    static int deadmyTankNums = 0;
    static Vector<Tank> tanks = new Vector<Tank>();
    static Vector<Node> nodes = new Vector<Node>();
    static boolean isGoOnPrivious = false; // �Ƿ������һ��
   
    FileReader fileReader;
    FileWriter fileWriter;
    BufferedReader buffReader;
    BufferedWriter buffWriter;
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	// TODO Auto-generated method stub
    
    	//�����˳�����
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
					System.exit(0); //ֻ�ܷ�����
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
    	     
    	}
    	//������һ����Ϸ
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
     * ����̹������һ����ɱ����һ
     */
    public static void enemyDead(){
    	
    	deadEnemyTankNums++;
    	enemyTankNums--;
    }
    
    /**
     * �ҷ�̹��������һ����������һ
     */
    public static void myDead(){
    	
    	myTankNums--;
    	deadmyTankNums++;
    }
    
    
}
//��ը��
class Bomb{
	
	int x, y;
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
	 int type = 0;
	 boolean isAlive = true;
	 boolean lockAction = false;
     Vector<Bullet> bullets = new Vector<Bullet>();
     //̹���ص��������
     Vector<Tank> tanks = new Vector<Tank>(); //�ⲿ����̹�˼��ϣ�������̹���������ע���ų��������


     /**
      * ��Ȿ̹���Ƿ���ײ������̹��
      */
     public boolean TouchOther(){
    	 
    	 boolean flag = false;
    	 for(int i=0; i<tanks.size(); i++){
    		 
    		 Tank tank = tanks.get(i);
    		 if(tank != this){ //�ų��������
    			  
    			 switch(direct){
    			 
    			 case 0:
    				 //��һ����̹�˷������� (x,y) (x+20,y)
    				 if(tank.direct==0 || tank.direct==1){
    					 //���
    					 if( x>tank.x && x<tank.x+20 && y>tank.y && y<tank.y+30){
    						 flag = true;
    					 }
    					 //�ұ�
    					if( x+20>tank.x && x+20<tank.x+20 && y>tank.y && y<tank.y+30){
    						
    						flag = true;
    					}
    				 }
    				 //�ڶ�����̹�˷�������
    				 if(tank.direct==2 || tank.direct==3){
    					 
    					 //���
    					 if( x>tank.x && x<tank.x+30 && y>tank.y && y<tank.y+20){
    						 flag = true;
    					 }
    					 //�ұ�
    					if( x+20>tank.x && x+20<tank.x+30 && y>tank.y && y<tank.y+20){
    						
    						flag = true;
    					}
    				 }
    				 break;
    			 case 1:
       				 //��һ����̹�˷������� (x,y+30) (x+20,y+30)
    				 if(tank.direct==0 || tank.direct==1){
    					 //���
    					 if(x>tank.x && x<tank.x+20 && y+30>tank.y && y+30<tank.y+30){
    						 flag = true;
    					 }
    					 //�ұ�
    					if(x+20>tank.x && x+20<tank.x+20 && y+30>tank.y && y+30<tank.y+30){
    						
    						flag = true;
    					}
    				 }
    				 //�ڶ�����̹�˷�������
    				 if(tank.direct==2 || tank.direct==3){
    					 
    					 //���
    					 if(x>tank.x && x<tank.x+30 && y+30>tank.y && y+30<tank.y+20){
    						 flag = true;
    					 }
    					 //�ұ�
    					if(x+20>tank.x && x+20<tank.x+30 && y+30>tank.y && y+30<tank.y+20){
    						
    						flag = true;
    					}
    				 }
    				 break;
    			 case 2:
       				 //��һ����̹�˷������� (x,y) (x,y+20)
    				 if(tank.direct==0 || tank.direct==1){
    					 //���
    					 if(x>tank.x && x<tank.x+20 && y>tank.y && y<tank.y+30){
    						 flag = true;
    					 }
    					 //�ұ�
    					if(x>tank.x && x<tank.x+20 && y+20>tank.y && y+20<tank.y+30){
    						
    						flag = true;
    					}
    				 }
    				 //�ڶ�����̹�˷�������
    				 if(tank.direct==2 || tank.direct==3){
    					 
    					 //���
    					 if(x>tank.x && x<tank.x+30 && y>tank.y && y<tank.y+20){
    						 flag = true;
    					 }
    					 //�ұ�
    					if(x>tank.x && x<tank.x+30 && y+20>tank.y && y+20<tank.y+20){
    						
    						flag = true;
    					}
    				 }
    				 break;
    			 case 3:
    				//��һ����̹�˷�������(x+30,y) (x+30,y+20)
    				 if(tank.direct==0 || tank.direct==1){
    					 //���
    					 if(x+30>tank.x && x+30<tank.x+20 && y>tank.y && y<tank.y+30){
    						 flag = true;
    					 }
    					 //�ұ�
    					if(x+30>tank.x && x+30<tank.x+20 && y+20>tank.y && y+20<tank.y+30 ){
    						
    						flag = true;
    					}
    				 }
    				 //�ڶ�����̹�˷�������
    				 if(tank.direct==2 || tank.direct==3){
    					 
    					 //���
    					 if(x+30>tank.x && x+30<tank.x+30 && y>tank.y && y<tank.y+20){
    						 flag = true;
    					 }
    					 //�ұ�
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
      * �ø�̹����Ϥ�ⲿ����
      * @param tanks Panel���ҷ�̹�˺͵з�̹���еļ���
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
	 * ÿִ��һ�θ���̹�˵ķ��򴴽�һ���ӵ������������ӵ����߳�
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

//����̹��
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
	 * ̹���Զ��ƶ�
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
	
		while(true){
			
			     if( !lockAction ){
			    	 
					 //̹���Զ�����ӵ�
					 if( bullets.size() < 2){
						 
						 shot();
					 }
					 //�ƶ�
					 
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
					 //��������
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

