package com.bn.test;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JD on 2017/12/16.
 */

public class WelcomActivity extends Activity {
    /**登录*/
    private EditText mname;                        //用户名编辑
    private EditText mPwd;                         //密码编辑
    private TextView mRegisterButton;              //注册按钮
    private Button mLoginButton;                   //登录按钮
    private SharedPreferences login_sp;
    private String userNameValue,passwordValue;
    private View loginView;                         //登录
    private View loginSuccessView;
    private TextView loginSuccessShow;
    private TextView mChangepwdText;
    List<String[]> ls;
    public static String mess[][];
    public static ImageView image;
    static Bitmap rawBitmap;
    /**注册*/
    private EditText mPwdCheck;                       //密码编辑
    private Button mCancelButton,mSureButton;         //取消按钮
    RelativeLayout relativeLayout;
    private  EditText mnamemima,  mPwdmima;           //账号 密码
    Activity activity;
    Dialog bottomDialog;
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.sub_logindialog);
        activity=this;
        initdenglu();
        View login=getLayoutInflater().inflate(R.layout.sub_logindialog,null);
        zhucheZhanhao(login);                            //注册账号初始化界面
    }
    public void zhucheZhanhao(View  contentView) {
        relativeLayout=(RelativeLayout) contentView.findViewById(R.id.register);
        mnamemima= (EditText)  contentView.findViewById(R.id.resetpwd_edit_name);
        mPwdmima= (EditText) contentView. findViewById(R.id.resetpwd_edit_pwd_old);
        mPwdCheck = (EditText) contentView. findViewById(R.id.resetpwd_edit_pwd_new);
        mSureButton = (Button)  contentView.findViewById(R.id.register_btn_sure);
        mCancelButton = (Button)  contentView.findViewById(R.id.register_btn_cancel);
    }
    public void initdenglu() {
        mname = (EditText) findViewById(R.id.login_edit_account);           //用户名
        mPwd = (EditText) findViewById(R.id.login_edit_pwd);                //密码
        mRegisterButton = (TextView) findViewById(R.id.login_btn_register); //注册
        mLoginButton = (Button) findViewById(R.id.login_btn_login);         //登陆
        loginView = findViewById(R.id.login_view);
        image = (ImageView) findViewById(R.id.logo);                         //使用ImageView显示logo
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dengLu();
            }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhuzheZhanhaoDialog();
            }
        });
    }
    private  void dengLu()
    {
        Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
        startActivity(intent);
//        if(mess==null||mess.equals(""))
//        {
//            Toast.makeText(WelcomActivity.this, "账号不正确", Toast.LENGTH_SHORT).show();
//            login_sp.edit().putBoolean("ISLOGIN", false).commit();
//            return;
//        } else if(!mPwd.getText().toString().equals(mess[0][11])) {
//            Toast.makeText(WelcomActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
//            login_sp.edit().putBoolean("ISLOGIN", false).commit();
//            return;
//        }
//        if(mname.getText().toString().equals(mess[0][7])&&mPwd.getText().toString().equals(mess[0][11]))
//        {
//            Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
//            startActivity(intent);
//        }else {
//            Toast.makeText(WelcomActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
//            login_sp.edit().putBoolean("ISLOGIN", false).commit();
//        }
    }
    /**注册对话框*/
    private void zhuzheZhanhaoDialog()
    {
        bottomDialog = new Dialog(this);
        View contentView = LayoutInflater.from(this).inflate(R.layout.sub_registerdialog, null);
        zhucheZhanhao( contentView);
        bottomDialog.setContentView(contentView);
        bottomDialog.setTitle("用户注册");
        bottomDialog.show();
        mCancelButton.setOnClickListener(
                new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     bottomDialog.cancel();
                 }
             });
        mSureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.cancel();
                Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
                startActivity(intent);
//                if (mnamemima.getText().toString().equals("") ) {
//                    Toast.makeText(WelcomActivity.this, "请检查是否输入了用户名！！", Toast.LENGTH_SHORT).show();
//                } else if (mPwdmima.getText().toString().equals("") || mPwdCheck.getText().toString().equals("")) {
//                    Toast.makeText(WelcomActivity.this, "请输入密码！！", Toast.LENGTH_SHORT).show();
//                } else if (!(mPwdmima.getText().toString().equals(mPwdCheck.getText().toString()))) {
//                    Toast.makeText(WelcomActivity.this, "两次输入的密码不一致！！", Toast.LENGTH_SHORT).show();
//                } else {
//                    bottomDialog.cancel();
//                    Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
            }
        } );
    }
}
