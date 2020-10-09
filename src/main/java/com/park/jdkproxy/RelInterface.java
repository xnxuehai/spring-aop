package com.park.jdkproxy;

/**
 * 业务接口
 *
 * @author Aaron
 * @date 2020/10/1 16:29
 */
public interface RelInterface {
    /**
     * 发送消息
     *
     * @param str 消息
     * @return true or false
     */
    boolean sendMessage(String str);
}
