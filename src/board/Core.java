package board;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import ai.CanWalkChess;
import ai.fallChessAI;
public class Core {
    private int[][] core;
    private int x;
    private int y;
    List<Integer> fall_list;//����㼯��
    private int[][] board_move;//�ƶ�����
    private int[][] board_eat;//��������
    Stack<Chess> stack;
    List<String> information;
    List<Chess_Manual> chess_Manual;
    int step_num;
    int stage;//1 ���壬2 ����
    int two_state;//���׶β�����0 �޲���   1 �ƶ�   2 ����      
    int chess1 =0;
    int chess2 =0;
    int game_over = 0;
    class Chess{
        int x;
        int y;
        public Chess(int x,int y) {
            this.x=x;
            this.y=y;
        }
    }
    class Chess_Manual{
    	String note;
    	int stage;//�׶�
    	int step;//����
        int x;//��
        int y;//��
        int x_move;//���ƶ��󣨽�2�׶Σ�
        int y_move;//���ƶ��󣨽�2�׶Σ�
        List<Chess> eat_chess;//���Ӽ���       
        public Chess_Manual(String note,int stage,int step,int x,int y,int x_move,int y_move,List<Chess> eat_chess) {
        	this.note=note;
        	this.stage=stage;
        	this.step=step;
            this.x=x;
            this.y=y;
            this.x_move=x_move;
            this.y_move=y_move;
            this.eat_chess=eat_chess;
        }
    }
    public Core(int x,int y) {
    	stage = 1; //��һ�׶�
    	step_num = 0;	//��һ����0
    	information = new ArrayList<String>();
    	chess_Manual = new ArrayList<Chess_Manual>();//���׳�ʼ��
    	fall_list = new ArrayList<Integer>();//����㼯��
    	init_fallList();//��������̳�ʼ��
    	information.add("��ʼ��һ�׶Σ�����");
    	chess_Manual.add(new Chess_Manual("��ʼ��һ�׶Σ�����", 1, 0, -1, -1, -1, -1, null));//��������
        stack = new Stack<>();
        core = new int[x][y];//����
        board_move = new int[x][y];//�ƶ�����
        board_eat = new int[x][y];//��������
        this.x=x;
        this.y=y;
    }
    private boolean __CanInput(int x,int y) {
        if(core[x][y]==0) return true;
        else return false;
    }
    private void init_fallList() {
    	fall_list = new ArrayList<Integer>();//����㼯��
    	for(int i=0;i<196;i++){ //0-195
    		this.fall_list.add(i);
    	}
    }
    private void update_fallList(int x,int y) {
    	int num = 14*(y)+(x);
    	for(int i=0;i<fall_list.size();i++){ //0-size
    		if(fall_list.get(i)==num)this.fall_list.remove(i);
    	}
    }
    private int checkVictory(int x,int y,int var) {
        return 0;
    }
    public void SetInfomation(int x,int y,int var) {
    	step_num++;
    	if(var==1){
    		information.add("�� "+step_num+" ��"+"�׷����ӣ�"+(x+1)+","+(char)(65+y));
        	chess_Manual.add(new Chess_Manual(null, 1, step_num, x, y, -1, -1, null));//��������
    	}else{
    		information.add("�� "+step_num+" ��"+"�ڷ����ӣ�"+(x+1)+","+(char)(65+y));
    		chess_Manual.add(new Chess_Manual(null, 1, step_num, x, y, -1, -1, null));//��������
    	}
    	if(step_num==196){
            step_num = 0;
            information.clear();
    		stage=2;
    		Chess_Delete(chess_Manual.get(1).x,chess_Manual.get(1).y);
    		Chess_Delete(chess_Manual.get(2).x,chess_Manual.get(2).y);
        	information.add("��ʼ�ڶ��׶Σ�����");
        	information.add("��ȡ����׶ε�1�� �׷���"+(chess_Manual.get(1).x+1)+","+(char)(65+chess_Manual.get(1).y));
        	information.add("��ȡ����׶ε�2�� �ڷ���"+(chess_Manual.get(2).x+1)+","+(char)(65+chess_Manual.get(2).y));
        	information.add("�ȴ����ڷ�������");
        	chess_Manual.add(new Chess_Manual("��ʼ�ڶ��׶Σ�����", 2, 0, -1, -1, -1, -1, null));
        	null_BoardMoveAndBoardEat();//��ʼ���ƶ�����������
        	this.two_state=0;//��ʼ�����׶�״̬
    	}
    }
    public boolean SetInfomationMove_Two(CanWalkChess canWalkChess,int var) {
    	step_num++;
    	int x1,x2,y1,y2;
    	x1=canWalkChess.getX1();y1=canWalkChess.getY1();
    	x2=canWalkChess.getX2();y2=canWalkChess.getY2();
    	if(var==1){
    		information.add("�� "+step_num+" ��"+"�׷��ƶ���"+(x1+1)+","+(char)(65+y1)+"-->"+(x2+1)+","+(char)(65+y2));
    	}else{
    		information.add("�� "+step_num+" ��"+"�ڷ��ƶ���"+(x1+1)+","+(char)(65+y1)+"-->"+(x2+1)+","+(char)(65+y2));
    	}
    	
    	if(chess1==0&&GetBoardNum(1)<=14){//�ж���ǰ���Ӹ���
    		chess1=1;
        	information.add("�����Ѿ�С��14�ӣ�������ӽ׶�(����)");
    	}
    	if(chess2==0&&GetBoardNum(2)<=14){//�ж���ǰ���Ӹ���
    		chess2=1;
        	information.add("�����Ѿ�С��14�ӣ�������ӽ׶�(����)");
    	}
    	if(GetBoardNum(1)==0){
    		System.out.println("��Ϸ�������׷�ʣ�����ӣ��ڷ�ʤ");
    		information.add("��Ϸ�������׷�ʣ�����ӣ��ڷ�ʤ");
    		game_over = 1;return false;
    	}
    	if(GetBoardNum(2)==0){
    		System.out.println("��Ϸ�������ڷ�ʣ�����ӣ��׷�ʤ");
    		information.add("��Ϸ�������ڷ�ʣ�����ӣ��׷�ʤ");
    		game_over = 1;return false;
    	}
    	return true;
    }
    public boolean SetInfomationEat_Two(CanWalkChess canWalkChess,int var) {
    	int x1,x2,y1,y2;
    	x1=canWalkChess.getX1();y1=canWalkChess.getY1();
    	x2=canWalkChess.getX2();y2=canWalkChess.getY2();
    	if(var==1){
    		information.add("�� "+step_num+" ��"+"�׷����ӣ�"+(x1+1)+","+(char)(65+y1));
    	}else{
    		information.add("�� "+step_num+" ��"+"�ڷ����ӣ�"+(x1+1)+","+(char)(65+y1));
    	}   	
    	if(chess1==0&&GetBoardNum(1)<=14){
    		chess1=1;
        	information.add("�����Ѿ�С��14�ӣ�������ӽ׶�(����)");
    	}
    	if(chess2==0&&GetBoardNum(2)<=14){//�ж���ǰ���Ӹ���
    		chess2=1;
        	information.add("�����Ѿ�С��14�ӣ�������ӽ׶�(����)");
    	}    	
    	if(GetBoardNum(1)==0){
    		System.out.println("��Ϸ�������׷�ʣ�����ӣ��ڷ�ʤ");
    		information.add("��Ϸ�������׷�ʣ�����ӣ��ڷ�ʤ");
    		game_over = 1;return false;
    	}
    	if(GetBoardNum(2)==0){
    		System.out.println("��Ϸ�������ڷ�ʣ�����ӣ��׷�ʤ");
    		information.add("��Ϸ�������ڷ�ʣ�����ӣ��׷�ʤ");
    		game_over = 1;return false;
    	}
    	return true;
    	
    }
    public int GetBoardNum(int var) {
    	int n=0;
    	for(int i=0;i<14;i++){
    		for(int j=0;j<14;j++){
    			if(core[i][j]==var)n++;
    		}
    	}
    	return n;
    }
    public boolean pub__CanInput(int x,int y) {
        if(core[x][y]==0) return true;
        else return false;
    }
    public boolean Chess_Delete(int x,int y) {
        if(core[x][y] != 0){
        	this.core[x][y] = 0;
        	return true;
        }else{
        	return false;
        }
    }
    public int ChessIt(int x,int y,int var) {
        if(__CanInput(x,y)) {
            core[x][y] =var;
            Chess chess = new Chess(x,y);
            stack.push(chess);
            update_fallList(x,y);
            return checkVictory(x, y, var);
        }
        else return -1;
    }
    public void EatChess(int x,int y) {
    	this.core[x][y]=0;
    }
    public void SetChessAll(int[][] core){
    	this.core = core;
    }
    public void Chess_Move(int x,int y) {
         board_move[x][y] = 1;//1 ����ѡ��
    }
    public void Chess_Eat(int x,int y) {
    	board_eat[x][y] = 1;//1 ����ѡ��
   }
    public boolean RetChess() {
        if(stack.isEmpty()) return false;
        Chess chess = stack.pop();
        core[chess.x][chess.y]= 0;
        information.remove(information.size()-1);
        chess_Manual.remove(chess_Manual.size()-1);
        this.fall_list.add((chess.y)*14 + (chess.x));
        step_num--;
        return true;
    }
    public int[][] getCore(){
        return this.core;
    }
    public int[][] getBoardMove(){
        return this.board_move;
    }
    public int[][] getBoardEat(){
        return this.board_eat;
    }
    public void null_BoardMoveAndBoardEat(){
        for(int i=0;i<this.x;i++) {
            for(int j=0;j<this.y;j++) {
                this.board_move[i][j]=0;//�ƶ�����
                this.board_eat[i][j]=0;//��������
            }
        }
    }
    public void FU_PAN(){
    	for(int i=0;i<14;i++){
    		for(int j=0;j<14;j++){
    			if(board_move[i][j]==1)this.core[i][j]=1;
    			if(board_eat[i][j]==1)this.core[i][j]=2;
    		}
    	}
    }
    public void Board_StartMove(){
    	this.two_state = 1;
    }
    public void Board_EndMove(){
    	this.two_state = 0;
    	null_BoardMoveAndBoardEat();
    }
    public void Board_StartEat(){
    	this.two_state = 2;
    }
    public void Board_EndEat(){
    	this.two_state = 0;
    	null_BoardMoveAndBoardEat();
    }
    public boolean Board_MakeMove(){
    	int n = 0;//�������
    	int m = 0;//�յ���
    	int x_sta = -1,y_sta = -1,x_end = -1,y_end = -1;
        for(int i=0;i<this.x;i++) {
            for(int j=0;j<this.y;j++) {
                if(this.board_move[i][j]==1){//�ƶ�����
                	n++;
                	if(this.core[i][j]==0){
                		m++;
                		x_end = i;
                		y_end = j;
                	}else{
                		x_sta = i;
                		y_sta = j;
                	}
                }
            }
        }
        if(n!=2||m!=1){
        	null_BoardMoveAndBoardEat();
        	information.add("�ƶ���Ч���Ѿ����ã�");
        	return false;
        }else{
            this.core[x_end][y_end] = this.core[x_sta][y_sta];
            this.core[x_sta][y_sta] = 0;
        	null_BoardMoveAndBoardEat();
        	int var = this.core[x_end][y_end];
        	if(var==1){
        		step_num++;
        		information.add("�� "+step_num+" ��"+"�׷��ƶ���"+(x_sta+1)+","+(char)(65+y_sta)+"-->"+(x_end+1)+","+(char)(65+y_end));
        	}else{
        		step_num++;
        		information.add("�� "+step_num+" ��"+"�ڷ��ƶ���"+(x_sta+1)+","+(char)(65+y_sta)+"-->"+(x_end+1)+","+(char)(65+y_end));
        	}
            return true;
        }
    }
    public boolean Board_MakeEat(){
    	int n = 0 ;
    	int var = 0;
        for(int i=0;i<this.x;i++) {
            for(int j=0;j<this.y;j++) {
                if(this.board_eat[i][j]==1){//��������
                	if(n==0)var=this.core[i][j];//��һ�μ�¼��ɫ
                		else if(var!=this.core[i][j]){
                        	null_BoardMoveAndBoardEat();
                        	information.add("������Ч����ɫ��ƥ�䣩���Ѿ����ã�");
                			return false;//��ɫ��ƥ��
                		}
                	if(this.core[i][j]==0){
                    	null_BoardMoveAndBoardEat();
                    	information.add("������Ч���յ㲻��ȥ�ӣ����Ѿ����ã�");
            			return false;//��ɫ��ƥ��
                	}
                	n++;
                }
            }
        }
        if(n==0){
        	null_BoardMoveAndBoardEat();
        	information.add("������Ч��δѡ����ӵ㣩���Ѿ����ã�");
			return false;//��ɫ��ƥ��
    	}
        int tizi_n=0;
    	for(int i=0;i<this.x;i++) {
            for(int j=0;j<this.y;j++) {
            	if(this.board_eat[i][j]==1){
                	if(var==1){
                		tizi_n++;
                		information.add("�� "+step_num+" ��"+"�ڷ���׷���"+tizi_n+"�ӣ�"+(i+1)+","+(char)(65+j));
                	}else{
                		tizi_n++;
                		information.add("�� "+step_num+" ��"+"�׷���ڷ���"+tizi_n+"�ӣ�"+(i+1)+","+(char)(65+j));
                	}
            		this.core[i][j]=0;
            	}
            }
        }
    	null_BoardMoveAndBoardEat();
    	return true;
    }
    public void Restart() {
        for(int i=0;i<this.x;i++) {
            for(int j=0;j<this.y;j++) {
                this.core[i][j]=0;
                this.board_move[i][j]=0;//�ƶ�����
                this.board_eat[i][j]=0;//��������
            }
        }
        stage=1;//1�׶�
        this.stack.clear();
    	init_fallList();//��������̳�ʼ��
        step_num = 0;        
        information.clear();
    	information.add("��ʼ��һ�׶Σ�����");
    	chess_Manual.clear();
    	chess_Manual.add(new Chess_Manual("��ʼ��һ�׶Σ�����", 1, 0, -1, -1, -1, -1, null));//��������   	
    }
}