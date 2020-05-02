package com.dss.workannotation.day01.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IdRes;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindClick {
    @IdRes int value()[];
}
