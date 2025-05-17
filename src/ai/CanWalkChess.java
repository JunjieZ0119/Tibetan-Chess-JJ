package ai;
public class CanWalkChess {
	private int x1;//起点
	private int y1;
	private int x2;//终点
	private int y2;
	private int eat_num;//吃子数
	private int get_score;//得分
	private int get_lian;//成链
	public int getGet_lian() {
		return get_lian;
	}
	public void setGet_lian(int get_lian) {
		this.get_lian = get_lian;
	}
	public int getX1() {
		return x1;
	}
	public int getY1() {
		return y1;
	}
	public int getX2() {
		return x2;
	}
	public int getY2() {
		return y2;
	}
	public int getEat_num() {
		return eat_num;
	}
	public int getGet_score() {
		return get_score;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
	}
	public void setX2(int x2) {
		this.x2 = x2;
	}
	public void setY2(int y2) {
		this.y2 = y2;
	}
	public void setEat_num(int eat_num) {
		this.eat_num = eat_num;
	}
	public void setGet_score(int get_score) {
		this.get_score = get_score;
	}
}