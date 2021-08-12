package com.example.host;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.i(TAG, "宿主应用 MainActivity onCreate");

        // 反射插件包中的 com.example.plugin.MainActivity
        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.example.plugin.MainActivity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Method method = null;
        try {
            method = clazz.getDeclaredMethod("log");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            // 执行 com.example.plugin.MainActivity 的 log 方法
            method.invoke(clazz.newInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        // 设置按钮点击事件
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 启动插件包中的 Activity
                Intent pluginIntent = new Intent();
                pluginIntent.setComponent(new ComponentName("com.example.plugin",
                        "com.example.plugin.MainActivity"));
                pluginIntent.putExtra("isPlugin", true);
                startActivity(pluginIntent);

            }
        });
    }
}