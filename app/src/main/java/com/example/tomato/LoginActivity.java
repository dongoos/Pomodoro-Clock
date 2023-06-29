package com.example.tomato;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomato.tool.ServerHelper;

import java.util.List;

public class LoginActivity extends Activity {

    private EditText et_email,et_password;
    Button btn_login;
    String email,password;
    TextView btn_signup,btn_forgetPwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
         et_email=findViewById(R.id.et_email);
         et_password=findViewById(R.id.et_pwd);

         btn_login=findViewById(R.id.btn_login);
         btn_signup = findViewById(R.id.tv_signup);
         btn_forgetPwd=findViewById(R.id.tv_forgetPwd);
         btn_login.setOnClickListener(view -> {
             email=et_email.getText().toString();
             password=et_password.getText().toString();
             ServerHelper serverHelper =new ServerHelper();
             Log.i("email",email);
             Log.i("pwd",password);
             serverHelper.login(email, password)
                     .thenAccept(complete -> {
                         Log.i("complete", String.valueOf(complete));
                         // 处理异步操作结果
                         if (complete == true) {
                             Log.i("登录", "成功，unlockedId: "  + ", uid: " );
                             serverHelper.getAllInfo(email)
                                     .thenAccept(user ->{
                                         if(user!=null){
                                             Log.i("配置信息","started");
                                             User.setUserSession(user.getName(),user.getEmail(),user.getUid(),password);
                                             Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                             startActivity(intent);
                                             Log.i("name,email,uid",User.getName()+User.getEmail()+User.getUid());
                                         }else{
                                             Log.i("配置信息","wrong");
                                         }
                                     });
                         } else {
                             // 登录失败
                             Log.i("登录", "失败");
                             Toast toast = Toast.makeText(this, "登录失败，请重试", Toast.LENGTH_SHORT);
                             toast.show();
                         }
                     });
         });

        tv_forgetPwd.setOnClickListener(view -> {
            ServerHelper serverHelper=new ServerHelper();
            serverHelper.setScore("200")
                    .thenAccept(complete -> {
                        Log.i("complete", String.valueOf(complete));
                        if (complete) {
                            Log.i("分数","设置成功" );
                        } else {
                            Log.i("分数", "失败");
                        }
                    });

        });

        btn_signup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });




















        /**初始化EditText,默认都为未选中状态**/
         et_email.setBackgroundResource(R.drawable.et_underline_unselected);
        et_password.setBackgroundResource(R.drawable.et_underline_unselected);
        /**第一个EditText的焦点监听事件**/
        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_email.setBackgroundResource(R.drawable.et_underline_selected);

                } else {
                    et_email.setBackgroundResource(R.drawable.et_underline_unselected);
                }
            }
        });

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_password.setBackgroundResource(R.drawable.et_underline_selected);

                } else {
                    et_password.setBackgroundResource(R.drawable.et_underline_unselected);
                }
            }
        });
  }

}