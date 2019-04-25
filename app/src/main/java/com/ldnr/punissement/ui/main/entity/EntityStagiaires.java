package com.ldnr.punissement.ui.main.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityStagiaires implements IEntity {

    private static List<EntityStagiaires> list = new ArrayList();

    private int id;
    private String lastname;
    private String firstname;
    private String path_photo;
    private String mail;
    private String sms;
    private String strAddToString = "";
    private int id_groupe;


    public EntityStagiaires(int id, String lastname, String firstname, String path_photo, String mail, String sms, int id_groupe) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.path_photo = path_photo;
        this.mail = mail;
        this.sms = sms;
        this.id_groupe = id_groupe;
        this.strAddToString = "";
    }

    public EntityStagiaires() {
    }

    public EntityStagiaires(String lastname) {
        this.lastname = lastname;
    }

    public static List<EntityStagiaires> getList() {
        // list.add(new EntityStagiaires("stagiares"));
        return list;
    }

    public static void setList(List list1) {
        list = list1;
    }

    public static void addElement(EntityStagiaires el) {
        list.add(el);
    }


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPath_photo() {
        return path_photo;
    }

    public void setPath_photo(String path_photo) {
        this.path_photo = path_photo;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getId_groupe() {
        return id_groupe;
    }

    public void setId_groupe(int id_groupe) {
        this.id_groupe = id_groupe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastAndFirstName() {
        return lastname + " " + firstname;
    }

    public String toString() {
        return this.firstname + " " + this.lastname + " " + this.mail + " " + this.sms + " " + this.strAddToString;
    }

    public void addToString(String str) {
        this.strAddToString += str + " ";
    }
}
