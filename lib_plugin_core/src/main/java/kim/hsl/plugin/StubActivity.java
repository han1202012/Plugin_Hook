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