package 迷宫寻路;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Game {
	
	public static boolean isSelf = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int Size = 50, width = 1000;
		if(Size % 2 == 0)
			Size++;
		GenerateMaze a = new GenerateMaze(Size);
		PaintMaze p = new PaintMaze(a.Maze, Size, width / Size);
		JFrame frame = new JFrame("迷宫寻路->made by lihao");
		frame.setLayout(new BorderLayout(10, 10));
		
		JButton playeryouself = new JButton("自己玩");
		JButton DFSFind = new JButton("深搜寻路");
		JButton BFSFind = new JButton("广搜寻路");
		JButton AStartFind = new JButton("A*寻路");
		JTextField txt_Size = new JTextField(5);
		JTextField txt_Start_X = new JTextField(5);
		JTextField txt_Start_Y = new JTextField(5);
		JTextField txt_End_X = new JTextField(5);
		JTextField txt_End_Y = new JTextField(5);
		JLabel Lab_Size = new JLabel("设置迷宫大小");
		JLabel Lab_Start = new JLabel("设置起点坐标");
		JLabel Lab_End = new JLabel("设置终点坐标");
		JButton CreatMaze = new JButton("生成地图");
		JLabel Lab_Game = new JLabel("迷宫寻路"); 
		JButton deter = new JButton("确定");
		
		
		//把按钮组件加入JPanel
		p.setLayout(null);
		
		Lab_Game.setFont(new  Font("宋体", Font.BOLD, 50));
		Lab_Game.setForeground(Color.red);
		Lab_Game.setBackground(Color.green);
		Lab_Game.setBounds(400, 20, 400, 50);
		p.add(Lab_Game);
		
		
		Lab_Size.setBounds(1050, 300, 100, 50);
		p.add(Lab_Size);
		
		txt_Size.setBounds(1130, 315, 50, 20);
		p.add(txt_Size);	
		
		CreatMaze.setBounds(1050, 350, 100, 30);
		p.add(CreatMaze);
		
		Lab_Start.setBounds(1050, 400, 100, 50);
		p.add(Lab_Start);
		
		txt_Start_X.setBounds(1130, 415, 30, 20);
		p.add(txt_Start_X);
		
		txt_Start_Y.setBounds(1170, 415, 30, 20);
		p.add(txt_Start_Y);
		
		Lab_End.setBounds(1050, 430, 100, 50);
		p.add(Lab_End);
		
		txt_End_X.setBounds(1130, 445, 30, 20);
		p.add(txt_End_X);
		
		txt_End_Y.setBounds(1170, 445, 30, 20);
		p.add(txt_End_Y);
		
		deter.setBounds(1050, 475, 100, 30);
		p.add(deter);
		
		playeryouself.setBounds(1050, 600, 200, 50);
		p.add(playeryouself);
		DFSFind.setBounds(1050, 700, 200, 50);
		p.add(DFSFind);
		BFSFind.setBounds(1050, 800, 200, 50);
		p.add(BFSFind);
		AStartFind.setBounds(1050, 900, 200, 50);
		p.add(AStartFind);
		
		frame.add(p);

		//自己玩点击事件
		playeryouself.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
					p.path.clear();
					p.end.way.clear();
					p.requestFocus(true);
					p.setKeyListener();
			}
			
		});
		
		
		//深搜点击事件
		DFSFind.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				p.requestFocus(true);
				p.DFS();
				p.FindPath =  !p.FindPath;
				p.repaint();
			}
			
		});
		
		//广搜点击事件
		BFSFind.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				p.requestFocus(true);
				p.BFS();
				p.FindPath =  !p.FindPath;
				p.repaint();
			}
		});
		
		
		//A*算法点击事件
		AStartFind.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				p.requestFocus(true);
				p.AStart();
				p.FindPath =  !p.FindPath;
				p.repaint();
			}
			
		});
		
		
		//生成迷宫点击事件
		CreatMaze.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				int size = Integer.parseInt(txt_Size.getText().trim());
				if(size > 10) {
					if(size % 2 == 0)
						size++;
					p.init(size);
				}
				else {
					JOptionPane.showMessageDialog(null, "请输入长度大于10的迷宫", "输入错误", JOptionPane.INFORMATION_MESSAGE);
				}
					
			}
		});
		
		
		//确定点击事件
		deter.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				int sX = Integer.parseInt(txt_Start_X.getText().trim());
				int sY = Integer.parseInt(txt_Start_Y.getText().trim());
				int eX = Integer.parseInt(txt_End_X.getText().trim());
				int eY = Integer.parseInt(txt_End_Y.getText().trim());
				if(sX > 0 && sX < p.SIZE - 1 && sY > 0 && sY < p.SIZE - 1 
						&& eX > 0 && eX < p.SIZE - 1 && eY > 0 && eY < p.SIZE - 1) {
						p.SetStartEnd(sX, sY, eX, eY);
				}
				else {
					JOptionPane.showMessageDialog(null, "输入错误，请重新输入", "输入错误", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width + 300, width + 200);
		frame.setVisible(true);
	}

}
