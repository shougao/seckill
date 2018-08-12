package com.itstyle.seckill.common.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 案例测试
 * @author 科帮网
 */
public class LockDemo {
	
	private static Lock lock = new ReentrantLock();
	public static void main(String[] args) {
		lockDemo();
	}
	/**
	 * 10万以下数据synchronized优于Lock
	 * 10万以上数据Lock优于synchronized
	 */
	public static void lockDemo(){
		long start = System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			final int num = i;
			new Runnable() {
				@Override
				public void run() {
					lock(num);
				}
			}.run();
		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
	public static void SyncDemo(){
		long start = System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			final int num = i;
			new Runnable() {
				@Override
				public void run() {
					sync(num);
				}
			}.run();
		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
    public static void lock(int i){
    	lock.lock();
    	lock.unlock();
    }
    public static synchronized void sync(int i){
    	
    }
}
