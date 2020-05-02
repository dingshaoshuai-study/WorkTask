package com.dss.workannotation.day02.anno;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IdRes;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventType(cls = View.OnClickListener.class, eventMethod = "setOnClickListener")
public @interface BindClick {
    @IdRes int[] value();
}
