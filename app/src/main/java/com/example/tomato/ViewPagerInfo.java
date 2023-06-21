package com.example.tomato;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;

public class ViewPagerInfo extends Activity {


    private AlertDialog dlg;
    private ImageButton btn_avatar;
    private TextView tv_name;
    private ImageButton btn_name;
    private TextView tv_email;
    private ImageButton btn_email;

    private Button btn_changePwd;
    private Button btn_signup;
    private Button btn_exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_info);

        btn_avatar = findViewById(R.id.btn_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        btn_name = findViewById(R.id.btn_name);
        btn_email = findViewById(R.id.btn_email);
        btn_changePwd = findViewById(R.id.changePassword);
        btn_signup = findViewById(R.id.signup);
        btn_exit = findViewById(R.id.btn_exit);
        Listener_dlg listener=new Listener_dlg();
        btn_name.setOnClickListener(listener);
        btn_email.setOnClickListener(listener);

    }


    public class Listener_dlg implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            View dlgView = LayoutInflater.from(ViewPagerInfo.this).inflate(R.layout.dialog_layout, null);
            EditText et_name = dlgView.findViewById(R.id.et_info);
            Button btn_ok = dlgView.findViewById(R.id.btn_ok);
          if(view.getId()== R.id.btn_name) {
                DBManager db=new DBManager();
                db.run();
              btn_ok.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      tv_name.setText(et_name.getText().toString());
                      dlg.dismiss();
                  }
              });
              dlg = new AlertDialog.Builder(ViewPagerInfo.this)
                      .setView(dlgView)
                      .create();
              dlg.show();

          }
          else if (view.getId()== R.id.btn_name) {
              btn_ok.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      tv_email.setText(et_name.getText().toString());
                      dlg.dismiss();
                  }
              });
              dlg = new AlertDialog.Builder(ViewPagerInfo.this)
                      .setView(dlgView)
                      .create();
              dlg.show();


          } else if (view.getId()==R.id.changePassword) {

          } else if (view.getId()==R.id.signup) {

          } else if (view.getId()== R.id.btn_exit) {
              //
          }

        }
    }
}
