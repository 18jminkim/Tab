package com.example.tab;

import android.graphics.drawable.Drawable;

public class Number {
    private String name;
    private String num;
    private Drawable icon;

    public String getName(){
        return name;
    }
    public String getNum(){
        return num;
    }
    public Drawable getIcon() { return null; }
    public void setName(String name){
        this.name = name;
    }
    public void setNum(String num){
        this.num = num;
    }
    public void setIcon(Drawable icon){this.icon = icon; }
    public boolean search(String name){
        if(this.name.contains(name)) return true;
        else return false;
    }


}
