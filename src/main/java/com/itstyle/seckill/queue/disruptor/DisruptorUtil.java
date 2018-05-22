package com.itstyle.seckill.queue.disruptor;

import java.util.concurrent.ThreadFactory;

import org.springframework.stereotype.Component;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

@Component
public class DisruptorUtil {
	
	static Disruptor<SeckillEvent> disruptor = null;
	static{
		SeckillEventFactory factory = new SeckillEventFactory();
		int ringBufferSize = 1024;
		ThreadFactory threadFactory = new ThreadFactory() {
			public Thread newThread(Runnable runnable) {
				return new Thread(runnable);
			}
		};
		disruptor = new Disruptor<SeckillEvent>(factory, ringBufferSize, threadFactory);
		disruptor.handleEventsWith(new SeckillEventConsumer());
		disruptor.start();
	}
	
	public static void producer(SeckillEvent kill){
		RingBuffer<SeckillEvent> ringBuffer = disruptor.getRingBuffer();
		SeckillEventProducer producer = new SeckillEventProducer(ringBuffer);
		producer.seckill(kill.getSeckillId(),kill.getUserId());
	}
}
