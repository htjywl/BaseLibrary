package com.htjy.baselibrary.bean;

import java.io.Serializable;


public class SimpleBaseBean implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;

    public String code;
    public String msg;

    public BaseBean toLzyResponse() {
        BaseBean lzyResponse = new BaseBean();
        lzyResponse.setCode(code);
        lzyResponse.setMessage(msg);
        return lzyResponse;
    }
}