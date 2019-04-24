package com.ldnr.punissement.ui.main.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ldnr.punissement.ui.main.entity.EntityPunissement;

public class PunitionHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "punissements.db";

        /* table Punition*/
    private static final String TABLE_PUNITION_NAME = "punition";
    private static final String TABLE_PUNITION_COLUMN_ID = "id_punition";
    private static final String TABLE_PUNITION_COLUMN_TITLE = "title_punition";
    private static final String TABLE_PUNITION_COLUMN_DESCRIPTION = "description_punition";
    private static final String TABLE_PUNITION_COLUMN_ID_TYPE = "id_type_punition";
    private static final String TABLE_PUNITION_COLUMN_ID_STAGIAIRE = "id_stagiaire_punition";
    private static final String TABLE_PUNITION_COLUMN_ID_GROUPE = "id_groupe_punition";


    // Déclaration méthodes d'accès à la BDD
    private SQLiteDatabase dbWrite = this.getWritableDatabase();
    // private SQLiteDatabase dbRead = this.getReadableDatabase();


    // Constructeur
    public PunitionHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    // OnCreate : création table groupe
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PUNITION_NAME
                + " ( " + TABLE_PUNITION_COLUMN_ID + " integer primary key, "
                + TABLE_PUNITION_COLUMN_TITLE + " text , "
                + TABLE_PUNITION_COLUMN_DESCRIPTION + " text , "
                + TABLE_PUNITION_COLUMN_ID_TYPE + " integer , "
                + TABLE_PUNITION_COLUMN_ID_STAGIAIRE + " integer , "
                + TABLE_PUNITION_COLUMN_ID_GROUPE + " integer )"
        );
    }

    // En cas de changement de version, suppression et re création de la table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUNITION_NAME);
        onCreate(db);
    }

    // Insertion d'une punition dans la base de données :
    // MODIFIER NOM OBJET !!!!!!!
    public void insertStagiaire(EntityPunissement pGroupe) {

        ContentValues content = new ContentValues();
        content.put(TABLE_PUNITION_COLUMN_TITLE, pGroupe.getText());
        content.put(TABLE_PUNITION_COLUMN_DESCRIPTION, pGroupe.getText());
        content.put(TABLE_PUNITION_COLUMN_ID_TYPE, pGroupe.getInt());
        content.put(TABLE_PUNITION_COLUMN_ID_STAGIAIRE, pGroupe.getInt());
        content.put(TABLE_PUNITION_COLUMN_ID_GROUPE, pGroupe.getInt());


        try {
            dbWrite.insertOrThrow(TABLE_PUNITION_NAME, null, content);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mise à jour d'un stagiaire
    public void updateStagiaire(EntityPunissement pPunition) {
        ContentValues values = new ContentValues();
        values.put(TABLE_PUNITION_COLUMN_TITLE, pPunition.getText());
        values.put(TABLE_PUNITION_COLUMN_DESCRIPTION, pPunition.getText());
        values.put(TABLE_PUNITION_COLUMN_ID_TYPE, pPunition.getInt());
        values.put(TABLE_PUNITION_COLUMN_ID_STAGIAIRE, pPunition.getInt());
        values.put(TABLE_PUNITION_COLUMN_ID_GROUPE, pPunition.getInt());


        // lancement mise à jour
        dbWrite.update(TABLE_PUNITION_NAME, values, TABLE_PUNITION_COLUMN_ID + " = ?", new String[]{String.valueOf(pPunition.getId())});
    }

    // Suppression Stagiaire

    public void deleteCity(EntityPunissement pPunition) {
        dbWrite.delete(TABLE_PUNITION_NAME, TABLE_PUNITION_COLUMN_ID + " = ?",
                new String[]{String.valueOf(pPunition.getId())});
    }

}
