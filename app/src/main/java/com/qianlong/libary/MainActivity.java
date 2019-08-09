package com.qianlong.libary;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qianlong.libary.app.LibApp;
import com.qianlong.libary.utils.CommonDialog;
import com.qianlong.libary.utils.MIniFile;
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

        TextView tv1 = findViewById(R.id.tv1);
        MIniFile mIniFile = LibApp.getInstance().getMIniFile("test.cfg");
        String name = mIniFile.ReadString("USER", "name", "");
        String sex = mIniFile.ReadString("USER", "sex", "");
        int age = mIniFile.ReadInt("USER", "age", 0);
        tv1.setText("读取assets文件信息：name="+ name +",sex="+sex +",age="+age);

        mIniFile = LibApp.getInstance().getMIniFile("product.ini");
        TextView tv2 = findViewById(R.id.tv2);
        String address = mIniFile.ReadString("USER", "address", "");
        String email = mIniFile.ReadString("USER", "email", "");
        tv2.setText("读取ini文件信息：address="+address+",email="+email);
    }
}
