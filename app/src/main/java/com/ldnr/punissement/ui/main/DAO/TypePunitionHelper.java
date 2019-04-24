package com.ldnr.punissement.ui.main.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TypePunitionHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "punissements.db";

       /* type Punition*/
    private static final String TABLE_TYPE_PUNITION_NAME = "type_punition";
    private static final String TABLE_TYPE_PUNITION_COLUMN_ID = "id_type_punition";
    private static final String TABLE_TYPE_PUNITION_COLUMN_TITLE = "title_type_punition";
    private static final String TABLE_TYPE_PUNITION_COLUMN_DESCRIPTION = "description_type_punition";


    // Déclaration méthodes d'accès à la BDD
    private SQLiteDatabase dbWrite = this.getWritableDatabase();
    // private SQLiteDatabase dbRead = this.getReadableDatabase();

        // Constructeur
    public TypePunitionHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    // OnCreate : création table punition
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TYPE_PUNITION_NAME
                + " ( " + TABLE_TYPE_PUNITION_COLUMN_ID + " integer primary key, "
                + TABLE_TYPE_PUNITION_COLUMN_TITLE + " text , "
                + TABLE_TYPE_PUNITION_COLUMN_DESCRIPTION + " text )"
        );
    }

    // En cas de changement de version, suppression et re création de la table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE_PUNITION_NAME);
        onCreate(db);
    }

    // Insertion d'un Groupe dans la base de données :
    // MODIFIER NOM OBJET !!!!!!!
    public void insertStagiaire(TypePunitionObject pTypePunition) {

        ContentValues content = new ContentValues();
        content.put(TABLE_TYPE_PUNITION_COLUMN_TITLE, pTypePunition.getText());
        content.put(TABLE_TYPE_PUNITION_COLUMN_DESCRIPTION, pTypePunition.getText());


        try {
            dbWrite.insertOrThrow(TABLE_TYPE_PUNITION_NAME, null, content);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mise à jour d'un type de punition
    public void updateStagiaire(TypePunitionObject pTypePunition) {
        ContentValues values = new ContentValues();
        values.put(TABLE_TYPE_PUNITION_COLUMN_TITLE, pTypePunition.getText());
        values.put(TABLE_TYPE_PUNITION_COLUMN_DESCRIPTION, pTypePunition.getImageUrl());


        // lancement mise à jour
        dbWrite.update(TABLE_TYPE_PUNITION_NAME, values, TABLE_TYPE_PUNITION_COLUMN_ID + " = ?", new String[]{String.valueOf(pTypePunition.getId())});
    }

    // Suppression type de punition

    public void deleteCity(TypePunitionObject pGroupe) {
        dbWrite.delete(TABLE_TYPE_PUNITION_NAME, TABLE_TYPE_PUNITION_COLUMN_ID + " = ?",
                new String[]{String.valueOf(pTypePunition.getId())});
    }

}
