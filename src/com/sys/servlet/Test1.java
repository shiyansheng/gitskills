package com.sys.servlet;

import java.util.Scanner;

public class Test1 {

	public static void main(String[] args) {
		int money=0;
		int six=0;
		int choose=0;
		System.out.println("******************��ӭ����ϴ����С����******************");
		do{
			System.out.println("��ѡ����    ��1����������Ǯ����  2:�鿴������   �� 3����66��   ��4��ϴ��������   5:�˳�ϵͳ��");
			Scanner scanner = new Scanner(System.in);
			choose = scanner.nextInt();
			if(1==choose){
				System.out.println("****��ѡ���������ࣺ ��1�����ڡ���  2����������");
				int target = scanner.nextInt();
				if(target==1){
					money+=50000;
					System.out.println("��ѡ�������ڣ���Ǯ����50000�����ڰ����ﹲ��"+money);
				}else if(target==2){
					money+=10000;
					System.out.println("��ѡ��������������Ǯ����10000�����ڰ����ﹲ��"+money);
				}
			}else if(2==choose) {
				System.out.println("���ڰ����ﹲ�н�Ǯ"+money+"Ԫ������"+six+"ƿ66");
			}else if(3==choose){
				if(money>=50000){
					money = money-50000;
					six++;
					System.out.println("������һƿ66�����ڰ����ﹲ�н�Ǯ"+money+"Ԫ������"+six+"ƿ66");
				}else {
					System.out.println("����������ֻ�н�Ǯ"+money+"Ԫ,������һƿ66�ģ���ȥ��������Ǯ��");
				}
			}else if(4==choose){
				if(six>0){
					six--;
					System.out.println("��ϲ�㣬ϴ����һ��JP��Ѫ������");
				}else {
					System.out.println("������66��û�У���ȥ��Ǯ��66��");
				}
			}else if(5==choose){
				System.out.println("�˳��ɹ�����ӭ���ٴ�ʹ��");
			}
			
		}while(choose!=5);
		

	}

}
