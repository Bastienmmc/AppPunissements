package com.ldnr.punissement.ui.main.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.entity.IEntity;

public class PunitionHelper extends SQLiteOpenHelper implements IDaoHelper {

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
    @Override
    public void insert(IEntity iEntity) {
        try {
            EntityPunissement pPunition = (EntityPunissement) iEntity;

            ContentValues value = new ContentValues();
            value.put(TABLE_PUNITION_COLUMN_TITLE, pPunition.getTitle());
            value.put(TABLE_PUNITION_COLUMN_DESCRIPTION, pPunition.getDescription());
            value.put(TABLE_PUNITION_COLUMN_ID_TYPE, pPunition.getId_groupe());
            value.put(TABLE_PUNITION_COLUMN_ID_STAGIAIRE, pPunition.getId_stagiaire());
            value.put(TABLE_PUNITION_COLUMN_ID_GROUPE, pPunition.getId_groupe());

            dbWrite.insertOrThrow(TABLE_PUNITION_NAME, null, value);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mise à jour d'un stagiaire
    @Override
    public void update(IEntity iEntity) {
        try {
            EntityPunissement pPunition = (EntityPunissement) iEntity;
            ContentValues value = new ContentValues();
            value.put(TABLE_PUNITION_COLUMN_TITLE, pPunition.getTitle());
            value.put(TABLE_PUNITION_COLUMN_DESCRIPTION, pPunition.getDescription());
            value.put(TABLE_PUNITION_COLUMN_ID_TYPE, pPunition.getId_type());
            value.put(TABLE_PUNITION_COLUMN_ID_STAGIAIRE, pPunition.getId_stagiaire());
            value.put(TABLE_PUNITION_COLUMN_ID_GROUPE, pPunition.getId_groupe());

            dbWrite.update(TABLE_PUNITION_NAME, value, TABLE_PUNITION_COLUMN_ID + " = ?", new String[]{String.valueOf(pPunition.getId())});
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    // Suppression Stagiaire
    @Override
    public void delete(IEntity iEntity) {
        try {

            EntityPunissement pPunition = (EntityPunissement) iEntity;
            EntityGroupes pGroupe = (EntityGroupes) iEntity;
            dbWrite.delete(TABLE_PUNITION_NAME, TABLE_PUNITION_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(pPunition.getId())});
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
