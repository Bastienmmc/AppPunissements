package com.ldnr.punissement.ui.main.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class EntityPunissement {

    public static List list = new ArrayList();

    private String title;

    public EntityPunissement(String title) {
        this.title = title;
    }


    public static List<EntityPunissement> getList() {
        list.add(new EntityPunissement("punissements"));
        return list;
    }
    public String getTitle() {
        return title;
    }

    public static void setList(List list) {
        EntityGroupes.list = list;
    }
}
