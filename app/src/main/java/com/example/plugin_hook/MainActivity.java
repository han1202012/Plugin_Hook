package com.example.plugin_hook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取按钮 , 并未按钮组件设置点击事件
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Button OnClickListener onClick");
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });

        hookOnClick(button);

        hookStartActivity();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    /**
     * hook Button 组件的 getListenerInfo 方法
     * @param view
     */
    private void hookOnClick(View view){

        // 获取 View 的 getListenerInfo 方法
        Method getListenerInfo = null;
        try {
            getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 执行所有的反射方法 , 设置成员变量 之前 , 都要设置可见性
        getListenerInfo.setAccessible(true);

        // 执行 View view 对象的 getListenerInfo 方法
        Object mListenerInfo = null;
        try {
            mListenerInfo = getListenerInfo.invoke(view);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 反射获取 OnClickListener 成员
        // ① 先根据全类名获取 ListenerInfo 字节码
        Class<?> clazz = null;
        try {
            clazz = Class.forName("android.view.View$ListenerInfo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // ② 获取 android.view.View.ListenerInfo 中的 mOnClickListener 成员
        Field field = null;
        try {
            field = clazz.getField("mOnClickListener");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // ③ 设置该字段访问性, 执行所有的反射方法 , 设置成员变量 之前 , 都要设置可见性
        field.setAccessible(true);

        // ④ 获取 mOnClickListener 成员变量
        View.OnClickListener mOnClickListener = null;
        try {
            mOnClickListener = (View.OnClickListener) field.get(mListenerInfo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // ⑤ 修改 View 的 ListenerInfo 成员的 mOnClickListener 成员
        // 其中 ListenerInfo 成员 是
        try {
            View.OnClickListener finalMOnClickListener = mOnClickListener;
            field.set(mListenerInfo, new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Hook Before");
                    finalMOnClickListener.onClick(view);
                    Log.i(TAG, "Hook After");
                }
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hook Activity 界面启动过程
     * 钩住 Instrumentation 的 execStartActivity 方法
     * 向该方法中注入自定义的业务逻辑
     */
    private void hookStartActivity(){

        // 1. 获取 Activity 字节码文件
        //    字节码文件是所有反射操作的入口
        Class<?> clazz = Activity.class;

        // 2. 获取 Activity 的 Instrumentation mInstrumentation 成员 Field 字段
        Field mInstrumentation_Field = null;
        try {
            mInstrumentation_Field = clazz.getDeclaredField("mInstrumentation");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // 3. 设置 Field mInstrumentation 字段的可访问性
        mInstrumentation_Field.setAccessible(true);

        // 4. 获取 Activity 的 Instrumentation mInstrumentation 成员对象值
        //      获取该成员的意义是 , 创建 Instrumentation 代理时, 需要将原始的 Instrumentation 传入代理对象中
        Instrumentation mInstrumentation = null;
        try {
            mInstrumentation = (Instrumentation) mInstrumentation_Field.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 5. 将 Activity 的 Instrumentation mInstrumentation 成员变量
        //      设置为自己定义的 Instrumentation 代理对象
        try {
            mInstrumentation_Field.set(this, new InstrumentationProxy(mInstrumentation));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }


}