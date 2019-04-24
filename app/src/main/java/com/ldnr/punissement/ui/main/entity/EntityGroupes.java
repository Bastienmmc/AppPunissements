package com.ldnr.punissement.ui.main.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityGroupes {

    public static List list = new ArrayList();

    private String title;

    public EntityGroupes(String title) {
        this.title = title;
    }


    public static List<EntityGroupes> getList() {
        list.add(new EntityGroupes("groupes"));
        return list;
    }

    public String getTitle() {
        return title;
    }

    public static void setList(List list) {
        EntityGroupes.list = list;
    }
}
