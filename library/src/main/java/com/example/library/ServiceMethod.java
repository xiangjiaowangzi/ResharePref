package com.example.library;

import android.support.annotation.IntDef;
import android.text.TextUtils;

import com.example.library.annotations.PrefGet;
import com.example.library.annotations.PrefPut;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

/**
 * Created by Lbin on 2017/10/24.
 */

public class ServiceMethod {

    private static final int PUT = 1;
    private static final int GET = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PUT, GET})
    public @interface TYPE {
    }

    /**
     * 名称
     */
    private String prefName;

    /**
     * key值
     */
    private String key;

    /**
     * 类型
     */
    private int mType;

    public ServiceMethod() {
    }

    public ServiceMethod(Builder builder) {
        this.prefName = builder.prefName;
        this.key = builder.mKey;
        this.mType = builder.type;
    }

    public Object invokerMethod(Object[] args){
        return "" ;
    }

    static class Builder {
        final Method method;
        final String prefName;
        final Annotation[] methodAnnotations;
        private int type = 1;
        String mKey;

        public Builder(String prefName, Method method) {
            this.prefName = prefName;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
        }

        public ServiceMethod build() {
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }
            return new ServiceMethod(this);
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof PrefGet) {//获取shredprefance内容
                String key = ((PrefGet) annotation).value();
                if (TextUtils.isEmpty(key)) {
                    throw new NullPointerException(" the key is null , please check the code !!!");
                }
                mKey = key;
                type = GET;
            } else if (annotation instanceof PrefPut) {
                String key = ((PrefPut) annotation).value();
                if (TextUtils.isEmpty(key)) {
                    throw new NullPointerException(" the key is null , please check the code !!!");
                }
                mKey = key;
                type = PUT;
            }
        }
    }
}
