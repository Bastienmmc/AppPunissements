package com.ldnr.punissement.ui.main.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.Services.StorageService;
import com.ldnr.punissement.ui.main.entity.EntityStagiaires;


public class StagiairesViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewNom;
    private TextView textViewEmail;
    private TextView textViewTelephone;
    private StorageService storageService;
    private ImageButton imageButtonIcon;
    //itemView est la vue correspondante à 1 cellule
    public StagiairesViewHolder(View itemView) {
        super(itemView);
        storageService= StorageService.getInstance(itemView.getContext());

        //c'est ici que l'on fait nos findView
        textViewNom = (TextView) itemView.findViewById(R.id.card_stagiaire_nom);
        textViewEmail = (TextView) itemView.findViewById(R.id.card_stagiaire_email);
        textViewTelephone = (TextView) itemView.findViewById(R.id.card_stagiaire_telephone);
        imageButtonIcon = (ImageButton) itemView.findViewById(R.id.card_stagiaire_icon);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(EntityStagiaires entityStagiaires) {
        textViewNom.setText(entityStagiaires.getLastAndFirstName());
        textViewEmail.setText("Email : " + entityStagiaires.getMail());
        textViewTelephone.setText("Telephone : "+ entityStagiaires.getSms());
        try{
            imageButtonIcon.setImageURI(this.storageService.getOutputMediaFile(entityStagiaires.getPath_photo()));
        }
        catch (Exception e){}
    }
}