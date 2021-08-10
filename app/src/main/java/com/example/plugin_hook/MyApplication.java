package com.example.plugin_hook;

import android.app.Application;
import android.util.Log;

import kim.hsl.plugin.HookUtils;
import kim.hsl.plugin.PluginManager;

public class MyApplication extends Application {

    private static final String TAG = "plugin_MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        // 先将 assets 中的插件包拷贝到 内置存储中
        CommandUtils.copyAssets2File(
                this,
                "plugin.apk",
                getFilesDir() + "/plugin.apk");

        // 将文件从 assets/plugin.apk 拷贝到 /data/user/0/com.example.plugin_hook/files/plugin.apk
        Log.i(TAG, "将文件从 assets/plugin.apk 拷贝到 " + getFilesDir() + "/plugin.apk");


        // 初始化插件包
        PluginManager.getInstance(this).init();

        Log.i(TAG, "插件化 初始化完毕");
    }

}
