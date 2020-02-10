package com.qupeng.concurrent.day06.part1;

/**
 * JMM：重排序：
 * 数据依赖性：
 * 如果两个操作访问同一个变量，且这两个操作中有一个是
 * 写操作，此时这两个操作之间就存在数据依赖性。
 * 数据依赖性分为三种类型：
 * 1、写后读，例如：a=1,b=a;写一个变量后再读这个变量。
 * 2、写后写，例如：a=1,a=2;写一个变量后再写这个变量。
 * 3、读后写，例如：b=a,a=1;读一个变量后再写这个变量。
 * 
 * 出现上面任意一种情况，编译器和处理器都不会对这两个操作进行重排序，
 * 因为对存在数据依赖关系的操作进行重排序会导致执行结果被改变。
 * 
 * @author qupeng
 */
public class JMMTest02 {
	

	public static void main(String[] args) {

	}

	
	public static void test() {
		/*
		 * (1)和(2)没有依赖性，就是没有关系，所以可能
		 * 发生重排序后出现(2)先执行，(1)后执行的情况
		 * 
		 * (1)和(3)或者(2)和(3)，(3)对(1)和(2)有依赖性，
		 * 所以(3)不会和(1)或(2)发生重排序
		 */
		int e=1;//(1)
		int f=2;//(2)
		int g=e/f;//(3)
	}
	
	
	private int h=3;
	
	private  int i=4;
	
	public   void getMethod(){
		i=5;   //2
		h=4;  //1
	}
	
	public   void setMethod(){
		if(h==4){ //3
			int j=h+i; //4   j=h(4)+i(4)=8 发生重排序后： j=h(4)+i(5)=9
		}
	}

}
