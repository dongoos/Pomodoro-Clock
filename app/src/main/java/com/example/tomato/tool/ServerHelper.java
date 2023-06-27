package com.example.tomato.tool;

import android.util.Log;

import com.example.tomato.User;

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
        String url = "http://" + ip + ":" + port + "/server/Signup";
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return FutureCall.futureCall(request);
    }

    public CompletableFuture<Boolean> login(String email, String password) {
        String url = "http://" + ip + ":" + port + "/server/Login";
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Log.i("login","start");

        return FutureCall.futureCall(request);
    }

    public CompletableFuture<Boolean> setName(String email, String name) {
        String url = "http://" + ip + ":" + port + "/server/SetName";
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("name", name)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return FutureCall.futureCall(request);
    }



    public CompletableFuture<User> getName(String email) {
        String url = "http://" + ip + ":" + port + "/server/GetName";
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return FutureCall.futureCallUser(request);
    }




    public CompletableFuture<User> getAllInfo(String email) {
        String url = "http://" + ip + ":" + port + "/server/GetAllInfo";
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Log.i("getAllInfo","start");
        return FutureCall.futureCallUser(request);
    }
    public CompletableFuture<Boolean> changePassword(String email, String newPwd) {
        String url = "http://" + ip + ":" + port + "/server/ChangePassword";
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("newPwd", newPwd)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return FutureCall.futureCall(request);
    }

    public CompletableFuture<Boolean> sendFriendRequest(String email) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        return future;
    }
    public CompletableFuture<Boolean> getFriendRequest(String email) {
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
    public CompletableFuture<Boolean> showFriendList(String email) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        return future;
    }
    public CompletableFuture<Boolean> isFriend(String email) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        return future;
    }

}