package com.itstyle.seckill.queue.disruptor;

import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
/**
 * 測試類
 * 创建者 科帮网
 */
public class SeckillEventMain {

	public static void main(String[] args) {
		producerWithTranslator();
	}
	public static void producerWithTranslator(){
		SeckillEventFactory factory = new SeckillEventFactory();
		int ringBufferSize = 1024;
		ThreadFactory threadFactory = new ThreadFactory() {
			public Thread newThread(Runnable runnable) {
				return new Thread(runnable);
			}
		};
		Disruptor<SeckillEvent> disruptor = new Disruptor<SeckillEvent>(factory, ringBufferSize, threadFactory);
		disruptor.handleEventsWith(new SeckillEventConsumer());
		disruptor.start();
		RingBuffer<SeckillEvent> ringBuffer = disruptor.getRingBuffer();
		SeckillEventProducer producer = new SeckillEventProducer(ringBuffer);
		for(long i = 0; i<10; i++){
			producer.seckill(i, i);
		}
	}
}
