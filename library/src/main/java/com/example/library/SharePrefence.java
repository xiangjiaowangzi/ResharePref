package com.example.library;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created by Lbin on 2017/10/24.
 */

public class SharePrefence {

    Context context;
    String prefName;

    private SharedPreferences.Editor mEditor;

    private boolean isInTransaction = false;

    public SharePrefence(String prefName, Context context) {
        this.prefName = prefName;
        this.context = context;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }


    public SharedPreferences.Editor getEditor() {
        if (mEditor == null) {
            mEditor = getSharedPreferences().edit();
        }
        return mEditor;
    }

    public Set<String> getStringSet(String key, Set<String> def) {
        return getSharedPreferences().getStringSet(key, def);
    }

    public Map<String, ?> getAll() {
        return getSharedPreferences().getAll();
    }

    public SharePrefence putInt(String key, int value) {
        wrap(getEditor().putInt(key, value));
        return this;
    }

    public SharePrefence putLong(String key, long value) {
        wrap(getEditor().putLong(key, value));
        return this;
    }

    public SharePrefence putFloat(String key, float value) {
        wrap(getEditor().putFloat(key, value));
        return this;
    }

    public SharePrefence putBoolean(String key, boolean value) {
        wrap(getEditor().putBoolean(key, value));
        return this;
    }

    public SharePrefence putString(String key, String value) {
        wrap(getEditor().putString(key, value));
        return this;
    }

    public SharePrefence putStringSet(String key, Set<String> value) {
        wrap(getEditor().putStringSet(key, value));
        return this;
    }

    public SharePrefence remove(String key) {
        wrap(getEditor().remove(key));
        return this;
    }

    public void clear() {
        getEditor().clear().commit();
        this.isInTransaction = false;
    }

    public boolean commit() {
        boolean success = getEditor().commit();
        this.isInTransaction = false;
        return success;
    }

    public void apply() {
        getEditor().apply();
        this.isInTransaction = false;
    }

    public SharePrefence beginTransaction() {
        this.isInTransaction = true;
        return this;
    }

    private void wrap(SharedPreferences.Editor editor) {
        if (!isInTransaction) {
            editor.apply();
        }
    }


    public int getInt(String key, int def) {
        return getSharedPreferences().getInt(key, def);
    }

    public long getLong(String key, long def) {
        return getSharedPreferences().getLong(key, def);
    }

    public float getFloat(String key, float def) {
        return getSharedPreferences().getFloat(key, def);
    }

    public boolean getBoolean(String key, boolean def) {
        return getSharedPreferences().getBoolean(key, def);
    }

    public String getString(String key, String def) {
        return getSharedPreferences().getString(key, def);
    }


}
