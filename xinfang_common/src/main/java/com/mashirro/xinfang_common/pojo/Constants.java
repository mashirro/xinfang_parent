package com.mashirro.xinfang_common.pojo;

/**
 * @Description: 通用常量信息类
 * @Author: Mashirro
 * @Date: 2020/3/13
 */
public class Constants {

    //图形验证码redis key
    public static final String CAPTCHA_CODE_KEY = "captcha_code:";

    //手机验证码redis key
    public static final String SMS_CODE_KEY = "sms_code:";

    //图形验证码长度
    public static final Integer CAPTCHA_CODE_SIZE = 4;

    //验证码有效期
    public static final Integer CAPTCHA_EXPIRATION = 2;

    //rabbitMQ队列名
    public static final String RABBITMQ_QUEUE_NAME = "sms";
}
