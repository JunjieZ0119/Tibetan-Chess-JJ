package board;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import ai.CanWalkChess;
import ai.fallChessAI;
import ai.flyChessAI;
import ai.walkChessAI;
import board.Core.Chess;

/**
 * @author  Junjie Zhou
 * @date  07/16/2023
 * @version 3.0
 */

public class Windows extends JFrame implements MouseListener,Runnable {
	public fallChessAI fallChessAI;
    public Core core;
    private static final long serialVersionUID = 1L;
    private int var = 1;
    private int AI_COLOR = 0;
    private int game_state =0; //0 准备中 、1 人人 、 2 AI为白 、3 AI为黑 、 4 AIvsAI   
    public Windows(String title) {
        super(title);
        core = new Core(14, 14);	//14x14的数组
        this.setSize(1000, 700);
        this.setLocation(500, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.addMouseListener(this);
    }
    int AI=0;//AI计算状态
    boolean thread_state=true;//AI线程状态
    //整体面板
    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
        for (int i = 0; i < 14; i++){
        	g.drawString(i+1+"",55 + i * 40,70);//横 数字
            g.drawLine(60, 90 + i * 40, 580, 90 + i * 40);//(x1,y1,x2,y2)
            g.drawString(i+1+"",55 + i * 40,640);//横 数字
        }
        for (int i = 0; i < 14; i++){
        	g.drawString((char)(65+i)+"",30,95 + i * 40);//竖 字母
            g.drawLine(60 + i * 40, 90, 60 + i * 40, 610);//(x1,y1,x2,y2)
            g.drawString((char)(65+i)+"",605,95 + i * 40);//竖 字母
        }
        g.fillOval(55 + 3 * 40, 85 + 3 * 40, 10, 10);
        g.fillOval(55 + 3 * 40, 85 + 10 * 40, 10, 10);
        g.fillOval(55 + 10 * 40, 85 + 3 * 40, 10, 10);
        g.fillOval(55 + 10 * 40, 85 + 10 * 40, 10, 10);
        g.fillOval(56 + 6 * 40, 86 + 6 * 40, 8, 8);
        g.fillOval(56 + 6 * 40, 86 + 7 * 40, 8, 8);
        g.fillOval(56 + 7 * 40, 86 + 6 * 40, 8, 8);
        g.fillOval(56 + 7 * 40, 86 + 7 * 40, 8, 8);
        int[][] board = core.getCore();
        int[][] board_move = core.getBoardMove();//移动棋盘
        int[][] board_eat = core.getBoardEat();//吃子棋盘
        int[][] board_move_show = core.getBoardMove();//移动显示
        int[][] board_eat_show = core.getBoardEat();//吃子显示
        for (int i = 0; i < 14; i++) {//画
            for (int j = 0; j < 14; j++) {
                if (board[i][j] == 1)//空心圆，白
                    g.drawOval(48 + i * 40, 78 + j * 40, 25, 25);
                if(board[i][j]==2)//实心圆，黑
                    g.fillOval(48 + i*40, 78 + j * 40, 25, 25);
                if(board_move[i][j]==1){//选子移动
                	g.setColor(Color.green);
                	g.drawRect(48 + i * 40, 78 + j * 40, 25, 25);
                	g.setColor(Color.black);
                }
                if(board_eat[i][j]==1){//选子吃子
                	g.setColor(Color.red);
                	g.drawRect(48 + i * 40, 78 + j * 40, 25, 25);
                	g.setColor(Color.black);
                }
            }
        }
        String infomation = "";
        g.drawString("输出信息:",670,80);
        g.drawRect(670,90, 270, 300);
        List<String> information = core.information;
        for(int i=0;i<information.size();i++){
        	g.drawString(information.get(i), 680, 110+i*15);
        	if(i>16)information.remove(0);
        }
        g.drawRect(670,420,60,30);
        g.drawString("开始",688,440);
        g.drawRect(755,420,100,30);
        g.drawString("棋 盘 复 盘",778,440);
        g.drawRect(880,420,60,30);
        g.drawString("悔棋",898,440);
        g.drawRect(670,460,100,30);
        g.drawString("开 始 移 动",690,480);
        g.drawRect(840,460,100,30);
        g.drawString("结 束 移 动",860,480);
        g.drawRect(670,500,100,30);
        g.drawString("确 定 移 动",690,520);
        g.drawRect(840,500,100,30);
        g.drawString("移 动 重 置",860,520);
        g.drawRect(670,540,100,30);
        g.drawString("开 始 提 子",690,560);  
        g.drawRect(840,540,100,30);
        g.drawString("结 束 提 子",860,560);
        g.drawRect(670,580,100,30);
        g.drawString("确 定 提 子",690,600);
        g.drawRect(840,580,100,30);
        g.drawString("提 子 重 置",860,600);
        g.drawRect(670,620,270,30);
        g.drawString("切 换 至  AI 智 能",760,640);
    }
    @Override
    public void mouseClicked(MouseEvent arg0) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    	out:
        if (e.getX() > 50 && e.getX() < 590 && e.getY() > 80 && e.getY() < 620) {	//此处限制棋盘范围
        	if(game_state==0)break out;//准备阶段
        	
        	if(game_state==1){//人人对战
            	if(core.stage==1){//一阶段 固定
            		if(!core.pub__CanInput(_CgetX(e.getX()), _CgetY(e.getY()))){
            			break out;//有子验证
            		}else{
            			int a = core.ChessIt(_CgetX(e.getX()), (_CgetY(e.getY())), var);//下棋，并获取胜负函数（将来去掉，此处无需判定胜负）
                        core.SetInfomation(_CgetX(e.getX()), (_CgetY(e.getY())), var);//写入输出信息
                        this.repaint();
                        if(a!=-1) {
                            if(var==1) var=2;
                            else if(var==2) var=1;
                        }
            		}
            	}
            	if(core.stage==2){//二阶段 固定
            		if(core.two_state==1){//移动
                		core.Chess_Move( _CgetX(e.getX()), _CgetY(e.getY()) );//选中位置
                		this.repaint();
            		}
            		if(core.two_state==2){//提子
                		core.Chess_Eat( _CgetX(e.getX()), _CgetY(e.getY()) );//选中位置
                		this.repaint();
            		}
            	}
        	}
        	if(game_state==2){ // AI为白
                if(core.stage==1){//一阶段 固定
            		if(var==1)break out;//锁定棋盘，等待AI落子
            		if(AI==1) break out;//AI状态验证
                	if(var==2){ //人落子
                		if(!core.pub__CanInput(_CgetX(e.getX()), _CgetY(e.getY()))){
                			break out;//有子验证
                		}else{
                			int a = core.ChessIt(_CgetX(e.getX()), (_CgetY(e.getY())), var);//下棋，并获取胜负函数（将来去掉，此处无需判定胜负）
                            core.SetInfomation(_CgetX(e.getX()), (_CgetY(e.getY())), var);//写入输出信息
                            this.repaint();
                            if(a!=-1) {
                                if(var==1) var=2;
                                else if(var==2) var=1;
                            }
                		}
                		this.repaint();
                    	System.out.println("当前局面评分"+"白方:"+new fallChessAI().evaluate_fallChess(core.getCore(), 1));
                    	System.out.println("当前局面评分"+"黑方:"+new fallChessAI().evaluate_fallChess(core.getCore(), 2));
                	}
        		}
            	if(core.stage==2){//二阶段 固定
            		if(core.two_state==1){//移动
                		core.Chess_Move( _CgetX(e.getX()), _CgetY(e.getY()) );//选中位置
                		this.repaint();
            		}
            		
            		if(core.two_state==2){//提子
                		core.Chess_Eat( _CgetX(e.getX()), _CgetY(e.getY()) );//选中位置
                		this.repaint();
            		}
            	}
        	}
        	if(game_state==3){ // AI为黑
                if(core.stage==1){//一阶段 固定
                	if(var==2)break out;//锁定棋盘，等待AI落子
                	if(AI==1) break out;//AI状态验证
                	if(var==1){ //人落子
                		if(!core.pub__CanInput(_CgetX(e.getX()), _CgetY(e.getY()))){
                			break out;//有子验证
                		}else{
                			int a = core.ChessIt(_CgetX(e.getX()), (_CgetY(e.getY())), var);//下棋，并获取胜负函数（将来去掉，此处无需判定胜负）
                            core.SetInfomation(_CgetX(e.getX()), (_CgetY(e.getY())), var);//写入输出信息
                            this.repaint();
                            if(a!=-1) {
                                if(var==1) var=2;
                                else if(var==2) var=1;
                            }
                		}
                		this.repaint();
                    	System.out.println("当前局面评分"+"白方:"+new fallChessAI().evaluate_fallChess(core.getCore(), 1));
                    	System.out.println("当前局面评分"+"黑方:"+new fallChessAI().evaluate_fallChess(core.getCore(), 2));
                	}
        		}
            	if(core.stage==2){//二阶段 固定
            		if(core.two_state==1){//移动
                		core.Chess_Move( _CgetX(e.getX()), _CgetY(e.getY()) );//选中位置
                		this.repaint();
            		}
            		
            		if(core.two_state==2){//提子
                		core.Chess_Eat( _CgetX(e.getX()), _CgetY(e.getY()) );//选中位置
                		this.repaint();
            		}
            	}
            	
        	}
        	
        	if(game_state==4){//AI VS AI
            	if(core.stage==1){//一阶段 固定
            	}
            	if(core.stage==2){//二阶段 固定
            	}
        	}
        }
	    if(e.getX()>755&&e.getX()<855&&e.getY()>420&&e.getY()<450) {//棋盘复盘
	    	core.FU_PAN();
	    	this.repaint();
	    }
        if(e.getX()>670&&e.getX()<730&&e.getY()>420&&e.getY()<450) {//按钮，开始
        	if(this.game_state==0){
                Object[] options = {"人VS人","AI为白","AI为黑","AI VS AI"};
                int n = JOptionPane.showOptionDialog(null,"请选择游戏模式？","游戏设置",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,options,options[0]);
                if(n==0) this.game_state=1;
                if(n==1) {this.game_state=2;AI_COLOR=1;}
                if(n==2) {this.game_state=3;AI_COLOR=2;}
                if(n==3) this.game_state=4;
                this.core.Restart();
                this.repaint();
                var=1;//白子为先！
            	if(n==1){//先落一手白棋
            		//白子第一手 G7
            		int a = core.ChessIt(6,6,var);
            		core.SetInfomation(6, 6, var);//写入输出信息
                    this.repaint();
                    if(a!=-1) {
                       if(var==1) var=2;
                       else if(var==2) var=1;
                    }
                    System.out.println(core.fall_list.size());
            	}
            	if(n==3){//AI VS AI
            		//先落 1 2 子
            		core.ChessIt(6, 6, 1);
            		core.SetInfomation(6, 6, 1);//写入输出信息
            		core.ChessIt(7, 7, 2);
            		core.SetInfomation(7, 7, 2);//写入输出信息
            		this.repaint();
            		new Thread(this).start();//使用线程实现,
            	}
        	}
        }
        if(e.getX()>880&&e.getX()<940&&e.getY()>420&&e.getY()<450) {//按钮，悔棋
        	if(core.stage==1){
                core.RetChess();
                if(var==1) var=2;
                else if(var==2) var=1;
                this.repaint();
        	}
        }
        if(e.getX()>670&&e.getX()<770&&e.getY()>460&&e.getY()<490) {//按钮，开始移动
        	core.Board_StartMove();
            this.repaint();
        }
        if(e.getX()>840&&e.getX()<940&&e.getY()>460&&e.getY()<490) {//按钮，结束移动
        	core.Board_EndMove();
            this.repaint();
        }
        if(e.getX()>670&&e.getX()<770&&e.getY()>500&&e.getY()<530) {//按钮，确定移动
            core.Board_MakeMove();
            this.repaint();
        }
        if(e.getX()>840&&e.getX()<940&&e.getY()>500&&e.getY()<530) {//按钮，移动重置
            core.null_BoardMoveAndBoardEat();
            this.repaint();
        }
        if(e.getX()>670&&e.getX()<770&&e.getY()>540&&e.getY()<570) {//按钮，开始提子
        	core.Board_StartEat();
            this.repaint();
        }
        if(e.getX()>840&&e.getX()<940&&e.getY()>540&&e.getY()<570) {//按钮，结束提子
        	core.Board_EndEat();
            this.repaint();
        }
        if(e.getX()>670&&e.getX()<770&&e.getY()>580&&e.getY()<610) {//按钮，确定提子
            core.Board_MakeEat();
            this.repaint();
        }
        if(e.getX()>840&&e.getX()<940&&e.getY()>580&&e.getY()<610) {//按钮，提子重置
            core.null_BoardMoveAndBoardEat();
            this.repaint();
        }
        if(e.getX()>670&&e.getX()<940&&e.getY()>620&&e.getY()<650) {//按钮，切换至下一方
            //执行AI程序
    		if(core.stage==2){//二阶段点击即换手
    			System.out.println(var);
	   			 if(var==1) var=2;
	   			 	else if(var==2) var=1;
	             core.step_num++;
    		}
        	AI_RUN();//执行AI算法
            this.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stu
    }
    private int _CgetX(int x) {
        x -= 60;			//去除左边界30
        if (x % 20 <= 10)		//此处将来需要做简单修改
            return x / 40;
        else
            return x / 40 + 1;
    }
    private int _CgetY(int y) {
        y -= 90;			//去除上边界60
        if (y % 20 <= 10)
            return y / 40;
        else
            return y / 40 + 1;
    }
    int two_stepNum=1;
    //AI算法
    public void AI_RUN() {
    	out:
    	if(core.game_over==1){System.out.println("游戏已经结束喽");}
    	else{
        	if((this.game_state==2&&var==1)||(this.game_state==3&&var==2)||(this.game_state==4)){//判定状态和颜色相对应
        		if(core.stage==1){//一阶段 AI调用
                	//调用AI落子函数进行落子
            		int ai_fall= new fallChessAI().AI_fallChess(var, core.fall_list, core.getCore(),core.step_num);
                	int ai_x=ai_fall%14;//替换为AI
                	int ai_y=ai_fall/14;//替换为AI
                	int a = core.ChessIt(ai_x,ai_y,var);
                	core.SetInfomation(ai_x, ai_y, var);//写入输出信息
                	//System.out.println(11);
                	if(a!=-1) {
                        if(var==1) var=2;
                        else if(var==2) var=1;
                    }
                	System.out.println("当前局面评分"+"白方:"+new fallChessAI().evaluate_fallChess(core.getCore(), 1));
                	System.out.println("当前局面评分"+"黑方:"+new fallChessAI().evaluate_fallChess(core.getCore(), 2));
                    this.repaint();
                    thread_state = true;
        		}
        		if(core.stage==2){//二阶段 AI调用
        			boolean over = true;//结束验证
            		if(core.step_num==0)var=2;//设置为黑方先下
            		int var1_yn=0,var2_yn=0;
            		if(core.chess1==0&&core.chess2==1){var1_yn=0;var2_yn=1;}
            		if(core.chess1==1&&core.chess2==0){var1_yn=1;var2_yn=0;}
            		if(core.chess1==1&&core.chess2==1){var1_yn=1;var2_yn=1;}
    				try {
    	    			System.out.print("第二阶段，第"+two_stepNum+"步：  ");
    	    			int x1,y1,x2,y2;
    	    			//获取当前颜色移动点
    	    			CanWalkChess canWalkChess=null;
    	    			//调用AI
    	    			if(var==1){
    	    				//if(core.chess1==0)canWalkChess = new walkChessAI().CanWalkChessList_AI(var, core.getCore()).get(0);//得分测试，行子AI
    	    				if(core.chess1==0&&core.chess2==0)canWalkChess = new walkChessAI().AI_walkChess(var, core.getCore());//行子AI
    	    				if(core.chess1==0&&core.chess2==1)canWalkChess = new flyChessAI().AI_walkChess(var, core.getCore(),var1_yn,var2_yn);//飞子AI(优势)
    	    				if(core.chess1==1)canWalkChess = new flyChessAI().AI_walkChess(var, core.getCore(),var1_yn,var2_yn);//飞子AI(劣势)
    	    			}
    	    			if(var==2){
    	    				//if(core.chess2==0)canWalkChess = new walkChessAI().CanWalkChessList_AI(var, core.getCore()).get(0);//得分测试，行子AI
    	    				if(core.chess2==0&&core.chess1==0)canWalkChess = new walkChessAI().AI_walkChess(var, core.getCore());//行子AI
    	    				if(core.chess2==0&&core.chess1==1)canWalkChess = new flyChessAI().AI_walkChess(var, core.getCore(),var1_yn,var2_yn);//飞子AI(优势)
    	    				if(core.chess2==1)canWalkChess = new flyChessAI().AI_walkChess(var, core.getCore(),var1_yn,var2_yn);//飞子AI(劣势)
    	    			}
    	    			x1=canWalkChess.getX1();y1=canWalkChess.getY1();
    	    			x2=canWalkChess.getX2();y2=canWalkChess.getY2();
    	    			//移动
    	    			if((x1-x2)==2||(x2-x1)==2||(y1-y2)==2||(y2-y1)==2){//跳子
    	    				core.EatChess(x1,y1);//起点
    	    				core.ChessIt(x2,y2,var);//终点
    	    				if(core.chess1==0&&core.chess2==0)core.EatChess((x1+x2)/2,(y1+y2)/2);//中间点
    	    				//后台显示
    	    				System.out.println("最终为:"+var+"方，起点："+(x1+1)+","+(char)(y1+65)+"--至--"+(x2+1)+","+(char)(y2+65)+"(跳吃："+(1+(x1+x2)/2)+","+(char)(65+(y1+y2)/2)+")");
    	    				over = core.SetInfomationMove_Two(canWalkChess,var);//移动信息显示
    	    				if(!over)break out;//结束验证
    	    				CanWalkChess canWalkChesstiao = new CanWalkChess();
    	    				canWalkChesstiao.setX1((x1+x2)/2);
    	    				canWalkChesstiao.setY1((y1+y2)/2);
    	    				over = core.SetInfomationEat_Two(canWalkChesstiao, var);//吃子信息显示
    	    				if(!over)break out;//结束验证
    	    				//显示,需要修改
    	    				core.Chess_Move(x1, y1);
    	    				core.Chess_Move(x2, y2);
    	    				if(core.chess1==0&&core.chess2==0)core.Chess_Eat((x1+x2)/2,(y1+y2)/2);
    	    			}else{
    	    				core.EatChess(x1,y1);//起点
    	    				core.ChessIt(x2,y2,var);//终点
    	    				System.out.println("最终为:"+var+"方，起点："+(x1+1)+","+(char)(y1+65)+"--至--"+(x2+1)+","+(char)(y2+65));
    	    				//信息存入
    	    				core.SetInfomationMove_Two(canWalkChess,var);//移动信息显示
    	    				if(!over)break out;//结束验证
    	    				//显示,需要修改
    	    				core.Chess_Move(x1, y1);
    	    				core.Chess_Move(x2, y2);
    	    			}
    	    			//吃子
    	    			for(int i=0;i<canWalkChess.getEat_num();i++){
    	    				CanWalkChess canEatChess = null;
    		    			//调用AI
    		    			if(var==1){//注意吃子AI相反，因为是判断对方当前是否处于飞子
    		    				//canEatChess = new walkChessAI().AI_eatChess(var, core.getCore());//随机处吃子AI
    		    				if(core.chess2==0&&core.chess1==0)canEatChess = new walkChessAI().AITrue_eatChess(var, core.getCore());//正常吃子AI
    		    				if(core.chess2==1||core.chess1==1)canEatChess = new flyChessAI().AITrue_eatChess(var, core.getCore(),var1_yn,var2_yn);//飞子吃子AI
    		    			}
    		    			if(var==2){
    		    				//canEatChess = new walkChessAI().AI_eatChess(var, core.getCore());//随机处吃子AI
    		    				if(core.chess2==0&&core.chess1==0)canEatChess = new walkChessAI().AITrue_eatChess(var, core.getCore());//正常吃子AI
    		    				if(core.chess2==1||core.chess1==1)canEatChess = new flyChessAI().AITrue_eatChess(var, core.getCore(),var1_yn,var2_yn);//飞子吃子AI
    		    			}
    	    				core.EatChess(canEatChess.getX1(),canEatChess.getY1());//吃子操作
    	    				System.out.println("最终为:"+var+"方，吃子："+(canEatChess.getX1()+1)+","+(char)(canEatChess.getY1()+65));
    	    				over = core.SetInfomationEat_Two(canWalkChess, var);//吃子信息显示
    	    				if(!over)break out;//结束验证
    	    				core.Chess_Eat(canEatChess.getX1(),canEatChess.getY1());//显示专用,显示,需要修改
    	    			}
    	    			if(var==1) var=2;
    	                else if(var==2) var=1;
    	    			two_stepNum++;
    	    			this.repaint();
    				} catch (ClassNotFoundException | IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
        		}
        	}
    	}
    }
	public void run(){//AI线程，防止不刷新点的情况
		while(core.stage==1){
			AI_RUN();
			repaint();
		}
		while(core.stage==2){
			core.null_BoardMoveAndBoardEat();
			AI_RUN();
			repaint();
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}