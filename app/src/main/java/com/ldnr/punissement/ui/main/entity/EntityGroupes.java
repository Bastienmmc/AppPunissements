package com.ldnr.punissement.ui.main.entity;

import android.arch.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

public class EntityGroupes {

    private static List<EntityGroupes> list = new ArrayList();
   // private static LiveData<EntityGroupes> list1 = new ArrayList();
    private static MutableLiveData<List> mFavs= new MutableLiveData<>();
    private String title;

    public static MutableLiveData<List>getMutableLiveData(){
        mFavs.setValue(list);
        return mFavs;
    }

    public EntityGroupes(String title) {
        this.title = title;
    }

    public static List<EntityGroupes> getList() {
        //list.add(new EntityGroupes("groupes"));
        return list;
    }
    public static void addElement(EntityGroupes el){
        list.add(el);
    }

    public static void setList(List list1) {
        list = list1;
    }

    public String getTitle() {
        return title;
    }
}
