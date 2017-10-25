package com.example.library;

import android.support.annotation.IntDef;
import android.text.TextUtils;

import com.example.library.annotations.PrefBody;
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

    private static final int NONE = 0;
    private static final int PUT = 1;
    private static final int GET = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NONE, PUT, GET})
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
     * 参数key值
     */
    private String[] parmeterKeys;

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
        this.parmeterKeys = builder.parmeterKeys;
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
        if (parmeterKeys.length != args.length) {
            throw new ArrayIndexOutOfBoundsException("the length is different ");
        }
        SharePrefence prefence = ReSharePref.getInstance().getSharedPrefence(prefName);
        prefence.beginTransaction();
        boolean success = false;
        int i = 0;
        for (Object o : args) {
            if (o instanceof Integer) {
                prefence.putInt(parmeterKeys[i++], (int) o);
            } else if (o instanceof String) {
                prefence.putString(parmeterKeys[i++], (String) o);
            } else if (o instanceof Long) {
                prefence.putLong(parmeterKeys[i++], (Long) o);
            } else if (o instanceof Float) {
                prefence.putFloat(parmeterKeys[i++], (Float) o);
            } else if (o instanceof Boolean) {
                prefence.putBoolean(parmeterKeys[i++], (Boolean) o);
            } else {
                prefence.putString(parmeterKeys[i++], o.toString());
            }
            success = prefence.commit();
        }
        return success;
    }

    private Object parseGetMethod(Object[] args) {
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
        final Annotation[][] parmeterAnnotations;
        String[] parmeterKeys;
        final Class<?> returnType;
        private int type = NONE;
        String key;

        public Builder(String prefName, Method method) {
            this.prefName = prefName;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.returnType = method.getReturnType();
            this.parmeterAnnotations = method.getParameterAnnotations();
        }

        public ServiceMethod build() {
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }
            if (type == PUT) {
                if (parmeterAnnotations.length < 0) {
                    throw new NullPointerException(" the parmeterAnnotation is null , please check the code !!!");
                }
                parseParmeterAnnotation();
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
                type = PUT;
            }
        }

        private void parseParmeterAnnotation() {
            parmeterKeys = new String[parmeterAnnotations.length];
            int i = 0;
            for (Annotation[] as : parmeterAnnotations) {
                for (Annotation a : as) {
                    if (a instanceof PrefBody) {
                        String value = ((PrefBody) a).value();
                        if (TextUtils.isEmpty(value)){
                            throw new NullPointerException(" the permater key is null , please check the code !!!");
                        }
                        parmeterKeys[i++] = value;
                    }
                }
            }
        }
    }
}
