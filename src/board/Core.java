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
    List<Integer> fall_list;//可落点集合
    private int[][] board_move;//移动棋盘
    private int[][] board_eat;//吃子棋盘
    Stack<Chess> stack;
    List<String> information;
    List<Chess_Manual> chess_Manual;
    int step_num;
    int stage;//1 下棋，2 行棋
    int two_state;//二阶段操作，0 无操作   1 移动   2 提子      
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
    	int stage;//阶段
    	int step;//步骤
        int x;//横
        int y;//纵
        int x_move;//横移动后（仅2阶段）
        int y_move;//纵移动后（仅2阶段）
        List<Chess> eat_chess;//吃子集合       
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
    	stage = 1; //第一阶段
    	step_num = 0;	//第一步，0
    	information = new ArrayList<String>();
    	chess_Manual = new ArrayList<Chess_Manual>();//棋谱初始化
    	fall_list = new ArrayList<Integer>();//可落点集合
    	init_fallList();//可落点棋盘初始化
    	information.add("开始第一阶段：下棋");
    	chess_Manual.add(new Chess_Manual("开始第一阶段：下棋", 1, 0, -1, -1, -1, -1, null));//存入棋谱
        stack = new Stack<>();
        core = new int[x][y];//棋盘
        board_move = new int[x][y];//移动棋盘
        board_eat = new int[x][y];//吃子棋盘
        this.x=x;
        this.y=y;
    }
    private boolean __CanInput(int x,int y) {
        if(core[x][y]==0) return true;
        else return false;
    }
    private void init_fallList() {
    	fall_list = new ArrayList<Integer>();//可落点集合
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
    		information.add("第 "+step_num+" 手"+"白方落子："+(x+1)+","+(char)(65+y));
        	chess_Manual.add(new Chess_Manual(null, 1, step_num, x, y, -1, -1, null));//存入棋谱
    	}else{
    		information.add("第 "+step_num+" 手"+"黑方落子："+(x+1)+","+(char)(65+y));
    		chess_Manual.add(new Chess_Manual(null, 1, step_num, x, y, -1, -1, null));//存入棋谱
    	}
    	if(step_num==196){
            step_num = 0;
            information.clear();
    		stage=2;
    		Chess_Delete(chess_Manual.get(1).x,chess_Manual.get(1).y);
    		Chess_Delete(chess_Manual.get(2).x,chess_Manual.get(2).y);
        	information.add("开始第二阶段：行棋");
        	information.add("提取下棋阶段第1手 白方："+(chess_Manual.get(1).x+1)+","+(char)(65+chess_Manual.get(1).y));
        	information.add("提取下棋阶段第2手 黑方："+(chess_Manual.get(2).x+1)+","+(char)(65+chess_Manual.get(2).y));
        	information.add("等待“黑方”行棋");
        	chess_Manual.add(new Chess_Manual("开始第二阶段：行棋", 2, 0, -1, -1, -1, -1, null));
        	null_BoardMoveAndBoardEat();//初始化移动及吃子棋盘
        	this.two_state=0;//初始化二阶段状态
    	}
    }
    public boolean SetInfomationMove_Two(CanWalkChess canWalkChess,int var) {
    	step_num++;
    	int x1,x2,y1,y2;
    	x1=canWalkChess.getX1();y1=canWalkChess.getY1();
    	x2=canWalkChess.getX2();y2=canWalkChess.getY2();
    	if(var==1){
    		information.add("第 "+step_num+" 手"+"白方移动："+(x1+1)+","+(char)(65+y1)+"-->"+(x2+1)+","+(char)(65+y2));
    	}else{
    		information.add("第 "+step_num+" 手"+"黑方移动："+(x1+1)+","+(char)(65+y1)+"-->"+(x2+1)+","+(char)(65+y2));
    	}
    	
    	if(chess1==0&&GetBoardNum(1)<=14){//判定当前棋子个数
    		chess1=1;
        	information.add("白棋已经小于14子，进入飞子阶段(怎儿)");
    	}
    	if(chess2==0&&GetBoardNum(2)<=14){//判定当前棋子个数
    		chess2=1;
        	information.add("黑棋已经小于14子，进入飞子阶段(怎儿)");
    	}
    	if(GetBoardNum(1)==0){
    		System.out.println("游戏结束，白方剩余零子，黑方胜");
    		information.add("游戏结束，白方剩余零子，黑方胜");
    		game_over = 1;return false;
    	}
    	if(GetBoardNum(2)==0){
    		System.out.println("游戏结束，黑方剩余零子，白方胜");
    		information.add("游戏结束，黑方剩余零子，白方胜");
    		game_over = 1;return false;
    	}
    	return true;
    }
    public boolean SetInfomationEat_Two(CanWalkChess canWalkChess,int var) {
    	int x1,x2,y1,y2;
    	x1=canWalkChess.getX1();y1=canWalkChess.getY1();
    	x2=canWalkChess.getX2();y2=canWalkChess.getY2();
    	if(var==1){
    		information.add("第 "+step_num+" 手"+"白方吃子："+(x1+1)+","+(char)(65+y1));
    	}else{
    		information.add("第 "+step_num+" 手"+"黑方吃子："+(x1+1)+","+(char)(65+y1));
    	}   	
    	if(chess1==0&&GetBoardNum(1)<=14){
    		chess1=1;
        	information.add("白棋已经小于14子，进入飞子阶段(怎儿)");
    	}
    	if(chess2==0&&GetBoardNum(2)<=14){//判定当前棋子个数
    		chess2=1;
        	information.add("黑棋已经小于14子，进入飞子阶段(怎儿)");
    	}    	
    	if(GetBoardNum(1)==0){
    		System.out.println("游戏结束，白方剩余零子，黑方胜");
    		information.add("游戏结束，白方剩余零子，黑方胜");
    		game_over = 1;return false;
    	}
    	if(GetBoardNum(2)==0){
    		System.out.println("游戏结束，黑方剩余零子，白方胜");
    		information.add("游戏结束，黑方剩余零子，白方胜");
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
         board_move[x][y] = 1;//1 代表选中
    }
    public void Chess_Eat(int x,int y) {
    	board_eat[x][y] = 1;//1 代表选中
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
                this.board_move[i][j]=0;//移动棋盘
                this.board_eat[i][j]=0;//吃子棋盘
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
    	int n = 0;//子数检测
    	int m = 0;//空点检测
    	int x_sta = -1,y_sta = -1,x_end = -1,y_end = -1;
        for(int i=0;i<this.x;i++) {
            for(int j=0;j<this.y;j++) {
                if(this.board_move[i][j]==1){//移动棋盘
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
        	information.add("移动无效，已经重置！");
        	return false;
        }else{
            this.core[x_end][y_end] = this.core[x_sta][y_sta];
            this.core[x_sta][y_sta] = 0;
        	null_BoardMoveAndBoardEat();
        	int var = this.core[x_end][y_end];
        	if(var==1){
        		step_num++;
        		information.add("第 "+step_num+" 手"+"白方移动："+(x_sta+1)+","+(char)(65+y_sta)+"-->"+(x_end+1)+","+(char)(65+y_end));
        	}else{
        		step_num++;
        		information.add("第 "+step_num+" 手"+"黑方移动："+(x_sta+1)+","+(char)(65+y_sta)+"-->"+(x_end+1)+","+(char)(65+y_end));
        	}
            return true;
        }
    }
    public boolean Board_MakeEat(){
    	int n = 0 ;
    	int var = 0;
        for(int i=0;i<this.x;i++) {
            for(int j=0;j<this.y;j++) {
                if(this.board_eat[i][j]==1){//吃子棋盘
                	if(n==0)var=this.core[i][j];//第一次记录颜色
                		else if(var!=this.core[i][j]){
                        	null_BoardMoveAndBoardEat();
                        	information.add("吃子无效（颜色不匹配），已经重置！");
                			return false;//颜色不匹配
                		}
                	if(this.core[i][j]==0){
                    	null_BoardMoveAndBoardEat();
                    	information.add("吃子无效（空点不可去子），已经重置！");
            			return false;//颜色不匹配
                	}
                	n++;
                }
            }
        }
        if(n==0){
        	null_BoardMoveAndBoardEat();
        	information.add("吃子无效（未选择吃子点），已经重置！");
			return false;//颜色不匹配
    	}
        int tizi_n=0;
    	for(int i=0;i<this.x;i++) {
            for(int j=0;j<this.y;j++) {
            	if(this.board_eat[i][j]==1){
                	if(var==1){
                		tizi_n++;
                		information.add("第 "+step_num+" 手"+"黑方提白方第"+tizi_n+"子："+(i+1)+","+(char)(65+j));
                	}else{
                		tizi_n++;
                		information.add("第 "+step_num+" 手"+"白方提黑方第"+tizi_n+"子："+(i+1)+","+(char)(65+j));
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
                this.board_move[i][j]=0;//移动棋盘
                this.board_eat[i][j]=0;//吃子棋盘
            }
        }
        stage=1;//1阶段
        this.stack.clear();
    	init_fallList();//可落点棋盘初始化
        step_num = 0;        
        information.clear();
    	information.add("开始第一阶段：下棋");
    	chess_Manual.clear();
    	chess_Manual.add(new Chess_Manual("开始第一阶段：下棋", 1, 0, -1, -1, -1, -1, null));//存入棋谱   	
    }
}