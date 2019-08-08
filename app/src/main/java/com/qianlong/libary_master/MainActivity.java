package com.qianlong.libary_master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpUtils.getInstance(this).putString("test","test message");
        TextView content = findViewById(R.id.content);
        content.setText(SpUtils.getInstance(this).getString("test"));
    }
}
