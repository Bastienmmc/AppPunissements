package com.ldnr.punissement.ui.main.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityGroupes implements IEntity {

    private static List<EntityGroupes> list = new ArrayList();

    // private static LiveData<EntityGroupes> list1 = new ArrayList();
    //private static MutableLiveData<List<EntityGroupes>> mFavs= new MutableLiveData<>();

    private int id;
    private String libelle_groupe;
    private String path_photo_groupe;

    /*public static MutableLiveData<List<EntityGroupes>>getMutableLiveData(){
        mFavs.setValue(list);
        return mFavs;
    }*/

    public EntityGroupes(int id, String libelle_groupe, String path_photo_groupe) {
        this.id = id;
        this.libelle_groupe = libelle_groupe;
        this.path_photo_groupe = path_photo_groupe;
    }

    public EntityGroupes() {

    }

    public EntityGroupes(String libelle_groupe) {
        this.libelle_groupe = libelle_groupe;
    }

    public static List<EntityGroupes> getList() {
        return list;
    }

    public static void setList(List list1) {
        list = list1;
    }

    public static void addElement(EntityGroupes el) {
        list.add(el);
    }

    public String getPath_photo_groupe() {
        return path_photo_groupe;
    }

    public void setPath_photo_groupe(String path_photo_groupe) {
        this.path_photo_groupe = path_photo_groupe;
    }

    public String getLibelle_groupe() {
        return libelle_groupe;
    }

    public void setLibelle_groupe(String libelle_groupe) {
        this.libelle_groupe = libelle_groupe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
