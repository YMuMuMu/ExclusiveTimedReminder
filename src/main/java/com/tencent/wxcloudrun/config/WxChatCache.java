package com.tencent.wxcloudrun.config;

public class WxChatCache {

    /**
     * 微信 accessToken缓存
     */
    public static class AccessToken {
        public static String token = null;   // accessToken
        public static Long expiration = 0L;  // accessToken 过期时间(获取的token 默认有效期2小时）
    }
}
