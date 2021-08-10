package kim.hsl.plugin;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * 主要职责 : Hook Activity 的启动过程
 * 本工具类只针对 API Level 28 实现 , 如果是完整插件化框架 , 需要实现所有版本的 Hook 过程
 * 不同的版本 , Activity 的启动过程是不同的 , 需要逐个根据 Activity 启动源码进行 Hook 适配
 */
public class HookUtils {

    private static final String TAG = "HookUtils";

    /**
     * 最终目的是劫持 ActivityManagerService 的 startActivity 方法 ,
     *      修改 Intent 中药启动的 Activity 类
     */
    public static void hookAms(Context context){
        // 获取 android.app.ActivityManager 类
        Class<?> activityManagerClass = null;
        try {
            activityManagerClass = Class.forName("android.app.ActivityManager");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 获取 android.app.ActivityManager 类 中的 IActivityManagerSingleton 属性
        // private static final Singleton<IActivityManager> IActivityManagerSingleton 成员变量
        Field iActivityManagerSingletonField = null;
        try {
            iActivityManagerSingletonField =
                    activityManagerClass.getDeclaredField("IActivityManagerSingleton");
            // 设置成员字段的可访问性
            iActivityManagerSingletonField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // 获取 android.app.ActivityManager 类的静态成员变量
        // private static final Singleton<IActivityManager> IActivityManagerSingleton
        // 直接调用 Field 字段 iActivityManagerSingletonField 的 get 方法 , 传入 null 即可获取
        Object iActivityManagerSingletonObject = null;
        try {
            iActivityManagerSingletonObject = iActivityManagerSingletonField.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 获取 Singleton 类
        // ActivityManager 中的 IActivityManagerSingleton 成员是 Singleton<IActivityManager> 类型的
        Class<?> singletonClass = null;
        try {
            singletonClass = Class.forName("android.util.Singleton");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 反射获取 Singleton 类中的 mInstance 字段
        Field mInstanceField = null;
        try {
            mInstanceField = singletonClass.getDeclaredField("mInstance");
            // 设置字段的可访问性
            mInstanceField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // 反射获取 Singleton 类中的 mInstance 成员对象
        // 该 mInstanceObject 成员对象就是 IActivityManager
        // private static final Singleton<IActivityManager> IActivityManagerSingleton
        Object mInstanceObject = null;
        try {
            mInstanceObject = mInstanceField.get(iActivityManagerSingletonObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 使用动态代理 , 替换 android.app.ActivityManager 中的
        // private static final Singleton<IActivityManager> IActivityManagerSingleton 成员的
        // mInstance 成员
        // 注意 : 该操作一定要在 AMS 启动之前将原来的 Intent 替换掉
        //          之后还要替换回去
        // 使用 Intent 启动插件包时 , 一般都使用隐式启动
        // 调用 Intent 的 setComponent , 通过包名和类名创建 Component ,
        //      这样操作 , 即使没有获得 Activity 引用 , 也不会报错
        // 该插件包中的 Activity 没有在 "宿主" 应用中注册 , 因此启动报错
        //      AMS 会干掉没有注册过的 Activity
        //      这里先在启动 AMS 之前 , 设置一个已经 注册过的 占坑 Activity ( StubActivity ) 执行启动流程
        //      在主线程生成 Activity 实例对象时 , 还需要恢复插件包中的 Activity

        // IActivityManager 是接口
        // 这是一个 AIDL 文件生成的 , 由 IActivityManager.aidl 生成
        Class<?> IActivityManagerInterface = null;
        try {
            IActivityManagerInterface = Class.forName("android.app.IActivityManager");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 动态代理的实际代理类
        AmsInvocationHandler amsInvocationHandler =
                new AmsInvocationHandler(context, mInstanceObject);

        // 动态代理过程
        Object proxy = Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(), // 类加载器
                new Class[]{IActivityManagerInterface},         // 接口
                amsInvocationHandler);                          // 代理的对象

        // 使用动态代理类 , 替换原来的 ActivityManager 中的 IActivityManagerSingleton 成员
        //      的 Singleton 类中的 mInstance 成员
        try {
            mInstanceField.set(iActivityManagerSingletonObject, proxy);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 劫持 Activity Thread 的 final H mH = new H(); 成员
     * 该成员类型是 class H extends Handler ;
     * @param context
     */
    public static void hookActivityThread(Context context) {
        // 反射获取 ActivityThread 类
        Class<?> activityThreadClass = null;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Activity Thread 是一个单例 , 内部的单例成员是
        // private static volatile ActivityThread sCurrentActivityThread;
        // 可以直接通过 ActivityThread 类 , 获取该单例对象
        // 这也是 Hook 点优先找静态变量的原因 , 静态变量对象容易拿到 , 通过反射即可获取 , 不涉及系统源码相关操作
        Field sCurrentActivityThreadField = null;
        try {
            sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            // 反射获取的字段一般都要设置可见性
            sCurrentActivityThreadField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // 获取类的静态变量 , 使用 字段.get(null) 即可
        Object activityThreadObject = null;
        try {
            activityThreadObject = sCurrentActivityThreadField.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 获取 Activity Thread 中的 final H mH = new H() 成员字段 ;
        Field mHField = null;
        try {
            mHField = activityThreadClass.getDeclaredField("mH");
            // 设置该字段的可见性
            mHField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // 通过反射获取 Activity Thread 中的 final H mH = new H() 成员实例对象
        Handler mHObject = null;
        try {
            mHObject = (Handler) mHField.get(activityThreadObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Class<?> handlerClass = null;
        try {
            handlerClass = Class.forName("android.os.Handler");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 通过反射获取 final H mH = new H() 成员的 mCallback 成员字段
        // Handler 中有成员变量 final Callback mCallback;
        Field mCallbackField = null;
        try {
            // 类可以直接获取到, 可以不用反射
            mCallbackField = Handler.class.getDeclaredField("mCallback");
            //mCallbackField = mHObject.getClass().getDeclaredField("mCallback");
            // 设置字段的可见性
            mCallbackField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // 使用静态代理类 HandlerProxy , 替换 final H mH = new H() 成员实例对象中的 mCallback 成员
        HandlerProxy proxy = new HandlerProxy();
        try {
            Log.i(TAG, "mCallbackField : " + mCallbackField + " , mHObject : " + mHObject + " , proxy : " + proxy);
            mCallbackField.set(mHObject, proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
