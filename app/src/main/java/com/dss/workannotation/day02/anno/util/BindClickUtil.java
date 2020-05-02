package com.dss.workannotation.day02.anno.util;

import android.app.Activity;
import android.view.View;

import com.dss.workannotation.day02.anno.EventType;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BindClickUtil {

    public static void init(Activity activity) {
        Class<? extends Activity> cls = activity.getClass();
        //获取所有方法
        Method[] methods = cls.getDeclaredMethods();
        //遍历方法
        for (Method method : methods) {
            //某一个方法上的所有注解
            Annotation[] annotations = method.getAnnotations();
            //遍历某一个方法上的所有注解
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //如果这个注解上还有一个叫 EventType 的注解
                if (annotationType.isAnnotationPresent(EventType.class)) {
                    EventType eventType = annotationType.getAnnotation(EventType.class);
                    Class<?> eventClass = eventType.cls();
                    String eventMethod = eventType.eventMethod();
                    try {
                        //获取注解内的value方法
                        Method valueMethod = annotationType.getDeclaredMethod("value");
                        //执行value方法获取id数组
                        int[] viewIdArray = (int[]) valueMethod.invoke(annotation);
                        method.setAccessible(true);
                        ListenerInvocationHandler<Activity> handler = new ListenerInvocationHandler<>(activity, method);
                        Object proxyInstance = Proxy.newProxyInstance(eventClass.getClassLoader(), new Class[]{eventClass}, handler);
                        //遍历id数组，获取View
                        for (int viewId : viewIdArray) {
                            View view = activity.findViewById(viewId);
                            //获取view的设置监听事件的方法,如：setOnClickListener
                            //setOnClickListener 本身就是公有的方法，直接用getMethod就可以
                            Method setMethod = view.getClass().getMethod(eventMethod, eventClass);//ok
                            //但是我想试试，用getDeclaredMethod，却报了找不到方法，按理说不是应该也是可以的吗
//                            Method setMethod = view.getClass().getDeclaredMethod(eventMethod, eventClass);//运行报错
                            //下面这个方法是针对私有的设置访问权限的，设不设置无关紧要
//                            setMethod.setAccessible(true);
                            setMethod.invoke(view, proxyInstance);
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }

    private static class ListenerInvocationHandler<T> implements InvocationHandler {

        private Method method;
        private T target;

        public ListenerInvocationHandler(T target, Method method) {
            this.target = target;
            this.method = method;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return this.method.invoke(target, args);
        }
    }
}
