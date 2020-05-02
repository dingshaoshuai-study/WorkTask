package com.dss.workannotation.day02;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dss.workannotation.R;
import com.dss.workannotation.common.ToastUtil;
import com.dss.workannotation.day02.anno.BindClick;
import com.dss.workannotation.day02.anno.BindLongClick;
import com.dss.workannotation.day02.anno.util.BindClickUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.appcompat.app.AppCompatActivity;

public class A2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2);
        BindClickUtil.init(this);
    }

    @BindClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Toast.makeText(this, "btn 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2:
                Toast.makeText(this, "btn 2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn3:
                try {
                    ToastUtil toastUtil = ToastUtil.class.newInstance();
                    Method toast = ToastUtil.class.getDeclaredMethod("showPrivate", Context.class, String.class);
                    toast.setAccessible(true);
                    toast.invoke(toastUtil, this, "调用私有方法");
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn4:
                try {
                    ToastUtil toastUtil = ToastUtil.class.newInstance();
                    Method toast2 = ToastUtil.class.getMethod("showPublic", Context.class, String.class);
                    toast2.invoke(toastUtil, this, "调用公有方法");
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @BindLongClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    private boolean longClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Toast.makeText(this, "btn1 longClick", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2:
                Toast.makeText(this, "btn2 longClick", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn3:
                Toast.makeText(this, "btn3 longClick", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn4:
                Toast.makeText(this, "btn4 longClick", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}
