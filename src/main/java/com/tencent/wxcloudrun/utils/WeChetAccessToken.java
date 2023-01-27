package com.tencent.wxcloudrun.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.WxChatCache;
import com.tencent.wxcloudrun.config.WxMpProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 获取微信token工具类
 */
@Component
public class WeChetAccessToken {

    //http请求类
    @Autowired
    private RestTemplate restTemplate;

    //微信配置类文件
    @Autowired
    private WxMpProperties wxMpProperties;

    public String getToken() {
        //如果缓存中的Token过期，则请求获取Token
        if (WxChatCache.AccessToken.expiration <= System.currentTimeMillis()) {
            //URL:https://api.weixin.qq.com/cgi-bin/token?appid=APPID&secret=APPSECRET
            String url = WxChatConstant.Url.ACCESS_TOKEN_URL
                    .replace("APPID", wxMpProperties.getAppId())
                    .replace("APPSECRET", wxMpProperties.getSecret());
            //使用restTemplate发起Http请求
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
            JSONObject jsonObject = JSON.parseObject(forEntity.getBody());
            Object errcode = jsonObject.get("errcode");
            if (errcode != null && "40013".equals(errcode.toString())) {
                System.out.println("不合法的APPID");
            }
            // expiration：为当前执行到此处的时间+2小时有效时间为过期时间
            WxChatCache.AccessToken.token = jsonObject.get("access_token").toString();
            WxChatCache.AccessToken.expiration = System.currentTimeMillis()+7200000;
            return WxChatCache.AccessToken.token;
        }
        //如果没过期则使用缓存中的Token
        else {
            System.out.println("返回缓存中的token:"+ WxChatCache.AccessToken.token);
            return WxChatCache.AccessToken.token;
        }
    }
}