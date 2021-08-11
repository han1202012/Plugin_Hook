package com.example.plugin_hook;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;

import kim.hsl.plugin.PluginManager;

public class MyApplication extends Application {

    private static final String TAG = "plugin_MyApplication";

    /**
     * 插件资源, 这种方式侵入代码 , 造成开发的差异性 , 建议使用 Hook 加载插件资源
     */
    //private Resources pluginResources;

    @Override
    public void onCreate() {
        super.onCreate();

        // 如果已经存在文件, 先删除 , 防止拷贝过程中出错
        File pluginFile = new File(getFilesDir() + "/plugin.apk");
        if (pluginFile.exists()){
            pluginFile.delete();
        }

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

        // 设置插件包中的资源文件, 这种方式侵入代码 , 造成开发的差异性 , 建议使用 Hook 加载插件资源
        //pluginResources = PluginManager.getInstance(this).getmResources();

    }

    /*
    // 这种方式侵入代码 , 造成开发的差异性 , 建议使用 Hook 加载插件资源
    @Override
    public Resources getResources() {
        if (pluginResources != null)
            return pluginResources;
        return super.getResources();
    }*/
}
