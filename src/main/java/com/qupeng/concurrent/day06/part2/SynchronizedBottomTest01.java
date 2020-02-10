package com.qupeng.concurrent.day06.part2;

import org.openjdk.jol.info.ClassLayout;

/**
 * synchronized关键字的底层原理
 * 
 * 由于虚拟机默认开启指针压缩，所以整个对象头的占12个字节 
 * 在VM参数中加入：-XX:-UseCompressedOops关闭指针压缩。
 * 
 * 由于Intel是采用小端存储的，所以是数据的低位保存在内存的低地址中，
 * 而数据的高位保存在内存的高地址中
 * 											地址：以字节为单位低----------->高
 *(object header)     01 00 00 00  (0_0000_0_01 00000000 00000000 00000000) (1)
 *(object header)     00 00 00 00 (0_0000000 00000000 00000000 00000000) (0)
 *
 *前8位分析：0没有用到   0000表示GC的年龄    0表示是否偏向   01表示锁的级别和GC状态标识
 *
 *所以的得出结论：
 *关于锁的状态就观察对象头的第一个字节的后3位。
 *第一位表示是否是偏向锁状态，
 *第二、三位表示锁的级别和GC的标记。01无锁，00轻量级锁  10重量级锁  11 GC标记
 *综上所述：
 *001：无锁，101偏向锁，最末两位 00轻量级锁  10重量级锁
 * @author qupeng
 */
public class SynchronizedBottomTest01 {
	
	private  int k=0;
	
	private Object myObjLock=new Object();

	public static  void main(String[] args) {
		SynchronizedBottomTest01 test=new SynchronizedBottomTest01();
		System.out.println("计算hashCode之前----------------------------------------------");
		System.out.println(ClassLayout.parseInstance(test).toPrintable());
		System.out.println("计算hashCode之后----------------------------------------------");
		int testHashCode = test.hashCode();
		System.out.println("test对象的hashCode是："+testHashCode);
		System.out.println("test对象的hashCode转换为16进制是："+Integer.toHexString(testHashCode));
		System.out.println(ClassLayout.parseInstance(test).toPrintable());
	}

}
