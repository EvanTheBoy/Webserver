package com.hw.myserver0428;

import java.util.HashMap;

//所有servlet的父亲
public abstract class IHttpServlet {
    //属于用户的session对象
    protected HashMap<String, String> session;

    //保存一次的键值对
    protected HashMap<String, String> parameters;

    //在该方法中响应用户的get请求
    public abstract void service(Response response);
}
