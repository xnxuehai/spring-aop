package com.park.jdkproxy;

/**
 * 业务接口
 *
 * @author Aaron
 * @date 2020/10/1 16:29
 */
public interface BusinessInterface {
    /**
     * 发送消息
     *
     * @param name 名称
     * @param pwd  密码
     * @return true or false
     */
    boolean login(String name, String pwd);
}
