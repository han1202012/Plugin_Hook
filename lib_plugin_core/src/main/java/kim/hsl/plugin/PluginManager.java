package kim.hsl.plugin;

import android.content.Context;

/**
 * 使用 Hook 实现的插件使用入口
 *  1. 加载插件包中的字节码
 *  2. 直接通过 hook 技术, 钩住系统的 Activity 启动流程实现
 *      ① Activity 创建之前 , 在 ActivityRecord 中加载 Activity 信息
 *      ② 使用 hook 技术 , 加载插件包 apk 中的 Activity
 *      ③ 实现跳转的 Activity
 *  3. 解决 Resources 资源冲突问题
 *
 *  插件包中的 Activity 是通过正规流程 , 由 AMS 进行创建并加载的
 *      但是该 Activity 并没有在 AndroidManifest.xml 清单文件中注册
 *      这里需要一个已经在清单文件注册的 Activity 欺骗系统 ,
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
