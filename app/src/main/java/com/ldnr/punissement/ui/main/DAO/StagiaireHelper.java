package com.ldnr.punissement.ui.main.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntityStagiaires;
import com.ldnr.punissement.ui.main.entity.IEntity;

import java.util.ArrayList;

public class StagiaireHelper extends SQLiteOpenHelper implements IDaoHelper {

    private static final String DATABASE_NAME = "punissements.db";

    /* table Stagiaire*/
    private static final String TABLE_STAGIAIRE_NAME = "stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_ID = "id_stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_NAME = "name_stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_FIRSTNAME = "firstname_stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_PATH_PHOTO = "path_photo_stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_MAIL = "mail_stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_SMS = "sms_stagiaire";
    private static final String TABLE_STAGIAIRE_COLUMN_ID_GROUPE = "id_groupe";


    // Déclaration méthodes d'accès à la BDD
    private SQLiteDatabase dbWrite = this.getWritableDatabase();
    private SQLiteDatabase dbRead = this.getReadableDatabase();

    // Constructeur
    public StagiaireHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    // OnCreate : création table stagiaire
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_STAGIAIRE_NAME
                + " ( " + TABLE_STAGIAIRE_COLUMN_ID + " integer primary key, "
                + TABLE_STAGIAIRE_COLUMN_NAME + " text , "
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
            value.put(TABLE_STAGIAIRE_COLUMN_NAME, pStagiaire.getName());
            value.put(TABLE_STAGIAIRE_COLUMN_FIRSTNAME, pStagiaire.getFirstname());
            value.put(TABLE_STAGIAIRE_COLUMN_PATH_PHOTO, pStagiaire.getPath_photo());
            value.put(TABLE_STAGIAIRE_COLUMN_MAIL, pStagiaire.getMail());
            value.put(TABLE_STAGIAIRE_COLUMN_SMS, pStagiaire.getSms());
            value.put(TABLE_STAGIAIRE_COLUMN_ID_GROUPE, pStagiaire.getId_groupe());


            dbWrite.insertOrThrow(TABLE_STAGIAIRE_NAME, null, value);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mise à jour d'un stagiaire
    @Override
    public void update(IEntity iEntity) {
        try {
            EntityStagiaires pStagiaire = (EntityStagiaires) iEntity;
            ContentValues value = new ContentValues();
            value.put(TABLE_STAGIAIRE_COLUMN_NAME, pStagiaire.getName());
            value.put(TABLE_STAGIAIRE_COLUMN_FIRSTNAME, pStagiaire.getFirstname());
            value.put(TABLE_STAGIAIRE_COLUMN_PATH_PHOTO, pStagiaire.getPath_photo());
            value.put(TABLE_STAGIAIRE_COLUMN_MAIL, pStagiaire.getPath_photo());
            value.put(TABLE_STAGIAIRE_COLUMN_SMS, pStagiaire.getPath_photo());
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
     * @return
     */
    @Override
    public ArrayList<IEntity> getList() {
        ArrayList<IEntity> stagiaires = new ArrayList<>();
        Cursor res = dbRead.rawQuery("SELECT * FROM " + TABLE_STAGIAIRE_NAME,null);
        // Si la base est vide, renvoie la liste initialisée à vide
        if(res.getCount() == 0) {
            res.close();
            return stagiaires;
        }
        res.moveToFirst();
        while(!res.isAfterLast()) {
            EntityStagiaires stagiaire = new EntityStagiaires(res.getInt(res.getColumnIndex(TABLE_STAGIAIRE_COLUMN_ID)),
                    res.getString(res.getColumnIndex(TABLE_STAGIAIRE_COLUMN_NAME)),
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
    }

    @Override
    public IEntity getElement(int id) {
        Cursor cursor = dbRead.query(TABLE_STAGIAIRE_COLUMN_NAME,
                new String[]{TABLE_STAGIAIRE_COLUMN_ID, TABLE_STAGIAIRE_COLUMN_NAME, TABLE_STAGIAIRE_COLUMN_FIRSTNAME, TABLE_STAGIAIRE_COLUMN_PATH_PHOTO, TABLE_STAGIAIRE_COLUMN_MAIL, TABLE_STAGIAIRE_COLUMN_SMS, TABLE_STAGIAIRE_COLUMN_ID_GROUPE},
                TABLE_STAGIAIRE_COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        EntityStagiaires stagiaire = new EntityStagiaires(cursor.getInt(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_FIRSTNAME)),
                cursor.getString(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_MAIL)),
                cursor.getString(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_SMS)),
                cursor.getString(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_PATH_PHOTO)),
                cursor.getInt(cursor.getColumnIndex(TABLE_STAGIAIRE_COLUMN_ID_GROUPE)));

        cursor.close();
        return stagiaire;
    }

}
