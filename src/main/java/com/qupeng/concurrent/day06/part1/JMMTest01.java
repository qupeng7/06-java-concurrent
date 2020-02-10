package com.qupeng.concurrent.day06.part1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * JMM：要求遵循
 * happens-before原则：
 * 
 * 注意：这个不是指先于谁发生的意思，
 * 一个操作在时间上先于另一个操作发生，并不意味着一个操作happen-before另一个操作。
 * 
 * 1、单线程情况下，前一个操作总是可以让后续的操作是可见的；（按程序顺序执行）
 * 2、对一个锁的解锁，总是让对一个锁的加锁是可见的；
 * 3、对用volatile的写操作，总是让后续对这个volatile的读操作是可见的；
 * 4、传递性：A happens-before  B，B happens-before  C，那么A happens-before  C：
 * 操作A对于B是可见的，操作B对于C又是可见的，那么操作A对于C也是可见的；
 * 5、对一个线程的start（启动操作），总是让后续的对这个线程的执行操作是可见的：
 * 调用线程的start方法，总是让对于run方法中的执行逻辑是可见的；
 * 6、线程的执行逻辑，总是让调用该线程的join的地方是可见的。
 * @author qupeng
 */
public class JMMTest01 {
	
	private   Lock  lock=new ReentrantLock();

	public static void main(String[] args) {
		

	}
	
	
	public  void happensBeforeTest01(){
		int c=0;//这个操作总是对后续的c=1   c=2是可见的
		c=1;
		c=2;
	}
	
	public  void happensBeforeTest02(){
		lock.lock();
		//业务逻辑
		lock.unlock();//本操作总是对其它线程的对这个lock的加锁是可见的
	}
	
	public  void happensBeforeTest03(){
		lock.lock();
		//业务逻辑
		lock.unlock();
	}
	
	
	public  void happensBeforeTest04(){
		//假如主线程调用本方法，那么
		Thread t = new Thread("子线程"){
			
			@Override
			public void run() {
				//子线程的执行逻辑
			}
		};
		t.start();
	}
	
	public  void happensBeforeTest05() throws InterruptedException{
		//假如主线程调用本方法，那么
		Thread t = new Thread("子线程"){
			
			@Override
			public void run() {
				//子线程的执行逻辑
			}
		};
		t.start();
		
		/*
		 * 这里是指让主线程等待t线程（子线程）执行完毕后再执行的意思
		 * 子线程的执行逻辑总是让调用join方法的地方（主线程）是可见的。
		 */
		t.join();
		
	}
	

}
