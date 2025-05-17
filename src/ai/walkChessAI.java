package ai;
import board.Core;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class walkChessAI {
	public Core core;//棋盘
	int Walk_AI_STEP = 20; //向下模拟步数 20
	int Walk_AI_ALL = 4000;//总随机次数 50000
	int EAT_AI_ALL  = 5000;//吃子模拟盘数 50000	
	int CoreMax_NUM = 1;//得分最大点加权（随机概率）	
	int first_ge = 2;//初步成格权重
	int first_get_lian = 5;//成链权重	
    private int get_four = 10;//成4得分(追加)
    private int get_three = 5;//成3得分(追加)
    private int get_two = 2;//成2得分(追加)
    private int tiao_eat = 4;//跳得分
    private int get_star = 3;//起点是否成格    
	public List<CanWalkChess> get_CanWalkChessList(int[][] core,int var){
        int n_var = -1;
		if(var==1) n_var=2;
    	else if(var==2) n_var=1;
		int x1,y1,x2,y2;		
		int max_score = 0;		
		List<CanWalkChess> canMove_list = new ArrayList<CanWalkChess>();
		for(int i=0;i<14;i++){
			for(int j=0;j<14;j++){
				if(core[i][j]==0){//空点
					if(i-1>=0){//上存在
						if(core[i-1][j]==var){//上为我方可移							
							x1=i-1;y1=j;//起点
							x2=i;y2=j;//终点
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//最大值放到第一个位置
							}else{
								canMove_list.add(canWalkChess);//普通插入
							}
						}
						if(core[i-1][j]==n_var){//敌方
							if(i-2>=0&&core[i-2][j]==var){//我方可跳
								x1=i-2;y1=j;//起点
								x2=i;y2=j;//终点
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//最大值放到第一个位置
								}else{
									canMove_list.add(canWalkChess);//普通插入
								}
							}
						}
					}
					if(i+1<=13){//下
						if(core[i+1][j]==var){//我方可移
							x1=i+1;y1=j;//起点
							x2=i;y2=j;//终点
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//最大值放到第一个位置
							}else{
								canMove_list.add(canWalkChess);//普通插入
							}
						}
						if(core[i+1][j]==n_var){//敌方
							if(i+2<=13&&core[i+2][j]==var){//我方可跳
								x1=i+2;y1=j;//起点
								x2=i;y2=j;//终点
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//最大值放到第一个位置
								}else{
									canMove_list.add(canWalkChess);//普通插入
								}
							}
						}
					}
					if(j-1>=0){//左
						if(core[i][j-1]==var){//我方可移
							x1=i;y1=j-1;//起点
							x2=i;y2=j;//终点
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//最大值放到第一个位置
							}else{
								canMove_list.add(canWalkChess);//普通插入
							}
						}
						if(core[i][j-1]==n_var){//敌方
							if(j-2>=0&&core[i][j-2]==var){//我方可跳
								x1=i;y1=j-2;//起点
								x2=i;y2=j;//终点
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//最大值放到第一个位置
								}else{
									canMove_list.add(canWalkChess);//普通插入
								}
							}
						}
					}
					if(j+1<=13){//右
						if(core[i][j+1]==var){//我方可移
							x1=i;y1=j+1;//起点
							x2=i;y2=j;//终点
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//最大值放到第一个位置
							}else{
								canMove_list.add(canWalkChess);//普通插入
							}
						}
						if(core[i][j+1]==n_var){//敌方
							if(j+2<=13&&core[i][j+2]==var){//我方可跳
								x1=i;y1=j+2;//起点
								x2=i;y2=j;//终点
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//最大值放到第一个位置
								}else{
									canMove_list.add(canWalkChess);//普通插入
								}
							}
						}
					}	
				}
			}
		}
		if(max_score==0){
			Collections.shuffle(canMove_list);
		}
		//System.out.println(canMove_list.get(0).getY1());
		return canMove_list;
	}
	//2获得随机处落子AI(此处非正式落子AI)
	public List<CanWalkChess> CanWalkChessList_AI(int var,int[][] core){
        int n_var = -1;
		if(var==1) n_var=2;
    	else if(var==2) n_var=1;
		int x1,y1,x2,y2;
		int max_score = 0;		
		List<CanWalkChess> canMove_list = new ArrayList<CanWalkChess>();
		for(int i=0;i<14;i++){
			for(int j=0;j<14;j++){
				if(core[i][j]==0){//空点
					if(i-1>=0){//上存在
						if(core[i-1][j]==var){//上为我方可移							
							x1=i-1;y1=j;//起点
							x2=i;y2=j;//终点
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);//计算得分
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//最大值放到第一个位置
							}else{
								canMove_list.add(canWalkChess);//普通插入
							}
						}
						if(core[i-1][j]==n_var){//敌方
							if(i-2>=0&&core[i-2][j]==var){//我方可跳
								x1=i-2;y1=j;//起点
								x2=i;y2=j;//终点
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//最大值放到第一个位置
								}else{
									canMove_list.add(canWalkChess);//普通插入
								}
							}
						}
					}
					
					if(i+1<=13){//下
						if(core[i+1][j]==var){//我方可移
							x1=i+1;y1=j;//起点
							x2=i;y2=j;//终点
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//最大值放到第一个位置
							}else{
								canMove_list.add(canWalkChess);//普通插入
							}
						}
						if(core[i+1][j]==n_var){//敌方
							if(i+2<=13&&core[i+2][j]==var){//我方可跳
								x1=i+2;y1=j;//起点
								x2=i;y2=j;//终点
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//最大值放到第一个位置
								}else{
									canMove_list.add(canWalkChess);//普通插入
								}
							}
						}
					}
					if(j-1>=0){//左
						if(core[i][j-1]==var){//我方可移
							x1=i;y1=j-1;//起点
							x2=i;y2=j;//终点
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//最大值放到第一个位置
							}else{
								canMove_list.add(canWalkChess);//普通插入
							}
						}
						if(core[i][j-1]==n_var){//敌方
							if(j-2>=0&&core[i][j-2]==var){//我方可跳
								x1=i;y1=j-2;//起点
								x2=i;y2=j;//终点
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//最大值放到第一个位置
								}else{
									canMove_list.add(canWalkChess);//普通插入
								}
							}
						}
					}
					if(j+1<=13){//右
						if(core[i][j+1]==var){//我方可移
							x1=i;y1=j+1;//起点
							x2=i;y2=j;//终点
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//最大值放到第一个位置
							}else{
								canMove_list.add(canWalkChess);//普通插入
							}
						}
						if(core[i][j+1]==n_var){//敌方
							if(j+2<=13&&core[i][j+2]==var){//我方可跳
								x1=i;y1=j+2;//起点
								x2=i;y2=j;//终点
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//最大值放到第一个位置
								}else{
									canMove_list.add(canWalkChess);//普通插入
								}
							}
						}
					}
					
				}
			}
		}
		if(max_score==0){
			Collections.shuffle(canMove_list);
		}else{
			int size_list = CoreMax_NUM*canMove_list.size();
			//System.out.println("添加个数："+size_list);
			CanWalkChess canWalkChess = canMove_list.get(0);
			for(int i=0;i<size_list;i++){//增加最大值落点的概率(加权)
				canMove_list.add(canWalkChess);
				canMove_list.add(0,canWalkChess);
			}
			Collections.shuffle(canMove_list);
		}
		return canMove_list;
	}	
	public CanWalkChess AI_eatChess(int var,int[][] core){
		CanWalkChess end_eat = new CanWalkChess();//记录最终吃子
        int n_var = -1;//获取敌方颜色
		if(var==1) n_var=2;
    	else if(var==2) n_var=1;
		int max_score=0;
		//记录可吃子
		List<CanWalkChess> caneat_list = new ArrayList<CanWalkChess>();
		for(int i=0;i<14;i++){
			for(int j=0;j<14;j++){
				if(core[i][j]==n_var){//为敌方点，代表可吃~~
					//计算得分，插入List当中
					int eat_score=0;//得分
					end_eat.setX1(i);end_eat.setY1(j);//先存入待吃子
					//我方棋眼
					if(i-1>=0&&core[i-1][j]==var){//上
						eat_score = eat_score + get_two;
						if(j-1>=0&&core[i][j-1]==var){//左
							eat_score = eat_score + get_three;
							if(core[i-1][j-1]==var)//上左
							{
								eat_score = eat_score + get_four;
							}
						}
						if(j+1<=13&&core[i][j+1]==var){//右
							eat_score = eat_score + get_three;
							if(core[i-1][j+1]==var)//上右
							{
								eat_score = eat_score + get_four;
							}
						}
					}
					if(i+1<=13&&core[i+1][j]==var){//下
						eat_score = eat_score + get_two;
						if(j-1>=0&&core[i][j-1]==var){//左
							eat_score = eat_score + get_three;
							if(core[i+1][j-1]==var)//下左
							{
								eat_score = eat_score + get_four;
							}
						}
						if(j+1<=13&&core[i][j+1]==var){//右
							eat_score = eat_score + get_three;
							if(core[i+1][j+1]==var)//下右
							{
								eat_score = eat_score + get_four;
							}
						}
					}
					if(j-1>=0&&core[i][j-1]==var)eat_score = eat_score + get_two;
					if(j+1<=13&&core[i][j+1]==var)eat_score = eat_score + get_two;
					if(i-1>=0&&core[i-1][j]==n_var){//上
						if(j-1>=0&&core[i][j-1]==n_var){//左
							if(core[i-1][j-1]==n_var)//上左
							{
								eat_score = eat_score - get_four*100;
							}
						}
						if(j+1<=13&&core[i][j+1]==n_var){//右
							if(core[i-1][j+1]==n_var)//上右
							{
								eat_score = eat_score - get_four*100;
							}
						}
					}
					if(i+1<=13&&core[i+1][j]==n_var){//下
						if(j-1>=0&&core[i][j-1]==n_var){//左
							if(core[i+1][j-1]==n_var)//下左
							{
								eat_score = eat_score - get_four*100;
							}
						}
						if(j+1<=13&&core[i][j+1]==n_var){//右
							if(core[i+1][j+1]==n_var)//下右
							{
								eat_score = eat_score - get_four*100;
							}
						}
					}
					if(eat_score>max_score){
						max_score = eat_score;
						caneat_list.add(0, end_eat);
					}else{
						caneat_list.add(end_eat);
					}
				}
			}
		}
		if(caneat_list.size()<1){
			end_eat.setX1(-1);//标记特殊情况----------------------------------------------------------------
			return end_eat;
		}
		end_eat = caneat_list.get(0);
		return end_eat;
	}
	public CanWalkChess Move_EatNumAndGetScore(int[][] core1,int var,int x1,int y1,int x2,int y2){
		CanWalkChess canWalkChess = new CanWalkChess();
		int eat_num=0;//吃子
		int get_score=0;//得分
		int core[][] = coreCopy(core1);//复制棋盘
		int i=x1;
		int j=y1;
		int get_lian=0;
		canWalkChess.setGet_lian(0);//成链验证，无链为0，有链为1
		if(i-1>=0&&core[i-1][j]==var){//上
			//get_score = get_score + get_two;
			if(j-1>=0&&core[i][j-1]==var){//左
				if(core[i-1][j-1]==var)//上左
				{
					get_score = get_score + get_star;//起点是否成格
					get_lian=1;
				}
			}
			if(j+1<=13&&core[i][j+1]==var){//右
				if(core[i-1][j+1]==var)//上右
				{
					get_score = get_score + get_star;//起点是否成格
					get_lian=1;
				}
			}
		}
		if(i+1<=13&&core[i+1][j]==var){//下
			if(j-1>=0&&core[i][j-1]==var){//左
				if(core[i+1][j-1]==var)//下左
				{
					get_score = get_score + get_star;//起点是否成格
					get_lian=1;
				}
			}
			if(j+1<=13&&core[i][j+1]==var){//右
				if(core[i+1][j+1]==var)//下右
				{
					get_score = get_score + get_star;//起点是否成格
					get_lian=1;
				}
			}
		}
		if((x1-x2)>1||(x2-x1)>1||(y1-y2)>1||(y2-y1)>1){//跳子
			core[x1][y1]=0;//模拟移动
			core[(x1+x2)/2][(y1+y2)/2]=0;//模拟跳子
			core[x2][y2]=var;//模拟落点
		}else{
			core[x1][y1]=0;//模拟移动
			core[x2][y2]=var;//模拟落点
		}
		i=x2;
		j=y2;
		if(i-1>=0&&core[i-1][j]==var){//上
			//get_score = get_score + get_two;
			if(j-1>=0&&core[i][j-1]==var){//左
				get_score = get_score + get_three;
				if(core[i-1][j-1]==var)//上左
				{
					eat_num++;
					get_score = get_score + get_four;
					if(get_lian==1)canWalkChess.setGet_lian(1);
				}
			}
			if(j+1<=13&&core[i][j+1]==var){//右
				get_score = get_score + get_three;
				if(core[i-1][j+1]==var)//上右
				{
					eat_num++;
					get_score = get_score + get_four;
					if(get_lian==1)canWalkChess.setGet_lian(1);
				}
			}
		}
		if(i+1<=13&&core[i+1][j]==var){//下
			//get_score = get_score + get_two;
			if(j-1>=0&&core[i][j-1]==var){//左
				get_score = get_score + get_three;
				if(core[i+1][j-1]==var)//下左
				{
					eat_num++;
					get_score = get_score + get_four;
					if(get_lian==1)canWalkChess.setGet_lian(1);
				}
			}
			if(j+1<=13&&core[i][j+1]==var){//右
				get_score = get_score + get_three;
				if(core[i+1][j+1]==var)//下右
				{
					eat_num++;
					get_score = get_score + get_four;
					if(get_lian==1)canWalkChess.setGet_lian(1);
				}
			}
		}
		canWalkChess.setX1(x1);
		canWalkChess.setY1(y1);
		canWalkChess.setX2(x2);
		canWalkChess.setY2(y2);
		canWalkChess.setEat_num(eat_num);
		canWalkChess.setGet_score(get_score);		
		return canWalkChess;
	}
	public int CopyWalkMove(int var,int[][] core1,int copy_eat_num,int get_lian) throws ClassNotFoundException, IOException{
		int[][] core = coreCopy(core1);//复制当前棋盘
		int i=0;//步数
		int var1_eat=0;
		int var2_eat=0;
		if(var==1){//注意，此处传过来的吃子，是反的！！！
			var2_eat=var2_eat+copy_eat_num*first_ge;
		}else{
			var1_eat=var1_eat+copy_eat_num*first_ge;
		}
		if(get_lian==1){//首落成链
			//首落成链加权
			if(var==1){//注意，此处是反的！！！
				var2_eat=var2_eat+1*first_get_lian;
			}else{
				var1_eat=var1_eat+1*first_get_lian;
			}
		}	
		while(i<Walk_AI_STEP){//无限循环(限制步数，深度)			
			int x1,y1,x2,y2;
			//获取当前颜色移动点
			List<CanWalkChess> canWalk_list = CanWalkChessList_AI(var,core);//获得得分list
			//胜负判定
			if(canWalk_list.size()==0) return (var1_eat-var2_eat);
			//随机取点(方法中已经排序)
			x1=canWalk_list.get(0).getX1();y1=canWalk_list.get(0).getY1();
			x2=canWalk_list.get(0).getX2();y2=canWalk_list.get(0).getY2();
			//落点
			if((x1-x2)>1||(x2-x1)>1||(y1-y2)>1||(y2-y1)>1){//跳子
				core[x1][y1]=0;//起点
				core[x2][y2]=var;//终点
				core[(x1+x2)/2][(y1+y2)/2]=0;//中间点				
			}else{
				core[x1][y1]=0;//起点
				core[x2][y2]=var;//终点
			}
			for(int j=0;j<canWalk_list.get(0).getEat_num();j++){
				CanWalkChess canEatChess = AI_eatChess(var, core);
				if(canEatChess.getX1()==-1)break;
				else{
					core[canEatChess.getX1()][canEatChess.getY1()]=0;
					if(var==1){var1_eat++;}//吃子计数
					if(var==2){var2_eat++;}
				}
			}
			i++;
			if(var==1)var=2;
			else if(var==2)var=1;
			if(i>500){//强行退出
				System.out.println("死循环");
				return (var1_eat-var2_eat);
			}
		}
		return (var1_eat-var2_eat);
	}
	public CanWalkChess AI_walkChess(int var,int[][] core) throws ClassNotFoundException, IOException{
		CanWalkChess end_walk = new CanWalkChess();//记录最终移动
		int max_value=0;
		int n_var=0;
		if(var==1)n_var=2;
		if(var==2)n_var=1;
		int flag = -1;
		//1、准备棋盘并复制
		int[][] canwalk_core = core;//获取当前棋盘
		List<CanWalkChess> canwalk_list = get_CanWalkChessList(core, var);//获取当前方可落棋盘
		long begintime = System.currentTimeMillis();//计时开始
		System.out.println(var+"方的可移动点个数为:"+canwalk_list.size());
		int canmove_chess = canwalk_list.size();
		for(int i=0;i<canwalk_list.size();i++){//遍历可落棋盘
			int[][] copy_canwalk_core = coreCopy(canwalk_core);//复制当前棋盘
			CanWalkChess copy_end_walk = canwalk_list.get(i);//获取当前落点
			int x1=copy_end_walk.getX1();
			int x2=copy_end_walk.getX2();
			int y1=copy_end_walk.getY1();
			int y2=copy_end_walk.getY2();
			if((x1-x2)>1||(x2-x1)>1||(y1-y2)>1||(y2-y1)>1){//跳子
				copy_canwalk_core[x1][y1]=0;//起点
				copy_canwalk_core[x2][y2]=var;//终点
				copy_canwalk_core[(x1+x2)/2][(y1+y2)/2]=0;//中间点
			}else{
				copy_canwalk_core[x1][y1]=0;//起点
				copy_canwalk_core[x2][y2]=var;//终点
			}
			CanWalkChess CanWalkChess_gelian = Move_EatNumAndGetScore(copy_canwalk_core, var, x1, y1, x2, y2);
			int copy_eat_num = CanWalkChess_gelian.getEat_num();
			int get_lian = CanWalkChess_gelian.getGet_lian();
			//模拟下多盘计算value
			int value=0;
			int Walk_AI_NUM = Walk_AI_ALL/canmove_chess;
			for(int ai_num=0;ai_num<Walk_AI_NUM;ai_num++){
				int var_eatvalue = CopyWalkMove(n_var, copy_canwalk_core,copy_eat_num,get_lian);//模拟棋盘的函数111111111
				if(var==1){
					value=value+var_eatvalue;
				}else{
					value=value+(-var_eatvalue);
				}
			}
			if(flag==-1){flag=1;max_value = value;end_walk = canwalk_list.get(i);}//第一次
			if(value>=max_value){
				max_value = value;
				end_walk = canwalk_list.get(i);
			}
			System.out.print(var+"方，移动："+(canwalk_list.get(i).getX1()+1)+","+(char)(canwalk_list.get(i).getY1()+65)+"--至--"+(canwalk_list.get(i).getX2()+1)+","+(char)(canwalk_list.get(i).getY2()+65)+" ");
			System.out.println("价值为："+value+"/"+Walk_AI_NUM);
		}

		long endtime=System.currentTimeMillis();//计时结束
		long costTime = (endtime - begintime);
		System.out.println("单布耗时："+costTime/1000+"s");
		return end_walk;
	}
	
	//真正的吃子AI
	public CanWalkChess AITrue_eatChess(int var,int[][] core) throws ClassNotFoundException, IOException{
		CanWalkChess end_eat = new CanWalkChess();//记录最终吃子
        int n_var = -1;//获取敌方颜色
		if(var==1) n_var=2;
    	else if(var==2) n_var=1;
		int max_num=0;
		for(int i1=0;i1<14;i1++)for(int j1=0;j1<14;j1++)if(core[i1][j1]==n_var)max_num++;
		int EAT_AI_NUM = EAT_AI_ALL/max_num;		
		if(max_num==0){
			end_eat.setX1(-1);//标记特殊情况----无子可吃------------------------------------------------------------
			return end_eat;
		}		
		int max_value=0;
		int flag=0;
		for(int i=0;i<14;i++){
			for(int j=0;j<14;j++){
				if(core[i][j]==n_var){//循环内为可吃的子
					int[][] copy_caneat_core = coreCopy(core);//复制当前棋盘
					copy_caneat_core[i][j]=0;
					int value=0;//记录价值
					for(int ai_num=0;ai_num<EAT_AI_NUM;ai_num++){
						int var_eatvalue = CopyWalkMove_eat(n_var, copy_caneat_core,0,0);//模拟棋盘的函数11111111
						if(var==1){
							value=value+var_eatvalue;
						}else{
							value=value+(-var_eatvalue);
						}
					}
					if(i-1>=0&&core[i-1][j]==n_var){//上
						if(j-1>=0&&core[i][j-1]==n_var){//左
							if(core[i-1][j-1]==n_var)//上左
							{
								value = value - 999999;
							}
						}
						if(j+1<=13&&core[i][j+1]==n_var){//右
							if(core[i-1][j+1]==n_var)//上右
							{
								value = value - 999999;
							}
						}
					}
					if(i+1<=13&&core[i+1][j]==n_var){//下
						if(j-1>=0&&core[i][j-1]==n_var){//左
							if(core[i+1][j-1]==n_var)//下左
							{
								value = value - 999999;
							}
						}
						if(j+1<=13&&core[i][j+1]==n_var){//右
							if(core[i+1][j+1]==n_var)//下右
							{
								value = value - 999999;
							}
						}
					}
					System.out.print(var+"方，吃子："+(i+1)+","+(char)(j+65));
					System.out.println("价值为："+value+"/"+EAT_AI_NUM);
					if(flag==0||value>=max_value){
						flag=1;
						max_value = value;//记录value最大值
						end_eat.setX1(i);end_eat.setY1(j);
					}
				}
			}
		}
		return end_eat;
	}
	public int CopyWalkMove_eat(int var,int[][] core1,int copy_eat_num,int get_lian) throws ClassNotFoundException, IOException{
		int[][] core = coreCopy(core1);//复制当前棋盘
		int i=0;//步数
		int var1_eat=0;
		int var2_eat=0;
		while(i<Walk_AI_STEP){//无限循环(限制步数，深度)
			int x1,y1,x2,y2;
			List<CanWalkChess> canWalk_list = CanWalkChessList_AI(var,core);//获得得分list
			if(canWalk_list.size()==0) return (var1_eat-var2_eat);
			x1=canWalk_list.get(0).getX1();y1=canWalk_list.get(0).getY1();
			x2=canWalk_list.get(0).getX2();y2=canWalk_list.get(0).getY2();
			if((x1-x2)>1||(x2-x1)>1||(y1-y2)>1||(y2-y1)>1){//跳子
				core[x1][y1]=0;//起点
				core[x2][y2]=var;//终点
				core[(x1+x2)/2][(y1+y2)/2]=0;
			}else{
				core[x1][y1]=0;//起点
				core[x2][y2]=var;//终点
			}
			for(int j=0;j<canWalk_list.get(0).getEat_num();j++){
				CanWalkChess canEatChess = AI_eatChess(var, core);
				if(canEatChess.getX1()==-1)break;
				else{
					core[canEatChess.getX1()][canEatChess.getY1()]=0;
					if(var==1){var1_eat++;}//吃子计数
					if(var==2){var2_eat++;}
				}
			}
			i++;
			if(var==1)var=2;
			else if(var==2)var=1;
			if(i>500){//强行退出
				System.out.println("死循环");
				return (var1_eat-var2_eat);
			}
		}
		return (var1_eat-var2_eat);
	}
	public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {  
	    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);  
	    out.writeObject(src);  
	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
	    ObjectInputStream in = new ObjectInputStream(byteIn);  
	    @SuppressWarnings("unchecked")  
	    List<T> dest = (List<T>) in.readObject();  
	    return dest;  
	}
	public static int [][] coreCopy(int[][] core){
		int [][] copy_core=new int[core.length][];
		for(int i = 0;i < core.length;i++){
			copy_core[i] = core[i].clone();
		}
		return copy_core;
	}
}
