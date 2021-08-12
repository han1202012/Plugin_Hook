package kim.hsl.plugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * 使用 Hook 实现的插件使用入口 <br><br>
 *  1. 加载插件包中的字节码<br><br>
 *  2. 直接通过 hook 技术, 钩住系统的 Activity 启动流程实现<br>
 *      ① Activity 对象创建之前 , 要做很多初始化操作 , 先在 ActivityRecord 中加载 Activity 信息<br>
 *          如果修改了该信息 , 将要跳转的 Activity 信息修改为插件包中的 Activity<br>
 *          原来的 Activity 只用于占位 , 用于欺骗 Android 系统<br>
 *      ② 使用 hook 技术 , 加载插件包 apk 中的 Activity<br>
 *      ③ 实现跳转的 Activity ( 插件包中的 )<br><br>
 *  3. 解决 Resources 资源冲突问题
 *  ( 使用上述 hook 插件化 , 可以不用考虑 Activity 的声明周期问题 )
 *  <br><br>
 *  插件包中的 Activity 是通过正规流程 , 由 AMS 进行创建并加载的
 *      但是该 Activity 并没有在 AndroidManifest.xml 清单文件中注册
 *      这里需要一个已经在清单文件注册的 Activity 欺骗系统<br><br>
 *
 *  插装式插件化 是通过代理 Activity , 将插件包加载的字节码 Class 作为一个普通的 Java 类<br>
 *      该普通的 Java 类有所有的 Activity 的业务逻辑<br>
 *      该 Activity 的声明周期 , 由代理 Activity 执行相关的生命周期方法<br>
 *  hook 插件化 : hook 插件化直接钩住系统中 Activity 启动流程的某个点<br>
 *      使用插件包中的 Activity 替换占位的 Activity<br>
 */
public class PluginManager {

    /**
     * 上下文
     */
    private Context mBase;

    /**
     * 单例
     */
    private static PluginManager mInstance;

    /**
     * 要加载的插件包中的资源文件
     */
    private Resources mResources;

    public static PluginManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PluginManager(context);
        }
        return mInstance;
    }

    private PluginManager(Context context) {
        this.mBase = context;
    }

    /**
     * Application 启动后 , 调用该方法初始化插件化环境
     *  加载插件包中的字节码
     */
    public void init() {
        // 加载 apk 文件
        loadApk();

        // 加载插件包中的资源文件
        loadResources();

        // 在 AMS 启动之前使用占坑 Activity 替换插件包 Activity
        HookUtils.hookAms(mBase);

        // 在 AMS 执行完毕后 , 主线程 ActivityThread 中创建 Activity 实例对象之间 ,
        //      再将插件包 Activity 替换回去
        HookUtils.hookActivityThread(mBase);

        // 通过 Hook 方式修改 Activity 中的 Resources 资源
        HookUtils.hookInstrumentation();
    }

    private void loadApk() {
        // 插件包的绝对路径 ,  /data/user/0/com.example.plugin_hook/files , 注意最后没有 " / "
        // 需要手动添加 "/"
        String apkPath = mBase.getFilesDir().getAbsolutePath() + "/plugin.apk";
        // 加载插件包后产生的缓存文件路径
        // /data/data/< package name >/app_plugin_cache/
        String cachePath =
                mBase.getDir("plugin_cache", Context.MODE_PRIVATE).getAbsolutePath();
        // 创建类加载器
        DexClassLoader plugin_dexClassLoader =
                new DexClassLoader(
                        apkPath,                // 插件包路径
                        cachePath,              // 插件包加载时产生的缓存路径
                        null,   // 库的搜索路径, 可以设置为空
                        mBase.getClassLoader()  // 父加载器, PathClassLoader
                );

        // 1. 反射 " 插件包 " 应用的 dexElement

        // 执行步骤 :
        // ① 反射获取 BaseDexClassLoader.class
        // ② 反射获取 BaseDexClassLoader.calss 中的 private final DexPathList pathList 成员字段
        // ③ 反射获取 DexClassLoader 类加载器中的 DexPathList pathList 成员对象
        // ④ 反射获取 DexPathList.class
        // ⑤ 反射获取 DexPathList.class 中的 private Element[] dexElements 成员变量的 Field 字段对象
        // ⑥ 反射获取 DexPathList 对象中的 private Element[] dexElements 成员变量对象

        // ① 反射获取 BaseDexClassLoader.class
        // 通过反射获取插件包中的 dexElements
        // 这种类加载是合并类加载 , 将所有的 Dex 文件 , 加入到应用的 dex 文件集合中
        //  可参考 dex 加固 , 热修复 , 插装式插件化 的实现步骤
        // 反射出 BaseDexClassLoader 类 , PathClassLoader 和 DexClassLoader
        //  都是 BaseDexClassLoader 的子类
        // 参考 https://www.androidos.net.cn/android/9.0.0_r8/xref/libcore/dalvik/src/main/java/dalvik/system/BaseDexClassLoader.java
        Class<?> baseDexClassLoaderClass = null;
        try {
            baseDexClassLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // ② 反射获取 BaseDexClassLoader.calss 中的 private final DexPathList pathList 成员字段
        Field plugin_pathListField = null;
        try {
            plugin_pathListField = baseDexClassLoaderClass.getDeclaredField("pathList");
            // 设置属性的可见性
            plugin_pathListField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // ③ 反射获取 plugin_dexClassLoader 类加载器中的 DexPathList pathList 成员对象
        // 根据 Field 字段获取 成员变量
        //  DexClassLoader 继承了 BaseDexClassLoader, 因此其内部肯定有
        //  private final DexPathList pathList 成员变量
        Object plugin_pathListObject = null;
        try {
            plugin_pathListObject = plugin_pathListField.get(plugin_dexClassLoader);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // ④ 获取 DexPathList.class

        // DexPathList 类中有 private Element[] dexElements 成员变量
        // 通过反射获取该成员变量
        // 参考 https://www.androidos.net.cn/android/9.0.0_r8/xref/libcore/dalvik/src/main/java/dalvik/system/DexPathList.java

        // 获取 DexPathList pathList 成员变量的字节码类型 ( 也可以通过反射获得 )
        // 获取的是 DexPathList.class
        Class<?> plugin_dexPathListClass = plugin_pathListObject.getClass();

        // ⑤ 反射获取 DexPathList.class 中的 private Element[] dexElements 成员变量的 Field 字段对象
        Field plugin_dexElementsField = null;
        try {
            plugin_dexElementsField = plugin_dexPathListClass.getDeclaredField("dexElements");
            // 设置属性的可见性
            plugin_dexElementsField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // ⑥ 反射获取 DexPathList 对象中的 private Element[] dexElements 成员变量对象
        Object plugin_dexElementsObject = null;
        try {
            plugin_dexElementsObject = plugin_dexElementsField.get(plugin_pathListObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        // 2. 反射 " 宿主 " 应用的 dexElement
        // 执行步骤 :
        // ① 反射获取 BaseDexClassLoader.class
        // ② 反射获取 BaseDexClassLoader.calss 中的 private final DexPathList pathList 成员字段
        // ③ 反射获取 PathClassLoader 类加载器中的 DexPathList pathList 成员对象
        // ④ 反射获取 DexPathList.class
        // ⑤ 反射获取 DexPathList.class 中的 private Element[] dexElements 成员变量的 Field 字段对象
        // ⑥ 反射获取 DexPathList 对象中的 private Element[] dexElements 成员变量对象

        // ① 反射获取 BaseDexClassLoader.class
        Class<?> host_baseDexClassLoaderClass = null;
        try {
            host_baseDexClassLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // ② 反射获取 BaseDexClassLoader.calss 中的 private final DexPathList pathList 成员字段
        Field host_pathListField = null;
        try {
            host_pathListField = host_baseDexClassLoaderClass.getDeclaredField("pathList");
            // 设置属性的可见性
            host_pathListField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // ③ 反射获取 DexClassLoader 类加载器中的 DexPathList pathList 成员对象
        // 根据 Field 字段获取 成员变量
        //  DexClassLoader 继承了 BaseDexClassLoader, 因此其内部肯定有
        //  private final DexPathList pathList 成员变量
        PathClassLoader host_pathClassLoader = (PathClassLoader) mBase.getClassLoader();
        Object host_pathListObject = null;
        try {
            host_pathListObject = host_pathListField.get(host_pathClassLoader);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // ④ 获取 DexPathList.class

        // DexPathList 类中有 private Element[] dexElements 成员变量
        // 通过反射获取该成员变量
        // 参考 https://www.androidos.net.cn/android/9.0.0_r8/xref/libcore/dalvik/src/main/java/dalvik/system/DexPathList.java

        // 获取 DexPathList pathList 成员变量的字节码类型 ( 也可以通过反射获得 )
        // 获取的是 DexPathList.class
        Class<?> host_dexPathListClass = host_pathListObject.getClass();

        // ⑤ 反射获取 DexPathList.class 中的 private Element[] dexElements 成员变量的 Field 字段对象
        Field host_dexElementsField = null;
        try {
            host_dexElementsField = host_dexPathListClass.getDeclaredField("dexElements");
            // 设置属性的可见性
            host_dexElementsField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // ⑥ 反射获取 DexPathList 对象中的 private Element[] dexElements 成员变量对象
        Object host_dexElementsObject = null;
        try {
            host_dexElementsObject = host_dexElementsField.get(host_pathListObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }



        // 3. 合并 “插件包“ 与 “宿主“ 中的 Element[] dexElements

        // 将两个 Element[] dexElements 数组合并 ,
        //  合并完成后 , 设置到 PathClassLoader 中的
        //  DexPathList pathList 成员的 Element[] dexElements 成员中

        // 获取 “宿主“ 中的 Element[] dexElements 数组长度
        int host_dexElementsLength = Array.getLength(host_dexElementsObject);
        // 获取 “插件包“ 中的 Element[] dexElements 数组长度
        int plugin_dexElementsLength = Array.getLength(plugin_dexElementsObject);

        // 获取 Element[] dexElements 数组中的 , 数组元素的 Element 类型
        // 获取的是 Element.class
        Class<?> elementClazz = host_dexElementsObject.getClass().getComponentType();

        // 合并后的 Element[] dexElements 数组长度
        int new_dexElementsLength = plugin_dexElementsLength + host_dexElementsLength;

        // 创建 Element[] 数组 , elementClazz 是 Element.class 数组元素类型
        Object newElementsArray = Array.newInstance(elementClazz, new_dexElementsLength);

        //  为新的 Element[] newElementsArray 数组赋值
        //      先将 “插件包“ 中的 Element[] dexElements 数组放入到新数组中
        //      然后将 “宿主“ 中的 Element[] dexElements 数组放入到新数组中
        for (int i = 0; i < new_dexElementsLength; i++) {
            if (i < plugin_dexElementsLength) {
                // “插件包“ 中的 Element[] dexElements 数组放入到新数组中
                Array.set(newElementsArray, i, Array.get(plugin_dexElementsObject, i));
            } else {
                //  “宿主“ 中的 Element[] dexElements 数组放入到新数组中
                Array.set(newElementsArray, i, Array.get(host_dexElementsObject, i - plugin_dexElementsLength));
            }
        }


        // 4. 重新设置 PathClassLoader 中的 DexPathList pathList 成员的 Element[] dexElements 属性值
        Field elementsFiled = null;
        try {
            elementsFiled = host_pathListObject.getClass().getDeclaredField("dexElements");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        elementsFiled.setAccessible(true);

        //  设置 DexPathList pathList 的 Element[] dexElements 属性值
        //      host_pathListObject 是原来的属性值
        //      newElementsArray 是新的合并后的 Element[] dexElements 数组
        //  注意 : 这里也可以使用 host_dexElementsField 字段进行设置
        try {
            elementsFiled.set(host_pathListObject, newElementsArray);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 加载资源文件
     * @return
     */
    public Resources loadResources() {

        // 使用反射工具类进行链式调用 , 创建 AssetManager 对象
        AssetManager assetManager = Reflector.on(AssetManager.class).newInstance();

        // 获取插件包 APK 文件路径 , 加载该 APK 下的资源
        // /data/user/0/com.example.plugin_hook/files/plugin.apk
        String pluginPath = mBase.getFilesDir() + "/plugin.apk";

        // 使用反射调用 AssetManager 中的 addAssetPath 方法 , 传入 APK 插件包的路径
        // addAssetPath 方法的参数为 /data/user/0/com.example.plugin_hook/files/plugin.apk
        Reflector.on(assetManager).method("addAssetPath", String.class).call(pluginPath);

        // 创建 Resources 并返回
        return mResources = new Resources(
                assetManager,
                mBase.getResources().getDisplayMetrics(),
                mBase.getResources().getConfiguration()
        );
    }

    public Resources getResources() {
        return mResources;
    }
}
