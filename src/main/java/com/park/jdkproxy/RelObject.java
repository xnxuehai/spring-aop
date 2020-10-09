package com.park.jdkproxy;

import java.io.Serializable;

/**
 * 被代理类
 *
 * @author Aaron
 * @date 2020/10/1 16:29
 */
public class RelObject implements RelInterface, Serializable {
    private static final String body = "Aaron";

    @Override
    public boolean sendMessage(String str) {
        if (body.equals(str)) {
            System.out.printf("发送[%s]消息成功...\n", str);
            return true;
        }
        System.out.printf("发送[%s]消息失败...\n", str);
        return false;
    }
}
