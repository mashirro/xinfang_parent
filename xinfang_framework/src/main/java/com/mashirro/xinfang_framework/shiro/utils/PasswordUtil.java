package com.mashirro.xinfang_framework.shiro.utils;


import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * shiro密码工具类
 */
public class PasswordUtil {

    //执行哈希时要使用的算法名称。(也可以使用别的,比如md5)
    public static final String ALGORITHM_NAME = "SHA-256";

    //执行了1024次哈希迭代以增强安全性
    public static final Integer HASHITERATIONS = 1024;

    //我们将用一个随机数发生器来产生盐,这比使用用户名作为salt或者根本没有salt要安全得多。
    private static final SecureRandomNumberGenerator GENERATOR = new SecureRandomNumberGenerator();

    /**
     * 使用org.apache.shiro.crypto.hash.SimpleHash对用户密码进行单向哈希
     *
     * @param password
     * @param salt
     * @return
     */
    public static String getHashedPassword(String password, String salt) {
        return new SimpleHash(ALGORITHM_NAME, password, salt, HASHITERATIONS).toHex();
    }

    /**
     * 生成盐
     *
     * @return
     */
    public static String generateSalt() {
        //我们将用一个随机数发生器来产生盐,这比使用用户名作为salt或者根本没有salt要安全得多。toHex:返回基础包装字节数组的十六进制格式的String表示形式。
        return GENERATOR.nextBytes().toHex();
    }
}
