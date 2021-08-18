package kim.hsl.plugin;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;

import java.lang.reflect.Field;

public class InstrumentationProxy extends Instrumentation {
    private static final String TAG = "InstrumentationProxy";

    /**
     * 持有被代理对象
     * 有一些操作需要使用原来的 Instrumentation 进行操作
     */
    private final Instrumentation mBase;

    /**
     * 在构造方法中注入被代理对象
     * @param mBase
     */
    public InstrumentationProxy(Instrumentation mBase) {
        this.mBase = mBase;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        ActivityResult result = null;

        // 反射调用 Instrumentation mBase 成员的 execStartActivity 方法
        result = Reflector.on("android.app.Instrumentation")
                .method("execStartActivity",    // 反射的方法名
                        Context.class,                // 后续都是方法的参数类型
                        IBinder.class,
                        IBinder.class,
                        Activity.class,
                        Intent.class,
                        int.class,
                        Bundle.class)
                .with(mBase)
                .call(who,                             // 后续都是传入 execStartActivity 方法的参数
                        contextThread,
                        token,
                        target,
                        intent,
                        requestCode,
                        options);

        return result;
    }

    /**
     * 在该方法中 , 可以拿到 Activity , 通过反射修改 Activity 中的 Resources 成员变量
     * @param cl
     * @param className
     * @param intent
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Activity activity = mBase.newActivity(cl, className, intent);
        // 替换 Activity 中的 Resources
        exchangeResourcesOfActivity(activity, intent);
        return activity;
    }

    /**
     * 在该方法中 , 可以拿到 Activity , 通过反射修改 Activity 中的 Resources 成员变量
     * @param clazz
     * @param context
     * @param token
     * @param application
     * @param intent
     * @param info
     * @param title
     * @param parent
     * @param id
     * @param lastNonConfigurationInstance
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    public Activity newActivity(Class<?> clazz, Context context, IBinder token, Application application, Intent intent, ActivityInfo info, CharSequence title, Activity parent, String id, Object lastNonConfigurationInstance) throws IllegalAccessException, InstantiationException {
        Activity activity = mBase.newActivity(clazz, context, token, application, intent, info, title, parent, id, lastNonConfigurationInstance);
        // 替换 Activity 中的 Resources
        exchangeResourcesOfActivity(activity, intent);
        return activity;
    }

    /**
     * 反射 Activity , 并设置 Activity 中 Resources 成员变量
     * @param activity
     * @param intent
     */
    private void exchangeResourcesOfActivity(Activity activity, Intent intent) {

        // 这里注意 : 所有的 Activity 创建 , 都会过这个方法 , 这里只将插件包中的 Activity 的资源替换

        // 这里要做一个判断
        //      不能修改宿主应用的资源
        //      只有插件包中的 Activity 才进行相应的修改
        // 在调用插件包中的组件时 , 在 Intent 中传入一个 isPlugin 变量 ,
        //      也可以传入插件的标志位 , 区分不同的插件包
        //      这里只有一个插件包 , 只设置一个 Boolean 变量即可
        if (!intent.getBooleanExtra("isPlugin", false)) return;


        // 获取插件资源
        Resources pluginResources = PluginManager.getInstance(activity).getResources();

        // 反射 ContextThemeWrapper 类 , Activity 是 ContextThemeWrapper 的子类
        // Resources mResources 成员定义在 ContextThemeWrapper 中
        Class<?> contextThemeWrapperClass = null;
        try {
            contextThemeWrapperClass = Class.forName("android.view.ContextThemeWrapper");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 反射获取 ContextThemeWrapper 类的 mResources 字段
        Field mResourcesField = null;
        try {
            mResourcesField = contextThemeWrapperClass.getDeclaredField("mResources");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        // 设置字段可见性
        mResourcesField.setAccessible(true);

        // 将插件资源设置到插件 Activity 中
        try {
            mResourcesField.set(activity, PluginManager.getInstance(activity).getResources());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
