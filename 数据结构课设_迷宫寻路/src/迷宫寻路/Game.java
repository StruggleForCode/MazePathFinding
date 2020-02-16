package �Թ�Ѱ·;

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
		JFrame frame = new JFrame("�Թ�Ѱ·->made by lihao");
		frame.setLayout(new BorderLayout(10, 10));
		
		JButton playeryouself = new JButton("�Լ���");
		JButton DFSFind = new JButton("����Ѱ·");
		JButton BFSFind = new JButton("����Ѱ·");
		JButton AStartFind = new JButton("A*Ѱ·");
		JTextField txt_Size = new JTextField(5);
		JTextField txt_Start_X = new JTextField(5);
		JTextField txt_Start_Y = new JTextField(5);
		JTextField txt_End_X = new JTextField(5);
		JTextField txt_End_Y = new JTextField(5);
		JLabel Lab_Size = new JLabel("�����Թ���С");
		JLabel Lab_Start = new JLabel("�����������");
		JLabel Lab_End = new JLabel("�����յ�����");
		JButton CreatMaze = new JButton("���ɵ�ͼ");
		JLabel Lab_Game = new JLabel("�Թ�Ѱ·"); 
		JButton deter = new JButton("ȷ��");
		
		
		//�Ѱ�ť�������JPanel
		p.setLayout(null);
		
		Lab_Game.setFont(new  Font("����", Font.BOLD, 50));
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

		//�Լ������¼�
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
		
		
		//���ѵ���¼�
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
		
		//���ѵ���¼�
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
		
		
		//A*�㷨����¼�
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
		
		
		//�����Թ�����¼�
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
					JOptionPane.showMessageDialog(null, "�����볤�ȴ���10���Թ�", "�������", JOptionPane.INFORMATION_MESSAGE);
				}
					
			}
		});
		
		
		//ȷ������¼�
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
					JOptionPane.showMessageDialog(null, "�����������������", "�������", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width + 300, width + 200);
		frame.setVisible(true);
	}

}
