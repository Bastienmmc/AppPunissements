package com.ldnr.punissement.ui.main.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.IEntity;

import java.util.ArrayList;

public class GroupeHelper extends SQLiteOpenHelper implements IDaoHelper {

    private static final String DATABASE_NAME = "punissements.db";
    /* table Groupe*/
    private static final String TABLE_GROUPE_NAME = "groupe";
    private static final String TABLE_GROUPE_COLUMN_ID = "id_groupe";
    private static final String TABLE_GROUPE_COLUMN_LIBELLE = "libelle_groupe";
    private static final String TABLE_GROUPE_COLUMN_PATH_PHOTO = "path_photo_groupe";
    private static GroupeHelper instance;
    // Déclaration méthodes d'accès à la BDD
    private SQLiteDatabase dbWrite = this.getWritableDatabase();
    private SQLiteDatabase dbRead = this.getReadableDatabase();

    // Constructeur
    private GroupeHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
        try {
            dbWrite.execSQL("CREATE TABLE " + TABLE_GROUPE_NAME
                    + " ( " + TABLE_GROUPE_COLUMN_ID + " integer primary key, "
                    + TABLE_GROUPE_COLUMN_LIBELLE + " text , "
                    + TABLE_GROUPE_COLUMN_PATH_PHOTO + " text )"
            );
        } catch (SQLException e) {

        }
    }

    public static GroupeHelper getInstance(Context context) {
        if (instance == null) {
            instance = new GroupeHelper(context);
        }
        return instance;
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
        try {
            EntityGroupes pGroupe = (EntityGroupes) iEntity;
            ContentValues value = new ContentValues();
            value.put(TABLE_GROUPE_COLUMN_LIBELLE, pGroupe.getLibelle_groupe());
            value.put(TABLE_GROUPE_COLUMN_PATH_PHOTO, pGroupe.getPath_photo_groupe());

            dbWrite.insertOrThrow(TABLE_GROUPE_NAME, null, value);
            //je trouve le dernier id
            Cursor res = dbRead.rawQuery("SELECT * FROM " + TABLE_GROUPE_NAME + " ORDER BY " + TABLE_GROUPE_COLUMN_ID + " DESC LIMIT 1", null);
            if (res != null && res.getCount() > 0) {
                res.moveToFirst();
                pGroupe.setId(res.getInt(res.getColumnIndex(TABLE_GROUPE_COLUMN_ID)));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    // Mise à jour d'un groupe
    @Override
    public void update(IEntity iEntity) {
        try {
            EntityGroupes pGroupe = (EntityGroupes) iEntity;
            ContentValues value = new ContentValues();
            value.put(TABLE_GROUPE_COLUMN_LIBELLE, pGroupe.getLibelle_groupe());
            value.put(TABLE_GROUPE_COLUMN_PATH_PHOTO, pGroupe.getPath_photo_groupe());
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

    /**
     * Fonction qui renvoie la liste des groupes
     *
     * @return
     */
    @Override
    public ArrayList<IEntity> getList() {
        ArrayList<IEntity> groupes = new ArrayList<>();
        try {

            Cursor res = dbRead.rawQuery("SELECT * FROM " + TABLE_GROUPE_NAME, null);
            // Si la base est vide, renvoie la liste initialisée à vide
            if (res.getCount() == 0) {
                res.close();
                return groupes;
            }
            res.moveToFirst();
            while (!res.isAfterLast()) {
                EntityGroupes groupe = new EntityGroupes(res.getInt(res.getColumnIndex(TABLE_GROUPE_COLUMN_ID)),
                        res.getString(res.getColumnIndex(TABLE_GROUPE_COLUMN_LIBELLE)),
                        res.getString(res.getColumnIndex(TABLE_GROUPE_COLUMN_PATH_PHOTO)));
                groupes.add(groupe);
                res.moveToNext();
            }
            res.close();
            return groupes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return groupes;
        }
    }

    @Override
    public IEntity getElement(int id) {
        EntityGroupes groupe = new EntityGroupes();
        try {

            Cursor cursor = dbRead.query(
                    TABLE_GROUPE_NAME /* table */,
                    new String[]{TABLE_GROUPE_COLUMN_ID, TABLE_GROUPE_COLUMN_LIBELLE, TABLE_GROUPE_COLUMN_PATH_PHOTO} /* columns */,
                    TABLE_GROUPE_COLUMN_ID + " = ?" /* where or selection */,
                    new String[]{String.valueOf(id)} /* selectionArgs i.e. value to replace ? */,
                    null /* groupBy */,
                    null /* having */,
                    null /* orderBy */
            );

            /*Cursor cursor = dbRead.query(TABLE_GROUPE_COLUMN_LIBELLE,
                    new String[]{TABLE_GROUPE_COLUMN_ID, TABLE_GROUPE_COLUMN_LIBELLE, TABLE_GROUPE_COLUMN_PATH_PHOTO},
                    TABLE_GROUPE_COLUMN_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);*/

            if (cursor != null) {
                cursor.moveToFirst();
                groupe.setId(cursor.getInt(cursor.getColumnIndex(TABLE_GROUPE_COLUMN_ID)));
                groupe.setLibelle_groupe(cursor.getString(cursor.getColumnIndex(TABLE_GROUPE_COLUMN_LIBELLE)));
                groupe.setPath_photo_groupe(cursor.getString(cursor.getColumnIndex(TABLE_GROUPE_COLUMN_PATH_PHOTO)));
            }

            cursor.close();
            return groupe;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return groupe;
        }

    }

    @Override
    public void deleteAllElelementsTable() {
        dbWrite.execSQL("DELETE FROM " + TABLE_GROUPE_NAME);
    }

}
