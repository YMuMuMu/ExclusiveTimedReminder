package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.model.SendTypeRequest;
import com.tencent.wxcloudrun.model.WeChatTemplateMsg;
import com.tencent.wxcloudrun.service.WxChatService;
import com.tencent.wxcloudrun.utils.WxSendMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 下班推送策略
 * */
@Service("OffWorkSend")
public class OffWorkSend implements WxChatService {

    @Autowired
    protected WxSendMessageUtils wxSendMessageUtils;

    @Override
    public void sendTest(SendTypeRequest send) {

        // 下班模板Id
        String templateId = "_7TEaOGNypm-lT7lQxdmmg7-IOHS0G1s3boRmdm5QR8";
        // 模板参数
        Map<String, WeChatTemplateMsg> sendMag = new HashMap<String, WeChatTemplateMsg>();
        sendMag.put("offWork", new WeChatTemplateMsg("宝~马上就要下班咯，收拾好随身物品准备早退啦！","#b89485"));
        // 发送
        wxSendMessageUtils.send(send.getOpenId(), templateId, sendMag);
    }
}
