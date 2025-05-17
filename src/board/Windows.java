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
    private int game_state =0; //0 ׼���� ��1 ���� �� 2 AIΪ�� ��3 AIΪ�� �� 4 AIvsAI   
    public Windows(String title) {
        super(title);
        core = new Core(14, 14);	//14x14������
        this.setSize(1000, 700);
        this.setLocation(500, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.addMouseListener(this);
    }
    int AI=0;//AI����״̬
    boolean thread_state=true;//AI�߳�״̬
    //�������
    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
        for (int i = 0; i < 14; i++){
        	g.drawString(i+1+"",55 + i * 40,70);//�� ����
            g.drawLine(60, 90 + i * 40, 580, 90 + i * 40);//(x1,y1,x2,y2)
            g.drawString(i+1+"",55 + i * 40,640);//�� ����
        }
        for (int i = 0; i < 14; i++){
        	g.drawString((char)(65+i)+"",30,95 + i * 40);//�� ��ĸ
            g.drawLine(60 + i * 40, 90, 60 + i * 40, 610);//(x1,y1,x2,y2)
            g.drawString((char)(65+i)+"",605,95 + i * 40);//�� ��ĸ
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
        int[][] board_move = core.getBoardMove();//�ƶ�����
        int[][] board_eat = core.getBoardEat();//��������
        int[][] board_move_show = core.getBoardMove();//�ƶ���ʾ
        int[][] board_eat_show = core.getBoardEat();//������ʾ
        for (int i = 0; i < 14; i++) {//��
            for (int j = 0; j < 14; j++) {
                if (board[i][j] == 1)//����Բ����
                    g.drawOval(48 + i * 40, 78 + j * 40, 25, 25);
                if(board[i][j]==2)//ʵ��Բ����
                    g.fillOval(48 + i*40, 78 + j * 40, 25, 25);
                if(board_move[i][j]==1){//ѡ���ƶ�
                	g.setColor(Color.green);
                	g.drawRect(48 + i * 40, 78 + j * 40, 25, 25);
                	g.setColor(Color.black);
                }
                if(board_eat[i][j]==1){//ѡ�ӳ���
                	g.setColor(Color.red);
                	g.drawRect(48 + i * 40, 78 + j * 40, 25, 25);
                	g.setColor(Color.black);
                }
            }
        }
        String infomation = "";
        g.drawString("�����Ϣ:",670,80);
        g.drawRect(670,90, 270, 300);
        List<String> information = core.information;
        for(int i=0;i<information.size();i++){
        	g.drawString(information.get(i), 680, 110+i*15);
        	if(i>16)information.remove(0);
        }
        g.drawRect(670,420,60,30);
        g.drawString("��ʼ",688,440);
        g.drawRect(755,420,100,30);
        g.drawString("�� �� �� ��",778,440);
        g.drawRect(880,420,60,30);
        g.drawString("����",898,440);
        g.drawRect(670,460,100,30);
        g.drawString("�� ʼ �� ��",690,480);
        g.drawRect(840,460,100,30);
        g.drawString("�� �� �� ��",860,480);
        g.drawRect(670,500,100,30);
        g.drawString("ȷ �� �� ��",690,520);
        g.drawRect(840,500,100,30);
        g.drawString("�� �� �� ��",860,520);
        g.drawRect(670,540,100,30);
        g.drawString("�� ʼ �� ��",690,560);  
        g.drawRect(840,540,100,30);
        g.drawString("�� �� �� ��",860,560);
        g.drawRect(670,580,100,30);
        g.drawString("ȷ �� �� ��",690,600);
        g.drawRect(840,580,100,30);
        g.drawString("�� �� �� ��",860,600);
        g.drawRect(670,620,270,30);
        g.drawString("�� �� ��  AI �� ��",760,640);
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
        if (e.getX() > 50 && e.getX() < 590 && e.getY() > 80 && e.getY() < 620) {	//�˴��������̷�Χ
        	if(game_state==0)break out;//׼���׶�
        	
        	if(game_state==1){//���˶�ս
            	if(core.stage==1){//һ�׶� �̶�
            		if(!core.pub__CanInput(_CgetX(e.getX()), _CgetY(e.getY()))){
            			break out;//������֤
            		}else{
            			int a = core.ChessIt(_CgetX(e.getX()), (_CgetY(e.getY())), var);//���壬����ȡʤ������������ȥ�����˴������ж�ʤ����
                        core.SetInfomation(_CgetX(e.getX()), (_CgetY(e.getY())), var);//д�������Ϣ
                        this.repaint();
                        if(a!=-1) {
                            if(var==1) var=2;
                            else if(var==2) var=1;
                        }
            		}
            	}
            	if(core.stage==2){//���׶� �̶�
            		if(core.two_state==1){//�ƶ�
                		core.Chess_Move( _CgetX(e.getX()), _CgetY(e.getY()) );//ѡ��λ��
                		this.repaint();
            		}
            		if(core.two_state==2){//����
                		core.Chess_Eat( _CgetX(e.getX()), _CgetY(e.getY()) );//ѡ��λ��
                		this.repaint();
            		}
            	}
        	}
        	if(game_state==2){ // AIΪ��
                if(core.stage==1){//һ�׶� �̶�
            		if(var==1)break out;//�������̣��ȴ�AI����
            		if(AI==1) break out;//AI״̬��֤
                	if(var==2){ //������
                		if(!core.pub__CanInput(_CgetX(e.getX()), _CgetY(e.getY()))){
                			break out;//������֤
                		}else{
                			int a = core.ChessIt(_CgetX(e.getX()), (_CgetY(e.getY())), var);//���壬����ȡʤ������������ȥ�����˴������ж�ʤ����
                            core.SetInfomation(_CgetX(e.getX()), (_CgetY(e.getY())), var);//д�������Ϣ
                            this.repaint();
                            if(a!=-1) {
                                if(var==1) var=2;
                                else if(var==2) var=1;
                            }
                		}
                		this.repaint();
                    	System.out.println("��ǰ��������"+"�׷�:"+new fallChessAI().evaluate_fallChess(core.getCore(), 1));
                    	System.out.println("��ǰ��������"+"�ڷ�:"+new fallChessAI().evaluate_fallChess(core.getCore(), 2));
                	}
        		}
            	if(core.stage==2){//���׶� �̶�
            		if(core.two_state==1){//�ƶ�
                		core.Chess_Move( _CgetX(e.getX()), _CgetY(e.getY()) );//ѡ��λ��
                		this.repaint();
            		}
            		
            		if(core.two_state==2){//����
                		core.Chess_Eat( _CgetX(e.getX()), _CgetY(e.getY()) );//ѡ��λ��
                		this.repaint();
            		}
            	}
        	}
        	if(game_state==3){ // AIΪ��
                if(core.stage==1){//һ�׶� �̶�
                	if(var==2)break out;//�������̣��ȴ�AI����
                	if(AI==1) break out;//AI״̬��֤
                	if(var==1){ //������
                		if(!core.pub__CanInput(_CgetX(e.getX()), _CgetY(e.getY()))){
                			break out;//������֤
                		}else{
                			int a = core.ChessIt(_CgetX(e.getX()), (_CgetY(e.getY())), var);//���壬����ȡʤ������������ȥ�����˴������ж�ʤ����
                            core.SetInfomation(_CgetX(e.getX()), (_CgetY(e.getY())), var);//д�������Ϣ
                            this.repaint();
                            if(a!=-1) {
                                if(var==1) var=2;
                                else if(var==2) var=1;
                            }
                		}
                		this.repaint();
                    	System.out.println("��ǰ��������"+"�׷�:"+new fallChessAI().evaluate_fallChess(core.getCore(), 1));
                    	System.out.println("��ǰ��������"+"�ڷ�:"+new fallChessAI().evaluate_fallChess(core.getCore(), 2));
                	}
        		}
            	if(core.stage==2){//���׶� �̶�
            		if(core.two_state==1){//�ƶ�
                		core.Chess_Move( _CgetX(e.getX()), _CgetY(e.getY()) );//ѡ��λ��
                		this.repaint();
            		}
            		
            		if(core.two_state==2){//����
                		core.Chess_Eat( _CgetX(e.getX()), _CgetY(e.getY()) );//ѡ��λ��
                		this.repaint();
            		}
            	}
            	
        	}
        	
        	if(game_state==4){//AI VS AI
            	if(core.stage==1){//һ�׶� �̶�
            	}
            	if(core.stage==2){//���׶� �̶�
            	}
        	}
        }
	    if(e.getX()>755&&e.getX()<855&&e.getY()>420&&e.getY()<450) {//���̸���
	    	core.FU_PAN();
	    	this.repaint();
	    }
        if(e.getX()>670&&e.getX()<730&&e.getY()>420&&e.getY()<450) {//��ť����ʼ
        	if(this.game_state==0){
                Object[] options = {"��VS��","AIΪ��","AIΪ��","AI VS AI"};
                int n = JOptionPane.showOptionDialog(null,"��ѡ����Ϸģʽ��","��Ϸ����",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,options,options[0]);
                if(n==0) this.game_state=1;
                if(n==1) {this.game_state=2;AI_COLOR=1;}
                if(n==2) {this.game_state=3;AI_COLOR=2;}
                if(n==3) this.game_state=4;
                this.core.Restart();
                this.repaint();
                var=1;//����Ϊ�ȣ�
            	if(n==1){//����һ�ְ���
            		//���ӵ�һ�� G7
            		int a = core.ChessIt(6,6,var);
            		core.SetInfomation(6, 6, var);//д�������Ϣ
                    this.repaint();
                    if(a!=-1) {
                       if(var==1) var=2;
                       else if(var==2) var=1;
                    }
                    System.out.println(core.fall_list.size());
            	}
            	if(n==3){//AI VS AI
            		//���� 1 2 ��
            		core.ChessIt(6, 6, 1);
            		core.SetInfomation(6, 6, 1);//д�������Ϣ
            		core.ChessIt(7, 7, 2);
            		core.SetInfomation(7, 7, 2);//д�������Ϣ
            		this.repaint();
            		new Thread(this).start();//ʹ���߳�ʵ��,
            	}
        	}
        }
        if(e.getX()>880&&e.getX()<940&&e.getY()>420&&e.getY()<450) {//��ť������
        	if(core.stage==1){
                core.RetChess();
                if(var==1) var=2;
                else if(var==2) var=1;
                this.repaint();
        	}
        }
        if(e.getX()>670&&e.getX()<770&&e.getY()>460&&e.getY()<490) {//��ť����ʼ�ƶ�
        	core.Board_StartMove();
            this.repaint();
        }
        if(e.getX()>840&&e.getX()<940&&e.getY()>460&&e.getY()<490) {//��ť�������ƶ�
        	core.Board_EndMove();
            this.repaint();
        }
        if(e.getX()>670&&e.getX()<770&&e.getY()>500&&e.getY()<530) {//��ť��ȷ���ƶ�
            core.Board_MakeMove();
            this.repaint();
        }
        if(e.getX()>840&&e.getX()<940&&e.getY()>500&&e.getY()<530) {//��ť���ƶ�����
            core.null_BoardMoveAndBoardEat();
            this.repaint();
        }
        if(e.getX()>670&&e.getX()<770&&e.getY()>540&&e.getY()<570) {//��ť����ʼ����
        	core.Board_StartEat();
            this.repaint();
        }
        if(e.getX()>840&&e.getX()<940&&e.getY()>540&&e.getY()<570) {//��ť����������
        	core.Board_EndEat();
            this.repaint();
        }
        if(e.getX()>670&&e.getX()<770&&e.getY()>580&&e.getY()<610) {//��ť��ȷ������
            core.Board_MakeEat();
            this.repaint();
        }
        if(e.getX()>840&&e.getX()<940&&e.getY()>580&&e.getY()<610) {//��ť����������
            core.null_BoardMoveAndBoardEat();
            this.repaint();
        }
        if(e.getX()>670&&e.getX()<940&&e.getY()>620&&e.getY()<650) {//��ť���л�����һ��
            //ִ��AI����
    		if(core.stage==2){//���׶ε��������
    			System.out.println(var);
	   			 if(var==1) var=2;
	   			 	else if(var==2) var=1;
	             core.step_num++;
    		}
        	AI_RUN();//ִ��AI�㷨
            this.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stu
    }
    private int _CgetX(int x) {
        x -= 60;			//ȥ����߽�30
        if (x % 20 <= 10)		//�˴�������Ҫ�����޸�
            return x / 40;
        else
            return x / 40 + 1;
    }
    private int _CgetY(int y) {
        y -= 90;			//ȥ���ϱ߽�60
        if (y % 20 <= 10)
            return y / 40;
        else
            return y / 40 + 1;
    }
    int two_stepNum=1;
    //AI�㷨
    public void AI_RUN() {
    	out:
    	if(core.game_over==1){System.out.println("��Ϸ�Ѿ������");}
    	else{
        	if((this.game_state==2&&var==1)||(this.game_state==3&&var==2)||(this.game_state==4)){//�ж�״̬����ɫ���Ӧ
        		if(core.stage==1){//һ�׶� AI����
                	//����AI���Ӻ�����������
            		int ai_fall= new fallChessAI().AI_fallChess(var, core.fall_list, core.getCore(),core.step_num);
                	int ai_x=ai_fall%14;//�滻ΪAI
                	int ai_y=ai_fall/14;//�滻ΪAI
                	int a = core.ChessIt(ai_x,ai_y,var);
                	core.SetInfomation(ai_x, ai_y, var);//д�������Ϣ
                	//System.out.println(11);
                	if(a!=-1) {
                        if(var==1) var=2;
                        else if(var==2) var=1;
                    }
                	System.out.println("��ǰ��������"+"�׷�:"+new fallChessAI().evaluate_fallChess(core.getCore(), 1));
                	System.out.println("��ǰ��������"+"�ڷ�:"+new fallChessAI().evaluate_fallChess(core.getCore(), 2));
                    this.repaint();
                    thread_state = true;
        		}
        		if(core.stage==2){//���׶� AI����
        			boolean over = true;//������֤
            		if(core.step_num==0)var=2;//����Ϊ�ڷ�����
            		int var1_yn=0,var2_yn=0;
            		if(core.chess1==0&&core.chess2==1){var1_yn=0;var2_yn=1;}
            		if(core.chess1==1&&core.chess2==0){var1_yn=1;var2_yn=0;}
            		if(core.chess1==1&&core.chess2==1){var1_yn=1;var2_yn=1;}
    				try {
    	    			System.out.print("�ڶ��׶Σ���"+two_stepNum+"����  ");
    	    			int x1,y1,x2,y2;
    	    			//��ȡ��ǰ��ɫ�ƶ���
    	    			CanWalkChess canWalkChess=null;
    	    			//����AI
    	    			if(var==1){
    	    				//if(core.chess1==0)canWalkChess = new walkChessAI().CanWalkChessList_AI(var, core.getCore()).get(0);//�÷ֲ��ԣ�����AI
    	    				if(core.chess1==0&&core.chess2==0)canWalkChess = new walkChessAI().AI_walkChess(var, core.getCore());//����AI
    	    				if(core.chess1==0&&core.chess2==1)canWalkChess = new flyChessAI().AI_walkChess(var, core.getCore(),var1_yn,var2_yn);//����AI(����)
    	    				if(core.chess1==1)canWalkChess = new flyChessAI().AI_walkChess(var, core.getCore(),var1_yn,var2_yn);//����AI(����)
    	    			}
    	    			if(var==2){
    	    				//if(core.chess2==0)canWalkChess = new walkChessAI().CanWalkChessList_AI(var, core.getCore()).get(0);//�÷ֲ��ԣ�����AI
    	    				if(core.chess2==0&&core.chess1==0)canWalkChess = new walkChessAI().AI_walkChess(var, core.getCore());//����AI
    	    				if(core.chess2==0&&core.chess1==1)canWalkChess = new flyChessAI().AI_walkChess(var, core.getCore(),var1_yn,var2_yn);//����AI(����)
    	    				if(core.chess2==1)canWalkChess = new flyChessAI().AI_walkChess(var, core.getCore(),var1_yn,var2_yn);//����AI(����)
    	    			}
    	    			x1=canWalkChess.getX1();y1=canWalkChess.getY1();
    	    			x2=canWalkChess.getX2();y2=canWalkChess.getY2();
    	    			//�ƶ�
    	    			if((x1-x2)==2||(x2-x1)==2||(y1-y2)==2||(y2-y1)==2){//����
    	    				core.EatChess(x1,y1);//���
    	    				core.ChessIt(x2,y2,var);//�յ�
    	    				if(core.chess1==0&&core.chess2==0)core.EatChess((x1+x2)/2,(y1+y2)/2);//�м��
    	    				//��̨��ʾ
    	    				System.out.println("����Ϊ:"+var+"������㣺"+(x1+1)+","+(char)(y1+65)+"--��--"+(x2+1)+","+(char)(y2+65)+"(���ԣ�"+(1+(x1+x2)/2)+","+(char)(65+(y1+y2)/2)+")");
    	    				over = core.SetInfomationMove_Two(canWalkChess,var);//�ƶ���Ϣ��ʾ
    	    				if(!over)break out;//������֤
    	    				CanWalkChess canWalkChesstiao = new CanWalkChess();
    	    				canWalkChesstiao.setX1((x1+x2)/2);
    	    				canWalkChesstiao.setY1((y1+y2)/2);
    	    				over = core.SetInfomationEat_Two(canWalkChesstiao, var);//������Ϣ��ʾ
    	    				if(!over)break out;//������֤
    	    				//��ʾ,��Ҫ�޸�
    	    				core.Chess_Move(x1, y1);
    	    				core.Chess_Move(x2, y2);
    	    				if(core.chess1==0&&core.chess2==0)core.Chess_Eat((x1+x2)/2,(y1+y2)/2);
    	    			}else{
    	    				core.EatChess(x1,y1);//���
    	    				core.ChessIt(x2,y2,var);//�յ�
    	    				System.out.println("����Ϊ:"+var+"������㣺"+(x1+1)+","+(char)(y1+65)+"--��--"+(x2+1)+","+(char)(y2+65));
    	    				//��Ϣ����
    	    				core.SetInfomationMove_Two(canWalkChess,var);//�ƶ���Ϣ��ʾ
    	    				if(!over)break out;//������֤
    	    				//��ʾ,��Ҫ�޸�
    	    				core.Chess_Move(x1, y1);
    	    				core.Chess_Move(x2, y2);
    	    			}
    	    			//����
    	    			for(int i=0;i<canWalkChess.getEat_num();i++){
    	    				CanWalkChess canEatChess = null;
    		    			//����AI
    		    			if(var==1){//ע�����AI�෴����Ϊ���ж϶Է���ǰ�Ƿ��ڷ���
    		    				//canEatChess = new walkChessAI().AI_eatChess(var, core.getCore());//���������AI
    		    				if(core.chess2==0&&core.chess1==0)canEatChess = new walkChessAI().AITrue_eatChess(var, core.getCore());//��������AI
    		    				if(core.chess2==1||core.chess1==1)canEatChess = new flyChessAI().AITrue_eatChess(var, core.getCore(),var1_yn,var2_yn);//���ӳ���AI
    		    			}
    		    			if(var==2){
    		    				//canEatChess = new walkChessAI().AI_eatChess(var, core.getCore());//���������AI
    		    				if(core.chess2==0&&core.chess1==0)canEatChess = new walkChessAI().AITrue_eatChess(var, core.getCore());//��������AI
    		    				if(core.chess2==1||core.chess1==1)canEatChess = new flyChessAI().AITrue_eatChess(var, core.getCore(),var1_yn,var2_yn);//���ӳ���AI
    		    			}
    	    				core.EatChess(canEatChess.getX1(),canEatChess.getY1());//���Ӳ���
    	    				System.out.println("����Ϊ:"+var+"�������ӣ�"+(canEatChess.getX1()+1)+","+(char)(canEatChess.getY1()+65));
    	    				over = core.SetInfomationEat_Two(canWalkChess, var);//������Ϣ��ʾ
    	    				if(!over)break out;//������֤
    	    				core.Chess_Eat(canEatChess.getX1(),canEatChess.getY1());//��ʾר��,��ʾ,��Ҫ�޸�
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
	public void run(){//AI�̣߳���ֹ��ˢ�µ�����
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