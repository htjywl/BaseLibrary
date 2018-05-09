/*
 * Copyright 2016 jeasonlzy(???)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.htjy.baselibrary.http.base;

import com.google.gson.stream.JsonReader;
import com.htjy.baselibrary.bean.BaseBean;
import com.htjy.baselibrary.bean.SimpleBaseBean;
import com.lzy.okgo.convert.Converter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * ================================================
 * ?    ??jeasonlzy?????Github???https://github.com/jeasonlzy
 * ?    ??1.0
 * ?????16/9/11
 * ?    ??
 * ?????
 * ================================================
 */
public class OkRxJsonConvert<T> implements Converter<T> {

    private Type type;
    private Class<T> clazz;

    public OkRxJsonConvert() {
    }

    public OkRxJsonConvert(Type type) {
        this.type = type;
    }

    public OkRxJsonConvert(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * ?????????????ui?????
     * ???????????? response ?????onSuccess??????????
     * ????????????????????,????????,??????????,??????????
     */
    @Override
    public T convertResponse(Response response) throws Throwable {

        // ?????????????????????????????????????????
        // ?????????????????????????????????????????
        // ?????????????????????????????????????????

        // ???????????????????????????: https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback
        // ???????????????????????????: https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback
        // ???????????????????????????: https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback

        if (type == null) {
            if (clazz == null) {
                // ????????????????????????????????????????????
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                return parseClass(response, clazz);
            }
        }

        if (type instanceof ParameterizedType) {
            return parseParameterizedType(response, (ParameterizedType) type);
        } else if (type instanceof Class) {
            return parseClass(response, (Class<?>) type);
        } else {
            return parseType(response, type);
        }
    }

    private T parseClass(Response response, Class<?> rawType) throws Exception {
        if (rawType == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        if (rawType == String.class) {
            //noinspection unchecked
            return (T) body.string();
        } else if (rawType == JSONObject.class) {
            //noinspection unchecked
            return (T) new JSONObject(body.string());
        } else if (rawType == JSONArray.class) {
            //noinspection unchecked
            return (T) new JSONArray(body.string());
        } else {
            T t = GsonConvert.fromJson(jsonReader, rawType);
            response.close();
            return t;
        }
    }

    private T parseType(Response response, Type type) throws Exception {
        if (type == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        // ??????? new JsonCallback<??JavaBean>(this)
        T t = GsonConvert.fromJson(jsonReader, type);
        response.close();
        return t;
    }

    private T parseParameterizedType(Response response, ParameterizedType type) throws Exception {
        if (type == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        Type rawType = type.getRawType();                     // ???????
        Type typeArgument = type.getActualTypeArguments()[0]; // ?????
        if (rawType != BaseBean.class) {
            // ??????? new JsonCallback<??BaseBean<??JavaBean>>(this)
            T t = GsonConvert.fromJson(jsonReader, type);
            response.close();
            return t;
        } else {
            if (typeArgument == Void.class) {
                //?????,????data???????  new DialogCallback<BaseBean<Void>>(this)  ??????????,???String??)
                SimpleBaseBean simpleResponse = GsonConvert.fromJson(jsonReader, SimpleBaseBean.class);
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
            } else {
                // ??????? new JsonCallback<LzyResponse<??JavaBean>>(this)
                BaseBean lzyResponse = GsonConvert.fromJson(jsonReader, type);
                response.close();
                String code = lzyResponse.getCode();
                String message = lzyResponse.getMessage();
                //???0?????
                //????????????????????????????????????????
                if (code.equals(BaseException.STATUS_OK)) {
                    return (T) lzyResponse;
                } else {
                    //?????????????,?????????????
                    throw new BaseException(code, message);
                }
            }
        }
    }
}
