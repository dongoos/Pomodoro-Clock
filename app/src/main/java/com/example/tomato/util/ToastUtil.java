package com.example.tomato.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void show(Context ctx,String details) {
        Toast.makeText(ctx,details,Toast.LENGTH_SHORT).show();
    }
}
