package com.ldnr.punissement.ui.main.screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.ldnr.punissement.ui.main.DAO.PunitionHelper;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.entity.EntityTypePunition;


public class DialogSendFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Création d'une instance de l'alertDialog Pour le choix du type d'envoi
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int index = 10;
        if (getArguments() != null) {
            index = getArguments().getInt("id");
            EntityPunissement entityPunissement = (EntityPunissement) PunitionHelper.getInstance(null).getElement(index);
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
                shareintent.setType("text/plain");
                //shareintent.setAction();
                //shareintent.putExtra("sms_body", "default content");
                shareintent.putExtra(shareintent.EXTRA_EMAIL, new String[]{"f.losada@hotmail.fr"});
                shareintent.putExtra(shareintent.EXTRA_SUBJECT, "Punissement");
                shareintent.putExtra(shareintent.EXTRA_TEXT, "Voici ta punition");
                //shareintent.putExtra(Intent.EXTRA_STREAM, this.uriPhoto);

                getActivity().startActivity(shareintent);

                Toast.makeText(getActivity(), "Envoi email", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }
}
