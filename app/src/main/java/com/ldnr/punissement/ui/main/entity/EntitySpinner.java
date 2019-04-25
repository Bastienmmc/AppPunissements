package com.ldnr.punissement.ui.main.entity;

public class EntitySpinner {
    private String str;
    private int index;
    private int type;
    public EntitySpinner(String str, int index, int type){
        this.str=str;
        this.index=index;
        this.type=type;
    }
    @Override
    public String toString(){
        return str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
