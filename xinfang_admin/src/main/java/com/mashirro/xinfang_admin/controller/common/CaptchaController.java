package com.mashirro.xinfang_admin.controller.common;

import com.mashirro.xinfang_common.pojo.Constants;
import com.mashirro.xinfang_common.pojo.Result;
import com.mashirro.xinfang_common.util.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @Description: 验证码控制器
 * @Author: Mashirro
 * @Date: 2020/3/6
 */
@RestController
public class CaptchaController {

    //StringRedisserializer是StringRedisTemplate默认的序列化方式
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //@Autowired
    //private RabbitTemplate rabbitTemplate;

    /**
     * 生成图形验证码
     * @Author: Mashirro
     * @return
     */
//    @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
//    public void getCaptcha(HttpServletResponse response) {
//        //获取四位验证码
//        String captchaVerifyCode = VerifyCodeUtils.generateCaptchaVerifyCode(4);
//        response.setContentType("image/jpeg");
//        //设置响应头信息，告诉浏览器不要缓存此内容
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//        //输出验证码图片方法
//        try {
//            OutputStream os = response.getOutputStream();
//            VerifyCodeUtils.outputImage(os, captchaVerifyCode);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 生成图形验证码
     *
     * @return
     * @Author: Mashirro
     */
    @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
    public Result getCaptcha(HttpServletResponse response) throws IOException {
        //获取四位验证码
        String captchaVerifyCode = VerifyCodeUtils.generateCaptchaVerifyCode(Constants.CAPTCHA_CODE_SIZE);
        //注意该验证码需分配一个唯一标识符，在存入redis时使用，在登陆校验时也要携带!!!
        String uuidStr = UUID.randomUUID().toString();
        //缓存验证码,有效期2分钟
        stringRedisTemplate.opsForValue().set(Constants.CAPTCHA_CODE_KEY + uuidStr, captchaVerifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            VerifyCodeUtils.outputImage(bos, captchaVerifyCode);
            Map<String, Object> map = new HashMap<>();
            BASE64Encoder encoder = new BASE64Encoder();
            String imgSrc = encoder.encodeBuffer(bos.toByteArray()).trim();
            map.put("img", "data:image/jpeg;base64," + imgSrc);
            map.put("img_uuid", uuidStr);
            return Result.success("获取图形验证码成功!", map);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("获取图形验证码失败!");
        } finally {
            bos.close();
        }
    }

    /**
     * 获取短信验证码
     *
     * @return
     */
//    @RequestMapping(value = "/getSmsCode", method = RequestMethod.POST)
//    public Result getSmsCode(String phoneNum) {
//        try {
//            if (StringUtils.isEmpty(phoneNum)) {
//                return Result.error("请先输入手机号!");
//            }
//            //redis中是否已存在此手机号的 redis key
//            if (stringRedisTemplate.hasKey(Constants.SMS_CODE_KEY + phoneNum)) {
//                return Result.error("发送验证码过于频繁,请稍后再试!");
//            }
//            //生成六位手机验证码
//            String smsCode = VerifyCodeUtils.generateSmsCode() + "";
//            //存入redis
//            stringRedisTemplate.opsForValue().set(Constants.SMS_CODE_KEY + phoneNum, smsCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
//            //把验证码和手机号发送至rabbitMQ
//            Map<String, Object> map = new HashMap<>();
//            map.put("phoneNum", phoneNum);
//            map.put("smsCode", smsCode);
//            rabbitTemplate.convertAndSend(Constants.RABBITMQ_QUEUE_NAME, map);
//            return Result.success("获取手机验证码成功!", null);
//        } catch (AmqpException e) {
//            e.printStackTrace();
//            return Result.error("获取手机验证码失败!");
//        }
//    }
}
