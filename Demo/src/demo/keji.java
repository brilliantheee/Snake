package demo;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Timer;

import javax.sound.sampled.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class keji extends JPanel  implements KeyListener, ActionListener
{
	// 蛇的初始化
	private int len;
	private int score; // 记入分数
	private int[] snake_x = new int[750];
	private int[] snake_y = new int[750];
	private String op = "R";   //蛇头的方向
	
	// 随机生成食物
	Random ra = new Random();
	//食物的坐标
	private int food_x;
	private int food_y;
	
	Clip paly;
	
	//开始按钮
	private boolean buttom = false;
	//结束按钮
	private boolean over  = false;
	//时钟触发蛇动起来
	javax.swing.Timer time = new javax.swing.Timer(100, this);
	
	ImageIcon title ;
	ImageIcon up ;
	ImageIcon down ;
	ImageIcon left ;
	ImageIcon right ;
	ImageIcon body ;
	ImageIcon food ;
	
	public keji()
	{
		init();
		//开始聚焦，添加键盘监听
		this.setFocusable(true);
		this.addKeyListener(this);
		//开启时钟
		time.start();
       // 加载图片 
		loadImgs();
		//加载音乐
		LoadSong();

		
	}
	
	public void init()
	{
		len = 3;
		snake_x[0] = 100;
		snake_y[0] = 100;
		snake_x[1] = 75;
		snake_y[1] = 100;
		snake_x[2] = 50;
		snake_y[2] = 100;	
		//初始化 food 坐标
		food_x = 25 + 25*ra.nextInt(34);
		food_y = 75+25*ra.nextInt(24);
		//初始化蛇头方向
		op = "R";
		score = 0;
	}
	
	public void paintComponent(Graphics g)
	{
    	super.paintComponent(g);
	   this.setBackground(Color.white);
	   title.paintIcon(this, g, 25, 11); // 标题区
	   	   
	   g.fillRect(25,75, 850, 600);  //游戏区
	   
	   //分数、长度
	   g.setColor(Color.white);
	   g.drawString("len : "+len, 750, 35);
	   g.drawString("score : "+score, 750, 50);
	   
	  // 控制蛇头方向
	    switch(op) 
	    {
	    case "R" :  right.paintIcon(this, g, snake_x[0], snake_y[0]);break;
	    case "L" :  left.paintIcon(this, g, snake_x[0], snake_y[0]);break;
	    case "U" :  up.paintIcon(this, g, snake_x[0], snake_y[0]);break;
	    case "D" :  down.paintIcon(this, g, snake_x[0], snake_y[0]);break;
	    }

	    
	    for(int i =1 ;i<len ; i++)
	    {
	    	body.paintIcon(this, g, snake_x[i],  snake_y[i]);
	    }
	    
	    
	    //按下键开始动
	  if(buttom == false ) {
		  
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("arial",Font.BOLD,45));
	    g.drawString("I am Snake...  ", 150,350);
	  } 
	  
	    //按下键出现游戏结束
		  if(over) {
			  
		    g.setColor(Color.RED);
		    g.setFont(new Font("arial",Font.BOLD,45));
		    g.drawString("  Game Over !!!!", 250,350);
		  } 
	  
	  //随机生成食物
	  food.paintIcon(this, g, food_x, food_y);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			if(over) {
				
				over = false;
				init(); //重新开始			
			}else {
			     buttom = !buttom;  //循环按才会有反应
			}
			repaint();   //重画			
			
			if(buttom)
			{
				playSong();
			}else
			{
				stopSong();
			}
		
			//控制方向	
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			op = "R";
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			op = "L";
		}else if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			op = "U";
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			op = "D";
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	//时钟
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		// 没有失败才刷新数据
		if(buttom && !over) {
		for(int i = len-1 ; i>0;i--)
		{
			snake_x[i] = snake_x[i-1];
			snake_y[i] = snake_y[i-1];			
		}
		

		//获取方向
		if(op == "R") {
		
			snake_x[0] = snake_x[0]+25;
		if(snake_x[0] > 850) snake_x[0]=25;
		
		}else if (op == "L")
		{
			snake_x[0] = snake_x[0]-25;
		  if(snake_x[0] <25) snake_x[0]=850;
			
		}else if (op == "U")
		{
			snake_y[0] = snake_y[0]-25;
		  if(snake_y[0] <75) snake_y[0]=650;
			
		}else if (op == "D")
		{
			snake_y[0] = snake_y[0]+25;
			  if(snake_y[0] >670) snake_y[0]=75;
			
		}
		
		//吃食物
		if(snake_x[0] == food_x && snake_y[0] == food_y)
		{
			len++;
			//分数也加
			score =score+ 5;
			// 重新生成 food
			food_x = 25 + 25*ra.nextInt(34);
			food_y = 75+25*ra.nextInt(24);
						
		}
		// 判断是否吃到自己的身体
		for(int i =1 ;i<len ; i++)
	    {
	        if(snake_x[0] == snake_x[i] && snake_y[0] == snake_y[i] )
	        	 over = true;
	    }
				
		repaint();	
	}
		time.start();		
	}
		
	private void  loadImgs()
	{
		InputStream in;
		try
		{
			in = getClass().getClassLoader().getResourceAsStream("imags/title.jpg");
			title = new ImageIcon(ImageIO.read(in));
			in = getClass().getClassLoader().getResourceAsStream("imags/body.png");
			body = new ImageIcon(ImageIO.read(in));
			in = getClass().getClassLoader().getResourceAsStream("imags/up.png");
			up = new ImageIcon(ImageIO.read(in));
			in = getClass().getClassLoader().getResourceAsStream("imags/down.png");
			down = new ImageIcon(ImageIO.read(in));
			in = getClass().getClassLoader().getResourceAsStream("imags/right.png");
			right = new ImageIcon(ImageIO.read(in));
			in = getClass().getClassLoader().getResourceAsStream("imags/left.png");
			left = new ImageIcon(ImageIO.read(in));
			in = getClass().getClassLoader().getResourceAsStream("imags/food.png");
			food = new ImageIcon(ImageIO.read(in));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
			
	}
	
	public void LoadSong()
	{
		try
		{
			//获取播放对象
		   paly = AudioSystem.getClip();
			//获取音频流
			InputStream in =this. getClass().getClassLoader().getResourceAsStream("song/bgm.wav");
			AudioInputStream au = AudioSystem.getAudioInputStream(in);
			//打开
			paly.open(au);

			
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void playSong()
	{
		//循环播放
		paly.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stopSong()
	{
		//循环播放
		paly.stop();
	}
	
}
