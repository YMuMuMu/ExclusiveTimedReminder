package com.tencent.wxcloudrun.model;

import lombok.Data;

/**
 * 微信签名校验
 * */
@Data
public class VerifyRequest {

  private String signature;

  private String timestamp;

  private String nonce;

  private String echostr;

}
