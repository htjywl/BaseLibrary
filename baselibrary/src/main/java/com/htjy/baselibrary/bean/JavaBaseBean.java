package com.htjy.baselibrary.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/21.
 */

public class JavaBaseBean<T> implements Serializable {
    private String status;
    private String error;
    private String timestamp;
    private T message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JavaBaseBean{" +
                "status='" + status + '\'' +
                ", error='" + error + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", message=" + message +
                '}';
    }
}
