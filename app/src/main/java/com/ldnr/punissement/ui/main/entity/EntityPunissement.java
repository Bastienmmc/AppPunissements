package com.ldnr.punissement.ui.main.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityPunissement implements IEntity {

    private static List<EntityPunissement> list = new ArrayList();


    private int id;
    private String title;
    private String description;
    private String lieu;
    private String date;

    private int id_type;
    private int id_stagiaire = 0;
    private int id_groupe = 0;


    public EntityPunissement(int id, String title, String description, int id_type, int id_stagiaire, int id_groupe, String lieu, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.id_type = id_type;
        this.id_stagiaire = id_stagiaire;
        this.id_groupe = id_groupe;
        this.lieu = lieu;
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public String getDate() {
        return date;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public EntityPunissement() {

    }
    public EntityPunissement(String title) {
        this.title = title;
    }

    public static List<EntityPunissement> getList() {
        return list;
    }

    public static void setList(List list1) {
        list = list1;
    }

    public static void addElement(EntityPunissement el) {
        list.add(el);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public int getId_stagiaire() {
        return id_stagiaire;
    }

    public void setId_stagiaire(int id_stagiaire) {
        this.id_stagiaire = id_stagiaire;
    }

    public int getId_groupe() {
        return id_groupe;
    }

    public void setId_groupe(int id_groupe) {
        this.id_groupe = id_groupe;
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
}
