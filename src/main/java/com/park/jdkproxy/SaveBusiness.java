package com.park.jdkproxy;

import java.io.Serializable;

/**
 * 被代理类
 *
 * @author Aaron
 * @date 2020/10/1 16:29
 */
public class SaveBusiness implements BusinessInterface, Serializable {

    @Override
    public boolean login(String name, String pwd) {
        System.out.printf("用户 name : [%s], pwd : [%s] 登录成功...\n", name, pwd);
        return true;
    }
}
