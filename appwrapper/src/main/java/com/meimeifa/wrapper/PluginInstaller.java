package com.meimeifa.wrapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import zeus.plugin.PluginConfig;
import zeus.plugin.PluginManager;
import zeus.plugin.PluginUtil;
import zeus.plugin.ZeusPlugin;

/**
 * Created by donglua on 2017/5/16.
 */

public class PluginInstaller {

    /**
     * 启动插件
     *
     */
    public static void startPlugin(Activity activity) {
        PluginManager.loadLastVersionPlugin(PluginConfig.PLUGIN_TEST);
        try {
            Class cl = PluginManager.mNowClassLoader.loadClass(PluginManager.getPlugin(PluginConfig.PLUGIN_TEST).getPluginMeta().mainClass);
            Intent intent = new Intent(activity, cl);
            //这种方式为通过在宿主AndroidManifest.xml中预埋activity实现
//            startActivity(intent);
            //这种方式为通过欺骗android系统的activity存在性校验的方式实现
            PluginManager.startActivity(activity, intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 安装插件
    public static boolean installPlugin(File apkFile) {
        ZeusPlugin zeusPlugin = PluginManager.getPlugin(PluginConfig.PLUGIN_TEST);
        FileOutputStream out = null;
        InputStream in = null;
        try {
            AssetManager am = PluginManager.mBaseResources.getAssets();
            in = am.open("zeusplugin_test.apk");
            PluginUtil.createDirWithFile(PluginUtil.getZipPath(PluginConfig.PLUGIN_TEST));
            out = new FileOutputStream(PluginUtil.getZipPath(PluginConfig.PLUGIN_TEST), false);
            byte[] temp = new byte[2048];
            int len;
            while ((len = in.read(temp)) > 0) {
                out.write(temp, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PluginUtil.close(in);
            PluginUtil.close(out);
        }

        return zeusPlugin.install();
    }

    /**
     * 安装assets中高版本插件plugin_test_version2.apk
     * 先拷贝到PluginUtil.getZipPath(PluginConfig.PLUGIN_TEST)
     * 然后调用install()安装。
     *
     */
    public static boolean installPlugin() {
        ZeusPlugin zeusPlugin = PluginManager.getPlugin(PluginConfig.PLUGIN_TEST);
        FileOutputStream out = null;
        InputStream in = null;
        try {
            AssetManager am = PluginManager.mBaseResources.getAssets();
            in = am.open("zeusplugin_test.apk");
            PluginUtil.createDirWithFile(PluginUtil.getZipPath(PluginConfig.PLUGIN_TEST));
            out = new FileOutputStream(PluginUtil.getZipPath(PluginConfig.PLUGIN_TEST), false);
            byte[] temp = new byte[2048];
            int len;
            while ((len = in.read(temp)) > 0) {
                out.write(temp, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PluginUtil.close(in);
            PluginUtil.close(out);
        }

        boolean installed = zeusPlugin.install();
        return installed;
    }
}
