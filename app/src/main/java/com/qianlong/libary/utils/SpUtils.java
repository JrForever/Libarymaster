package com.qianlong.libary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpUtils {

    private static final String TAG = SpUtils.class.getSimpleName();

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "ql_data";

    private static SharedPreferences saveInfo;
    private static Editor saveEditor;
    private static volatile SpUtils spUtils;
    private Context context;

    private SpUtils(Context context) {
        this.context = context;
        saveInfo = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        saveEditor = saveInfo.edit();
    }

    public static SpUtils getInstance(Context context) {
        if (spUtils == null) {
            synchronized (SpUtils.class) {
                if (spUtils == null) {
                    spUtils = new SpUtils(context);
                }
            }
        }
        return spUtils;
    }

    public void putString(String key, String value) {
        saveEditor.putString(key, value);
        saveEditor.apply();
    }

    public Editor buildString(String key, String value) {
        return saveEditor.putString(key, value);
    }

    public String getString(String key) {
//		if (key.equals("qs_self_code_account"))
//		{
//			return "11011911011";
//		}
        return saveInfo.getString(key, "");
    }

    public String getString(String key, String value) {
        return saveInfo.getString(key, value);
    }

    public void putInt(String key, int value) {
        saveEditor.putInt(key, value);
        saveEditor.commit();
    }

    public Editor buildInt(String key, int value) {
        return saveEditor.putInt(key, value);
    }

    public int getInt(String key) {
        return saveInfo.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return saveInfo.getInt(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        saveEditor.putBoolean(key, value);
        saveEditor.apply();
    }

    public Editor buildBoolean(String key, boolean value) {
        return saveEditor.putBoolean(key, value);
    }

    public void apply() {
        saveEditor.apply();
    }

    public boolean getBoolean(String key) {
        return saveInfo.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return saveInfo.getBoolean(key, defaultValue);
    }

    /**
     * 保存ArrayList
     */
    public void putArray(List<String> sKey, String keyName) {
        if (sKey == null)
            return;
        int size = sKey.size();
        saveEditor.putInt(keyName + "_num", size); /* sKey is an array */

        for (int i = 0; i < size; i++) {
            saveEditor.remove(keyName + "_id_" + i);
            saveEditor.putString(keyName + "_id_" + i, sKey.get(i));
        }

        saveEditor.apply();
    }

    /**
     * 获取ArrayList
     */
    public List<String> getArray(String keyName) {
        List<String> sKey = new ArrayList<>();
        int size = saveInfo.getInt(keyName + "_num", 0);

        for (int i = 0; i < size; i++) {
            sKey.add(saveInfo.getString(keyName + "_id_" + i, ""));
        }

        return sKey;
    }

    public void clearArray(String keyName) {
        int size = saveInfo.getInt(keyName + "_num", 0);
        for (int i = 0; i < size; i++) {
            saveEditor.remove(keyName);
        }
        saveEditor.remove(keyName + "_num");
        saveEditor.apply();
    }

    /**
     * 获取ArrayList
     */
    public <T> List<T> getDataList(String tag, Class<T> cls) {
        List<T> datalist = new ArrayList<T>();
        try {
            String strJson = saveInfo.getString(tag, null);
            if (null == strJson) {
                return datalist;
            }
            Gson gson = new Gson();
            JsonArray array = new JsonParser().parse(strJson).getAsJsonArray();
            for (JsonElement element : array) {
                datalist.add(gson.fromJson(element, cls));
            }
        } catch (Exception e) {
            Log.e(TAG, "getDataList:Exception");
        }

        return datalist;

    }

    /**
     * 存储arraylist
     */
    public <T> void putDataList(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        saveEditor.remove(tag);
        saveEditor.putString(tag, strJson);
        saveEditor.apply();
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        saveEditor.remove(key);
        saveEditor.apply();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        saveEditor.clear();
        saveEditor.apply();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return saveInfo.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        return saveInfo.getAll();
    }

    /**
     * 保存 账号信息
     */
    public void saveKey(String mKey, String mValue) {
        SharedPreferences mSharePrefs = context.getSharedPreferences(
                "Account_info", 0);
        Editor editor;
        if (mSharePrefs != null) {
            editor = mSharePrefs.edit();
            editor.putString(mKey, mValue);
            editor.apply();
        }
    }

    public String getKeyStringValue(String mKey, String mDefValue) {
        String mStr = null;
        SharedPreferences mSharePrefs = context.getSharedPreferences("Account_info", 0);
        if (mSharePrefs != null) {
            mStr = mSharePrefs.getString(mKey, mDefValue);
        }
        return mStr;
    }

    public void clear(String PrefName) {
        SharedPreferences mSharePrefs = context.getSharedPreferences("Account_info",
                0);
        Editor editor;
        if (mSharePrefs != null) {
            editor = mSharePrefs.edit();
            editor.remove(PrefName);
//				editor.clear();
            editor.apply();
        }
    }
}