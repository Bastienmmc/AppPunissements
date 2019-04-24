package com.ldnr.punissement.ui.main.entity;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class EntityPunissement implements IEntity {

    private static List<EntityPunissement> list = new ArrayList();


    private int id;
    private String title;
    private String description;
    private int id_type;
    private int id_stagiaire=0;
    private int id_groupe=0;

    public String getDescription() {
        return description;
    }

    public int getId_type() {
        return id_type;
    }

    public int getId_stagiaire() {
        return id_stagiaire;
    }

    public int getId_groupe() {
        return id_groupe;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public void setId_stagiaire(int id_stagiaire) {
        this.id_stagiaire = id_stagiaire;
    }

    public void setId_groupe(int id_groupe) {
        this.id_groupe = id_groupe;
    }

    public EntityPunissement(String title) {
        this.title = title;
    }


    public static List<EntityPunissement> getList() {
        return list;
    }

    public static void addElement(EntityPunissement el){
        list.add(el);
    }
    public static void setList(List list1) {
        list = list1;
    }

    public String getTitle() {
        return title;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
