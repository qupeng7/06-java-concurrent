package com.qupeng.concurrent.day06.part2;


/**
 * synchronized关键字的底层原理
 * 查看字节码文件中的指令信息锁对象锁的是
 * @author qupeng
 */
public class SynchronizedBottomTest00 {
	
	private static int k=5;
	
	private static Object myObjLock=new Object();

	public static  void main(String[] args) {
		
		synchronized (myObjLock) {
			System.out.println("随便输出一句话");
		}
		
		k=6;
	}

}
