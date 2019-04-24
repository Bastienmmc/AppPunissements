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

    private int id;
    private String libelle_groupe;
    private String path_photo_groupe;

    public static MutableLiveData<List>getMutableLiveData(){
        mFavs.setValue(list);
        return mFavs;
    }

    public String getPath_photo_groupe() {
        return path_photo_groupe;
    }

    public void setLibelle_groupe(String libelle_groupe) {
        this.libelle_groupe = libelle_groupe;
    }

    public void setPath_photo_groupe(String path_photo_groupe) {
        this.path_photo_groupe = path_photo_groupe;
    }

    public EntityGroupes(String libelle_groupe) {
        this.libelle_groupe = libelle_groupe;
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

    public String getLibelle_groupe() {
        return libelle_groupe;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
