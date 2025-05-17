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
public class flyChessAI {
	public Core core;//����
	int Walk_AI_STEP = 16; //����ģ�ⲽ��
	int Walk_AI_ALL = 100000;//��������� 100000
	int EAT_AI_ALL  = 50000;//����ģ������ 50000
	int CoreMax_NUM = 1;//�÷������Ȩ��������ʣ�
	int first_ge = 2;//�����ɸ�Ȩ��
	int first_get_lian = 5;//����Ȩ��
    private int get_four = 10;//��4�÷�(׷��)
    private int get_three = 5;//��3�÷�(׷��)
    private int get_two = 2;//��2�÷�(׷��)
    private int tiao_eat = 4;//���÷�
    private int get_star = 3;//����Ƿ�ɸ�
	public List<CanWalkChess> get_YES_CanWalkChessList(int var,int[][] core){
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
					}
					
				}
			}
		}
		if(max_score==0){
			Collections.shuffle(canMove_list);
		}
		return canMove_list;
	}
	public List<CanWalkChess> CanWalkChessList_YES_AI(int var,int[][] core){
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
	public List<CanWalkChess> get_NO_CanWalkChessList(int var,int[][] core){
        int n_var = -1;
		if(var==1) n_var=2;
    	else if(var==2) n_var=1;
		int max_start_value = 0;
		int max_end_value = 0;
		int start_i=0,start_j=0;
		int num=0;
		List<CanWalkChess> canMove_list = new ArrayList<CanWalkChess>();
		int get_ge = 0;
		for(int i=0;i<14;i++){//�յ�ѡ��
			for(int j=0;j<14;j++){
				CanWalkChess canflyChess = new CanWalkChess();
				int end_value=0;
				int eat_num=0;
				if(core[i][j]==0){//�յ㣬���ǿ���㣬���յ�
					if(i-1>=0&&core[i-1][j]==var)end_value++;
					if(i-1>=0&&core[i-1][j]==n_var)end_value++;
					if(i+1<=13&&core[i+1][j]==var)end_value++;
					if(i+1<=13&&core[i+1][j]==n_var)end_value++;
					if(j-1>=0&&core[i][j-1]==var)end_value++;
					if(j-1>=0&&core[i][j-1]==n_var)end_value++;
					if(j+1<=13&&core[i][j+1]==var)end_value++;
					if(j+1<=13&&core[i][j+1]==n_var)end_value++;
					if(i-1>=0&&j-1>=0&&core[i-1][j-1]==var)end_value++;
					if(i-1>=0&&j-1>=0&&core[i-1][j-1]==n_var)end_value++;
					if(i-1>=0&&j+1<=13&&core[i-1][j+1]==var)end_value++;
					if(i-1>=0&&j+1<=13&&core[i-1][j+1]==n_var)end_value++;
					if(i+1<=13&&j-1>=0&&core[i+1][j-1]==var)end_value++;
					if(i+1<=13&&j-1>=0&&core[i+1][j-1]==n_var)end_value++;
					if(i+1<=13&&j+1<=13&&core[i+1][j+1]==var)end_value++;
					if(i+1<=13&&j+1<=13&&core[i+1][j+1]==n_var)end_value++;
					if(i-1>=0&&core[i-1][j]==var){//��
						if(j-1>=0&&core[i][j-1]==var){//��
							if(core[i-1][j-1]==var)//����
							{
								eat_num++;end_value=end_value+50;
								get_ge=1;
							}
						}
						if(j+1<=13&&core[i][j+1]==var){//��
							if(core[i-1][j+1]==var)//����
							{
								eat_num++;end_value=end_value+50;
								get_ge=1;
							}
						}
					}
					if(i+1<=13&&core[i+1][j]==var){//��
						//get_score = get_score + get_two;
						if(j-1>=0&&core[i][j-1]==var){//��
							if(core[i+1][j-1]==var)//����
							{
								eat_num++;end_value=end_value+50;
								get_ge=1;
							}
						}
						if(j+1<=13&&core[i][j+1]==var){//��
							if(core[i+1][j+1]==var)//����
							{
								eat_num++;end_value=end_value+50;
								get_ge=1;
							}
						}
					}
					canflyChess.setX1(0);
					canflyChess.setY1(0);
					canflyChess.setX2(i);
					canflyChess.setY2(j);
					canflyChess.setEat_num(eat_num);
					canflyChess.setGet_score(end_value);
					if(end_value>=max_end_value){
						max_end_value = end_value;
						canMove_list.add(0,canflyChess);
					}else if(end_value>=1){//��ȥ��մ�ߵĵ�
						canMove_list.add(canflyChess);
					}
				}
			}
		}
		for(int i=0;i<14;i++){//���ѡ��
			for(int j=0;j<14;j++){
				int start_value=0;
				if(core[i][j]==var){//�ҷ�����ǿ��ƶ���
					num++;//��¼��Ϸ�Ƿ����
					if(i-1>=0&&core[i-1][j]==0)start_value++;
					if(i+1<=13&&core[i+1][j]==0)start_value++;
					if(j-1>=0&&core[i][j-1]==0)start_value++;
					if(j+1<=13&&core[i][j+1]==0)start_value++;
					if(i-1>=0&&j-1>=0&&core[i-1][j-1]==0)start_value++;
					if(i-1>=0&&j+1<=13&&core[i-1][j+1]==0)start_value++;
					if(i+1<=13&&j-1>=0&&core[i+1][j-1]==0)start_value++;
					if(i+1<=13&&j+1<=13&&core[i+1][j+1]==0)start_value++;
					if(get_ge==1){//���ɸ�
						if(i-1>=0&&core[i-1][j]==var){//��
							if(j-1>=0&&core[i][j-1]==var){//��
								if(core[i-1][j-1]==var)//����
								{
									start_value=start_value+50;//����
								}
							}
							
							if(j+1<=13&&core[i][j+1]==var){//��
								if(core[i-1][j+1]==var)//����
								{
									start_value=start_value+50;//����
								}
							}
						}
						if(i+1<=13&&core[i+1][j]==var){//��
							if(j-1>=0&&core[i][j-1]==var){//��
								if(core[i+1][j-1]==var)//����
								{
									start_value=start_value+50;//����
								}
							}
							if(j+1<=13&&core[i][j+1]==var){//��
								if(core[i+1][j+1]==var)//����
								{
									start_value=start_value+50;//����
								}
							}
						}
					}
					if(start_value>=max_start_value){//��õ÷��������
						max_start_value = start_value;
						start_i=i; start_j=j;
					}
				}
			}
		}
		for(int i=0;i<canMove_list.size();i++){
			canMove_list.get(i).setX1(start_i);
			canMove_list.get(i).setY1(start_j);
		}
		return canMove_list;
	}
	public List<CanWalkChess> CanWalkChessList_NO_AI(int var,int[][] core){
		List<CanWalkChess> canFly_list = new ArrayList<CanWalkChess>();
		canFly_list = get_NO_CanWalkChessList(var,core);
		if(canFly_list.size()==0)return canFly_list;//�б�Ϊ�գ�ֱ�ӷ���
		if(canFly_list.get(0).getGet_score()==0){
			Collections.shuffle(canFly_list);
		}else{
			int size_list = CoreMax_NUM*canFly_list.size();
			CanWalkChess canWalkChess = canFly_list.get(0);
			for(int i=0;i<size_list;i++){//�������ֵ���ĸ���(��Ȩ)
				canFly_list.add(canWalkChess);
				canFly_list.add(0,canWalkChess);
			}
			Collections.shuffle(canFly_list);
		}
		return canFly_list;
	}
	public int CopyWalkMove(int var,int[][] core1,int copy_eat_num,int get_lian,int var1_yn,int var2_yn) throws ClassNotFoundException, IOException{
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
			List<CanWalkChess> canWalk_list = new ArrayList<CanWalkChess>();
			int x1,y1,x2,y2;
			if(var==1&&var1_yn==0)canWalk_list = CanWalkChessList_YES_AI(var,core);//�������
			if(var==2&&var2_yn==0)canWalk_list = CanWalkChessList_YES_AI(var,core);//�������
			if(var==1&&var1_yn==1)canWalk_list = CanWalkChessList_NO_AI(var,core);//�������
			if(var==2&&var2_yn==1)canWalk_list = CanWalkChessList_NO_AI(var,core);//�������
			if(canWalk_list.size()==0) return (var1_eat-var2_eat);
			x1=canWalk_list.get(0).getX1();y1=canWalk_list.get(0).getY1();
			x2=canWalk_list.get(0).getX2();y2=canWalk_list.get(0).getY2();
			if((x1-x2)>1||(x2-x1)>1||(y1-y2)>1||(y2-y1)>1){//����
				core[x1][y1]=0;//���
				core[x2][y2]=var;//�յ�
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
	public CanWalkChess AI_walkChess(int var,int[][] core,int var1_yn,int var2_yn) throws ClassNotFoundException, IOException{
		CanWalkChess end_walk = new CanWalkChess();//��¼�����ƶ�
		int max_value=0;
		int n_var=0;
		if(var==1)n_var=2;
		if(var==2)n_var=1;
		int flag = -1;
		int[][] canwalk_core = core;//��ȡ��ǰ����
		List<CanWalkChess> canwalk_list = new ArrayList<CanWalkChess>();
		if(var==1&&var1_yn==0)canwalk_list = get_YES_CanWalkChessList(var,core);//�������1111
		if(var==2&&var2_yn==0)canwalk_list = get_YES_CanWalkChessList(var,core);//�������1111
		if(var==1&&var1_yn==1)canwalk_list = get_NO_CanWalkChessList(var,core);//�������11111111
		if(var==2&&var2_yn==1)canwalk_list = get_NO_CanWalkChessList(var,core);//�������11111
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
			}else{
				copy_canwalk_core[x1][y1]=0;//���
				copy_canwalk_core[x2][y2]=var;//�յ�
			}
			CanWalkChess CanWalkChess_gelian = Move_EatNumAndGetScore(copy_canwalk_core, var, x1, y1, x2, y2);
			int copy_eat_num = CanWalkChess_gelian.getEat_num();
			int get_lian = CanWalkChess_gelian.getGet_lian();
			CanWalkChess CanWalkChess_dugelian = Move_EatNumAndGetScore(copy_canwalk_core, n_var, x1, y1, x2, y2);
			int copy_N_eat_num = CanWalkChess_dugelian.getEat_num();
			int get_N_lian = CanWalkChess_dugelian.getGet_lian();
			copy_eat_num = copy_eat_num+copy_N_eat_num;
			get_lian= get_lian+get_N_lian;
			int value=0;
			int Walk_AI_NUM = Walk_AI_ALL/canmove_chess;
			for(int ai_num=0;ai_num<Walk_AI_NUM;ai_num++){
				int var_eatvalue = CopyWalkMove(n_var, copy_canwalk_core,copy_eat_num,get_lian,var1_yn,var2_yn);//ģ�����̵ĺ���111111111
				if(var==1){
					if(var_eatvalue>0)value++;
					if(value*1.0/Walk_AI_NUM>0.95){//��ֹ��ʤ���޽����
						if(copy_eat_num>0){value=value+first_ge*15;}
						if(get_lian==1){value=value+first_get_lian*30;}
					}
				}else{
					if(var_eatvalue<0)value++;
					
					if(value*1.0/Walk_AI_NUM>0.95){//��ֹ��ʤ���޽����
						if(copy_eat_num>0){value=value+first_ge*15;}
						if(get_lian==1){value=value+first_get_lian*30;}
					}
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
		if(max_value<=10){//������������
			if(get_NO_CanWalkChessList(var,core).size()>0){
				end_walk = get_NO_CanWalkChessList(var,core).get(0);
				System.out.println("������������");
				System.out.print(var+"�����ƶ���"+(end_walk.getX1()+1)+","+(char)(end_walk.getY1()+65)+"--��--"+(end_walk.getX2()+1)+","+(char)(end_walk.getY2()+65)+" ");
			}
		}
		long endtime=System.currentTimeMillis();//��ʱ����
		long costTime = (endtime - begintime);
		System.out.println("������ʱ��"+costTime/1000+"s");
		return end_walk;
	}
	public CanWalkChess AI_eatChess(int var,int[][] core){
		CanWalkChess end_eat = new CanWalkChess();//��¼���ճ���
        int n_var = -1;//��ȡ�з���ɫ
		if(var==1) n_var=2;
    	else if(var==2) n_var=1;
		int max_score=0;
		List<CanWalkChess> caneat_list = new ArrayList<CanWalkChess>();
		for(int i=0;i<14;i++){
			for(int j=0;j<14;j++){
				if(core[i][j]==n_var){//Ϊ�з��㣬����ɳ�~~
					int eat_score=0;//�÷�
					end_eat.setX1(i);end_eat.setY1(j);//�ȴ��������
					if(i-1>=0&&core[i-1][j]==var){//��
						if(j-1>=0&&core[i][j-1]==var){//��
							if(core[i-1][j-1]==var)//����
							{
								eat_score = eat_score + get_four;
							}
						}
						if(j+1<=13&&core[i][j+1]==var){//��
							if(core[i-1][j+1]==var)//����
							{
								eat_score = eat_score + get_four;
							}
						}
					}
					if(i+1<=13&&core[i+1][j]==var){//��
						if(j-1>=0&&core[i][j-1]==var){//��
							if(core[i+1][j-1]==var)//����
							{
								eat_score = eat_score + get_four;
							}
						}
						if(j+1<=13&&core[i][j+1]==var){//��
							if(core[i+1][j+1]==var)//����
							{
								eat_score = eat_score + get_four;
							}
						}
					}
					if(i-1>=0&&core[i-1][j]==n_var){//��
						if(j-1>=0&&core[i][j-1]==n_var){//��
							eat_score = eat_score + get_three;
							if(core[i-1][j-1]==n_var)//����
							{
								eat_score = eat_score - get_four;
							}
						}
						if(j+1<=13&&core[i][j+1]==n_var){//��
							if(core[i-1][j+1]==n_var)//����
							eat_score = eat_score + get_three;
							{
								eat_score = eat_score - get_four;
							}
						}
					}
					if(i+1<=13&&core[i+1][j]==n_var){//��
						if(j-1>=0&&core[i][j-1]==n_var){//��
							eat_score = eat_score + get_three;
							if(core[i+1][j-1]==n_var)//����
							{
								eat_score = eat_score - get_four;
							}
						}
						if(j+1<=13&&core[i][j+1]==n_var){//��
							if(core[i+1][j+1]==n_var)//����
							eat_score = eat_score + get_three;
							{
								eat_score = eat_score - get_four;
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
	public CanWalkChess AITrue_eatChess(int var,int[][] core,int var1_yn,int var2_yn) throws ClassNotFoundException, IOException{
		CanWalkChess end_eat = new CanWalkChess();//��¼���ճ���
        int n_var = -1;//��ȡ�з���ɫ
		if(var==1) n_var=2;
    	else if(var==2) n_var=1;
		int max_num=0;
		for(int i1=0;i1<14;i1++){
			for(int j1=0;j1<14;j1++){
				if(core[i1][j1]==n_var)max_num++;
			}
		}
		int EAT_AI_NUM = EAT_AI_ALL/max_num;
		
		if(max_num==0){
			end_eat.setX1(-1);//����������----���ӿɳ�------------------------------------------------------------
			return end_eat;
		}
		int max_value=0;
		for(int i=0;i<14;i++){
			for(int j=0;j<14;j++){
				if(core[i][j]==n_var){//ѭ����Ϊ�ɳԵ���
					int[][] copy_caneat_core = coreCopy(core);//���Ƶ�ǰ����
					copy_caneat_core[i][j]=0;
					int value=0;//��¼��ֵ
					for(int ai_num=0;ai_num<EAT_AI_NUM;ai_num++){
						int var_eatvalue = CopyWalkMove(n_var, copy_caneat_core,0,0,var1_yn,var2_yn);//ģ�����̵ĺ���
						if(var==1){
							if(var_eatvalue>0)value++;
						}else{
							if(var_eatvalue<0)value++;
						}
					}
					System.out.print(var+"�������ӣ�"+(i+1)+","+(char)(j+65));
					System.out.println("��ֵΪ��"+value+"/"+EAT_AI_NUM);
					if(value>=max_value){
						max_value = value;
						end_eat.setX1(i);end_eat.setY1(j);
					}
				}
			}
		}
		return end_eat;
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