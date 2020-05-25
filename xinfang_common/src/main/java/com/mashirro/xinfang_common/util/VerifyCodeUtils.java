package com.mashirro.xinfang_common.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @Description: 验证码工具类
 * @Author: Mashirro
 * @Date: 2020/3/6
 */
public class VerifyCodeUtils {

    //验证码字符源(可以去掉易混淆字符)
    public static final String VERIFY_CODES = "1234567890QWERTYUIOPASDFGHJKLZXCVBNM";

    //图片宽
    private static final int w = 95;

    //图片高
    private static final int h = 25;

    //构造一个实现默认随机数算法的安全随机数生成器
    private static Random random = new SecureRandom();

    /**
     * 生成6位手机验证码
     *
     * @return
     */
    public static Integer generateSmsCode() {
        int smsCode = random.nextInt(999999);
        if (smsCode < 100000) {
            smsCode = smsCode + 100000;
        }
        return smsCode;
    }


    /**
     * 使用默认源生成验证码
     *
     * @param size 验证码长度
     * @return
     */
    public static String generateCaptchaVerifyCode(int size) {
        return generateCaptchaVerifyCode(size, VERIFY_CODES);
    }

    /**
     * 使用指定源生成验证码
     *
     * @param size    验证码长度
     * @param sources 验证码字符源
     * @return
     */
    public static String generateCaptchaVerifyCode(int size, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = VERIFY_CODES;
        }
        //构造一个字符串生成器，其中没有字符，并且初始容量由{@code capacity}参数指定。
        StringBuilder verifyCode = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            verifyCode.append(sources.charAt(random.nextInt(sources.length() - 1)));
        }
        return verifyCode.toString();
    }


    /**
     * 输出指定验证码的图片流
     *
     * @param os         输出流
     * @param verifyCode 验证码
     * @throws IOException
     */
    public static void outputImage(OutputStream os, String verifyCode) throws IOException {
        //BufferedImage描述具有可访问图像数据缓冲区的图像
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        //Graphics2D类扩展了Graphics类，以提供对几何图形、坐标变换、颜色管理和文本布局的更复杂的控制。
        Graphics2D g2 = image.createGraphics();
        //设置字体
        g2.setFont(new Font("Fixedsys", Font.ITALIC, 18));
        char[] chars = verifyCode.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            //绘制由指定字符串给定的文本
            g2.drawString(String.valueOf(chars[i]), 17 * (i + 1), 16);
        }
        g2.dispose();
        //将图像写入OutputStream,JPEG格式,此方法不会在写入操作完成后关闭提供的OutputStream
        ImageIO.write(image, "JPEG", os);
        os.close();
    }
}
