package com.qupeng.concurrent.day06.part1;

/**
 * 线程安全的单例模式：
 * 用double-check-lock（DCL）实现
 * 
 * 在创建对象时，分为了3步：
 * 1、在堆中给DCLSingleton 的实例分配内存；
 * 2、初始化DCLSingleton 的构造器；
 * 3、将instance对象指向分配的内存空间（注意到这步instance就非null了）
 * 
 * 由于运行期间处理器会进行重排序，我们有可能是1->2->3也有可能是1->3->2
 * 即我们有可能在先返回instance实例，然后执行构造方法。
 * 即：double-check-locking可能存在线程拿到一个没有执行构造方法的对象。
 * 
 * 或者在构造器中，出现重排序后，构造器还没有执行完毕，但是呢这个instance已经不是null了，
 * 然后另一条线程拿到这个instance并且调用该实例的成员变量的方法，就会出现空指针异常。
 * @author qupeng
 */
public class DCLSingleton {
	
	private  static DCLSingleton instance=null;
	
	private  String str1;
	
	private  String str2;
	
	private  String str3;
	
	private  String str4;
	
	//私有化构造器防止外部new
	private DCLSingleton(){}
	
	public static DCLSingleton getInstance(){
		if(instance==null){
			synchronized (DCLSingleton.class) {
				if(instance==null){
					instance=new DCLSingleton();
				}
			}
		}
		return  instance;
	}
	
	
	public static void main(String[] args) {
		DCLSingleton sgt1 = getInstance();
		DCLSingleton sgt2 = getInstance();
		System.out.println(sgt1==sgt2);
	}

}
