package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.SendTypeRequest;

import java.util.concurrent.ExecutionException;

public interface WxChatService {
    /**
     * 向一个用户推送消息（测试）
     * @param
     */
    void sendTest(SendTypeRequest send) throws ExecutionException, InterruptedException;
}
