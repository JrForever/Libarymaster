package com.qianlong.libary;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qianlong.libary.utils.CommonDialog;
import com.qianlong.libary.utils.SpUtils;
import com.qianlong.libary.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        SpUtils.getInstance(this).putString("test","test message");
        TextView content = findViewById(R.id.content);
        content.setText(SpUtils.getInstance(this).getString("test"));

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommonDialog(mContext, "提示" , "我是测试Dialog的文本",
                        "确定","取消").setOnDiaLogListener(new CommonDialog.OnDialogListener() {
                    @Override
                    public void dialogPositiveListener(View customView, DialogInterface dialogInterface, int which) {
                        ToastUtils.showToastCenter(mContext, "确定按钮点击");
                    }

                    @Override
                    public void dialogNegativeListener(View customView, DialogInterface dialogInterface, int which) {
                        ToastUtils.showToastCenter(mContext, "取消按钮点击");
                    }
                }).showDialog();
            }
        });
    }
}
