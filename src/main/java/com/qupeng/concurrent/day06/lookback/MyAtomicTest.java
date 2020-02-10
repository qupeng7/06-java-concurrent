package com.qupeng.concurrent.day06.lookback;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import com.powernode.concurrent.day05.bean.MyAtomicReference;

/**
 * 测试自己写的原子类
 * 
 * 让一个线程去调用原子类的API的内部进行休眠，
 * 然后让另外的线程也去调用原子类的同一个API，
 * 然后另外的线程不休眠，然后看一下最后结果。
 * @author qupeng
 */
public class MyAtomicTest {
	
	
	private   static  MyAtomicReference<Integer> c=new MyAtomicReference<Integer>(1);
	

	public static void main(String[] args) {
		
		new Thread("线程1："){
			@Override
			public void run() {
				/*
				 * 这个原子性的功能的定义是保证当前更新操作的原子性
				 * 在本例中指的是我要把最新值更新为10的这个结果是具有
				 * 原子性的，不可被其它线程的打断而影响到本线程更新操作的
				 * 最终的更新结果的意思，不是指我在执行的过程中不能被打断的意思
				 * 
				 * 通俗来说就是我不关心过程，我只关心结果。
				 */
				c.getAndUpdate(10);
			}
		}.start();
		
		new Thread("线程2："){
			@Override
			public void run() {
				c.getAndUpdate(11);
			}
		}.start();
		
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
		System.out.println("c的值为："+c.get());
		System.out.println("主线程执行完毕！");
	}

}
