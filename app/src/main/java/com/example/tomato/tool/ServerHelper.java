package com.example.tomato.tool;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerHelper {

    private String ip = "8.130.17.190";
    private String port = "8080";
    private boolean ok;

    public CompletableFuture<Boolean> signup(String email, String password) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        String url = "http://" + ip + ":" + port + "/server/Signup";
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
            public void onFailure(Call call, IOException e) {
                future.complete(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                future.complete(true);
            }
        });
        return future;
    }

    public CompletableFuture<Boolean> login(String email, String password) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        String url = "http://" + ip + ":" + port + "/server/Login";
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
            public void onFailure(Call call, IOException e) {
                future.complete(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                future.complete(true);
            }
        });
        return future;
    }

    public CompletableFuture<Boolean> setName(String email, String name) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        String url = "http://" + ip + ":" + port + "/server/SetName";
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("name", name)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.complete(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                future.complete(true);
            }
        });
        return future;
    }

    public CompletableFuture<Boolean> getName(String email) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        String url = "http://" + ip + ":" + port + "/server/GetName";
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.complete(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                future.complete(true);
            }
        });
        return future;
    }

    public CompletableFuture<Boolean> changePassword(String email, String newPwd) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        String url = "http://" + ip + ":" + port + "/server/ChangePassword";
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("newPwd", newPwd)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.complete(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                future.complete(true);
            }
        });
        return future;
    }

    public CompletableFuture<Boolean> addFriend(String email) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        return future;
    }

    public CompletableFuture<Boolean> setLockId() {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        return future;
    }
    public CompletableFuture<Boolean> getLockId(String fid) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        return future;
    }
    public CompletableFuture<Boolean> add(String email) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        return future;
    }

    public CompletableFuture<Boolean> exit(String email) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        return future;
    }

}