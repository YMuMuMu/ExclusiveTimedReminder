package com.tencent.wxcloudrun.model;

import lombok.Data;
import lombok.ToString;

/**
 * 推送的微信公众信息的每一个 {
 {}} 值的内容和颜色封装
 */
@Data
@ToString
public class WeChatTemplateMsg {
    /**
     * 消息
     */
    private String value;
    /**
     * 消息颜色
     */
    private String color;
    public WeChatTemplateMsg(String value) {
        this.value = value;
        this.color = "#173177";
    }
    public WeChatTemplateMsg(String value, String color) {
        this.value = value;
        this.color = color;
    }
}