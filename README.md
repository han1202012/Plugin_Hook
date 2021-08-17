# Plugin_Hook

# Android 插件化系列文章目录

[【Android 插件化】插件化简介 ( 组件化与插件化 )](https://hanshuliang.blog.csdn.net/article/details/117391407)
[【Android 插件化】插件化原理 ( JVM 内存数据 | 类加载流程 )](https://hanshuliang.blog.csdn.net/article/details/117414063)
[【Android 插件化】插件化原理 ( 类加载器 )](https://hanshuliang.blog.csdn.net/article/details/117417293)

[【Android 插件化】“ 插桩式 “ 插件化框架 ( 原理与实现思路 )](https://hanshuliang.blog.csdn.net/article/details/117430014)
[【Android 插件化】“ 插桩式 “ 插件化框架 ( 类加载器创建 | 资源加载 )](https://hanshuliang.blog.csdn.net/article/details/117453759) 
[【Android 插件化】“ 插桩式 “ 插件化框架 ( 注入上下文的使用 )](https://hanshuliang.blog.csdn.net/article/details/117485680)
[【Android 插件化】“ 插桩式 “ 插件化框架 ( 获取插件入口 Activity 组件 | 加载插件 Resources 资源 )](https://hanshuliang.blog.csdn.net/article/details/117700329)
[【Android 插件化】“ 插桩式 “ 插件化框架 ( 运行应用 | 代码整理 )](https://hanshuliang.blog.csdn.net/article/details/117933240)


[【Android 插件化】Hook 插件化框架 ( Hook 技术 | 代理模式 | 静态代理 | 动态代理 )](https://hanshuliang.blog.csdn.net/article/details/117952470)
[【Android 插件化】Hook 插件化框架 ( Hook 实现思路 | Hook 按钮点击事件 )](https://hanshuliang.blog.csdn.net/article/details/117966828)
[【Android 插件化】Hook 插件化框架 ( Hook Activity 启动过程 | 静态代理 )](https://hanshuliang.blog.csdn.net/article/details/117989555)

[【Android 插件化】Hook 插件化框架 ( 从 Hook 应用角度分析 Activity 启动流程 一 | Activity 进程相关源码 )](https://hanshuliang.blog.csdn.net/article/details/118001126)
[【Android 插件化】Hook 插件化框架 ( 从 Hook 应用角度分析 Activity 启动流程 二 | AMS 进程相关源码 | 主进程相关源码 )](https://hanshuliang.blog.csdn.net/article/details/118027393)

[【Android 插件化】Hook 插件化框架 ( hook 插件化原理 | 插件包管理 )](https://hanshuliang.blog.csdn.net/article/details/118035052)
[【Android 插件化】Hook 插件化框架 ( 通过反射获取 “插件包“ 中的 Element[] dexElements )](https://hanshuliang.blog.csdn.net/article/details/119333656)
[【Android 插件化】Hook 插件化框架 ( 通过反射获取 “宿主“ 应用中的 Element[] dexElements )](https://hanshuliang.blog.csdn.net/article/details/119334277)
[【Android 插件化】Hook 插件化框架 ( 合并 “插件包“ 与 “宿主“ 中的 Element[] dexElements | 设置合并后的 Element[] 数组 )](https://hanshuliang.blog.csdn.net/article/details/119335178)
[【Android 插件化】Hook 插件化框架 ( 创建插件应用 | 拷贝插件 APK | 初始化插件包 | 测试插件 DEX 字节码 )](https://hanshuliang.blog.csdn.net/article/details/119348096) 

[【Android 插件化】Hook 插件化框架 ( Hook Activity 启动流程 | Hook 点分析 )](https://hanshuliang.blog.csdn.net/article/details/119416905)
[【Android 插件化】Hook 插件化框架 ( Hook Activity 启动流程 | 反射获取 IActivityManager 对象 )](https://hanshuliang.blog.csdn.net/article/details/119521364)
[【Android 插件化】Hook 插件化框架 ( Hook Activity 启动流程 | AMS 启动前使用动态代理替换掉插件 Activity 类 )](https://hanshuliang.blog.csdn.net/article/details/119548665)
[【Android 插件化】Hook 插件化框架 ( Hook Activity 启动流程 | 主线程创建 Activity 实例之前使用插件 Activity 类替换占位的组件 )](https://hanshuliang.blog.csdn.net/article/details/119566102) 

[【Android 插件化】Hook 插件化框架 ( 反射工具类 | 反射常用操作整理 )](https://hanshuliang.blog.csdn.net/article/details/119603284)

[【Android 插件化】Hook 插件化框架 ( 插件包资源加载 )](https://hanshuliang.blog.csdn.net/article/details/119579034)
[【Android 插件化】Hook 插件化框架 ( 从源码角度分析加载资源流程 | Hook 点选择 | 资源冲突解决方案 )](https://hanshuliang.blog.csdn.net/article/details/119611025)
[【Android 插件化】Hook 插件化框架 ( 使用 Hook 方式替换插件 Activity 的 mResources 成员变量 )](https://hanshuliang.blog.csdn.net/article/details/119619952)

[【Android 插件化】Hook 插件化框架总结 ( 插件包管理 | Hook Activity 启动流程 | Hook 插件包资源加载 ) ★★★](https://hanshuliang.blog.csdn.net/article/details/119647811)

---





@[TOC](文章目录)

# 前言

本系列博客开发了一个简易 Hook 插件化框架 , 仅做学习使用 , 商业化还是使用大厂退出的成熟插件化框架  ; 

源码在博客资源中 ; 




<br>
<br>
<br>
<br>

# 一、项目结构及运行方法

---

<br>




<br>

## 1、项目结构 


<br>

这是项目的结构图 ; 

host 是宿主应用 Module ; 

plugin 是插件应用 Module ; 

lib_plugin_core 是插件化框架 , 是插件化依赖库 , 项目类型是 Android Library Module ; 


![在这里插入图片描述](https://img-blog.csdnimg.cn/eab8240e3f804f1fa46156b5560aeb1a.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hhbjEyMDIwMTI=,size_16,color_FFFFFF,t_70)


<br>

## 2、项目运行 


<br>

编译 plugin 插件应用 , 将编译后的 APK 安装包拷贝到宿主应用 host 的 " Plugin_Hook\host\src\main\assets " 目录下 ; 





![在这里插入图片描述](https://img-blog.csdnimg.cn/8c223343872543a79557a84b53271921.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hhbjEyMDIwMTI=,size_16,color_FFFFFF,t_70)

在 host 应用启动时 , 会将文件从 项目资源文件目录 "  assets/plugin.apk " 拷贝到 " /data/user/0/com.example.plugin_hook/files/plugin.apk " Android 内置存储目录中 ; 

运行时直接读取该内置文件中的插件包 , 加载 , 并显示插件包 APK 中的 Activity 界面 ; 

<br>

GitHub 上的应用可以直接运行 , 我已经将 plugin 插件应用编译成 APK  , 并拷贝到了 宿主应用的 assets 资源目录下 ; 

<br>

注意拷贝后将 APK 插件包文件名修改为 plugin.apk ; 






<br>
<br>
<br>
<br>

# 二、宿主应用

---

<br>

![在这里插入图片描述](https://img-blog.csdnimg.cn/8f054acf9dc447ddbe3bcbd49a1f6441.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hhbjEyMDIwMTI=,size_16,color_FFFFFF,t_70)


<br>

## 1、拷贝工具类


<br>

该工具类的作用是将 assets 资源文件拷贝到 Android 文件系统中 ; 

```java
package com.example.host;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandUtils {

    /**
     * 将 Assets 中的文件拷贝到应用内置存储区域
     * @param context   上下文
     * @param assetsFilePath Assets 中的文件路径
     * @param appFilePath 应用内置存储
     * @return
     */
    public static boolean copyAssets2File(Context context, String assetsFilePath, String appFilePath) {
        // 内置存储文件对象
        File file = new File(appFilePath);

        // 确保目录存在
        File filesDirectory = file.getParentFile();
        if (!filesDirectory.exists()){
            filesDirectory.mkdirs();
        }

        // 拷贝文件
        boolean ret = false;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = context.getAssets().open(assetsFilePath);
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[2048];
            int n;
            while ((n = is.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }
            fos.flush();
            ret = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public static String inputStream2String(InputStream inputStream) {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = r.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```




<br>

## 2、自定义 Application 


<br>

主要用于初始化插件化框架 ; 

```java
package com.example.host;

import android.app.Application;
import android.util.Log;

import java.io.File;

import kim.hsl.plugin.PluginManager;

public class MyApplication extends Application {

    private static final String TAG = "plugin_MyApplication";

    /**
     * 插件资源, 这种方式侵入代码 , 造成开发的差异性 , 建议使用 Hook 加载插件资源
     */
    //private Resources pluginResources;

    @Override
    public void onCreate() {
        super.onCreate();

        // 如果已经存在文件, 先删除 , 防止拷贝过程中出错
        File pluginFile = new File(getFilesDir() + "/plugin.apk");
        if (pluginFile.exists()){
            pluginFile.delete();
        }

        // 先将 assets 中的插件包拷贝到 内置存储中
        CommandUtils.copyAssets2File(
                this,
                "plugin.apk",
                getFilesDir() + "/plugin.apk");

        // 将文件从 assets/plugin.apk 拷贝到 /data/user/0/com.example.plugin_hook/files/plugin.apk
        Log.i(TAG, "将文件从 assets/plugin.apk 拷贝到 " + getFilesDir() + "/plugin.apk");


        // 初始化插件包
        PluginManager.getInstance(this).init();

        Log.i(TAG, "插件化 初始化完毕");

        // 设置插件包中的资源文件, 这种方式侵入代码 , 造成开发的差异性 , 建议使用 Hook 加载插件资源
        //pluginResources = PluginManager.getInstance(this).getmResources();

    }

    /*
    // 这种方式侵入代码 , 造成开发的差异性 , 建议使用 Hook 加载插件资源
    @Override
    public Resources getResources() {
        if (pluginResources != null)
            return pluginResources;
        return super.getResources();
    }*/
}

```

<br>

## 3、宿主 Activity 界面


<br>

在该 Activity 界面中 , 主要用于跳转到插件 Activity 中 ; 

```java
package com.example.host;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.i(TAG, "宿主应用 MainActivity onCreate");

        // 反射插件包中的 com.example.plugin.MainActivity
        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.example.plugin.MainActivity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Method method = null;
        try {
            method = clazz.getDeclaredMethod("log");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            // 执行 com.example.plugin.MainActivity 的 log 方法
            method.invoke(clazz.newInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        // 设置按钮点击事件
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 启动插件包中的 Activity
                Intent pluginIntent = new Intent();
                pluginIntent.setComponent(new ComponentName("com.example.plugin",
                        "com.example.plugin.MainActivity"));
                pluginIntent.putExtra("isPlugin", true);
                startActivity(pluginIntent);

            }
        });
    }
}
```

<br>
<br>
<br>
<br>

# 三、插件化框架 

---

<br>

![在这里插入图片描述](https://img-blog.csdnimg.cn/72eca11cb74249d884399ae00b8f96f6.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hhbjEyMDIwMTI=,size_16,color_FFFFFF,t_70)


<br>

## 1、反射工具类

<br>

使用反射工具类 , 能快速开发反射相关功能 ; 

```java
package kim.hsl.plugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 封装反射相关逻辑的工具类
 * 该封装类会维持链式调用
 */
public class Reflector {

    /**
     * 反射的类型
     */
    private Class<?> mClass;

    /**
     * 反射针对的实例对象
     * 如获取 Object 某个字段的值
     */
    private Object mCaller;

    /**
     * 反射的字段
     */
    private Field mField;

    /**
     * 反射的方法
     */
    private Method mMethod;

    /**
     * 反射某个类的入口方法
     *
     * @param type 要反射的类
     * @return
     */
    public static Reflector on(Class<?> type) {
        Reflector reflector = new Reflector();
        reflector.mClass = type;
        return reflector;
    }

    /**
     * 反射某个类的入口方法
     *
     * @param className 要反射的类名
     * @return
     */
    public static Reflector on(String className) {
        try {
            return on(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 反射某个类的入口方法
     *
     * @param object 反射类对应的实例对象
     * @return
     */
    public static Reflector on(Object object) {
        return on(object.getClass()).with(object);
    }

    /**
     * 设置反射对应的实例对象
     *
     * @param object
     * @return
     */
    public Reflector with(Object object) {
        mCaller = object;
        return this;
    }

    /**
     * 创建 mClass 类型的实例对象
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T newInstance() {
        try {
            return (T) mClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 反射类中的某个字段
     *
     * @param name 要反射的字段名称
     * @return
     */
    public Reflector field(String name) {
        mField = findField(name);
        mField.setAccessible(true);
        return this;
    }

    /**
     * 查找字段名称
     *      首先在本类中查找
     *          如果找到直接返回字段
     *          如果在本类中没有找到 , 就去遍历它的父类 , 尝试在父类中查找该字段
     *              如果有父类 , 则在父类中查找
     *                  如果在父类中找到 , 返回该字段
     *                  如果在父类中没有找到 , 则返回空
     *              如果没有父类 , 返回空
     *
     * 尽量传具体的正确的类 , 不要传子类
     * @param fieldName
     * @return
     */
    private Field findField(String fieldName) {
        try {
            // 首先在本类中查找 , 如果找到直接返回字段
            return mClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // 如果在本类中没有找到 , 就去遍历它的父类 , 尝试在父类中查找该字段
            for (Class<?> clazz = mClass; clazz != null; clazz = clazz.getSuperclass()) {
                try {
                    // 如果在父类中找到 , 返回该字段
                    return clazz.getDeclaredField(fieldName);
                } catch (NoSuchFieldException ex) {
                    // 如果在父类中没有找到 , 则返回空
                    return null;
                }
            }
            // 如果没有父类, 则返回空
            return null;
        }
    }

    /**
     * 获取 mCaller 对象中的 mField 属性值
     *
     * @return
     */
    public Object get() {
        try {
            return mField.get(mCaller);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置 mCaller 对象中的 mField 属性值
     *
     * @param value
     * @return 链式调用 , 返回 Reflector
     */
    public Reflector set(Object value) {
        try {
            mField.set(mCaller, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 反射类中的某个方法
     *
     * @param name
     * @param args
     * @return
     */
    public Reflector method(String name, Class<?>... args) {
        mMethod = findMethod(name, args);
        mMethod.setAccessible(true);
        return this;
    }

    /**
     * 根据方法名 和 参数名称 , 查找 Method 方法
     *      首先在本类中查找
     *          如果找到直接返回字段
     *          如果在本类中没有找到 , 就去遍历它的父类 , 尝试在父类中查找该字段
     *              如果有父类 , 则在父类中查找
     *                  如果在父类中找到 , 返回该字段
     *                  如果在父类中没有找到 , 则返回空
     *              如果没有父类 , 返回空
     *
     * 尽量传具体的正确的类 , 不要传子类
     * @param name
     * @param args
     * @return
     */
    private Method findMethod(String name, Class<?>... args) {
        try {
            // 首先在本类中查找 , 如果找到直接返回方法
            return mClass.getDeclaredMethod(name, args);
        } catch (NoSuchMethodException e) {
            // 如果在本类中没有找到 , 就去遍历它的父类 , 尝试在父类中查找该方法
            for (Class<?> cls = mClass; cls != null; cls = cls.getSuperclass()) {
                try {
                    // 如果在父类中找到 , 返回该字段
                    return cls.getDeclaredMethod(name);
                } catch (NoSuchMethodException ex) {
                    // 如果在父类中没有找到 , 则返回空
                    return null;
                }
            }
            // 如果没有父类, 则返回空
            return null;
        }
    }

    /**
     * 调用 mCaller 的 mMethod 方法
     *
     * @param args
     * @param <T>
     * @return
     */
    public <T> T call(Object... args) {
        try {
            return (T) mMethod.invoke(mCaller, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

}
```

<br>

## 2、插件包管理器类 

<br>

在 init 方法中 , 进行插件的总体初始化操作 , 包括 加载 APK 文件 , 加载 APK 中的资源文件 , Activity 替换 , 加载插件 Activity 资源 ; 

<br>

loadApk 方法中 , 读取文件系统中的插件包 , 加载其中的 Dex 字节码文件 , 将其合并到宿主字节码数据中 ; 

loadResources 方法中 ,  读取文件系统中的插件包 , 加载其中的资源文件 ; 


```java
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

```


<br>

## 3、Hook 操作类 

<br>

插件化涉及到的 hook 操作 , 都在该类中执行 ; 

hookAms 方法 , 在 AMS 启动之前使用占坑 Activity 替换插件包 Activity ; 

hookActivityThread 方法 , 在 AMS 执行完毕后 , 主线程 ActivityThread 中创建 Activity 实例对象之前 , 再将插件包 Activity 替换回去 ; 

hookInstrumentation 方法 , 通过 Hook 方式修改 Activity 中的 Resources 资源 ; 


```java
package kim.hsl.plugin;

import android.app.Instrumentation;
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

    /**
     * 主要用于 Resources 资源的加载
     */
    public static void hookInstrumentation() {

        // 反射 ActivityThread 类
        // 反射获取 ActivityThread 类中的 sCurrentActivityThread 静态成员
        // 这是单例类内部的静态成员
        Object sCurrentActivityThreadObj =
                Reflector.on("android.app.ActivityThread")      // 反射 ActivityThread 类
                        .field("sCurrentActivityThread")        // 获取 sCurrentActivityThread 字段
                        .get();                                 // 获取 sCurrentActivityThread 对象

        // 反射获取 ActivityThread 对象中的 mInstrumentation 成员变量
        // 目的是替换 sCurrentActivityThread 中的 mInstrumentation 字段
        Reflector reflector =
                Reflector.on("android.app.ActivityThread")      // 反射 ActivityThread 类
                        .field("mInstrumentation")              // 获取 mInstrumentation 字段
                        .with(sCurrentActivityThreadObj);       // 设置 ActivityThread 实例对象

        // 获取 ActivityThread 中的 mInstrumentationObj 成员, 创建 Instrumentation 静态代理时使用
        Instrumentation mInstrumentationObj = (Instrumentation) reflector.get();

        // 将 ActivityThread 对象中的 mInstrumentation 成员变量
        // 替换成自己的代理类
        reflector.set(new InstrumentationProxy(mInstrumentationObj));
    }

}

```


<br>

## 4、Hook AMS 代理类 

<br>

在 AMS 启动之前使用占坑 Activity 替换插件包 Activity ; 

```java
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

```


<br>

## 5、Hook Handler 代理类 

<br>

静态代理 ActivityThread 中的 final H mH = new H() 成员 ; 

在 AMS 执行完毕后 , 主线程 ActivityThread 中创建 Activity 实例对象之间 , 再将插件包 Activity 替换回去 ; 



```java
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

```

<br>

## 6、Hook Instrumentation 代理类 

<br>


通过 Hook 方式修改 Activity 中的 Resources 资源 ; 

```java
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

```

<br>

## 7、占坑 Activity 

<br>

一个普通的 Activity , 在清单文件中正常注册 , 在 Hook Activity 启动过程中 , 起到占坑作用 ; 

```java
package kim.hsl.plugin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * 该 Activity 主要用于占位
 * 实际上使用插件包中的 Activity 替换该 Activity
 */
public class StubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stub);

        Log.i("plugin", "启动了占坑 Activity");
    }
}
```



<br>
<br>
<br>
<br>

# 四、插件应用

---

<br>

插件应用是普通的应用 , 与正常应用没有区别 , 不用作特别的操作 , 这也是 Hook 插件化框架的优点 , 对代码的侵入性很小 , 开发者可以按照正常的开发逻辑 , 开发插件应用 ; 

![在这里插入图片描述](https://img-blog.csdnimg.cn/0a82aad398de4ecca8773926b5fcf8e8.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hhbjEyMDIwMTI=,size_16,color_FFFFFF,t_70)


```java
package com.example.plugin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    private static final String TAG = "plugin_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("plugin", "启动了插件 Activity");
    }

    public void log(){
        Log.i(TAG, "Plugin MainActivity");
    }

    /*
    // 这种方式侵入代码 , 造成开发的差异性 , 建议使用 Hook 加载插件资源
    @Override
    public Resources getResources() {
        if (getApplication() != null && getApplication().getResources() != null) {
            return getApplication().getResources();
        }
        return super.getResources();
    }*/
}
```



<br>
<br>
<br>
<br>

# 五、博客资源 
---

<br>

**博客资源 :** 

 - **GitHub :** [https://github.com/han1202012/Plugin_Hook](https://github.com/han1202012/Plugin_Hook)
 - **CSDN 下载 :** [https://download.csdn.net/download/han1202012/21047705](https://download.csdn.net/download/han1202012/21047705)




