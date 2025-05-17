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
	public Core core;//����
	int Walk_AI_STEP = 20; //����ģ�ⲽ�� 20
	int Walk_AI_ALL = 4000;//��������� 50000
	int EAT_AI_ALL  = 5000;//����ģ������ 50000	
	int CoreMax_NUM = 1;//�÷������Ȩ��������ʣ�	
	int first_ge = 2;//�����ɸ�Ȩ��
	int first_get_lian = 5;//����Ȩ��	
    private int get_four = 10;//��4�÷�(׷��)
    private int get_three = 5;//��3�÷�(׷��)
    private int get_two = 2;//��2�÷�(׷��)
    private int tiao_eat = 4;//���÷�
    private int get_star = 3;//����Ƿ�ɸ�    
	public List<CanWalkChess> get_CanWalkChessList(int[][] core,int var){
        int n_var = -1;
		if(var==1) n_var=2;
    	else if(var==2) n_var=1;
		int x1,y1,x2,y2;		
		int max_score = 0;		
		List<CanWalkChess> canMove_list = new ArrayList<CanWalkChess>();
		for(int i=0;i<14;i++){
			for(int j=0;j<14;j++){
				if(core[i][j]==0){//�յ�
					if(i-1>=0){//�ϴ���
						if(core[i-1][j]==var){//��Ϊ�ҷ�����							
							x1=i-1;y1=j;//���
							x2=i;y2=j;//�յ�
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
							}else{
								canMove_list.add(canWalkChess);//��ͨ����
							}
						}
						if(core[i-1][j]==n_var){//�з�
							if(i-2>=0&&core[i-2][j]==var){//�ҷ�����
								x1=i-2;y1=j;//���
								x2=i;y2=j;//�յ�
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
								}else{
									canMove_list.add(canWalkChess);//��ͨ����
								}
							}
						}
					}
					if(i+1<=13){//��
						if(core[i+1][j]==var){//�ҷ�����
							x1=i+1;y1=j;//���
							x2=i;y2=j;//�յ�
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
							}else{
								canMove_list.add(canWalkChess);//��ͨ����
							}
						}
						if(core[i+1][j]==n_var){//�з�
							if(i+2<=13&&core[i+2][j]==var){//�ҷ�����
								x1=i+2;y1=j;//���
								x2=i;y2=j;//�յ�
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
								}else{
									canMove_list.add(canWalkChess);//��ͨ����
								}
							}
						}
					}
					if(j-1>=0){//��
						if(core[i][j-1]==var){//�ҷ�����
							x1=i;y1=j-1;//���
							x2=i;y2=j;//�յ�
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
							}else{
								canMove_list.add(canWalkChess);//��ͨ����
							}
						}
						if(core[i][j-1]==n_var){//�з�
							if(j-2>=0&&core[i][j-2]==var){//�ҷ�����
								x1=i;y1=j-2;//���
								x2=i;y2=j;//�յ�
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
								}else{
									canMove_list.add(canWalkChess);//��ͨ����
								}
							}
						}
					}
					if(j+1<=13){//��
						if(core[i][j+1]==var){//�ҷ�����
							x1=i;y1=j+1;//���
							x2=i;y2=j;//�յ�
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
							}else{
								canMove_list.add(canWalkChess);//��ͨ����
							}
						}
						if(core[i][j+1]==n_var){//�з�
							if(j+2<=13&&core[i][j+2]==var){//�ҷ�����
								x1=i;y1=j+2;//���
								x2=i;y2=j;//�յ�
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
								}else{
									canMove_list.add(canWalkChess);//��ͨ����
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
	//2������������AI(�˴�����ʽ����AI)
	public List<CanWalkChess> CanWalkChessList_AI(int var,int[][] core){
        int n_var = -1;
		if(var==1) n_var=2;
    	else if(var==2) n_var=1;
		int x1,y1,x2,y2;
		int max_score = 0;		
		List<CanWalkChess> canMove_list = new ArrayList<CanWalkChess>();
		for(int i=0;i<14;i++){
			for(int j=0;j<14;j++){
				if(core[i][j]==0){//�յ�
					if(i-1>=0){//�ϴ���
						if(core[i-1][j]==var){//��Ϊ�ҷ�����							
							x1=i-1;y1=j;//���
							x2=i;y2=j;//�յ�
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);//����÷�
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
							}else{
								canMove_list.add(canWalkChess);//��ͨ����
							}
						}
						if(core[i-1][j]==n_var){//�з�
							if(i-2>=0&&core[i-2][j]==var){//�ҷ�����
								x1=i-2;y1=j;//���
								x2=i;y2=j;//�յ�
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
								}else{
									canMove_list.add(canWalkChess);//��ͨ����
								}
							}
						}
					}
					
					if(i+1<=13){//��
						if(core[i+1][j]==var){//�ҷ�����
							x1=i+1;y1=j;//���
							x2=i;y2=j;//�յ�
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
							}else{
								canMove_list.add(canWalkChess);//��ͨ����
							}
						}
						if(core[i+1][j]==n_var){//�з�
							if(i+2<=13&&core[i+2][j]==var){//�ҷ�����
								x1=i+2;y1=j;//���
								x2=i;y2=j;//�յ�
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
								}else{
									canMove_list.add(canWalkChess);//��ͨ����
								}
							}
						}
					}
					if(j-1>=0){//��
						if(core[i][j-1]==var){//�ҷ�����
							x1=i;y1=j-1;//���
							x2=i;y2=j;//�յ�
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
							}else{
								canMove_list.add(canWalkChess);//��ͨ����
							}
						}
						if(core[i][j-1]==n_var){//�з�
							if(j-2>=0&&core[i][j-2]==var){//�ҷ�����
								x1=i;y1=j-2;//���
								x2=i;y2=j;//�յ�
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
								}else{
									canMove_list.add(canWalkChess);//��ͨ����
								}
							}
						}
					}
					if(j+1<=13){//��
						if(core[i][j+1]==var){//�ҷ�����
							x1=i;y1=j+1;//���
							x2=i;y2=j;//�յ�
							CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
							if(canWalkChess.getGet_score()>max_score){
								max_score = canWalkChess.getGet_score();
								canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
							}else{
								canMove_list.add(canWalkChess);//��ͨ����
							}
						}
						if(core[i][j+1]==n_var){//�з�
							if(j+2<=13&&core[i][j+2]==var){//�ҷ�����
								x1=i;y1=j+2;//���
								x2=i;y2=j;//�յ�
								CanWalkChess canWalkChess = Move_EatNumAndGetScore(core, var, x1, y1, x2, y2);
								if(canWalkChess.getGet_score()>max_score){
									max_score = canWalkChess.getGet_score();
									canMove_list.add(0,canWalkChess);//���ֵ�ŵ���һ��λ��
								}else{
									canMove_list.add(canWalkChess);//��ͨ����
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
			//System.out.println("��Ӹ�����"+size_list);
			CanWalkChess canWalkChess = canMove_list.get(0);
			for(int i=0;i<size_list;i++){//�������ֵ���ĸ���(��Ȩ)
				canMove_list.add(canWalkChess);
				canMove_list.add(0,canWalkChess);
			}
			Collections.shuffle(canMove_list);
		}
		return canMove_list;
	}	
	public CanWalkChess AI_eatChess(int var,int[][] core){
		CanWalkChess end_eat = new CanWalkChess();//��¼���ճ���
        int n_var = -1;//��ȡ�з���ɫ
		if(var==1) n_var=2;
    	else if(var==2) n_var=1;
		int max_score=0;
		//��¼�ɳ���
		List<CanWalkChess> caneat_list = new ArrayList<CanWalkChess>();
		for(int i=0;i<14;i++){
			for(int j=0;j<14;j++){
				if(core[i][j]==n_var){//Ϊ�з��㣬����ɳ�~~
					//����÷֣�����List����
					int eat_score=0;//�÷�
					end_eat.setX1(i);end_eat.setY1(j);//�ȴ��������
					//�ҷ�����
					if(i-1>=0&&core[i-1][j]==var){//��
						eat_score = eat_score + get_two;
						if(j-1>=0&&core[i][j-1]==var){//��
							eat_score = eat_score + get_three;
							if(core[i-1][j-1]==var)//����
							{
								eat_score = eat_score + get_four;
							}
						}
						if(j+1<=13&&core[i][j+1]==var){//��
							eat_score = eat_score + get_three;
							if(core[i-1][j+1]==var)//����
							{
								eat_score = eat_score + get_four;
							}
						}
					}
					if(i+1<=13&&core[i+1][j]==var){//��
						eat_score = eat_score + get_two;
						if(j-1>=0&&core[i][j-1]==var){//��
							eat_score = eat_score + get_three;
							if(core[i+1][j-1]==var)//����
							{
								eat_score = eat_score + get_four;
							}
						}
						if(j+1<=13&&core[i][j+1]==var){//��
							eat_score = eat_score + get_three;
							if(core[i+1][j+1]==var)//����
							{
								eat_score = eat_score + get_four;
							}
						}
					}
					if(j-1>=0&&core[i][j-1]==var)eat_score = eat_score + get_two;
					if(j+1<=13&&core[i][j+1]==var)eat_score = eat_score + get_two;
					if(i-1>=0&&core[i-1][j]==n_var){//��
						if(j-1>=0&&core[i][j-1]==n_var){//��
							if(core[i-1][j-1]==n_var)//����
							{
								eat_score = eat_score - get_four*100;
							}
						}
						if(j+1<=13&&core[i][j+1]==n_var){//��
							if(core[i-1][j+1]==n_var)//����
							{
								eat_score = eat_score - get_four*100;
							}
						}
					}
					if(i+1<=13&&core[i+1][j]==n_var){//��
						if(j-1>=0&&core[i][j-1]==n_var){//��
							if(core[i+1][j-1]==n_var)//����
							{
								eat_score = eat_score - get_four*100;
							}
						}
						if(j+1<=13&&core[i][j+1]==n_var){//��
							if(core[i+1][j+1]==n_var)//����
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
			end_eat.setX1(-1);//����������----------------------------------------------------------------
			return end_eat;
		}
		end_eat = caneat_list.get(0);
		return end_eat;
	}
	public CanWalkChess Move_EatNumAndGetScore(int[][] core1,int var,int x1,int y1,int x2,int y2){
		CanWalkChess canWalkChess = new CanWalkChess();
		int eat_num=0;//����
		int get_score=0;//�÷�
		int core[][] = coreCopy(core1);//��������
		int i=x1;
		int j=y1;
		int get_lian=0;
		canWalkChess.setGet_lian(0);//������֤������Ϊ0������Ϊ1
		if(i-1>=0&&core[i-1][j]==var){//��
			//get_score = get_score + get_two;
			if(j-1>=0&&core[i][j-1]==var){//��
				if(core[i-1][j-1]==var)//����
				{
					get_score = get_score + get_star;//����Ƿ�ɸ�
					get_lian=1;
				}
			}
			if(j+1<=13&&core[i][j+1]==var){//��
				if(core[i-1][j+1]==var)//����
				{
					get_score = get_score + get_star;//����Ƿ�ɸ�
					get_lian=1;
				}
			}
		}
		if(i+1<=13&&core[i+1][j]==var){//��
			if(j-1>=0&&core[i][j-1]==var){//��
				if(core[i+1][j-1]==var)//����
				{
					get_score = get_score + get_star;//����Ƿ�ɸ�
					get_lian=1;
				}
			}
			if(j+1<=13&&core[i][j+1]==var){//��
				if(core[i+1][j+1]==var)//����
				{
					get_score = get_score + get_star;//����Ƿ�ɸ�
					get_lian=1;
				}
			}
		}
		if((x1-x2)>1||(x2-x1)>1||(y1-y2)>1||(y2-y1)>1){//����
			core[x1][y1]=0;//ģ���ƶ�
			core[(x1+x2)/2][(y1+y2)/2]=0;//ģ������
			core[x2][y2]=var;//ģ�����
		}else{
			core[x1][y1]=0;//ģ���ƶ�
			core[x2][y2]=var;//ģ�����
		}
		i=x2;
		j=y2;
		if(i-1>=0&&core[i-1][j]==var){//��
			//get_score = get_score + get_two;
			if(j-1>=0&&core[i][j-1]==var){//��
				get_score = get_score + get_three;
				if(core[i-1][j-1]==var)//����
				{
					eat_num++;
					get_score = get_score + get_four;
					if(get_lian==1)canWalkChess.setGet_lian(1);
				}
			}
			if(j+1<=13&&core[i][j+1]==var){//��
				get_score = get_score + get_three;
				if(core[i-1][j+1]==var)//����
				{
					eat_num++;
					get_score = get_score + get_four;
					if(get_lian==1)canWalkChess.setGet_lian(1);
				}
			}
		}
		if(i+1<=13&&core[i+1][j]==var){//��
			//get_score = get_score + get_two;
			if(j-1>=0&&core[i][j-1]==var){//��
				get_score = get_score + get_three;
				if(core[i+1][j-1]==var)//����
				{
					eat_num++;
					get_score = get_score + get_four;
					if(get_lian==1)canWalkChess.setGet_lian(1);
				}
			}
			if(j+1<=13&&core[i][j+1]==var){//��
				get_score = get_score + get_three;
				if(core[i+1][j+1]==var)//����
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
		int[][] core = coreCopy(core1);//���Ƶ�ǰ����
		int i=0;//����
		int var1_eat=0;
		int var2_eat=0;
		if(var==1){//ע�⣬�˴��������ĳ��ӣ��Ƿ��ģ�����
			var2_eat=var2_eat+copy_eat_num*first_ge;
		}else{
			var1_eat=var1_eat+copy_eat_num*first_ge;
		}
		if(get_lian==1){//�������
			//���������Ȩ
			if(var==1){//ע�⣬�˴��Ƿ��ģ�����
				var2_eat=var2_eat+1*first_get_lian;
			}else{
				var1_eat=var1_eat+1*first_get_lian;
			}
		}	
		while(i<Walk_AI_STEP){//����ѭ��(���Ʋ��������)			
			int x1,y1,x2,y2;
			//��ȡ��ǰ��ɫ�ƶ���
			List<CanWalkChess> canWalk_list = CanWalkChessList_AI(var,core);//��õ÷�list
			//ʤ���ж�
			if(canWalk_list.size()==0) return (var1_eat-var2_eat);
			//���ȡ��(�������Ѿ�����)
			x1=canWalk_list.get(0).getX1();y1=canWalk_list.get(0).getY1();
			x2=canWalk_list.get(0).getX2();y2=canWalk_list.get(0).getY2();
			//���
			if((x1-x2)>1||(x2-x1)>1||(y1-y2)>1||(y2-y1)>1){//����
				core[x1][y1]=0;//���
				core[x2][y2]=var;//�յ�
				core[(x1+x2)/2][(y1+y2)/2]=0;//�м��				
			}else{
				core[x1][y1]=0;//���
				core[x2][y2]=var;//�յ�
			}
			for(int j=0;j<canWalk_list.get(0).getEat_num();j++){
				CanWalkChess canEatChess = AI_eatChess(var, core);
				if(canEatChess.getX1()==-1)break;
				else{
					core[canEatChess.getX1()][canEatChess.getY1()]=0;
					if(var==1){var1_eat++;}//���Ӽ���
					if(var==2){var2_eat++;}
				}
			}
			i++;
			if(var==1)var=2;
			else if(var==2)var=1;
			if(i>500){//ǿ���˳�
				System.out.println("��ѭ��");
				return (var1_eat-var2_eat);
			}
		}
		return (var1_eat-var2_eat);
	}
	public CanWalkChess AI_walkChess(int var,int[][] core) throws ClassNotFoundException, IOException{
		CanWalkChess end_walk = new CanWalkChess();//��¼�����ƶ�
		int max_value=0;
		int n_var=0;
		if(var==1)n_var=2;
		if(var==2)n_var=1;
		int flag = -1;
		//1��׼�����̲�����
		int[][] canwalk_core = core;//��ȡ��ǰ����
		List<CanWalkChess> canwalk_list = get_CanWalkChessList(core, var);//��ȡ��ǰ����������
		long begintime = System.currentTimeMillis();//��ʱ��ʼ
		System.out.println(var+"���Ŀ��ƶ������Ϊ:"+canwalk_list.size());
		int canmove_chess = canwalk_list.size();
		for(int i=0;i<canwalk_list.size();i++){//������������
			int[][] copy_canwalk_core = coreCopy(canwalk_core);//���Ƶ�ǰ����
			CanWalkChess copy_end_walk = canwalk_list.get(i);//��ȡ��ǰ���
			int x1=copy_end_walk.getX1();
			int x2=copy_end_walk.getX2();
			int y1=copy_end_walk.getY1();
			int y2=copy_end_walk.getY2();
			if((x1-x2)>1||(x2-x1)>1||(y1-y2)>1||(y2-y1)>1){//����
				copy_canwalk_core[x1][y1]=0;//���
				copy_canwalk_core[x2][y2]=var;//�յ�
				copy_canwalk_core[(x1+x2)/2][(y1+y2)/2]=0;//�м��
			}else{
				copy_canwalk_core[x1][y1]=0;//���
				copy_canwalk_core[x2][y2]=var;//�յ�
			}
			CanWalkChess CanWalkChess_gelian = Move_EatNumAndGetScore(copy_canwalk_core, var, x1, y1, x2, y2);
			int copy_eat_num = CanWalkChess_gelian.getEat_num();
			int get_lian = CanWalkChess_gelian.getGet_lian();
			//ģ���¶��̼���value
			int value=0;
			int Walk_AI_NUM = Walk_AI_ALL/canmove_chess;
			for(int ai_num=0;ai_num<Walk_AI_NUM;ai_num++){
				int var_eatvalue = CopyWalkMove(n_var, copy_canwalk_core,copy_eat_num,get_lian);//ģ�����̵ĺ���111111111
				if(var==1){
					value=value+var_eatvalue;
				}else{
					value=value+(-var_eatvalue);
				}
			}
			if(flag==-1){flag=1;max_value = value;end_walk = canwalk_list.get(i);}//��һ��
			if(value>=max_value){
				max_value = value;
				end_walk = canwalk_list.get(i);
			}
			System.out.print(var+"�����ƶ���"+(canwalk_list.get(i).getX1()+1)+","+(char)(canwalk_list.get(i).getY1()+65)+"--��--"+(canwalk_list.get(i).getX2()+1)+","+(char)(canwalk_list.get(i).getY2()+65)+" ");
			System.out.println("��ֵΪ��"+value+"/"+Walk_AI_NUM);
		}

		long endtime=System.currentTimeMillis();//��ʱ����
		long costTime = (endtime - begintime);
		System.out.println("������ʱ��"+costTime/1000+"s");
		return end_walk;
	}
	
	//�����ĳ���AI
	public CanWalkChess AITrue_eatChess(int var,int[][] core) throws ClassNotFoundException, IOException{
		CanWalkChess end_eat = new CanWalkChess();//��¼���ճ���
        int n_var = -1;//��ȡ�з���ɫ
		if(var==1) n_var=2;
    	else if(var==2) n_var=1;
		int max_num=0;
		for(int i1=0;i1<14;i1++)for(int j1=0;j1<14;j1++)if(core[i1][j1]==n_var)max_num++;
		int EAT_AI_NUM = EAT_AI_ALL/max_num;		
		if(max_num==0){
			end_eat.setX1(-1);//����������----���ӿɳ�------------------------------------------------------------
			return end_eat;
		}		
		int max_value=0;
		int flag=0;
		for(int i=0;i<14;i++){
			for(int j=0;j<14;j++){
				if(core[i][j]==n_var){//ѭ����Ϊ�ɳԵ���
					int[][] copy_caneat_core = coreCopy(core);//���Ƶ�ǰ����
					copy_caneat_core[i][j]=0;
					int value=0;//��¼��ֵ
					for(int ai_num=0;ai_num<EAT_AI_NUM;ai_num++){
						int var_eatvalue = CopyWalkMove_eat(n_var, copy_caneat_core,0,0);//ģ�����̵ĺ���11111111
						if(var==1){
							value=value+var_eatvalue;
						}else{
							value=value+(-var_eatvalue);
						}
					}
					if(i-1>=0&&core[i-1][j]==n_var){//��
						if(j-1>=0&&core[i][j-1]==n_var){//��
							if(core[i-1][j-1]==n_var)//����
							{
								value = value - 999999;
							}
						}
						if(j+1<=13&&core[i][j+1]==n_var){//��
							if(core[i-1][j+1]==n_var)//����
							{
								value = value - 999999;
							}
						}
					}
					if(i+1<=13&&core[i+1][j]==n_var){//��
						if(j-1>=0&&core[i][j-1]==n_var){//��
							if(core[i+1][j-1]==n_var)//����
							{
								value = value - 999999;
							}
						}
						if(j+1<=13&&core[i][j+1]==n_var){//��
							if(core[i+1][j+1]==n_var)//����
							{
								value = value - 999999;
							}
						}
					}
					System.out.print(var+"�������ӣ�"+(i+1)+","+(char)(j+65));
					System.out.println("��ֵΪ��"+value+"/"+EAT_AI_NUM);
					if(flag==0||value>=max_value){
						flag=1;
						max_value = value;//��¼value���ֵ
						end_eat.setX1(i);end_eat.setY1(j);
					}
				}
			}
		}
		return end_eat;
	}
	public int CopyWalkMove_eat(int var,int[][] core1,int copy_eat_num,int get_lian) throws ClassNotFoundException, IOException{
		int[][] core = coreCopy(core1);//���Ƶ�ǰ����
		int i=0;//����
		int var1_eat=0;
		int var2_eat=0;
		while(i<Walk_AI_STEP){//����ѭ��(���Ʋ��������)
			int x1,y1,x2,y2;
			List<CanWalkChess> canWalk_list = CanWalkChessList_AI(var,core);//��õ÷�list
			if(canWalk_list.size()==0) return (var1_eat-var2_eat);
			x1=canWalk_list.get(0).getX1();y1=canWalk_list.get(0).getY1();
			x2=canWalk_list.get(0).getX2();y2=canWalk_list.get(0).getY2();
			if((x1-x2)>1||(x2-x1)>1||(y1-y2)>1||(y2-y1)>1){//����
				core[x1][y1]=0;//���
				core[x2][y2]=var;//�յ�
				core[(x1+x2)/2][(y1+y2)/2]=0;
			}else{
				core[x1][y1]=0;//���
				core[x2][y2]=var;//�յ�
			}
			for(int j=0;j<canWalk_list.get(0).getEat_num();j++){
				CanWalkChess canEatChess = AI_eatChess(var, core);
				if(canEatChess.getX1()==-1)break;
				else{
					core[canEatChess.getX1()][canEatChess.getY1()]=0;
					if(var==1){var1_eat++;}//���Ӽ���
					if(var==2){var2_eat++;}
				}
			}
			i++;
			if(var==1)var=2;
			else if(var==2)var=1;
			if(i>500){//ǿ���˳�
				System.out.println("��ѭ��");
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
