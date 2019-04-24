package com.ldnr.punissement.ui.main.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ldnr.punissement.ui.main.entity.EntityTypePunition;

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

    public void insert(EntityTypePunition typePunition) {

        ContentValues content = new ContentValues();
        content.put(TABLE_TYPE_PUNITION_COLUMN_TITLE, typePunition.getTitle());
        content.put(TABLE_TYPE_PUNITION_COLUMN_DESCRIPTION, typePunition.getDescription());


        try {
            dbWrite.insertOrThrow(TABLE_TYPE_PUNITION_NAME, null, content);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mise à jour d'un type de punition
    public void update(EntityTypePunition typePunition) {
        ContentValues values = new ContentValues();
        values.put(TABLE_TYPE_PUNITION_COLUMN_TITLE, typePunition.getTitle());
        values.put(TABLE_TYPE_PUNITION_COLUMN_DESCRIPTION, typePunition.getDescription());


        // lancement mise à jour
        try {
        dbWrite.update(TABLE_TYPE_PUNITION_NAME, values, TABLE_TYPE_PUNITION_COLUMN_ID + " = ?", new String[]{String.valueOf(typePunition.getId())});
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Suppression type de punition

    public void delete(EntityTypePunition typePunition) {
        try{
            dbWrite.delete(TABLE_TYPE_PUNITION_NAME, TABLE_TYPE_PUNITION_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(typePunition.getId())});
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


}
