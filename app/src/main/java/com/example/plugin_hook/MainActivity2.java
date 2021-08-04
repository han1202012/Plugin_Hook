package com.example.plugin_hook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity2 extends Activity {

    private static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Log.i(TAG, "MainActivity2 onCreate");

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
    }
}