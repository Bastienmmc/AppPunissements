package com.ldnr.punissement.ui.main.screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.ldnr.punissement.ui.main.DAO.GroupeHelper;
import com.ldnr.punissement.ui.main.DAO.PunitionHelper;
import com.ldnr.punissement.ui.main.DAO.StagiaireHelper;
import com.ldnr.punissement.ui.main.DAO.TypePunitionHelper;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.entity.EntityStagiaires;
import com.ldnr.punissement.ui.main.entity.EntityTypePunition;

import java.util.ArrayList;
import java.util.List;


public class DialogSendFragment extends AppCompatDialogFragment {
    EntityPunissement entityPunissement;
    EntityTypePunition entityTypePunitiont;
    EntityGroupes entityGroupes;
    EntityStagiaires entityStagiaires;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Création d'une instance de l'alertDialog Pour le choix du type d'envoi
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int index = 10;
        if (getArguments() != null) {
            index = getArguments().getInt("id");
            this.entityPunissement = (EntityPunissement) PunitionHelper.getInstance(null).getElement(index);
            Log.d("testing", "id type " + entityPunissement.getId_type());//this.entityPunissement.getId_type()
            if (entityPunissement.getId_stagiaire() != 0) {
                this.entityStagiaires = (EntityStagiaires) StagiaireHelper.getInstance(null).getElement(this.entityPunissement.getId_stagiaire());
            }
            if (entityPunissement.getId_groupe() != 0) {
                this.entityGroupes = (EntityGroupes) GroupeHelper.getInstance(null).getElement(this.entityPunissement.getId_groupe());
            }

            this.entityTypePunitiont = (EntityTypePunition) TypePunitionHelper.getInstance(null).getElement(this.entityPunissement.getId_type());


            Log.d("testing", "test " + entityPunissement.getTitle());
        }
        Log.d("testing", "test email " + index);
        //Titre et message du dialogue
        builder.setTitle("Envoyer")
                .setMessage("Choisissez le type d'envoi au stagiaire")
                .setCancelable(true)
                .setPositiveButton("Envoi SMS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Toast.makeText(getActivity(), "Envoi sms", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setNegativeButton("Envoi EMAIL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent shareintent = new Intent(Intent.ACTION_SEND);
                //shareintent.setType("text/plain");
                shareintent.setType("text/email");


                if (entityPunissement != null) {
                    //List<String> arr= new ArrayList();
                    String arr1[]= new String[200];
                    shareintent.putExtra(shareintent.EXTRA_SUBJECT, entityPunissement.getTitle());
                    String app = "Description : " + entityPunissement.getDescription() + "\n"
                            + "Lieu : " + entityPunissement.getLieu() + "\n"
                            + "Date : " + entityPunissement.getDate() + "\n"
                            + "Type : " + entityTypePunitiont.getTitle() + "\n";
                    if(entityPunissement.getId_stagiaire()!=0){
                        app+=  "Stagiaire : " + entityStagiaires.getLastAndFirstName() + "\n";
                        //arr.add(entityStagiaires.getMail());
                        arr1[0]=(entityStagiaires.getMail());
                    }else{
                        app+=  "Groupe : " + entityGroupes.getLibelle_groupe() + "\n";

                        List lists =StagiaireHelper.getInstance(null).getList(entityGroupes.getId());
                        int i =0;
                        for (Object list: lists){
                            //arr1.add(((EntityStagiaires)list).getMail());
                            arr1[i]=(((EntityStagiaires)list).getMail());
                            i++;
                        }
                    }
                    //arr1.toArray()
                    shareintent.putExtra(shareintent.EXTRA_TEXT, app);

                    shareintent.putExtra(shareintent.EXTRA_EMAIL, arr1);

                    //shareintent.putExtra(shareintent.EXTRA_EMAIL, arr.toArray(new String[0]));
                    getActivity().startActivity(shareintent);

                }


                Toast.makeText(getActivity(), "Envoi email", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }
}
