package �Թ�Ѱ·;

import java.util.ArrayList;

public class Node {
	int x, y;		// ���ڼ�¼����
	ArrayList<Integer> way = new ArrayList<Integer>();		// ��������ʱ��¼·��
	int f;			// ����A*����ʱ��¼Ȩֵ
	Node(){
		x = 0;
		y = 0;
	}
	Node(int x, int y){
		this.x = x;
		this.y = y;
	}
}
