package com.ldnr.punissement.ui.main.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityGroupes {

    private static List<EntityGroupes> list = new ArrayList();

    private String title;

    public EntityGroupes(String title) {
        this.title = title;
    }

    public static List<EntityGroupes> getList() {
        //list.add(new EntityGroupes("groupes"));
        return list;
    }
    public static void addElement(EntityGroupes el){
        list.add(el);
    }

    public static void setList(List list1) {
        list = list1;
    }

    public String getTitle() {
        return title;
    }
}
