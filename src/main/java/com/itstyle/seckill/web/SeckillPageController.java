package com.itstyle.seckill.web;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itstyle.seckill.common.entity.Result;
import com.itstyle.seckill.common.entity.Seckill;
import com.itstyle.seckill.service.ISeckillService;

@Api(tags = "秒杀商品")
@RestController
@RequestMapping("/seckillPage")
public class SeckillPageController {
	
	@Autowired
	private ISeckillService seckillService;
	
	@ApiOperation(value = "秒杀商品列表", nickname = "小柒2012")
	@PostMapping("/list")
	public Result list() {
		//返回JSON数据、前端VUE迭代即可
		List<Seckill>  List = seckillService.getSeckillList();
		return Result.ok(List);
	}
}
