package com.ldnr.punissement.ui.main.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityStagiaires  implements IEntity{

    private static List<EntityStagiaires> list = new ArrayList();

    private int id;
    private String name;
    private String firstname;
    private String path_photo;
    private int id_groupe;

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setPath_photo(String path_photo) {
        this.path_photo = path_photo;
    }

    public void setId_groupe(int id_groupe) {
        this.id_groupe = id_groupe;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPath_photo() {
        return path_photo;
    }

    public int getId_groupe() {
        return id_groupe;
    }

    public EntityStagiaires(String name) {
        this.name = name;
    }

    public static List<EntityStagiaires> getList() {
       // list.add(new EntityStagiaires("stagiares"));
        return list;
    }
    public static void addElement(EntityStagiaires el){
        list.add(el);
    }

    public static void setList(List list1) {
        list = list1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
