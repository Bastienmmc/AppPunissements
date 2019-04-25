package com.ldnr.punissement.ui.main.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ldnr.punissement.ui.main.entity.EntityTypePunition;
import com.ldnr.punissement.ui.main.entity.IEntity;

import java.util.ArrayList;


public class TypePunitionHelper extends SQLiteOpenHelper implements IDaoHelper {

    private static final String DATABASE_NAME = "punissements.db";

    /* type Punition*/
    private static final String TABLE_TYPE_PUNITION_NAME = "type_punition";
    private static final String TABLE_TYPE_PUNITION_COLUMN_ID = "id_type_punition";
    private static final String TABLE_TYPE_PUNITION_COLUMN_TITLE = "title_type_punition";
    private static final String TABLE_TYPE_PUNITION_COLUMN_DESCRIPTION = "description_type_punition";
    private static TypePunitionHelper instance;
    // Déclaration méthodes d'accès à la BDD
    private SQLiteDatabase dbWrite = this.getWritableDatabase();
    private SQLiteDatabase dbRead = this.getReadableDatabase();

    // Constructeur
    private TypePunitionHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
        try {
            dbWrite.execSQL("CREATE TABLE " + TABLE_TYPE_PUNITION_NAME
                    + " ( " + TABLE_TYPE_PUNITION_COLUMN_ID + " integer primary key, "
                    + TABLE_TYPE_PUNITION_COLUMN_TITLE + " text , "
                    + TABLE_TYPE_PUNITION_COLUMN_DESCRIPTION + " text )"
            );
        } catch (SQLException e) {

        }
    }

    public static TypePunitionHelper getInstance(Context context) {
        if (instance == null) {
            instance = new TypePunitionHelper(context);
        }
        return instance;
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
    @Override
    public void insert(IEntity iEntity) {
        try {
            EntityTypePunition typePunition = (EntityTypePunition) iEntity;
            ContentValues content = new ContentValues();
            content.put(TABLE_TYPE_PUNITION_COLUMN_TITLE, typePunition.getTitle());
            content.put(TABLE_TYPE_PUNITION_COLUMN_DESCRIPTION, typePunition.getDescription());
            dbWrite.insert(TABLE_TYPE_PUNITION_NAME, null, content);
            //dbWrite.insertOrThrow(TABLE_TYPE_PUNITION_NAME, null, content);
            Cursor res = dbRead.rawQuery("SELECT * FROM " + TABLE_TYPE_PUNITION_NAME + " ORDER BY " + TABLE_TYPE_PUNITION_COLUMN_ID + " DESC LIMIT 1", null);
            if (res != null && res.getCount() > 0) {
                res.moveToFirst();
                typePunition.setId(res.getInt(res.getColumnIndex(TABLE_TYPE_PUNITION_COLUMN_ID)));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mise à jour d'un type de punition
    @Override
    public void update(IEntity iEntity) {

        try {
            EntityTypePunition typePunition = (EntityTypePunition) iEntity;
            ContentValues values = new ContentValues();
            values.put(TABLE_TYPE_PUNITION_COLUMN_TITLE, typePunition.getTitle());
            values.put(TABLE_TYPE_PUNITION_COLUMN_DESCRIPTION, typePunition.getDescription());


            // lancement mise à jour

            dbWrite.update(TABLE_TYPE_PUNITION_NAME, values, TABLE_TYPE_PUNITION_COLUMN_ID + " = ?", new String[]{String.valueOf(typePunition.getId())});
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Suppression type de punition
    @Override
    public void delete(IEntity iEntity) {
        try {
            EntityTypePunition typePunition = (EntityTypePunition) iEntity;
            dbWrite.delete(TABLE_TYPE_PUNITION_NAME, TABLE_TYPE_PUNITION_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(typePunition.getId())});
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
        ArrayList<IEntity> typePunitions = new ArrayList<>();
        try {


            Cursor res = dbRead.rawQuery("SELECT * FROM " + TABLE_TYPE_PUNITION_NAME, null);
            // Si la base est vide, renvoie la liste initialisée à vide
            if (res.getCount() == 0) {
                res.close();
                return typePunitions;
            }
            res.moveToFirst();
            while (!res.isAfterLast()) {
                EntityTypePunition typePunition = new EntityTypePunition(res.getInt(res.getColumnIndex(TABLE_TYPE_PUNITION_COLUMN_ID)),
                        res.getString(res.getColumnIndex(TABLE_TYPE_PUNITION_COLUMN_TITLE)),
                        res.getString(res.getColumnIndex(TABLE_TYPE_PUNITION_COLUMN_DESCRIPTION)));
                typePunitions.add(typePunition);
                res.moveToNext();
            }
            res.close();
            return typePunitions;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return typePunitions;
        }

    }

    @Override
    public IEntity getElement(int id) {
        try {
            Cursor cursor = dbRead.query(TABLE_TYPE_PUNITION_COLUMN_TITLE,
                    new String[]{TABLE_TYPE_PUNITION_COLUMN_ID, TABLE_TYPE_PUNITION_COLUMN_TITLE, TABLE_TYPE_PUNITION_COLUMN_DESCRIPTION},
                    TABLE_TYPE_PUNITION_COLUMN_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            EntityTypePunition typePunition = new EntityTypePunition(cursor.getInt(cursor.getColumnIndex(TABLE_TYPE_PUNITION_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(TABLE_TYPE_PUNITION_COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(TABLE_TYPE_PUNITION_COLUMN_DESCRIPTION)));

            cursor.close();
            return typePunition;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteAllElelementsTable() {
        dbWrite.execSQL("DELETE FROM " + TABLE_TYPE_PUNITION_NAME);
    }

}
