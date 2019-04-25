package com.ldnr.punissement.ui.main.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ldnr.punissement.ui.main.entity.EntityStagiaires;
import com.ldnr.punissement.ui.main.entity.IEntity;

import java.util.ArrayList;

public class StagiaireHelper extends SQLiteOpenHelper implements IDaoHelper {

    private static final String DATABASE_NAME = "punissements.db";

    /* table Stagiaire*/
    private static final String TABLE_STAGIAIRE_NAME = "stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_ID = "id_stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_LASTNAME = "lastname_stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_FIRSTNAME = "firstname_stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_PATH_PHOTO = "path_photo_stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_MAIL = "mail_stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_SMS = "sms_stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_ID_GROUPE = "id_groupe";
    private static StagiaireHelper instance;
    // Déclaration méthodes d'accès à la BDD
    private SQLiteDatabase dbWrite = this.getWritableDatabase();
    private SQLiteDatabase dbRead = this.getReadableDatabase();

    // Constructeur
    private StagiaireHelper(Context context) {

        super(context, DATABASE_NAME, null, 3);
        try {
            dbWrite.execSQL("CREATE TABLE " + TABLE_STAGIAIRE_NAME
                    + " ( " + TABLE_STAGIAIRE_COLUMN_ID + " integer primary key, "
                    + TABLE_STAGIAIRE_COLUMN_LASTNAME + " text , "
                    + TABLE_STAGIAIRE_COLUMN_FIRSTNAME + " text , "
                    + TABLE_STAGIAIRE_COLUMN_PATH_PHOTO + " text , "
                    + TABLE_STAGIAIRE_COLUMN_MAIL + " text , "
                    + TABLE_STAGIAIRE_COLUMN_SMS + " text , "
                    + TABLE_STAGIAIRE_COLUMN_ID_GROUPE + " integer )"
            );
        } catch (SQLException e) {

        }
    }

    public static StagiaireHelper getInstance(Context context) {
        if (instance == null) {
            instance = new StagiaireHelper(context);
        }
        return instance;
    }

    // OnCreate : création table stagiaire
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_STAGIAIRE_NAME
                + " ( " + TABLE_STAGIAIRE_COLUMN_ID + " integer primary key, "
                + TABLE_STAGIAIRE_COLUMN_LASTNAME + " text , "
                + TABLE_STAGIAIRE_COLUMN_FIRSTNAME + " text , "
                + TABLE_STAGIAIRE_COLUMN_PATH_PHOTO + " text , "
                + TABLE_STAGIAIRE_COLUMN_MAIL + " text , "
                + TABLE_STAGIAIRE_COLUMN_SMS + " text , "
                + TABLE_STAGIAIRE_COLUMN_ID_GROUPE + " integer )"
        );
    }

    // En cas de changement de version, suppression et re création de la table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAGIAIRE_NAME);
        onCreate(db);
    }

    // Insertion d'un Stagiaire dans la base de données :
    // MODIFIER NOM OBJET !!!!!!!
    @Override
    public void insert(IEntity iEntity) {
        try {
            EntityStagiaires pStagiaire = (EntityStagiaires) iEntity;
            ContentValues value = new ContentValues();
            value.put(TABLE_STAGIAIRE_COLUMN_LASTNAME, pStagiaire.getLastname());
            value.put(TABLE_STAGIAIRE_COLUMN_FIRSTNAME, pStagiaire.getFirstname());
            value.put(TABLE_STAGIAIRE_COLUMN_PATH_PHOTO, pStagiaire.getPath_photo());
            value.put(TABLE_STAGIAIRE_COLUMN_MAIL, pStagiaire.getMail());
            value.put(TABLE_STAGIAIRE_COLUMN_SMS, pStagiaire.getSms());
            value.put(TABLE_STAGIAIRE_COLUMN_ID_GROUPE, pStagiaire.getId_groupe());

            dbWrite.insert(TABLE_STAGIAIRE_NAME, null, value);
            // dbWrite.insertOrThrow(TABLE_STAGIAIRE_NAME, null, value);

            Cursor res = dbRead.rawQuery("SELECT * FROM " + TABLE_STAGIAIRE_NAME + " ORDER BY " + TABLE_STAGIAIRE_COLUMN_ID + " DESC LIMIT 1", null);
            if (res != null && res.getCount() > 0) {
                res.moveToFirst();
                pStagiaire.setId(res.getInt(res.getColumnIndex(TABLE_STAGIAIRE_COLUMN_ID)));
            }
        } catch (SQLException e) {
            Log.d("testing", "#########" + e.getMessage());
        }
    }

    // Mise à jour d'un stagiaire
    @Override
    public void update(IEntity iEntity) {
        try {
            EntityStagiaires pStagiaire = (EntityStagiaires) iEntity;
            ContentValues value = new ContentValues();
            value.put(TABLE_STAGIAIRE_COLUMN_LASTNAME, pStagiaire.getLastname());
            value.put(TABLE_STAGIAIRE_COLUMN_FIRSTNAME, pStagiaire.getFirstname());
            value.put(TABLE_STAGIAIRE_COLUMN_PATH_PHOTO, pStagiaire.getPath_photo());
            value.put(TABLE_STAGIAIRE_COLUMN_MAIL, pStagiaire.getMail());
            value.put(TABLE_STAGIAIRE_COLUMN_SMS, pStagiaire.getSms());
            value.put(TABLE_STAGIAIRE_COLUMN_ID_GROUPE, pStagiaire.getId_groupe());


            // lancement mise à jour
            dbWrite.update(TABLE_STAGIAIRE_NAME, value, TABLE_STAGIAIRE_COLUMN_ID + " = ?", new String[]{String.valueOf(pStagiaire.getId())});
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Suppression Stagiaire
    @Override
    public void delete(IEntity iEntity) {

        try {
            EntityStagiaires pStagiaire = (EntityStagiaires) iEntity;
            dbWrite.delete(TABLE_STAGIAIRE_NAME, TABLE_STAGIAIRE_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(pStagiaire.getId())});
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fonction qui renvoie la liste des stagiaires
     *
     * @return
     */
    @Override
    public ArrayList<IEntity> getList() {
        ArrayList<IEntity> stagiaires = new ArrayList<>();
        try {

            Cursor res = dbRead.rawQuery("SELECT * FROM " + TABLE_STAGIAIRE_NAME, null);
            // Si la base est vide, renvoie la liste initialisée à vide
            if (res.getCount() == 0) {
                res.close();
                return stagiaires;
            }
            res.moveToFirst();
            while (!res.isAfterLast()) {
                EntityStagiaires stagiaire = new EntityStagiaires(res.getInt(res.getColumnIndex(TABLE_STAGIAIRE_COLUMN_ID)),
                        res.getString(res.getColumnIndex(TABLE_STAGIAIRE_COLUMN_LASTNAME)),
                        res.getString(res.getColumnIndex(TABLE_STAGIAIRE_COLUMN_FIRSTNAME)),
                        res.getString(res.getColumnIndex(TABLE_STAGIAIRE_COLUMN_PATH_PHOTO)),
                        res.getString(res.getColumnIndex(TABLE_STAGIAIRE_COLUMN_MAIL)),
                        res.getString(res.getColumnIndex(TABLE_STAGIAIRE_COLUMN_SMS)),
                        res.getInt(res.getColumnIndex(TABLE_STAGIAIRE_COLUMN_ID_GROUPE)));
                stagiaires.add(stagiaire);
                res.moveToNext();
            }
            res.close();
            return stagiaires;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return stagiaires;
        }
    }

    @Override
    public IEntity getElement(int id) {
        EntityStagiaires stagiaire = new EntityStagiaires();
        try {

            Cursor cursor = dbRead.query(
                    TABLE_STAGIAIRE_NAME /* table */,
                    new String[]{TABLE_STAGIAIRE_COLUMN_ID, TABLE_STAGIAIRE_COLUMN_LASTNAME, TABLE_STAGIAIRE_COLUMN_FIRSTNAME, TABLE_STAGIAIRE_COLUMN_PATH_PHOTO, TABLE_STAGIAIRE_COLUMN_MAIL, TABLE_STAGIAIRE_COLUMN_SMS, TABLE_STAGIAIRE_COLUMN_ID_GROUPE} /* columns */,
                    TABLE_STAGIAIRE_COLUMN_ID + " = ?" /* where or selection */,
                    new String[]{String.valueOf(id)} /* selectionArgs i.e. value to replace ? */,
                    null /* groupBy */,
                    null /* having */,
                    null /* orderBy */
            );

            if (cursor != null) {
                cursor.moveToFirst();
                stagiaire.setId(cursor.getInt(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_ID)));
                stagiaire.setLastname(cursor.getString(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_LASTNAME)));
                stagiaire.setFirstname(cursor.getString(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_FIRSTNAME)));
                stagiaire.setPath_photo(cursor.getString(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_PATH_PHOTO)));
                stagiaire.setMail(cursor.getString(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_MAIL)));
                stagiaire.setSms(cursor.getString(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_SMS)));
                stagiaire.setId_groupe(cursor.getInt(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_ID_GROUPE)));

            }

            cursor.close();
            return stagiaire;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return stagiaire;
        }


    }

    @Override
    public void deleteAllElelementsTable() {
        dbWrite.execSQL("DELETE FROM " + TABLE_STAGIAIRE_NAME);
    }

}
