package com.tencent.wxcloudrun.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 微信公众配置信息
 */
@Data
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {
        /**
         * 设置微信公众号的appid
         */
        private String appId;
        /**
         * 设置微信公众号的app secret
         */
        private String secret;
        /**
         * 设置微信公众号的token
         */
        private String token;
        /**
         * 设置微信公众号的EncodingAESKey
         */
        private String aesKey;
    }
