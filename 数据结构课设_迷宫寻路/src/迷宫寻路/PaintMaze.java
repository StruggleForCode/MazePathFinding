package �Թ�Ѱ·;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * һЩ����Ļ�ͼ���������а������ѣ����ѣ�A*��һЩ�㷨
 * @author 27289
 *
 */

public class PaintMaze extends JPanel {
	private int Maze[][];		// ���ڼ�¼�Թ���
	public int SIZE;			// ���ڼ�¼�Թ��Ĵ�С
	private int width;			// ���ڶ����Թ���ÿһ��ǽ�Ŀ��
	public boolean FindPath = false;		// �����ж��Ƿ����drawPath����
	
	//���õ�ͼ�ര�ڵľ���
	private final static int padX = 50;
	private final static int padY = 70;
	
	//��̬�����¼�����߹���·��
	public ArrayList<Node> path = new ArrayList<Node>();
	
	//���ڼ�¼Tank������
	private int TankX = 1, TankY = 1;
	
	//���ڼ�¼Tank��ת��
	private int dir = 1;
	
	//Tank���ĸ���������  ��������
	private int[][] direct = { {-1, 0}, {0, 1}, {1, 0}, {0, -1} };
	
	//���ڼ�¼̹���Ƿ��߹�
	private boolean[][] flag;
	
	//�յ������
	public Node end = new Node();

	PaintMaze(int[][] maze, int size, int wid) { 
		Maze = maze;
		SIZE = size;
		width = wid;
		flag = new boolean[SIZE][SIZE];
		end.x = size - 2;
		end.y = size - 2;
		for (int i = 0; i < SIZE - 1; i++) {
			for (int j = 0; j < SIZE - 1; j++)
				flag[i][j] = false;
		}
	}

	//�ƶ�����
	synchronized void move(int c) {
		int tx = TankX, ty = TankY;
		switch (c) {
		case KeyEvent.VK_LEFT:
			tx--;
			dir = 0;
			break;
		case KeyEvent.VK_RIGHT:
			tx++;
			dir = 1;
			break;
		case KeyEvent.VK_UP:
			ty--;
			dir = 2;
			break;
		case KeyEvent.VK_DOWN:
			ty++;
			dir = 3;
			break;
		default:
		}
		if (tx > 0 && tx < SIZE - 1 && ty > 0 && ty < SIZE - 1 && Maze[tx][ty] == 1) {
			TankX = tx;
			TankY = ty;
		}
	}

	// �����ı���������ɵ�ͼ
	public void init(int size) {
		GenerateMaze now = new GenerateMaze(size);
		Maze = now.Maze;
		SIZE = size;
		width = 1000 / size;
		flag = new boolean[SIZE][SIZE];
		TankX = 1; TankY = 1;
		end.x = size - 2;
		end.y = size - 2;
		for (int i = 0; i < SIZE - 1; i++) {
			for (int j = 0; j < SIZE - 1; j++)
				flag[i][j] = false;
		}
		end.way.clear();
		path.clear();
		repaint();
	}

	// ���Ƶ�ͼ
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawWall(g);
		drawStratEnd(g);
		if(FindPath) {
			drawPath(g);
		}
	}
	
	//���水����Ӧ�¼�
	public void setKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int c = e.getKeyCode();
                move(c);
                repaint();
                checkIsWin();
            }
        });
    }

	//�ж��Ƿ��ʤ
	private void checkIsWin() {
        if (TankX == end.x &&  TankY == end.y) {
            JOptionPane.showMessageDialog(null, "YOU WIN !", "���߳����Թ���",
                    JOptionPane.PLAIN_MESSAGE);
            init(SIZE);
        }
    }
	
	// ����ǽ
	private void drawWall(Graphics g) {
		Image image = Toolkit.getDefaultToolkit().getImage("Map.jpg");

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (Maze[i][j] == 0) {
					g.drawImage(image, i * width + padX, j * width + padY, width, width, this);
				}
			}
		}

	}

	// ����̹�˵�λ�ú��յ�
	private void drawStratEnd(Graphics g) {
		Image image_player_up = Toolkit.getDefaultToolkit().getImage("Tank_up.png");
		Image image_player_down = Toolkit.getDefaultToolkit().getImage("Tank_down.png");
		Image image_player_right = Toolkit.getDefaultToolkit().getImage("Tank_right.png");
		Image image_player_left = Toolkit.getDefaultToolkit().getImage("Tank_left.png");
		Image image_player_end = Toolkit.getDefaultToolkit().getImage("end.png");
		switch(dir) {
		case 0:		// ��
			g.drawImage(image_player_left, TankX * width + padX, TankY * width + padY, width, width, this);
			break;
		case 1:
			g.drawImage(image_player_right, TankX * width + padX, TankY * width + padY, width, width, this);
			break;
		case 2:
			g.drawImage(image_player_up, TankX * width + padX, TankY * width + padY, width, width, this);
			break;
		case 3:
			g.drawImage(image_player_down, TankX * width + padX, TankY * width + padY, width, width, this);
			break;
		}
		
		g.drawImage(image_player_end, end.x * width + padX, end.y * width + padY, width, width, this);

	}
	
	public void SetStartEnd(int sX, int sY, int eX, int eY) {
		TankX = sX;
		TankY = sY;
		end.x = eX;
		end.y = eY;
		end.way.clear();
		path.clear();
		for (int i = 0; i < SIZE - 1; i++) {
			for (int j = 0; j < SIZE - 1; j++)
				flag[i][j] = false;
		}
		repaint();
	}
	
	//�����������
	public void DFS() {
		path.clear();
		for (int i = 0; i < SIZE - 1; i++) {
			for (int j = 0; j < SIZE - 1; j++)
				flag[i][j] = false;
		}
		end.way.clear();
		this.requestFocus(true);
		Stack<Node> stack = new Stack<>();
		Node n = new Node();
		n.x = TankX; n.y = TankY;
		stack.push(n);
		while(!stack.isEmpty()) {
			Node now = stack.peek();
			stack.pop();
			flag[now.x][now.y] = true;
			System.out.println(now.x + "..........." + now.y);
			path.add(now);
			if(now.x == end.x && now.y == end.y) {
				for (int i = 0; i < now.way.size(); i++) {
					end.way.add(now.way.get(i));
				}
				System.out.println("�ҵ���");
				break;
			}
			for (int i = 0; i < 4; i++) {   // ��������
				int dx = now.x + direct[i][0];
				int dy = now.y + direct[i][1];
				if(dx > 0 && dx < SIZE - 1 && dy > 0 && dy < SIZE - 1 && Maze[dx][dy] == 1 && !flag[dx][dy] ) {
					Node t = new Node();
					t.x = dx;
					t.y = dy;
					for (int j = 0; j < now.way.size(); j++) {
						t.way.add(now.way.get(j));
					}
					t.way.add(i);
					stack.push(t);
				}
			}
		}
	}
	
	//��·��
	private void drawPath(Graphics g) {
		Image image_player_foot = Toolkit.getDefaultToolkit().getImage("foot.png");
		for (int i  = 1; i < path.size() - 1; i++) {
			Node now = path.get(i);
			//g.drawImage(image_player_foot, now.x * width, now.y * width, width, width, this);
			g.setColor(Color.green);
			g.fillRect(now.x * width + padX, now.y * width + padY, width, width);
			repaint();
			g.setColor(this.getBackground());
			g.fillRect(now.x * width + padX, now.y * width + padY, width / 2, width / 2);
			
//			try {
//				Thread.currentThread();
//				Thread.sleep(5);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//			}
			repaint();
		}
		
		g.setColor(Color.blue);
		int tankx = TankX; int tanky = TankY;
		for (int i = 0; i < end.way.size() - 1; i++) {
			int dir = end.way.get(i);
			switch(dir) {		// ��������
			case 0:
				tankx--;
				g.drawImage(image_player_foot, tankx * width + padX, tanky * width + padY, width, width, this);
				//g.fillRect(tankx * width + padX , tanky * width + padY, width, width);
				break;
			case 1:
				tanky++;
				g.drawImage(image_player_foot, tankx * width + padX, tanky * width + padY, width, width, this);
				//g.fillRect(tankx * width + padX , tanky * width + padY, width, width);
				break;
			case 2:
				tankx++;
				g.drawImage(image_player_foot, tankx * width + padX, tanky * width + padY, width, width, this);
				//g.fillRect(tankx * width + padX, tanky * width + padY, width, width);
				break;
			case 3:
				tanky--;
				g.drawImage(image_player_foot, tankx * width + padX, tanky * width+ padY, width, width, this);
			//	g.fillRect(tankx * width + padX, tanky * width + padY, width, width);
				break;
			}
		}
	}
	
	//����
	public void BFS() {
		path.clear();
		for (int i = 0; i < SIZE - 1; i++) {
			for (int j = 0; j < SIZE - 1; j++)
				flag[i][j] = false;
		}
		end.way.clear();
		Queue<Node> que = new LinkedList<Node>();
		Node n = new Node();
		n.x = TankX; n.y = TankY;
		que.add(n);
		flag[TankX][TankY] = true;
		while(!que.isEmpty()) {
			Node now = que.poll();
			System.out.println(now.x + "........" + now.y);
			if(now.x == end.x && now.y == end.y) {
				for (int i = 0; i < now.way.size(); i++) {
					end.way.add(now.way.get(i));
				}
				System.out.println("�ҵ���");
				break;
			}
			path.add(now);
			for (int i = 0; i < 4; i++) {
				int dx = now.x + direct[i][0];
				int dy = now.y + direct[i][1];
				if(dx > 0 && dx < SIZE - 1 && dy > 0 && dy < SIZE - 1 && Maze[dx][dy] == 1 && !flag[dx][dy]) {
					Node t = new Node();
					t.x = dx;
					t.y = dy;
					for (int j = 0; j < now.way.size(); j++) {
						t.way.add(now.way.get(j));
					}
					t.way.add(i);
					flag[t.x][t.y]= true; 
					que.add(t);
				}
			}
		}
		
	}
	
	
	// A* ���������� �����پ���
	private int weight(int x, int y) {
		// �����پ���
		return Math.abs(x - TankX) + Math.abs(x - TankY) + Math.abs(x - end.x) + Math.abs(y - end.y);
		
	}
	
	//A* Ѱ·
	void AStart() {
		path.clear();
		for (int i = 0; i < SIZE - 1; i++) {
			for (int j = 0; j < SIZE - 1; j++)
				flag[i][j] = false;
		}
		end.way.clear();
		Node n = new Node();
		n.x = TankX; n.y = TankY;
		n.f = weight(n.x, n.y);
		
		
		
		Queue<Node> que = new PriorityQueue<Node>(SIZE, new Comparator<Node>() {
			@Override
			public int compare(Node a, Node b) {
				// TODO Auto-generated method stub
				return a.f - b.f;
			}
		});
		
		que.add(n);
		flag[TankX][TankY] = true;
		while(!que.isEmpty()) {
			Node now = que.poll();
			//System.out.println(now.x + "........" + now.y);
			if(now.x == end.x && now.y == end.y) {
				for (int i = 0; i < now.way.size(); i++) {
					end.way.add(now.way.get(i));
				}
				System.out.println("�ҵ���");
				break;
			}
			path.add(now);
			for (int i = 0; i < 4; i++) {
				int dx = now.x + direct[i][0];
				int dy = now.y + direct[i][1];
				if(dx > 0 && dx < SIZE - 1 && dy > 0 && dy < SIZE - 1 && Maze[dx][dy] == 1 && !flag[dx][dy]) {
					Node t = new Node();
					t.x = dx;
					t.y = dy;
					for (int j = 0; j < now.way.size(); j++) {
						t.way.add(now.way.get(j));
					}
					t.way.add(i);
					t.f = weight(t.x, t.y);
					flag[t.x][t.y]= true; 
					que.add(t);
				}
			}
		}
	}
	
}
