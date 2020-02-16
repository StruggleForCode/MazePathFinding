package 迷宫寻路;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * prime随机生成迷宫， 迷宫相对比较自然，但迷宫的分岔路会比较多
 * 所以迷宫可能会更复杂，即玩家需要做选择的次数可能比较多。
 * @author 27289
 *
 */

public class GenerateMaze {
	private int SIZE;
	public int[][] Maze;
	ArrayList<Node> maze = new ArrayList<>();  	// 用于随机取点
	int[][] Move = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; 	 //上右下左
	
	GenerateMaze(int size){
		SIZE = size;
		Maze = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE - 1; i++) {
			for (int j = 0; j < SIZE - 1; j++) {
				Maze[i][j] = 0;
			}
		}
		
		for (int i = 0; i < SIZE / 2; i++) {
			for (int j = 0; j < SIZE /2; j++) {
				Node n = new Node();
				n.x = i; n.y = j;
				maze.add(n);
			}
		}
		createMaze();
	}
	
	// 判断那个方向以走
	private int findWay(Node a) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		
		for (int i  = 0; i < 4; i++) {
			int tx = a.x + Move[i][0];
			int ty = a.y + Move[i][1];
			if(tx < 0 || tx >= SIZE / 2 || ty < 0 || ty >= SIZE / 2 || Maze[tx * 2 + 1][ty * 2 + 1] == 1)
				continue;
			temp.add(i);
		}
		
		if(temp.size() == 0) {
			// 如果没有方向可以移动， 则可能为死点， 将其四周可连通的方向打通
			for (int i = 0; i < 4; i++) {
				int tx = a.x * 2 + 1 + Move[i][0];
				int ty = a.y * 2 + 1 + Move[i][1];
				if(tx > 0 && tx < SIZE - 1 && ty > 0 && ty < SIZE - 1) {
					Maze[tx][ty] = 1;
				}
			}
			
			//如果是死点， 则生成一个二乘二的房间与周围相连
			for (int i = 0; i <= 1; i++) {
				for (int j = 0; j <= 1; j++) {
					int tx = a.x * 2 + 1 + i;
					int ty = a.y * 2 + 1 + j;
					if(tx > 0 && tx < SIZE - 1 && ty > 0 && ty < SIZE - 1)
						Maze[tx][ty] = 1;
				}
			}
			return -1;
		}
		else {
			Random ran = new Random();
			int index = ran.nextInt(temp.size());
			int t =  (int)temp.get(index);
			return t;
			
		}
		
	}
	
	private Node RandNode() {
		Random ran = new Random();
		int temp = ran.nextInt(maze.size());
		Node now = maze.get(temp);
		return now;
	}
	
	private void createMaze() {
		while(maze.size() != 0) {
			Node t = RandNode();
			Queue<Node> que = new LinkedList<Node>();
			que.add(t);
			while(!que.isEmpty()) {
				Node temp = que.poll();
				maze.removeIf((Node e)->{
					if (e.x == temp.x && e.y == temp.y)
						return true;
					return false;
				});
				
				Maze[temp.x * 2 + 1][temp.y * 2 + 1] = 1;
				int to = findWay(temp);
				if(to == -1)
					break;
				int dx = Move[to][0], dy = Move[to][1];
				int nextX = temp.x * 2 + 1 + dx;
				int nextY = temp.y * 2 + 1 + dy;
				Maze[nextX][nextY] = 1;
				Node e = new Node();
				e.x = temp.x + dx; 
				e.y = temp.y + dy;
				que.add(e);
			}
		}
	}
}

