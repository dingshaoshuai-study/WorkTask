package com.dss.workannotation.day01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.dss.workannotation.R;
import com.dss.workannotation.day01.anno.AutoWrite;
import com.dss.workannotation.day01.anno.bean.LikeBean;
import com.dss.workannotation.day01.anno.bean.LikeBean2;
import com.dss.workannotation.day01.anno.util.AutoWriteUtil;

import java.util.Arrays;
import java.util.List;

public class AActivity extends AppCompatActivity {
    private static final String TAG = "dss_test";

    @AutoWrite
    private String name;
    @AutoWrite
    private int age;
    @AutoWrite("isMan")
    private boolean isMan;
    @AutoWrite("likeBean")
    private LikeBean likeBean;
    @AutoWrite
    private List<LikeBean> likeBeanList;
    @AutoWrite
    private LikeBean[] likeBeanArray;
    @AutoWrite
    private LikeBean2[] likeBean2Array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        AutoWriteUtil.init(this);
        Log.i(TAG, "onCreate: name:" + name);
        Log.i(TAG, "onCreate: age:" + age);
        Log.i(TAG, "onCreate: isMan:" + isMan);
        Log.i(TAG, "onCreate: likeBean:" + likeBean);
        Log.i(TAG, "onCreate: likeBeanList:" + likeBeanList);
        Log.i(TAG, "onCreate: likeBeanArray:" + Arrays.toString(likeBeanArray));
        Log.i(TAG, "onCreate: likeBean2Array:" + Arrays.toString(likeBean2Array));
    }
}
