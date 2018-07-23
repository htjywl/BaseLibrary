package com.htjy.baselibrary.http.base;

import com.htjy.baselibrary.bean.BaseBean;
import com.htjy.baselibrary.bean.SimpleBaseBean;
import com.htjy.baselibrary.utils.ToastUtils;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Response;

public abstract class JsonCallback<T> extends AbsCallback<T> {

    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String TAG = "OKGO_MY";
    //private String sessionId;
    private final String SESSION = "PHPSESSID";


    @SuppressWarnings("unchecked")
    @Override
    public T convertResponse(Response response) throws Exception {
        //Logger.t("OkGo").d("convertResponse");
        //以下代码是通过泛型解析实际参数,泛型必须传

        //com.lzy.demo.callback.JsonDialogCallback<com.lzy.demo.model.LzyResponse<com.lzy.demo.model.ServerModel>> 得到类的泛型，包括了泛型参数
        Type genType = getClass().getGenericSuperclass();
        //从上述的类中取出真实的泛型参数，有些类可能有多个泛型，所以是数值
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        // 这里这么写的原因是，我们需要保证上面我解析到的type泛型，仍然还具有一层参数化的泛型，也就是两层泛型
        // 如果你不喜欢这么写，不喜欢传递两层泛型，那么以下两行代码不用写，并且javabean按照第一种方式定义就可以实现
        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
        //如果确实还有泛型，那么我们需要取出qu真实的泛型
        //此时，rawType的类型实际上是 class，但 Class 实现了 Type 接口，所以我们用 Type 接收没有问题
        Type rawType = ((ParameterizedType) type).getRawType();
        //这里获取最终内部泛型的类型 com.lzy.demo.model.ServerModel
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

        //这里我们既然都已经拿到了泛型的真实类型，即对应的 class ，那么当然可以开始解析数据了，我们采用 Gson 解析
        //以下代码是根据泛型解析数据，返回对象，返回的对象自动以参数的形式传递到 onSuccess 中，可以直接使用
        String body = null;
        try {
            body = new StringConvert().convertResponse(response);
        } catch (Throwable throwable) {
            throw new BaseException(BaseException.STRING_COVERT_ERROR, throwable.getMessage());
        }
        //Logger.t("OkGo").d(body + "---------------------------------------" + response.request().url());

        response.close();
        if (typeArgument == Void.class) {
            //无数据类型,表示没有data数据的情况（以  new DialogCallback<BaseBean<Void>>(this)  以这种形式传递的泛型,其实用String也行)
            SimpleBaseBean simpleResponse = GsonConvert.fromJson(body, SimpleBaseBean.class);
            switch (simpleResponse.code) {
                case BaseException.STATUS_OK:
                    if (simpleResponse.toLzyResponse() != null) {
                        return (T) simpleResponse.toLzyResponse();
                    }
               /* case BaseException.NOT_LOGIN:
                    throw new BaseException(BaseException.NOT_LOGIN, BaseException.NOT_LOGIN_MESSAGE);*/
                default:
                    throw new BaseException(simpleResponse.code, simpleResponse.msg);
            }

        } else if (rawType == BaseBean.class) {
            JSONObject jObj = null;
            jObj = new JSONObject(body);
            // response.close();
            String code = jObj.getString("code");
            String message = jObj.getString("message");
            /*if (code.equals(HttpConstants.STATUS_OK)) {
                //最坑的是不是ok时,extraData这个字段直接没有,?  ?

                if (!jObj.isNull("extraData")) {
                    String extraData = jObj.getString("extraData");
                    if (extraData.equals("[]")) {
                        throw new BaseException(BaseException.EMPTY_MESSAGE_CODE, BaseException.EMPTY_MESSAGE);
                    }
                    if ("{}".equals(extraData)) {
                        throw new BaseException(BaseException.EMPTY_MESSAGE_CODE, BaseException.EMPTY_MESSAGE);
                    }

                }
            }*/
            if (code.equals(BaseException.STATUS_OK)) {
                BaseBean data = GsonConvert.fromJson(body, type);
                afterConvertSuccess((T) data);
                return (T) data;
            } else {
                //这个地方是有返回数据的时候,根据返回错误码的不同做处理
                throw new BaseException(code, message);
            }

        } else {
            // response.close();
            throw new BaseException(BaseException.JSON_ERROR, BaseException.JSON_ERROR_MESSAGE);
        }
    }

    /**
     * 子线程中，对特殊不符合规划的数据返回惊喜处理
     */
    public void afterConvertSuccess(T t) {
    }



    protected boolean showErrorFromServer() {
        return false;
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

    /*//看自己要不要全局处理,不处理的话,就具体请求再处理
    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
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
    }*/

    protected boolean showSuccessFromServer() {
        return false;
    }

    //看自己要不要全局处理,不处理的话,就具体请求再处理
    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        Throwable exception = response.getException();
        if (exception instanceof BaseException) {
            if (showErrorFromServer()) {
                ToastUtils.showShortToast(((BaseException) exception).getDisplayMessage());
            }
        } else if (exception instanceof JSONException){
            ToastUtils.showShortToast(BaseException.JSON_ERROR_MESSAGE);
        }
        else{
            ToastUtils.showShortToast(BaseException.NETWORD_ERROR_MESSAGE);
        }
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {
        if (showSuccessFromServer()) {
            T body = response.body();
            if (showSuccessFromServer()) {
                if (body instanceof BaseBean) {
                    ToastUtils.showShortToast(((BaseBean) body).getMessage());
                }
            }
        }
    }
}
