package com.ldnr.punissement.ui.main.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityStagiaires {

    private static List list = new ArrayList();
    private String title;

    public EntityStagiaires(String title) {
        this.title = title;
    }

    public static List<EntityStagiaires> getList() {
        list.add(new EntityStagiaires("stagiares"));
        return list;
    }

    public static void setList(List list1) {
        list = list1;
    }

    public String getTitle() {
        return title;
    }
}
