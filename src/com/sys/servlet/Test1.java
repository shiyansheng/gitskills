package com.sys.servlet;

import java.util.Scanner;

public class Test1 {

	public static void main(String[] args) {
		int money=0;
		int six=0;
		int choose=0;
		System.out.println("******************欢迎进入洗宝宝小程序******************");
		do{
			System.out.println("请选择功能    【1：做任务挣钱】【  2:查看包袱】   【 3：买66】   【4：洗宝宝】【   5:退出系统】");
			Scanner scanner = new Scanner(System.in);
			choose = scanner.nextInt();
			if(1==choose){
				System.out.println("****请选择任务种类： 【1：跑镖】【  2：卖体力】");
				int target = scanner.nextInt();
				if(target==1){
					money+=50000;
					System.out.println("你选择了跑镖，金钱增加50000，现在包袱里共有"+money);
				}else if(target==2){
					money+=10000;
					System.out.println("你选择了卖体力，金钱增加10000，现在包袱里共有"+money);
				}
			}else if(2==choose) {
				System.out.println("现在包袱里共有金钱"+money+"元，还有"+six+"瓶66");
			}else if(3==choose){
				if(money>=50000){
					money = money-50000;
					six++;
					System.out.println("你买了一瓶66，现在包袱里共有金钱"+money+"元，还有"+six+"瓶66");
				}else {
					System.out.println("包袱里现在只有金钱"+money+"元,不够买一瓶66的，快去做任务挣钱吧");
				}
			}else if(4==choose){
				if(six>0){
					six--;
					System.out.println("恭喜你，洗出来一个JP吸血鬼！！！");
				}else {
					System.out.println("你连个66都没有，快去挣钱买66吧");
				}
			}else if(5==choose){
				System.out.println("退出成功，欢迎您再次使用");
			}
			
		}while(choose!=5);
		

	}

}
