package com.qianlong.libary_master.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author:gkh
 * @date: 2017-03-01 15:03
 * @自定义封装的通用对话框
 */

public class CommonDialog {
    private Context context;
    private int customeLayoutId;
    private String dialogTitle;
    private String dialogMessage;
    private SpannableStringBuilder dialogMsgBuilder;
    private String positiveText;
    private String negativeText;

    private View dialogView;
    private OnDialogListener listener;
    private CustomDialog.Builder dialog;
    private int height = ViewGroup.LayoutParams.WRAP_CONTENT;

    //带有自定义view的构造器
    public CommonDialog(Context context, int customeLayoutId, String dialogTitle, String positiveText, String negativeText) {
        this.context = context;
        this.customeLayoutId = customeLayoutId;
        this.dialogTitle = dialogTitle;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
        this.dialogView = View.inflate(context, customeLayoutId, null);
    }

    //不带自定义view的构造器
    public CommonDialog(Context context, String dialogTitle, String dialogMessage, String positiveText, String negativeText) {
        this.context = context;
        this.dialogTitle = dialogTitle;
        this.dialogMessage = dialogMessage;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
    }

    public CommonDialog(Context context, SpannableStringBuilder dialogMsgBuilder, String positiveText, String negativeText) {
        this.context = context;
        this.dialogMsgBuilder = dialogMsgBuilder;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
    }

    public View getDialogView() {
        return dialogView;
    }

    public void setDialogView(View dialogView) {
        this.dialogView = dialogView;
    }

    public void showDialog() {
        dialog = new CustomDialog.Builder(context);
        dialog.setTitle(dialogTitle);//设置标题
        //注意:dialogMessage和dialogView是互斥关系也就是dialogMessage存在dialogView就不存在,dialogView不存在dialogMessage就存在
        if (dialogMessage != null) {
            dialog.setMessage(dialogMessage);//设置对话框内容
        } else if (dialogMsgBuilder != null) {
            dialog.setMsgBuilder(dialogMsgBuilder);
        } else {
            dialog.setContentView(dialogView);//设置对话框的自定义view对象
        }
        dialog.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                if (listener != null) {
                    listener.dialogPositiveListener(dialogView, dialogInterface, which);
                }
            }
        });
        if (!TextUtils.isEmpty(negativeText)) {
            dialog.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    dialogInterface.dismiss();
                    if (listener != null) {
                        listener.dialogNegativeListener(dialogView, dialogInterface, which);
                    }
                }
            });
        }
        dialog.create(height).show();
    }

    //设置对话框内容对齐方式
    public void setDialogSetting() {
        dialog.dialogOptionSetting();
    }

    //注册监听器方法
    public CommonDialog setOnDiaLogListener(OnDialogListener listener) {
        this.listener = listener;
        return this;//把当前对象返回,用于链式编程
    }

    //定义一个监听器接口
    public interface OnDialogListener {
        //customView　这个参数需要注意就是如果没有自定义view,那么它则为null
        public void dialogPositiveListener(View customView, DialogInterface dialogInterface, int which);

        public void dialogNegativeListener(View customView, DialogInterface dialogInterface, int which);
    }

    public CommonDialog setHeight(int height) {
        this.height = height;
        return this;
    }
}
