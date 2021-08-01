package kim.hsl.plugin;

import android.content.Context;

import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

/**
 * 使用 Hook 实现的插件使用入口
 *  1. 加载插件包中的字节码
 *  2. 直接通过 hook 技术, 钩住系统的 Activity 启动流程实现
 *      ① Activity 对象创建之前 , 要做很多初始化操作 , 先在 ActivityRecord 中加载 Activity 信息
 *          如果修改了该信息 , 将要跳转的 Activity 信息修改为插件包中的 Activity
 *          原来的 Activity 只用于占位 , 用于欺骗 Android 系统
 *      ② 使用 hook 技术 , 加载插件包 apk 中的 Activity
 *      ③ 实现跳转的 Activity ( 插件包中的 )
 *  3. 解决 Resources 资源冲突问题
 *  ( 使用上述 hook 插件化 , 可以不用考虑 Activity 的声明周期问题 )
 *
 *  插件包中的 Activity 是通过正规流程 , 由 AMS 进行创建并加载的
 *      但是该 Activity 并没有在 AndroidManifest.xml 清单文件中注册
 *      这里需要一个已经在清单文件注册的 Activity 欺骗系统
 *
 *  插装式插件化 是通过代理 Activity , 将插件包加载的字节码 Class 作为一个普通的 Java 类
 *      该普通的 Java 类有所有的 Activity 的业务逻辑
 *      该 Activity 的声明周期 , 由代理 Activity 执行相关的生命周期方法
 *  hook 插件化 : hook 插件化直接钩住系统中 Activity 启动流程的某个点
 *      使用插件包中的 Activity 替换占位的 Activity
 */
public class PluginManager {

    /**
     * 上下文
     */
    private Context mBase;

    /**
     * 单例
     */
    private static PluginManager instance;

    public static PluginManager getInstance(Context context) {
        if (instance == null) {
            instance = new PluginManager(context);
        }
        return instance;
    }

    private PluginManager(Context context) {
        this.mBase = context;
    }

    /**
     * Application 启动后 , 调用该方法初始化插件化环境
     *  加载插件包中的字节码
     */
    private void init() {
        // 加载 apk 文件
        loadApk();
    }

    private void loadApk() {
        // 插件包的绝对路径 ,  /data/data/< package name >/files/
        String apkPath = mBase.getFilesDir().getAbsolutePath() + "x.apk";
        // 加载插件包后产生的缓存文件路径
        // data/data/< package name >/app_plugin_cache
        String cachePath =
                mBase.getDir("plugin_cache", Context.MODE_PRIVATE).getAbsolutePath();
        // 创建类加载器
        DexClassLoader dexClassLoader =
                new DexClassLoader(
                        apkPath,                // 插件包路径
                        cachePath,              // 插件包加载时产生的缓存路径
                        null,   // 库的搜索路径, 可以设置为空
                        mBase.getClassLoader()  // 父加载器, PathClassLoader
                );

        // 1. 反射 " 插件包 " 应用的 dexElement

        // 通过反射获取插件包中的 dexElements
        // 这种类加载是合并类加载 , 将所有的 Dex 文件 , 加入到应用的 dex 文件集合中
        //  可参考 dex 加固 , 热修复 , 插装式插件化 的实现步骤
        // 反射出 BaseDexClassLoader 类 , PathClassLoader 和 DexClassLoader
        //  都是 BaseDexClassLoader 的子类
        Class<?> baseDexClassLoaderClass = null;
        try {
            baseDexClassLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 获取 BaseDexClassLoader 类中的
        //  private final DexPathList pathList 成员变量
        Field pathListField = null;
        try {
            pathListField = baseDexClassLoaderClass.getDeclaredField("pathList");
            // 设置属性的可见性
            pathListField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // 获取 DexClassLoader dexClassLoader 类加载器的 DexPathList pathList 成员变量
        //  DexClassLoader 继承了 BaseDexClassLoader, 因此其内部肯定有
        //  private final DexPathList pathList 成员变量
        Object pathListObject = null;
        try {
            pathListObject = pathListField.get(dexClassLoader);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 2. 反射 " 宿主 " 应用的 dexElement

        // 获取 DexPathList pathList 成员变量的字节码类型
        Class<?> dexPathListClass = pathListObject.getClass();





    }

}
