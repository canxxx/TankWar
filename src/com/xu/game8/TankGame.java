package com.xu.game8;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.*;
/**
 * @version  TankGame1.8
 * 
 * bug: 敌我双方坦克越界
 * 1 退出功能
 * 2 存盘退出功能
 * 3 继续上局游戏
 */
public class TankGame extends JFrame implements ActionListener{
 
	MyPanel myPanel ;
	FirstPanel firstPanel;
	JMenuBar bar;
	JMenu menu;
	JMenuItem start;
	JMenuItem pause;
	JMenuItem goOn;
	JMenuItem exit;
	JMenuItem saveAndExit;
	JMenuItem goOnPrivious;
	Config config = new Config();
	
	public static void main(String[] args){
		
		new TankGame();
	}
	
	//构造函数
	public TankGame(){
		
	   
	   firstPanel = new FirstPanel();
	   bar = new JMenuBar();
	   menu = new JMenu("菜单");
	   start = new JMenuItem("开始游戏");
	   pause = new JMenuItem("暂停");
	   goOn = new JMenuItem("继续");
	   exit = new JMenuItem("退出");
	   saveAndExit = new JMenuItem("存盘退出游戏");
	   goOnPrivious = new JMenuItem("继续上一局");
	   
	  
	   goOnPrivious.addActionListener(this);//第二个观察者
	   goOnPrivious.addActionListener(config);
	   goOnPrivious.setActionCommand("goOnPrivious");

	   exit.addActionListener(this);
	   exit.setActionCommand("exit");
	   start.addActionListener(this);
	   start.setActionCommand("start");
	   saveAndExit.addActionListener(config);
	   saveAndExit.setActionCommand("saveAndExit");
	   
	  


	   
	   new Thread(firstPanel).start();
	   
	   this.setJMenuBar(bar);
	   bar.add(menu);
	   menu.add(start);
	   menu.add(exit);
	   menu.addSeparator();
	   menu.add(goOnPrivious);
	   this.add(firstPanel);
	   
	   
	   //设置主窗体属性
	   this.setTitle("TankGame Asa");
	   this.setSize(700, 600);
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   this.setVisible(true);
	}

   @Override
  public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	   
	   if(e.getActionCommand().equals("start" ) || e.getActionCommand().equals("goOnPrivious")){
		   
		   myPanel = new MyPanel();
		   pause.addActionListener(myPanel);
		   pause.setActionCommand("pause");
		   goOn.addActionListener(myPanel);
		   goOn.setActionCommand("goOn");
		   this.addKeyListener(myPanel);
		   //移除开始的panel，添加新的panel
		   //将所有坦克存入配置类
		   Config.tanks = myPanel.allTank;
		   new Thread(myPanel).start();
		   //注册监听器
		   this.remove(firstPanel);
		   menu.remove(goOnPrivious);
		   this.add(myPanel);
		   menu.add(pause);
		   menu.add(goOn);
		   menu.addSeparator();
		   menu.add(saveAndExit);
		   this.setVisible(true);
	   }
	   else if(e.getActionCommand().equals("exit")){
		   
		   System.exit(0);
	   }

  }
	
	
}

class FirstPanel extends JPanel implements Runnable{
	
	int time;
	public void paint(Graphics g){
		
		super.paint(g);
        g.fillRect(0, 0, 500, 400);        
        Font font = new Font("宋体", Font.BOLD, 30);
        g.setFont(font);
        g.setColor(Color.yellow);
        if(time % 2 == 0){
        	g.drawString("第一关", 200, 200);
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


//战场界面
class MyPanel extends JPanel implements KeyListener,Runnable,ActionListener{
	
	
	MyTank myTank ;
	Vector<EnemyTank> enemyTanks;
    Vector<Bomb> bombs = new Vector<Bomb>() ;
	Image image1,image2,image3;
	Vector<Tank> allTank = new Vector<Tank>();
	
	public MyPanel(){
		
		enemyTanks = new Vector<EnemyTank>();
		if(!Config.isGoOnPrivious){
			
			myTank = new MyTank(250, 300);
			allTank.add(myTank);
			for (int i = 0; i < Config.enemyTankNums; i++) {

				EnemyTank enemyTank = new EnemyTank((i + 1) * 50, 0);
				enemyTanks.add(enemyTank);
				// 将敌人坦克添加到所有坦克组
				allTank.add(enemyTank);
				new Thread(enemyTank).start();
			}
		}
		else{
		     
		    for(int i=0; i<Config.nodes.size(); i++){
		    	
		    	Node node = Config.nodes.get(i);
		    	if(node.type == 0){
		    		
		    		myTank = new MyTank(node.x, node.y);
		    		myTank.direct = node.direct;
		    		allTank.add(myTank);
		    	}
		    	else if(node.type == 1){

		    		
		    		EnemyTank enemyTank = new EnemyTank(node.x, node.y);
		    		enemyTank.direct = node.direct;
		    		enemyTanks.add(enemyTank);
		    		allTank.add(enemyTank);
		    		new Thread(enemyTank).start();
		    	}
		    }
		    
		    if(myTank == null){
		    	
	    		
	    		myTank = new MyTank(0, 0);
	    		myTank.isAlive = false;
		    }
		    
		}
		
		try {
			image1 = ImageIO.read(new File("bomb_1.png"));
			image2 = ImageIO.read(new File("bomb_2.png"));
			image3 = ImageIO.read(new File("bomb_3.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//让所有坦克熟悉外部世界
		myTank.setTanks(allTank);
		for(int i=0; i<enemyTanks.size(); i++){
			
			EnemyTank enemyTank = enemyTanks.get(i);
			enemyTank.setTanks(allTank);
		}
		
	}
	
	public void paint(Graphics g){
		
		super.paint(g);
		g.fillRect(0, 0, 500, 400);
		
		//显示用户数据
		showPlayerData(g);
		//画出我的坦克
		if(myTank.isAlive == true){
			
			this.drawTank(myTank.x, myTank.y, g, myTank.direct, 0);
		}else{
			
			allTank.remove(myTank);
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
		
        //画出爆炸效果
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
		//画出敌人坦克
		for(int i=0; i<enemyTanks.size(); i++){
		    
			EnemyTank enemyTank = enemyTanks.get(i);
			//画出敌人子弹
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
				allTank.remove(enemyTank);

			}
		}
		


	}
	
	public void showPlayerData(Graphics g){
		
		//正下方 显示敌我实力
		drawTank(125, 450, g, 0, 1);
		drawTank(325, 450, g, 0, 0);
		Font font = new Font("宋体", Font.BOLD, 30);
		g.setColor(Color.BLACK);
	    g.setFont(font);
	    g.drawString(Config.enemyTankNums+"", 175, 475);
	    g.drawString(Config.myTankNums+"", 375, 475);
	    
	    //显示右方玩家击杀数据
	    
	    font = new Font("宋体",Font.BOLD,50);
	    g.drawString("玩家1战绩", 500, 50);
	    g.drawString("击杀:"+Config.deadEnemyTankNums, 500, 100);
	    g.drawString("死亡："+Config.deadmyTankNums, 500, 150);
	    
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
		switch (e.getKeyChar()) {

		case 'w':
            if(myTank.y>0 && !myTank.lockAction){
            	
            	myTank.goUp();
            }
			break;
		case 's':
            if(myTank.y<370 && !myTank.lockAction){
            	
            	myTank.goDown();
            }
			break;
		case 'a':
            if(myTank.x>0 && !myTank.lockAction){
            	
            	myTank.goLeft();
            }
			break;
		case 'd':
            if(myTank.x<470 && !myTank.lockAction){
            	
            	myTank.goRight();
            }
			break;
		}
		
		if(e.getKeyChar() == 'j'){
			
			if(myTank.bullets.size() < 5 && !myTank.lockAction){
				
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
	
	/**
	 * 让所有坦克子弹停止所有活动
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		

		if(e.getActionCommand().equals("pause")){
			
			System.out.println("come in");
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
			
			//调用crashBullet()函数检测我方坦克是否碰到子弹
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
			//调用crashBullet()函数检测敌方坦克是否碰到子弹
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


