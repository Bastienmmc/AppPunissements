package com.ldnr.punissement.ui.main.entity;

import java.util.ArrayList;
import java.util.List;


public class EntityTypePunition {

    private static List<EntityTypePunition> list = new ArrayList();
    private String title;
    private String description;
    private int id;

    public EntityTypePunition(String title) {
        this.title = title;
    }

    public static List<EntityTypePunition> getList() {
        // list.add(new EntityStagiaires("stagiares"));
        return list;
    }
    public static void addElement(EntityTypePunition el){
        list.add(el);
    }

    public String getDescription() {
        return description;
    }

    public static void setList(List<EntityTypePunition> list) {
        EntityTypePunition.list = list;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
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