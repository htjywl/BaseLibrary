package com.htjy.baselibrary.bean;

import com.htjy.baselibrary.http.base.BaseException;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/21.
 */

public class BaseBean<T> implements Serializable {
    private String code;
    private String message;
    private T extraData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getExtraData() {
        return extraData;
    }



    public void setExtraData(T extraData) {
        this.extraData = extraData;
    }
    /**
     * 是否执行成功
     *
     * @return
     */
    public boolean isSuccess() {
        return code != null && BaseException.STATUS_OK.equals(code);
    }
    @Override
    public String toString() {
        return "BaseBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", extraData=" + extraData +
                '}';
    }
}
