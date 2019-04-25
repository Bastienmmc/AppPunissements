package com.ldnr.punissement.ui.main.screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;


public class DialogSendFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Création d'une instance de l'alertDialog Pour le choix du type d'envoi
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Titre et message du dialogue
        builder.setTitle("Envoyer")
                .setMessage("Choisissez le type d'envoi au stagiaire")
                .setPositiveButton("Envoi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Action choisie
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
