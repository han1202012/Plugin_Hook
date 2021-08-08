package kim.hsl.plugin;

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
        
    }

}
