package kim.hsl.plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * 主要职责 : Hook Activity 的启动过程
 * 本工具类只针对 API Level 28 实现 , 如果是完整插件化框架 , 需要实现所有版本的 Hook 过程
 * 不同的版本 , Activity 的启动过程是不同的 , 需要逐个根据 Activity 启动源码进行 Hook 适配
 */
public class HookUtils {

    /**
     * 最终目的是劫持 ActivityManagerService 的 startActivity 方法 ,
     *      修改 Intent 中药启动的 Activity 类
     */
    public static void hookAms(){
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

        // IActivityManager 是接口
        // 这是一个 AIDL 文件生成的 , 由 IActivityManager.aidl 生成
        Class<?> IActivityManagerInterface = null;
        try {
            IActivityManagerInterface = Class.forName("android.app.IActivityManager");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 动态代理过程
        Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(), // 类加载器
                new Class[]{IActivityManagerInterface},         // 接口
                null);                                      // 代理的对象

    }

}
