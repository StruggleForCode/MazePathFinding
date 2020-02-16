package 迷宫寻路;

import java.util.ArrayList;

public class Node {
	int x, y;		// 用于记录坐标
	ArrayList<Integer> way = new ArrayList<Integer>();		// 用于搜索时记录路径
	int f;			// 用于A*搜索时记录权值
	Node(){
		x = 0;
		y = 0;
	}
	Node(int x, int y){
		this.x = x;
		this.y = y;
	}
}
