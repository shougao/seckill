package com.itstyle.seckill.queue.redis;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itstyle.seckill.common.entity.Result;
import com.itstyle.seckill.common.webSocket.WebSocketServer;
import com.itstyle.seckill.service.ISeckillService;
/**
 * 消费者
 * @author 科帮网 By https://blog.52itstyle.com
 */
@Service
public class RedisConsumer {
	
	@Autowired
	private ISeckillService seckillService;
	
    public void receiveMessage(String message) {
        //收到通道的消息之后执行秒杀操作(超卖)
    	String[] array = message.split(";"); 
    	Result result = seckillService.startSeckilAopLock(Long.parseLong(array[0]), Long.parseLong(array[1]));
    	if(result.equals(Result.ok())){
    		try {
				WebSocketServer.sendInfo(array[0].toString(), array[1].toString());//推送给前台
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
}