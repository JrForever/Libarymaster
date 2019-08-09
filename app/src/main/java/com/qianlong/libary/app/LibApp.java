package com.qianlong.libary.app;

import android.content.Context;

import com.qianlong.libary.utils.MIniFile;

public class LibApp {
    private static LibApp mInstance = null;
    private Context mContext;

    private LibApp() {
    }

    public static LibApp getInstance() {
        if (mInstance == null) {
            mInstance = new LibApp();
        }

        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public MIniFile getMIniFile(String cfgFile) {
        MIniFile iniFile = new MIniFile();
        String fileData = iniFile.getFromAssets(mContext, cfgFile);
        iniFile.setData(fileData);
        return iniFile;
    }
}
