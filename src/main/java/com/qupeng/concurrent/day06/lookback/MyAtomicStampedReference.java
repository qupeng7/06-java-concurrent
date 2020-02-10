package com.qupeng.concurrent.day06.lookback;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 自己手写一个原子的不存在ABA问题的引用类
 * @author qupeng
 */
public class MyAtomicStampedReference<Va> {

	private AtomicStampedReference<Va> atmStaRef = null;

	public MyAtomicStampedReference(Va initialRef, int initialStamp) {
		atmStaRef = new AtomicStampedReference<Va>(initialRef, initialStamp);
	}
	
	public  Va   get(){
		return  atmStaRef.getReference();
	}
	

	public void getAndUpdate(Va newReference,int newStamp) {
		for(;;){
			//读取到最新的引用
			Va currentReference = atmStaRef.getReference();
			//读取到最新的戳
			int currentStamp=atmStaRef.getStamp();
			//执行CAS算法
			if (atmStaRef.compareAndSet(currentReference, newReference, currentStamp, newStamp)){
				return;
			}
		}
	}

}
