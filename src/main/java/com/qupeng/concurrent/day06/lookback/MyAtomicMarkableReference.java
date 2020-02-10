package com.qupeng.concurrent.day06.lookback;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * 自己手写一个原子的不存在ABA问题的引用类
 * @author qupeng
 */
public class MyAtomicMarkableReference<Va> {

	private AtomicMarkableReference<Va> atmMakRef = null;

	public MyAtomicMarkableReference(Va initialRef, boolean initialMark) {
		atmMakRef = new AtomicMarkableReference<Va>(initialRef, initialMark);
	}
	
	public  Va   get(){
		return  atmMakRef.getReference();
	}
	
	
	public void getAndUpdate(Va newReference,boolean newMark) {   
		for(;;){
			//读取到最新的引用
			Va currentReference = atmMakRef.getReference();
			//读取到最新的标记
			boolean marked = atmMakRef.isMarked();
			//执行CAS算法
			/*
			 * t1 : 将1改为2   将false改为true    休眠2S
			 * t2 : 将1 改为2，将false改为true
			 * 休眠1S后t3 :  将2改为1，将true改为false     
			 * 因此这里一定要进行重置
			 */
			if (atmMakRef.compareAndSet(currentReference, newReference, marked, newMark)
					&&atmMakRef.attemptMark(newReference, marked)){
				return;
			}
		}
	}

}
