package com.example.plugin_hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InstrumentationProxy extends Instrumentation {

    private static final String TAG = "InstrumentationProxy";
    /**
     * Activity 中原本的 Instrumentation mInstrumentation 成员
     * 从构造函数中进行初始化
     */
    final Instrumentation orginalInstrumentation;

    public InstrumentationProxy(Instrumentation orginalInstrumentation) {
        this.orginalInstrumentation = orginalInstrumentation;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        Log.i(TAG, "注入的 Hook 前执行的业务逻辑");

        // 1. 反射执行 Instrumentation orginalInstrumentation 成员的 execStartActivity 方法
        Method execStartActivity_Method = null;
        try {
            execStartActivity_Method = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity",
                    Context.class,
                    IBinder.class,
                    IBinder.class,
                    Activity.class,
                    Intent.class,
                    int.class,
                    Bundle.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 2. 设置方法可访问性
        execStartActivity_Method.setAccessible(true);

        // 3. 执行 Instrumentation orginalInstrumentation 的 execStartActivity 方法
        //    使用 Object 类型对象接收反射方法执行结果
        ActivityResult activityResult = null;
        try {
            activityResult = (ActivityResult) execStartActivity_Method.invoke(orginalInstrumentation,
                    who,
                    contextThread,
                    token,
                    target,
                    intent,
                    requestCode,
                    options);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "注入的 Hook 后执行的业务逻辑");

        return activityResult;
    }
}
