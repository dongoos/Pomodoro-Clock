package com.example.tomato.tool;

import android.util.Log;

import com.example.tomato.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FutureCall {
    public static CompletableFuture<Boolean> futureCall(Request request){
        OkHttpClient httpClient = new OkHttpClient();
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                future.complete(false);
                Log.i("callback","fail");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (result.equals("true")) {future.complete(true);}
               else{future.complete(false);}


            }
        });
        return future;
    } ;
    public static CompletableFuture<User> futureCallUser(Request request) {
        OkHttpClient httpClient = new OkHttpClient();
        CompletableFuture<User> future = new CompletableFuture<>();
        Call call = httpClient.newCall(request);
        User user =new User();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("callback","failed");

                future.complete(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("callbackUser","success");
                    String responseBody = response.body().string();
                    Log.i("responseBody",responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(responseBody);
                    user.setUid(jsonObject.getInt("uid"));
                    user.setName(jsonObject.getString("name"));
                    user.setEmail(jsonObject.getString("email"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.i("callbackUser_user",User.getName()+User.getEmail()+User.getUid());

                future.complete(user);

            }
        });
        return future;
    }

}
