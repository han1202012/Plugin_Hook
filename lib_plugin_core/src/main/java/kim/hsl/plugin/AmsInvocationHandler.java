package kim.hsl.plugin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理的代理类
 */
public class AmsInvocationHandler implements InvocationHandler {

    /**
     * 上下文对象
     */
    private final Context mContext;

    /**
     * 持有被代理的原对象
     */
    private final Object mIActivityManager;

    public AmsInvocationHandler(Context context, Object iActivityManager) {
        this.mContext = context;
        this.mIActivityManager = iActivityManager;
    }

    /**
     * 代理 IActivityManager 的 startActivity 方法
     * 替换要启动的 Activity 的 Intent
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 检测到方法名称是 startActivity
        // 要使用自己的方法 , 替换被代理的方法
        // 主要进行替换要启动的 Activity 的 Intent 操作
        if("startActivity".equals(method.getName())){
            Intent intent = null;

            // Intent 会通过参数传入
            // 遍历方法的参数即可
            for (int i= 0; i < args.length; i++){
                // 获取参数对象
                Object arg = args[i];

                // 方法参数类型是 Intent
                if(arg instanceof Intent){
                    // 将原来的传入的 Intent 参数 , 改成自己的 Intent , 启动自己的类
                    intent = (Intent) arg;

                    // 新的 Intent , 用于替换原有的 Intent
                    Intent exchangeIntent = new Intent(mContext, StubActivity.class);

                    // 原来的 Intent 不能丢 , 里面包含了很多信息 , 如实际的跳转信息
                    // 最终还要替换回去
                    exchangeIntent.putExtra("pluginIntent", intent);

                    // 替换原来的 Intent 参数值
                    args[i] = exchangeIntent;
                    break;
                }
            }
        }

        // 继续向后执行 , 这里要避免截断方法
        return method.invoke(mIActivityManager, args);
    }
}
