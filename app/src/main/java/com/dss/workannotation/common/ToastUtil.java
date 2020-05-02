package com.dss.workannotation.common;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private void showPrivate(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void showPublic(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
