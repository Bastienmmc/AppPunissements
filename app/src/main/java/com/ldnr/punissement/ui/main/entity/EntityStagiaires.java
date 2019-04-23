package com.ldnr.punissement.ui.main.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityStagiaires {

    public static List list = new ArrayList();
    public String title;

    public EntityStagiaires(String title) {
        this.title = title;
    }

    public static List<EntityStagiaires> getList() {
        list.add(new EntityStagiaires("stagiares"));
        return list;
    }
}
