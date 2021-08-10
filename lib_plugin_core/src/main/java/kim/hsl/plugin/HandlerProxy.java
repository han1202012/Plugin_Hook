package kim.hsl.plugin;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 静态代理 ActivityThread 中的 final H mH = new H() 成员
 */
public class HandlerProxy implements Handler.Callback {

    public static final int EXECUTE_TRANSACTION = 159;

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == EXECUTE_TRANSACTION) {


            // 反射 android.app.servertransaction.ClientTransaction 类
            // 该类中有如下成员变量
            // private List<ClientTransactionItem> mActivityCallbacks;
            // 这个集合中存放的就是 android.app.servertransaction.LaunchActivityItem 类实例
            // 不能直接获取 LaunchActivityItem 实例 , 否则会出错
            Class<?> clientTransactionClass = null;
            try {
                clientTransactionClass =
                        Class.forName("android.app.servertransaction.ClientTransaction");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // 验证当前的 msg.obj 是否是 ClientTransaction 类型 , 如果不是则不进行 Intent 替换
            // 通过阅读源码可知 , 在 ActivityThread 的 mH 中, 处理 EXECUTE_TRANSACTION 信号时
            // 有 final ClientTransaction transaction = (ClientTransaction) msg.obj;
            if (!clientTransactionClass.isInstance(msg.obj)) {
                return true;
            }

            // 反射获取
            // private List<ClientTransactionItem> mActivityCallbacks; 成员字段
            Field mActivityCallbacksField = null;
            try {
                mActivityCallbacksField =
                        clientTransactionClass.getDeclaredField("mActivityCallbacks");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            // 设置成员字段可见性
            mActivityCallbacksField.setAccessible(true);

            // 反射获取
            // private List<ClientTransactionItem> mActivityCallbacks; 成员字段实例
            Object mActivityCallbacksObject = null;
            try {
                mActivityCallbacksObject = mActivityCallbacksField.get(msg.obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // 将
            // private List<ClientTransactionItem> mActivityCallbacks; 成员字段实例
            // 强转为 List 类型 , 以用于遍历
            List mActivityCallbacksObjectList = (List) mActivityCallbacksObject;

            for (Object item : mActivityCallbacksObjectList) {
                Class<?> clazz = item.getClass();

                // 只处理 LaunchActivityItem 的情况
                if (clazz.getName().equals("android.app.servertransaction.LaunchActivityItem")) {
                    // 获取 LaunchActivityItem 的 private Intent mIntent; 字段
                    // 该 Intent 中的 Activity 目前是占坑 Activity 即 StubActivity
                    // 需要在实例化之前 , 替换成插件包中的 Activity
                    Field mIntentField = null;
                    try {
                        mIntentField = clazz.getDeclaredField("mIntent");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    mIntentField.setAccessible(true);

                    // 获取 LaunchActivityItem 对象的 mIntent 成员 , 即可得到 Activity 跳转的 Intent
                    Intent intent = null;
                    try {
                        intent = (Intent) mIntentField.get(item);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    // 获取 启动 插件包 组件的 Intent
                    Intent pluginIntent = intent.getParcelableExtra("pluginIntent");
                    if (pluginIntent != null) {
                        // 使用 包含插件包组件信息的 Intent ,
                        // 替换之前在 Ams 启动之前设置的 占坑 StubActivity 对应的 Intent
                        try {
                            mIntentField.set(item, pluginIntent);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }
}
