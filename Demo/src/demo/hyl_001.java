package demo;

import javax.swing.JFrame;

public class hyl_001
{

	public static void main(String[] args)
	{
			JFrame jf = new JFrame();                 //图形化工具
			jf.setBounds(10, 10, 900, 720);     // 游戏界面大小
	     	jf.setResizable(false);                         // 是否可拖动更改方框大小
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //点击关闭
			
			jf.add(new keji());   //添加画布
			
			jf.setVisible(true);  // 是否可视化
			
		
		
		
	}

}
