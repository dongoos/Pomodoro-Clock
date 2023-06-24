package com.example.tomato.tool;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerHelper {

    private String ip ="8.130.17.190";
    private String port ="8080";
    private boolean ok ;
    public boolean signup(String email,String password) {
        Log.d("email",email);
        Log.d("password",password);
        String url = "http://"+ip+":"+port+"/server/Signup";
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient httpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("email", email)
                        .add("password", password)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();

                Call call = httpClient.newCall(request);
                Log.i("callinit","finished");

                call.enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e)
                    {
                        ok=false;
                        Log.d("register","失败了");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ok=true;
                        Log.d("register",response.toString()+"------------------");
                        Log.d("register",response.body().toString()+"------------------");
                    }
                });

            }
        }).start();
        return ok;
    }

    public boolean login(String email,String password) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://"+ip+":"+port+"/server/Login";
                OkHttpClient httpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("email", email)
                        .add("password", password)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                Call call = httpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e)
                    {   ok=false;
                       Log.i("login","失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ok = true;
                        Log.d("login",response+"---------response---------");
                    }
                });
            }
        }).start();
        return ok;
    }




}
