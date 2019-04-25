package com.ldnr.punissement.ui.main.entity;

import java.util.ArrayList;
import java.util.List;


public class EntityTypePunition implements IEntity {

    private static List<EntityTypePunition> list = new ArrayList();
    private String title;
    private String description;
    private int id;

    public EntityTypePunition(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public EntityTypePunition() {

    }

    public static List<EntityTypePunition> getList() {
        return list;
    }

    public static void setList(List list1) {
        list = list1;
    }

    public static void addElement(EntityTypePunition el) {
        list.add(el);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return this.title + " " + this.description;
    }
}