package com.htjy.baselibrary.http.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.htjy.baselibrary.http.HttpFactory;
import com.htjy.baselibrary.utils.DialogUtils;
import com.htjy.baselibrary.utils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.base.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Cookie;
import okhttp3.Response;


public abstract class StringDialogCallback extends StringCallback {

    private static final String TAG = "StringDialogCallback";
    private WeakReference<Activity> mWeakRefrence;
    private boolean mNetwork = true;
    private Dialog dialog;
    private boolean isCartoonDialog = false; //是否显示卡通进度条
    private boolean canCancelDialog = true; // 是否能取消进度条
    private boolean showProgressDialog = true;// 是否显示进度条
    private static final String CHARSET_UTF8 = "UTF-8";
    //private String sessionId;
    private final String SESSION = "PHPSESSID";
    private String sessionId = null;
    private StringConvert convert;

    /**
     * 动态设置进度条
     *
     * @param activity           activity
     * @param showProgressDialog 是否显示进度条
     * @param isCartoonDialog    是否显示卡通进度条
     * @param canCancelDialog    是否能取消进度条
     */
    public StringDialogCallback(Activity activity, boolean showProgressDialog, boolean isCartoonDialog, boolean canCancelDialog) {
        super();
        convert = new StringConvert();
        mNetwork = HttpFactory.isOpenNetwork(activity);
        mWeakRefrence = new WeakReference<>(activity);
        setCanCancelDialog(canCancelDialog);
        setCartoonDialog(isCartoonDialog);
        setShowProgressDialog(showProgressDialog);
        getProgressDialog(activity);
    }

    /**
     * 默认的构造方法,默认显示普通进度条
     *
     * @param activity ac
     */
    public StringDialogCallback(Activity activity) {
        super();
        convert = new StringConvert();
        mNetwork = HttpFactory.isOpenNetwork(activity);
        mWeakRefrence = new WeakReference<>(activity);
        getProgressDialog(activity);
    }


    public Activity getThisActivity() {
        return mWeakRefrence.get();
    }

    @Override
    public void onStart(Request request) {
        setSessionId();
        if (sessionId != null) {
            request.headers("Cookie", SESSION + "=" + sessionId);
        } else {
            //目前使用的是全局的session,所以这个暂时没用,
            setSessionId();
            request.headers("Cookie", SESSION + "=" + sessionId);
        }
        //request.url(strUrl);
        //网络请求前显示对话框
        if (mNetwork && dialog != null  && showProgressDialog) {
            dialog.show();
        }
    }

    //子线程,这里可以解析数据,抛出错误,交给onError处理
    @Override
    public String convertResponse(Response response) throws Exception {
        String s = null;
        try {
            s = convert.convertResponse(response);
        } catch (Throwable throwable) {
            throw new BaseException(BaseException.STRING_COVERT_ERROR, throwable.getMessage());
        }
        response.close();
        JSONObject jObj = null;
        jObj = new JSONObject(s);
        //JSONObject jObj2;
        String code = jObj.getString("code");
        String message = jObj.getString("message");
        /*if (HttpConstants.STATUS_BACK_LOGIN.equals(code)) {
            //sendProcessMessage(Constants.STATUS_BACK_LOGIN, null);
            //说明没有登录
            throw new BaseException(BaseException.NOT_LOGIN, BaseException.NOT_LOGIN_MESSAGE);
        } else */
        if (code.equals(BaseException.STATUS_OK)) {
           /* String extraData = jObj.getString("extraData");
            if (extraData.equals("[]")) {
                throw new BaseException(BaseException.EMPTY_MESSAGE_CODE, BaseException.EMPTY_MESSAGE);
            }
            if ("{}".equals(extraData)) {
                throw new BaseException(BaseException.EMPTY_MESSAGE_CODE, BaseException.EMPTY_MESSAGE);
            }*/
            return s;
        } else {
            //这个地方是有返回数据的时候,根据返回错误码的不同做处理
            throw new BaseException(code, message);
        }
    }

    //全局的错误处理,也可以在请求的地方单独直接处理用法一样
    @Override
    public void onError(com.lzy.okgo.model.Response response) {
        super.onError(response);
        Throwable exception = response.getException();
        if (exception instanceof BaseException) {
        } else if (exception instanceof JSONException) {
            ToastUtils.showShortToast(BaseException.JSON_ERROR_MESSAGE);
        } else if (exception instanceof SocketTimeoutException) {
            ToastUtils.showShortToast(BaseException.NETWORD_TIMEOUT_MESSAGE);
        } else {
            ToastUtils.showShortToast(BaseException.NETWORD_ERROR_MESSAGE);
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing() && !getThisActivity().isFinishing()) {
            dialog.dismiss();
        }
    }

    /**
     * 进度对话框
     *
     * @return
     */
    /**
     * 进度对话框
     *
     * @return
     */
    public Dialog getProgressDialog(Context context) {
        if (context != null && dialog == null) {
            dialog = new DialogUtils.DefaultProgressDialog(context);
            /*if (isCartoonDialog)
                dialog = new DialogUtils.CustomProgressDialog(activity);
            else
                dialog = new DialogUtils.DefaultProgressDialog(activity);*/
        }
        if (dialog != null) {
            dialog.setCancelable(canCancelDialog);
            dialog.setCanceledOnTouchOutside(canCancelDialog);
        }
        return dialog;
    }

    public void setCartoonDialog(boolean cartoonDialog) {
        isCartoonDialog = cartoonDialog;
    }

    /**
     * 是否显示进度条
     *
     * @return
     */
    public boolean isShowProgressDialog() {
        return showProgressDialog;
    }

    /**
     * 设置是否显示进度条
     *
     * @param showProgressDialog
     */
    public void setShowProgressDialog(boolean showProgressDialog) {
        this.showProgressDialog = showProgressDialog;
    }

    /**
     * 设置是否能取消进度条
     *
     * @param canCancelDialog
     */
    public void setCanCancelDialog(boolean canCancelDialog) {
        this.canCancelDialog = canCancelDialog;
    }

    /**
     * 转码http的网址，只对中文进行转码
     *
     * @param str
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String urlEncode(String str, String charset)
            throws UnsupportedEncodingException {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
        Matcher m = p.matcher(str);
        StringBuffer b = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(b, URLEncoder.encode(m.group(0), charset));
        }
        m.appendTail(b);
        return b.toString();
    }

    /**
     * 设置seesionId
     */
    private void setSessionId() {
        //一般手动取出cookie的目的只是交给 webview 等等，非必要情况不要自己操作
        com.lzy.okgo.cookie.store.CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        List<Cookie> cookies = cookieStore.getAllCookie();
        for (int i = 0; i < cookies.size(); i++) {
            // 这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
            //DialogUtils.showLog("sessionid",cookies.get(i).value());
            if ("PHPSESSID".equals(cookies.get(i).name())) {
                sessionId = cookies.get(i).value();
                break;
            }
        }
    }
}
