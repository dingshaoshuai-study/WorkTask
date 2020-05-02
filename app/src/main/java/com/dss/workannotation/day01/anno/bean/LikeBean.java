package com.dss.workannotation.day01.anno.bean;

import java.io.Serializable;

public class LikeBean implements Serializable {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "LikeBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
