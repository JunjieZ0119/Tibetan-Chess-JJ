package ai;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import board.Core;
public class fallChessAI{
	Core core;
	int AI_SUM = 500; 	//������� ��׼5000
	int gezi_q = 3; 	//���ӵ�Ȩ��
	int qimen_q = 10;   //���ŵ�Ȩ��
	int qimen4_q = 10;	//�����ŵ�Ȩ��
	int qimen2_q = 5;	//�����ŵ�Ȩ��
	int houdu4_q = 10;	//�����ź�ȵ�Ȩ��
	int houdu2_q = 5;	//�����ź�ȵ�Ȩ��
	public int[][] rather_fallChess(int var,List<Integer> fall_list,int[][] core_rather){
		Collections.shuffle(fall_list);
		for(int i=0;i<fall_list.size();i++){
			core_rather[fall_list.get(i)%14][fall_list.get(i)/14] = var;
            if(var==1) var=2;
            	else if(var==2) var=1;
		}
		return core_rather;
	}
	public double evaluate_fallChess(int[][] core,int var){
		int n_var=0;
		if (var==1)n_var=2;
		if (var==2)n_var=1;
		double value=0;
		for(int i=0;i<14;i++){
			for(int j=0;j<14;j++){
				int h_qimen=0;
				int z_qimen=0;
				if(i-1>=0&&core[i][j]==var&&core[i][j]==core[i-1][j]){//��
					value=value+1*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
					z_qimen++;
				}
				if(i+1<=13&&core[i][j]==var&&core[i][j]==core[i+1][j]){//��
					value=value+1*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
					z_qimen++;
				}
				if(j-1>=0&&core[i][j]==var&&core[i][j]==core[i][j-1]){//��
					value=value+1*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
					h_qimen++;
				}
				if(j+1<=13&&core[i][j]==var&&core[i][j]==core[i][j+1]){//��
					value=value+1*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
					h_qimen++;
				}
				int duoge = 0;	//�������Ȩ������
				if(z_qimen>=1&&h_qimen>=1){
					if(i-1>=0&&core[i][j]==var&&core[i][j]==core[i-1][j])//��
						if(j-1>=0&&core[i][j]==var&&core[i][j]==core[i][j-1])//��
							if(core[i][j]==core[i-1][j-1])//����
							{
								value=value+gezi_q*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
								duoge++;
							}
					if(i-1>=0&&core[i][j]==var&&core[i][j]==core[i-1][j])//��
						if(j+1<=13&&core[i][j]==var&&core[i][j]==core[i][j+1])//��
							if(core[i][j]==core[i-1][j+1])//����
							{
								value=value+gezi_q*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
								duoge++;
							}
					if(i+1<=13&&core[i][j]==var&&core[i][j]==core[i+1][j])//��
						if(j-1>=0&&core[i][j]==var&&core[i][j]==core[i][j-1])//��
							if(core[i][j]==core[i+1][j-1])//����
							{
								value=value+gezi_q*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
								duoge++;
							}
					if(i+1<=13&&core[i][j]==var&&core[i][j]==core[i+1][j])//��
						if(j+1<=13&&core[i][j]==var&&core[i][j]==core[i][j+1])//��
							if(core[i][j]==core[i+1][j+1])//����
							{
								value=value+gezi_q*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
								duoge++;
							}
					if(duoge>=2){
						value=value+duoge*gezi_q*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
					}
				}
				int duoqimen = 0;//��¼���Ÿ���
				int huodu = 0;//��¼���
				if(core[i][j]==n_var){//�з���
					if(i-1>=0&&core[i-1][j]==var)//��
						if(j-1>=0&&core[i][j-1]==var)//��
							if(core[i-1][j-1]==var)//����
							{
								value=value+qimen_q*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
								duoqimen++;
								if(i-2>=0&&core[i-2][j]==var)huodu++;//����
								if(j-2>=0&&core[i][j-2]==var)huodu++;//����
								if(i-2>=0&&core[i-2][j-1]==var)huodu++;//����-��
								if(j-2>=0&&core[i-1][j-2]==var)huodu++;//����-��
								//if(j-2>=0&&i-2>=0&&core[i-2][j-2]==var)huodu++;//����-����
							}
					if(i-1>=0&&core[i-1][j]==var)//��
						if(j+1<=13&&core[i][j+1]==var)//��
							if(core[i-1][j+1]==var)//����
							{
								value=value+qimen_q*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
								duoqimen++;
								if(i-2>=0&&core[i-2][j]==var)huodu++;//���ϣ��ظ�
								if(j+2<=13&&core[i][j+2]==var)huodu++;//����
								if(i-2>=0&&core[i-2][j+1]==var)huodu++;//����-��
								if(j+2<=13&&core[i-1][j+2]==var)huodu++;//����-��
							}
					if(i+1<=13&&core[i+1][j]==var)//��
						if(j-1>=0&&core[i][j-1]==var)//��
							if(core[i+1][j-1]==var)//����
							{
								value=value+qimen_q*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
								duoqimen++;
								if(i+2<=13&&core[i+2][j]==var)huodu++;//����
								if(j-2>=0&&core[i][j-2]==var)huodu++;//����
								if(i+2<=13&&core[i+2][j-1]==var)huodu++;//����-��
								if(j-2>=0&&core[i+1][j-2]==var)huodu++;//����-��
							}
					if(i+1<=13&&core[i+1][j]==var)//��
						if(j+1<=13&&core[i][j+1]==var)//��
							if(core[i+1][j+1]==var)//����
							{
								value=value+qimen_q*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
								duoqimen++;
								if(i+2<=13&&core[i+2][j]==var)huodu++;//����,�ظ�
								if(j+2<=13&&core[i][j+2]==var)huodu++;//����
								if(i+2<=13&&core[i+2][j+1]==var)huodu++;//����-��
								if(j+2<=13&&core[i+1][j+2]==var)huodu++;//����-��
							}
					if(duoqimen>=4){
						value=value+duoqimen*qimen4_q*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
						value=value+huodu*houdu4_q*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
					}
					if(duoqimen>=2){
						value=value+duoqimen*qimen2_q*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
						value=value+huodu*houdu2_q*(Math.abs(Math.abs(i-6.5)+Math.abs(j-6.5)-13));
					}

				}
				
			}
		}
		
		return value;
	}
	
	//������������2(�����ߵĸ���*2)(��ȦΪ2����ȦΪ1)()
	public double evaluate_fallChess2(int[][] core,int var){
		double value=0;
		for(int i=0;i<14;i++){
			for(int j=0;j<14;j++){
				if(i-1>=0&&core[i][j]==var&&core[i][j]==core[i-1][j]){//��
					if(i<3||i>10||j<3||j>10){
						value=value+1;
					}else{
						value=value+2;
					}
				}
				if(i+1<=13&&core[i][j]==var&&core[i][j]==core[i+1][j]){//��
					if(i<3||i>10||j<3||j>10){
						value=value+1;
					}else{
						value=value+2;
					}
				}
				if(j-1>=0&&core[i][j]==var&&core[i][j]==core[i][j-1]){//��
					if(i<3||i>10||j<3||j>10){
						value=value+1;
					}else{
						value=value+2;
					}
				}
				if(j+1<=13&&core[i][j]==var&&core[i][j]==core[i][j+1]){//��
					if(i<3||i>10||j<3||j>10){
						value=value+1;
					}else{
						value=value+2;
					}
				}
			}
		}
		return value;
	}
	public int AI_fallChess(int var,List<Integer> fall_list,int[][] core,int step){
		int fall=-1;
		int value=0;
		int n_var=0;//��ȡ�з���ɫ
		if(var==1) n_var=2;
        else if(var==2) n_var=1;
		if(step==0||step==1){//��
			if(core[6][6]==0)fall=90;
			if(core[7][7]==0)fall=105;
			return fall;
		}
		for(int i=0;i<fall_list.size();i++){
			try {
				int test_value=0;
				int copy_core[][] = coreCopy(core);//��������
				List<Integer> fall_list_test;
				fall_list_test = deepCopy(fall_list);//���ƿ�������
				System.out.print("ģ������:"+(fall_list_test.get(i)%14+1)+","+(char)(65+fall_list_test.get(i)/14)+" ");
				int fall_test = fall_list_test.get(i);
				copy_core[fall_list_test.get(i)%14][fall_list_test.get(i)/14]=var;
				fall_list_test.remove(i);
				for(int n=0;n<AI_SUM;n++){
					copy_core = rather_fallChess(n_var, fall_list_test, copy_core);
					double var_value = evaluate_fallChess(copy_core, var);
					double n_var_value = evaluate_fallChess(copy_core, n_var);
					test_value = (int) (test_value + (var_value - n_var_value));
				}
				System.out.println("�õ�ļ�ֵ"+test_value);
				if(fall==-1||test_value>value){
					value = test_value;
					fall = fall_test;
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		return fall;
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
	public static int [][] coreCopy(int[][] core){//����ĸ���
		int [][] copy_core=new int[core.length][];
		for(int i = 0;i < core.length;i++){
			copy_core[i] = core[i].clone();
		}
		return copy_core;
	}
}