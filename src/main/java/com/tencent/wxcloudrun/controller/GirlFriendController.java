package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.SendTypeRequest;
import com.tencent.wxcloudrun.service.WxChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class GirlFriendController {

    //上下文，用于策略模式获取对应策略
    @Autowired
    private ApplicationContext applicationContext;


    //表示每个月星期一到星期五下午4点50分执行
//    @Scheduled(cron = "0 50 16 ? * MON-FRI")
    //    ” 每15秒，30秒，45秒时触发任务
    @Scheduled(cron = "0 0 * * * ?")
    public void sendOffWork() throws ExecutionException, InterruptedException {

        System.out.println("发送早安提醒");
        //参数一发送类型，参数二是推送的对象OpenId
        SendTypeRequest sendTypeRequest = new SendTypeRequest("OffWorkSend","o7mr56USYpOYeTa_LPHyOtjsAwMk");
        WxChatService chatService = applicationContext.getBean(sendTypeRequest.getType(),WxChatService.class);
        chatService.sendTest(sendTypeRequest);

        System.out.println("发送早安提醒");
        //参数一发送类型，参数二是推送的对象OpenId
        SendTypeRequest ys = new SendTypeRequest("OffWorkSend","o7mr56byf8BSjtXx73DUq1jVMXXc");
        WxChatService ysService = applicationContext.getBean(ys.getType(),WxChatService.class);
        ysService.sendTest(ys);
    }
}

