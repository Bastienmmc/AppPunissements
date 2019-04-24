package com.ldnr.punissement.ui.main.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityPunissement {

    private static List list = new ArrayList();

    private String title;

    public EntityPunissement(String title) {
        this.title = title;
    }


    public static List<EntityPunissement> getList() {
        list.add(new EntityPunissement("punissements"));
        return list;
    }

    public static void setList(List list1) {
        list = list1;
    }

    public String getTitle() {
        return title;
    }
}
