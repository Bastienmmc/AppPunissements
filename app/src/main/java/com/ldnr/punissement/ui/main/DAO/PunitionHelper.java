package com.ldnr.punissement.ui.main.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.entity.EntityStagiaires;
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


    // Déclaration méthodes d'accès à la BDD
    private SQLiteDatabase dbWrite = this.getWritableDatabase();
    private SQLiteDatabase dbRead = this.getReadableDatabase();


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

    /**
     * Fonction qui renvoie la liste des punitions
     * @return
     */
    @Override
    public ArrayList<IEntity> getList() {
        ArrayList<IEntity> punissements = new ArrayList<>();
        Cursor res = dbRead.rawQuery("SELECT * FROM " + TABLE_PUNITION_COLUMN_TITLE,null);
        // Si la base est vide, renvoie la liste initialisée à vide
        if(res.getCount() == 0) {
            res.close();
            return punissements;
        }
        res.moveToFirst();
        while(!res.isAfterLast()) {
            EntityPunissement punissement = new EntityPunissement(res.getInt(res.getColumnIndex(TABLE_PUNITION_COLUMN_ID)),
                    res.getString(res.getColumnIndex(TABLE_PUNITION_COLUMN_TITLE)),
                    res.getString(res.getColumnIndex(TABLE_PUNITION_COLUMN_DESCRIPTION)),
                    res.getInt(res.getColumnIndex(TABLE_PUNITION_COLUMN_ID_TYPE)),
                    res.getInt(res.getColumnIndex(TABLE_PUNITION_COLUMN_ID_STAGIAIRE)),
                    res.getInt(res.getColumnIndex(TABLE_PUNITION_COLUMN_ID_GROUPE)));
            punissements.add(punissement);
            res.moveToNext();
        }
        res.close();
        return punissements;
    }

    @Override
    public IEntity getElement(int id) {
        Cursor cursor = dbRead.query(TABLE_PUNITION_COLUMN_TITLE,
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
                cursor.getInt(cursor.getColumnIndex(TABLE_PUNITION_COLUMN_ID_GROUPE)));

        cursor.close();
        return punissement;
    }
}
