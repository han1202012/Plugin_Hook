package kim.hsl.plugin;

import android.content.Context;

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

    }

}
