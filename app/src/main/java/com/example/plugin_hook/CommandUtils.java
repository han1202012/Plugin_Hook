package com.example.plugin_hook;

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
