package com.qupeng.concurrent.day06.lookback;

import com.qupeng.concurrent.day05.bean.PulbicPerson;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;


/**
 * 测试原子类的方法的原子性
 * 
 * @author qupeng
 */
public class AtomicFieldUpdaterTest {
	
	
	private   static  AtomicIntegerFieldUpdater<PulbicPerson> c=AtomicIntegerFieldUpdater.newUpdater(PulbicPerson.class, "age");
	
	private   static PulbicPerson p=new PulbicPerson("", 0, "");
	
	private static  void testMethod(){
		//从1加到10000
		for(int i=0;i<10000;i++){
			c.getAndIncrement(p);//p.testNum++;
		}
	}
	

	public static void main(String[] args) throws InterruptedException {
		Thread[] threads=new Thread[5];
		
		//创建5个线程
		for(int i=0;i<5;i++){
			Thread t=new Thread(){
				@Override
				public void run() {
					testMethod();
				}
				
			};
			threads[i]=t;
			t.start();
		}
		
		//等待所有数组中的线程执行结束
		for(Thread thread:threads){
			thread.join();
		}

		System.out.println("多线程并发执行后的p对象testNum的值："+c.get(p));
	}

}
