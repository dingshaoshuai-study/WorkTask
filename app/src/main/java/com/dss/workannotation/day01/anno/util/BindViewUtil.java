package com.dss.workannotation.day01.anno.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.dss.workannotation.day01.anno.BindClick;
import com.dss.workannotation.day01.anno.BindView;
import com.dss.workannotation.day01.anno.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BindViewUtil {
    private static final String TAG = "dss_test";

    public static void init(final Activity activity) {
        Class<? extends Activity> cls = activity.getClass();
        Field[] fields = cls.getDeclaredFields();
        Log.i(TAG, "init: 字段数量：" + fields.length);
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof BindView) {
                    Log.i(TAG, "init: BindView");
                } else if (annotation instanceof Test) {
                    Log.i(TAG, "init: Test");
                }
            }
            if (field.isAnnotationPresent(BindView.class)) {
                BindView bindView = field.getAnnotation(BindView.class);
                int viewId = bindView.value();
                try {
                    Method findViewById = cls.getDeclaredMethod("findViewById", int.class);
                    Object view = findViewById.invoke(activity, viewId);
                    field.setAccessible(true);
                    field.set(activity, view);
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        Method[] mothods = cls.getDeclaredMethods();
        for (final Method mothod : mothods) {
            if (mothod.isAnnotationPresent(BindClick.class)) {
                BindClick bindClick = mothod.getAnnotation(BindClick.class);
                mothod.setAccessible(true);
                int[] viewIdArray = bindClick.value();
                for (int viewId : viewIdArray) {
                    final View view = activity.findViewById(viewId);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                mothod.invoke(activity, view);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }
}
