package com.meimeifa.wrapper;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import zeus.plugin.PluginConfig;
import zeus.plugin.PluginManager;
import zeus.plugin.PluginUtil;
import zeus.plugin.ZeusBaseActivity;
import zeus.plugin.ZeusHelper;

public class MainActivity extends ZeusBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isInstall = PluginManager.isInstall(PluginConfig.PLUGIN_TEST);

        if (!isInstall) {
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {
                    return PluginInstaller.installPlugin();
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    Toast.makeText(MainActivity.this, "isInstalled = " + aBoolean, Toast.LENGTH_SHORT).show();
                    PluginInstaller.startPlugin(MainActivity.this);
                    finish();
                }
            }.execute();
        } else {
            PluginInstaller.startPlugin(MainActivity.this);
            finish();
        }
    }

}
