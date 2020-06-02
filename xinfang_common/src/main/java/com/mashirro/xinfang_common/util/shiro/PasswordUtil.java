package com.mashirro.xinfang_common.util.shiro;


import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * shiro密码工具类
 */
public class PasswordUtil {

    //执行哈希时要使用的算法名称。(也可以使用别的,比如md5)
    public static final String ALGORITHM_NAME = "SHA-256";

    //执行了1024次哈希迭代以增强安全性
    public static final Integer HASHITERATIONS = 1024;

    /**
     * 使用org.apache.shiro.crypto.hash.SimpleHash对用户密码进行单向哈希
     * @param password
     * @param salt
     * @return
     */
    public static String getHashedPassword(String password, String salt) {
        return new SimpleHash(ALGORITHM_NAME, password, salt, HASHITERATIONS).toHex();
    }
}
