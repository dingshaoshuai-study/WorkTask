package com.dss.workannotation.day01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dss.workannotation.R;
import com.dss.workannotation.day01.anno.BindClick;
import com.dss.workannotation.day01.anno.BindView;
import com.dss.workannotation.day01.anno.Test;
import com.dss.workannotation.day01.anno.bean.LikeBean;
import com.dss.workannotation.day01.anno.bean.LikeBean2;
import com.dss.workannotation.day01.anno.util.BindViewUtil;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn1)
    @Test(R.id.btn1)
    private Button btn1;
    @BindView(R.id.btn2)
    private Button btn2;

    private int number;

    public static final String TOAST_NB = "牛批牛批";
    public static final String TOAST_LOW = "捞，捞！";

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TOAST_NB, TOAST_LOW})
    @interface ToastType {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BindViewUtil.init(this);
    }

    @BindClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    private void click(View view) {
        Class<? extends MainActivity> cls = getClass();
        switch (view.getId()) {
            case R.id.btn1:
                try {
                    Method method = cls.getDeclaredMethod("startA");
                    method.setAccessible(true);//不设置也可以调用到，不知道是不是在本类调用就不需要设置的原因
                    method.invoke(this);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn2:
                try {
//                    Class<? extends MainActivity> cls = getClass();
                    Method monthod = cls.getDeclaredMethod("toast2", String.class);
                    monthod.setAccessible(true);//toast2是本类中的私有方法，这个不设置也可以调用到，不知道是不是在本类调用就不需要设置的原因
                    monthod.invoke(this, "哈哈哈");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn3:
                toast3(TOAST_LOW);
                break;
            case R.id.btn4:
                toast4();
                break;
        }
    }

    private void startA() {
        LikeBean likeBean = new LikeBean();
        likeBean.setName("小红");
        likeBean.setAge(16);

        LikeBean[] likeBeanArray = new LikeBean[5];
        for (int i = 0; i < likeBeanArray.length; i++) {
            likeBeanArray[i] = likeBean;
        }

        List<LikeBean> likeBeanList = new ArrayList<>();
        for (LikeBean bean : likeBeanArray) {
            likeBeanList.add(bean);
        }

        LikeBean2[] likeBean2Array = new LikeBean2[5];
        for (int i = 0; i < likeBean2Array.length; i++) {
            likeBean2Array[i] = new LikeBean2();
        }

        Intent intent = new Intent(this, AActivity.class)
                .putExtra("name", "小明")
                .putExtra("isMan", true)
                .putExtra("age", 18)
                .putExtra("likeBean", likeBean)
                .putExtra("likeBeanList", (Serializable) likeBeanList)
                .putExtra("likeBeanArray", likeBeanArray)
                .putExtra("likeBean2Array", likeBean2Array);
        startActivity(intent);
    }

    private void toast4() {
        Class<? extends MainActivity> cls = getClass();
        try {
            Field number = cls.getDeclaredField("number");
            number.set(this, 100);
            number = cls.getDeclaredField("number");
            int numberInt = number.getInt(this);
            Toast.makeText(this, "number:" + numberInt, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "number:" + MainActivity.this.number, Toast.LENGTH_SHORT).show();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void toast3(@ToastType String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void toast2(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
