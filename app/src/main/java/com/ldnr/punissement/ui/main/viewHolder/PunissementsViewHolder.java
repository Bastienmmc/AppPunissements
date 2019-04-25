package com.ldnr.punissement.ui.main.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.DAO.GroupeHelper;
import com.ldnr.punissement.ui.main.DAO.StagiaireHelper;
import com.ldnr.punissement.ui.main.Services.StorageService;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.entity.EntityStagiaires;

public class PunissementsViewHolder extends RecyclerView.ViewHolder {


    private TextView textViewNom;
    private TextView textViewTitle;
    private TextView textViewDate;
    private StorageService storageService;
    private ImageButton imageButtonIcon;
    //itemView est la vue correspondante à 1 cellule
    public PunissementsViewHolder(View itemView) {
        super(itemView);
        storageService= StorageService.getInstance(itemView.getContext());
        //c'est ici que l'on fait nos findView
        textViewNom = (TextView) itemView.findViewById(R.id.card_punissement_nom);
        textViewTitle = (TextView) itemView.findViewById(R.id.card_punissement_title);
        textViewDate = (TextView) itemView.findViewById(R.id.card_punissement_date);
        imageButtonIcon = (ImageButton) itemView.findViewById(R.id.card_punissement_icon);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(EntityPunissement entityPunissement) {
        textViewTitle.setText(entityPunissement.getTitle());
        textViewDate.setText(entityPunissement.getDate());
        if(entityPunissement.getId_stagiaire()>0){
            EntityStagiaires a=(EntityStagiaires) StagiaireHelper.getInstance(null).getElement(entityPunissement.getId_stagiaire());
            textViewNom.setText(a.getLastAndFirstName())  ;
            try{
                imageButtonIcon.setImageURI(this.storageService.getOutputMediaFile(a.getPath_photo()));
            }
            catch (Exception e){}
        }
        if(entityPunissement.getId_groupe()>0){
            EntityGroupes a=(EntityGroupes) GroupeHelper.getInstance(null).getElement(entityPunissement.getId_groupe());
            textViewNom.setText(a.getLibelle_groupe())  ;
            try{
                imageButtonIcon.setImageURI(this.storageService.getOutputMediaFile(a.getPath_photo_groupe()));
            }
            catch (Exception e){}
        }



    }
}