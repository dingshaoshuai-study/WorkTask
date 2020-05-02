package com.dss.workannotation.day01.anno.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LikeBean2 implements Parcelable {
    private String name;
    private int age;

    public LikeBean2() {
    }

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

    public LikeBean2(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<LikeBean2> CREATOR = new Creator<LikeBean2>() {
        @Override
        public LikeBean2 createFromParcel(Parcel in) {
            return new LikeBean2(in);
        }

        @Override
        public LikeBean2[] newArray(int size) {
            return new LikeBean2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    @Override
    public String toString() {
        return "LikeBean2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
