package com.example.tomato;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tomato.tool.ServerHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_email,et_password,et_password_rePut;
    Button btn_register;
    String email,password,passwordRePut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_pwd);
        et_password_rePut = findViewById(R.id.et_pwd_rePut);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        email=et_email.getText().toString();
        password=et_password.getText().toString();
        passwordRePut=et_password_rePut.getText().toString();

        if(password.equals(passwordRePut)){
            ServerHelper serverHelper =new ServerHelper();
            serverHelper.signup(email, password)
                    .thenAccept(complete -> {
                        Log.i("complete", String.valueOf(complete));
                        // 处理异步操作结果
                        if (complete != false) {
                            Log.i("注册", "成功，: "  + ", uid: " );
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            // 登录失败
                            Log.i("注册", "失败");

                        }
                    });
        }else   {
            Toast toast = Toast.makeText(this, "两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}