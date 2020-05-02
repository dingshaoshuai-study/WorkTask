package com.dss.workannotation.day01.anno.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import com.dss.workannotation.day01.anno.AutoWrite;

import java.lang.reflect.Field;
import java.util.Arrays;

public class AutoWriteUtil {

    public static void init(Activity activity) {
        Intent intent = activity.getIntent();
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        Class<? extends Activity> cls = activity.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoWrite.class)) {
                AutoWrite autoWrite = field.getAnnotation(AutoWrite.class);
                String key = TextUtils.isEmpty(autoWrite.value()) ? field.getName() : autoWrite.value();
                if (!bundle.containsKey(key)) {
                    continue;
                }
                Object o = bundle.get(key);

                // todo Parcelable数组类型不能直接设置，其他的都可以.
                //获得数组单个元素类型
                Class<?> componentType = field.getType().getComponentType();
                //当前属性是数组并且是 Parcelable（子类）数组
                if (field.getType().isArray() &&
                        Parcelable.class.isAssignableFrom(componentType)) {
                    Object[] objs = (Object[]) o;
                    //创建对应类型的数组并由objs拷贝
                    Object[] objects = Arrays.copyOf(objs, objs.length, (Class<? extends Object[]>) field.getType());
                    o = objects;
                }
                try {
                    field.setAccessible(true);
                    field.set(activity, o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
