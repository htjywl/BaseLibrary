package com.htjy.baselibrary.http.base;

/**
 * @Description: 全局的公共exception处理
 */

public class BaseException extends Exception {

    //数据为"[]"
    public static final String STATUS_OK = "200";
    public static final String EMPTY_MESSAGE_CODE = "100001";
    public static final String NOT_LOGIN = "9001";
    public static final String NOT_LOGIN_STRING = "code\":\"9001\"";
    public static final String NOT_LOGIN_MESSAGE = "没有登录";
    public static final String EMPTY_MESSAGE = "数据为空";
    public static final String OTHER_ERROR = "100002";//其他错误
    public static final String OTHER_ERROR_MESSAGE = "其他错误";//其他错误

    public static final String JSON_ERROR = "100003";//其他错误
    public static final String JSON_ERROR_MESSAGE = "json解析错误";//其他错误
    public static final String NETWORD_ERROR = "100004";
    public static final String NETWORD_ERROR_MESSAGE = "网络错误";
    public static final String STRING_COVERT_ERROR = "100005";//throwable错误  我也不知道是什么错误
    public static final String NETWORD_TIMEOUT_MESSAGE = "远程服务器未响应，请联系平台管理员";


    private String code = "";
    private String displayMessage = "";

    public BaseException() {

    }

    public BaseException(String code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public BaseException(String message, String code, String displayMessage) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }


    @Override
    public String getMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
