package com.ldnr.punissement.ui.main.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.IEntity;

public class GroupeHelper extends SQLiteOpenHelper implements IDaoHelper {

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
    @Override
    public void insert(IEntity iEntity) {
        EntityGroupes pGroupe = (EntityGroupes) iEntity;
        ContentValues value = new ContentValues();
        value.put(TABLE_GROUPE_COLUMN_LIBELLE, pGroupe.getLibelle_groupe());
        value.put(TABLE_GROUPE_COLUMN_PATH_PHOTO, pGroupe.getPath_photo_groupe());

        try {
            dbWrite.insertOrThrow(TABLE_GROUPE_NAME, null, value);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    // Mise à jour d'un stagiaire
    @Override
    public void update(IEntity iEntity) {
        EntityGroupes pGroupe = (EntityGroupes) iEntity;
        ContentValues value = new ContentValues();
        value.put(TABLE_GROUPE_COLUMN_LIBELLE, pGroupe.getLibelle_groupe());
        value.put(TABLE_GROUPE_COLUMN_PATH_PHOTO, pGroupe.getPath_photo_groupe());

        try {
            // lancement mise à jour
            dbWrite.update(TABLE_GROUPE_NAME, value, TABLE_GROUPE_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(pGroupe.getId())});
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    @Override
    public void delete(IEntity iEntity) {
        try {
            EntityGroupes pGroupe = (EntityGroupes) iEntity;
            dbWrite.delete(TABLE_GROUPE_NAME, TABLE_GROUPE_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(pGroupe.getId())});
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
