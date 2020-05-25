package com.mashirro.xinfang_common.pojo;

import java.util.HashMap;

/**
 * @Description: 统一结果返回实体类
 * @Author: Mashirro
 * @Date: 2020/3/6
 */
public class Result extends HashMap<String, Object> {
    //返回内容
    private static final String data_tag = "data";
    //返回消息
    private static final String message_tag = "message";
    //是否成功
    private static final String flag_tag = "flag";

    public Result() {
    }

    private Result(boolean flag, String message, Object data) {
        super.put(flag_tag, flag);
        super.put(message_tag, message);
        if (data != null) {
            super.put(data_tag, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @param message
     * @param data
     * @return
     */
    public static Result success(String message, Object data) {
        return new Result(true, message, data);
    }

    /**
     * 返回失败消息
     *
     * @param message
     * @return
     */
    public static Result error(String message) {
        return new Result(false, message, null);
    }
}
