package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.VerifyRequest;
import com.tencent.wxcloudrun.utils.AesException;
import com.tencent.wxcloudrun.utils.SHA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;


/**
 * counter控制器
 */
@RestController

public class CounterController {

  final CounterService counterService;
  final Logger logger;

  public CounterController(@Autowired CounterService counterService) {
    this.counterService = counterService;
    this.logger = LoggerFactory.getLogger(CounterController.class);
  }


  /**
   * 获取当前计数
   * @return API response json
   */
  @GetMapping(value = "/api/count")
  ApiResponse get() {
    logger.info("/api/count get request");
    Optional<Counter> counter = counterService.getCounter(1);
    Integer count = 0;
    if (counter.isPresent()) {
      count = counter.get().getCount();
    }

    return ApiResponse.ok(count);
  }


  /**
   * 更新计数，自增或者清零
   * @param request {@link CounterRequest}
   * @return API response json
   */
  @PostMapping(value = "/api/count")
  ApiResponse create(@RequestBody CounterRequest request) {
    logger.info("/api/count post request, action: {}", request.getAction());

    Optional<Counter> curCounter = counterService.getCounter(1);
    if (request.getAction().equals("inc")) {
      Integer count = 1;
      if (curCounter.isPresent()) {
        count += curCounter.get().getCount();
      }
      Counter counter = new Counter();
      counter.setId(1);
      counter.setCount(count);
      counterService.upsertCount(counter);
      return ApiResponse.ok(count);
    } else if (request.getAction().equals("clear")) {
      if (!curCounter.isPresent()) {
        return ApiResponse.ok(0);
      }
      counterService.clearCount(1);
      return ApiResponse.ok(0);
    } else {
      return ApiResponse.error("参数action错误");
    }
  }
  /**
   * 用于校验服务器是否合规，此处校验方式可以根据自己选择进行加密算法的选择
   * 理论上此处可以省略工具类的校验，直接返回请求中的参数。
   * 但是笔者直接返回却报错，不知道原因为何，看到这里的小伙伴可以尝试一下直接返回，即：return request.getEchostr()；
   * @return String
   */
  @GetMapping(value = "/checkToken")
  public String checkToken(VerifyRequest request) {
    //配置中自己填写的Token
    String token = "yangsen.97";
    String sha1 = "";
    try {
      sha1 = SHA.getSHA1(token, request.getTimestamp(), request.getNonce(), "");
    } catch (AesException e) {
      e.printStackTrace();
    }
    System.out.println("加密:"+sha1);
    System.out.println("本身:"+request.getSignature());
    //如果校验成功，则返回请求中的echostr参数。

    if(sha1.equals(request.getSignature())){
      return request.getEchostr();
    }
    else {
      return "非法访问";
    }
  }

}