package com.chinabrushwriting.lee.domain;

/**
 * Created by Lee on 2016/7/21.
 */
public class PersonInfo implements Comparable {
    private String name;
    private String values;
    private int bool = -1;
    private String pinyin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public int getBool() {
        return bool;
    }

    public void setBool(int bool) {
        this.bool = bool;
    }

    @Override
    public String toString() {
        return name + " " + values + " " + bool;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }


    @Override
    public int compareTo(Object another) {
        PersonInfo info = (PersonInfo) another;
        return getPinyin().compareTo(info.getPinyin());
    }
}
