package com.ldnr.punissement.ui.main.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GroupeHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "punissements.db";

       /* table Groupe*/
    private static final String TABLE_GROUPE_NAME = "groupe";

    private static final String TABLE_GROUPE_COLUMN_ID = "id_groupe";
    private static final String TABLE_GROUPE_COLUMN_LIBELLE = "libelle_groupe";
    private static final String TABLE_GROUPE_COLUMN_PATH_PHOTO = "path_photo_groupe";


    // Déclaration méthodes d'accès à la BDD
    private SQLiteDatabase dbWrite = this.getWritableDatabase();
    // private SQLiteDatabase dbRead = this.getReadableDatabase();


    // Constructeur
    public GroupeHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    // OnCreate : création table groupe
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_GROUPE_NAME
                + " ( " + TABLE_GROUPE_COLUMN_ID + " integer primary key, "
                + TABLE_GROUPE_COLUMN_LIBELLE + " text , "
                + TABLE_GROUPE_COLUMN_PATH_PHOTO + " text )"
        );
    }

    // En cas de changement de version, suppression et re création de la table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPE_NAME);
        onCreate(db);
    }

    // Insertion d'un Groupe dans la base de données :
    // MODIFIER NOM OBJET !!!!!!!
    public void insertStagiaire(GroupeObject pGroupe) {

        ContentValues content = new ContentValues();
        content.put(TABLE_GROUPE_COLUMN_LIBELLE, pGroupe.getText());
        content.put(TABLE_GROUPE_COLUMN_PATH_PHOTO, pGroupe.getImageUrl());


        try {
            dbWrite.insertOrThrow(TABLE_GROUPE_NAME, null, content);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mise à jour d'un stagiaire
    public void updateStagiaire(GroupeObject pGroupe) {
        ContentValues values = new ContentValues();
        values.put(TABLE_GROUPE_COLUMN_LIBELLE, pGroupe.getText());
        values.put(TABLE_GROUPE_COLUMN_PATH_PHOTO, pGroupe.getImageUrl());


        // lancement mise à jour
        dbWrite.update(TABLE_GROUPE_NAME, values, TABLE_GROUPE_COLUMN_ID + " = ?", new String[]{String.valueOf(pGroupe.getId())});
    }

    // Suppression Stagiaire

    public void deleteCity(GroupeObject pGroupe) {
        dbWrite.delete(TABLE_GROUPE_NAME, TABLE_GROUPE_COLUMN_ID + " = ?",
                new String[]{String.valueOf(pGroupe.getId())});
    }

}
