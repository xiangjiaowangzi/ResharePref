package com.example.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.library.annotations.PrefGet;
import com.example.library.annotations.PrefModel;
import com.example.library.annotations.PrefPut;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Lbin on 2017/10/24.
 */

public class ReSharePref {

    private Context mContext;

    private static volatile ReSharePref instance;

    private final Map<Method, ServiceMethod> serviceMethodCache = new LinkedHashMap<>();

    public static ReSharePref getInstance() {
        if (instance == null) {
            synchronized (ReSharePref.class) {
                if (instance == null) {
                    instance = new ReSharePref();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
    }

    public <T> T create(final Class<T> pref) {
        Objects.requireNonNull(mContext);
        Objects.requireNonNull(pref);
        if (!pref.isInterface() || !isPrefModelAnnotations(pref)) {
            throw new IllegalArgumentException("class must be interfaces and annotation must be PrefModel.class");
        }
        final String eraxName = pref.getAnnotation(PrefModel.class).value();
        final String prefName = pref.getCanonicalName() + eraxName;
        initServiceMethod(pref, prefName);
        return (T) Proxy.newProxyInstance(pref.getClassLoader(), new Class[]{pref}
                , new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        ServiceMethod result = loadServiceMethod(prefName , method);
                        return result.invokerMethod(args);
//                        return null;
                    }
                });
    }

    private void initServiceMethod(Class<?> pref, String prefName) {
        for (Method method : pref.getDeclaredMethods()) {
            loadServiceMethod(prefName, method);
        }
    }

    private ServiceMethod loadServiceMethod(String prefName, Method method) {
        ServiceMethod result;
        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod.Builder(prefName, method).build();
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }

    public boolean isPrefModelAnnotations(final Class clazz) {
        return clazz.isAnnotationPresent(PrefModel.class);
    }
}
