package com.ldnr.punissement.ui.main.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.entity.IEntity;

import java.util.ArrayList;

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
    private static final String TABLE_PUNITION_COLUMN_LIEU = "lieu_punition";
    private static final String TABLE_PUNITION_COLUMN_DATE = "date_punition";
    private static PunitionHelper instance;
    // Déclaration méthodes d'accès à la BDD
    private SQLiteDatabase dbWrite = this.getWritableDatabase();
    private SQLiteDatabase dbRead = this.getReadableDatabase();

    // Constructeur
    private PunitionHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
        try {
            dbWrite.execSQL("CREATE TABLE " + TABLE_PUNITION_NAME
                    + " ( " + TABLE_PUNITION_COLUMN_ID + " integer primary key, "
                    + TABLE_PUNITION_COLUMN_TITLE + " text , "
                    + TABLE_PUNITION_COLUMN_DESCRIPTION + " text , "
                    + TABLE_PUNITION_COLUMN_ID_TYPE + " integer , "
                    + TABLE_PUNITION_COLUMN_ID_STAGIAIRE + " integer , "
                    + TABLE_PUNITION_COLUMN_ID_GROUPE + " integer ,"
                    + TABLE_PUNITION_COLUMN_LIEU + " text ,"
                    + TABLE_PUNITION_COLUMN_DATE + " text )"
            );
        } catch (SQLException e) {

        }


    }

    public static PunitionHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PunitionHelper(context);
        }
        return instance;
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
                + TABLE_PUNITION_COLUMN_ID_GROUPE + " integer ,"
                + TABLE_PUNITION_COLUMN_LIEU + " text ,"
                + TABLE_PUNITION_COLUMN_DATE + " text )"
        );
    }

    // En cas de changement de version, suppression et re création de la table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUNITION_NAME);
        onCreate(db);
    }

    // Insertion d'une punition dans la base de données :

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
            value.put(TABLE_PUNITION_COLUMN_LIEU, pPunition.getLieu());
            value.put(TABLE_PUNITION_COLUMN_DATE, pPunition.getDate());


            dbWrite.insertOrThrow(TABLE_PUNITION_NAME, null, value);

            Cursor res = dbRead.rawQuery("SELECT * FROM " + TABLE_PUNITION_NAME + " ORDER BY " + TABLE_PUNITION_COLUMN_ID + " DESC LIMIT 1", null);
            if (res != null && res.getCount() > 0) {
                res.moveToFirst();
                pPunition.setId(res.getInt(res.getColumnIndex(TABLE_PUNITION_COLUMN_ID)));
            }


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
            value.put(TABLE_PUNITION_COLUMN_LIEU, pPunition.getLieu());
            value.put(TABLE_PUNITION_COLUMN_DATE, pPunition.getDate());

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
            dbWrite.delete(TABLE_PUNITION_NAME, TABLE_PUNITION_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(pPunition.getId())});
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fonction qui renvoie la liste des punitions
     *
     * @return
     */
    @Override
    public ArrayList<IEntity> getList() {
        ArrayList<IEntity> punissements = new ArrayList<>();
        try {
            Cursor res = dbRead.rawQuery("SELECT * FROM " + TABLE_PUNITION_NAME, null);
            // Si la base est vide, renvoie la liste initialisée à vide
            if (res.getCount() == 0) {
                res.close();
                return punissements;
            }
            res.moveToFirst();
            while (!res.isAfterLast()) {
                EntityPunissement punissement = new EntityPunissement(res.getInt(res.getColumnIndex(TABLE_PUNITION_COLUMN_ID)),
                        res.getString(res.getColumnIndex(TABLE_PUNITION_COLUMN_TITLE)),
                        res.getString(res.getColumnIndex(TABLE_PUNITION_COLUMN_DESCRIPTION)),
                        res.getInt(res.getColumnIndex(TABLE_PUNITION_COLUMN_ID_TYPE)),
                        res.getInt(res.getColumnIndex(TABLE_PUNITION_COLUMN_ID_STAGIAIRE)),
                        res.getInt(res.getColumnIndex(TABLE_PUNITION_COLUMN_ID_GROUPE)),
                        res.getString(res.getColumnIndex(TABLE_PUNITION_COLUMN_LIEU)),
                        res.getString(res.getColumnIndex(TABLE_PUNITION_COLUMN_DATE)));
                punissements.add(punissement);
                res.moveToNext();
            }
            res.close();
            return punissements;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return punissements;
        }
    }

    @Override
    public IEntity getElement(int id) {
        try {
            Cursor cursor = dbRead.query(TABLE_PUNITION_NAME,
                    new String[]{TABLE_PUNITION_COLUMN_ID, TABLE_PUNITION_COLUMN_TITLE, TABLE_PUNITION_COLUMN_DESCRIPTION, TABLE_PUNITION_COLUMN_ID_TYPE, TABLE_PUNITION_COLUMN_ID_STAGIAIRE, TABLE_PUNITION_COLUMN_ID_GROUPE},
                    TABLE_PUNITION_COLUMN_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            EntityPunissement punissement = new EntityPunissement(cursor.getInt(cursor.getColumnIndex(TABLE_PUNITION_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(TABLE_PUNITION_COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(TABLE_PUNITION_COLUMN_DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndex(TABLE_PUNITION_COLUMN_ID_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(TABLE_PUNITION_COLUMN_ID_STAGIAIRE)),
                    cursor.getInt(cursor.getColumnIndex(TABLE_PUNITION_COLUMN_ID_GROUPE)),
                    cursor.getString(cursor.getColumnIndex(TABLE_PUNITION_COLUMN_LIEU)),
                    cursor.getString(cursor.getColumnIndex(TABLE_PUNITION_COLUMN_DATE)));

            cursor.close();
            return punissement;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteAllElelementsTable() {
        dbWrite.execSQL("DELETE FROM " + TABLE_PUNITION_NAME);
    }
}
