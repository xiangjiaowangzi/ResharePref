package com.example.library;

import android.support.annotation.IntDef;
import android.text.InputFilter;
import android.text.TextUtils;

import com.example.library.annotations.PrefGet;
import com.example.library.annotations.PrefPut;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

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
    private String mKey;

    /**
     * 类型
     */
    private int mType;
    private Class<?> mReturnType;

    public ServiceMethod() {
    }

    public ServiceMethod(Builder builder) {
        this.prefName = builder.prefName;
        this.mKey = builder.key;
        this.mType = builder.type;
        this.mReturnType = builder.returnType;
//        Utils.log(" prefName " + prefName);
//        Utils.log(" mKey " + mKey);
//        Utils.log(" mType " + mType);
//        Utils.log(" mReturnType " + mReturnType.getCanonicalName());
    }

    public Object invokerMethod(Object[] args) {
        switch (mType) {
            case PUT:
                return parsePutMethod(args);
            case GET:
                return parseGetMethod(args);
        }
        return "";
    }

    private Object parsePutMethod(Object[] args) {
//        Utils.log("PUT");
        SharePrefence prefence = ReSharePref.getInstance().getSharedPrefence(prefName);
        boolean success = false;
        for (Object o : args) {
            if (o instanceof Integer) {
                success = prefence.putInt(mKey, (int) o).commit();
            } else if (o instanceof String) {
                success = prefence.putString(mKey, (String) o).commit();
            } else if (o instanceof Long) {
                success = prefence.putLong(mKey, (Long) o).commit();
            } else if (o instanceof Float) {
                success = prefence.putFloat(mKey, (Float) o).commit();
            } else if (o instanceof Boolean) {
                success = prefence.putBoolean(mKey, (Boolean) o).commit();
            } else {
                success = prefence.putString(mKey, o.toString()).commit();
            }
        }
        return success;
    }

    private Object parseGetMethod(Object[] args) {
//        Utils.log("GET");
        SharePrefence prefence = ReSharePref.getInstance().getSharedPrefence(prefName);
        switch (mReturnType.getCanonicalName()) {
            case "int":
                return prefence.getInt(mKey, 0);
            case "float":
                return prefence.getFloat(mKey, 0);
            case "long":
                return prefence.getLong(mKey, 0);
            case "boolean":
                return prefence.getBoolean(mKey, false);
            case "java.lang.String":
                return prefence.getString(mKey, "");
        }
        return "";
    }

    static class Builder {
        final Method method;
        final String prefName;
        final Annotation[] methodAnnotations;
        private int type = 1;
        String key;
        Class<?> returnType;

        public Builder(String prefName, Method method) {
            this.prefName = prefName;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.returnType = method.getReturnType();
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
                this.key = key;
                type = GET;
            } else if (annotation instanceof PrefPut) {
                String key = ((PrefPut) annotation).value();
                if (TextUtils.isEmpty(key)) {
                    throw new NullPointerException(" the key is null , please check the code !!!");
                }
                this.key = key;
                type = PUT;
            }
        }
    }
}
