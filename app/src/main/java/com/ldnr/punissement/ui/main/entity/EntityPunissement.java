package com.ldnr.punissement.ui.main.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityPunissement {

    private static List<EntityPunissement> list = new ArrayList();

    private String title;

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
}
