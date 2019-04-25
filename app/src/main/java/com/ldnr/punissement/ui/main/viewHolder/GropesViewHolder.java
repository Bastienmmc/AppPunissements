package com.ldnr.punissement.ui.main.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.Services.StorageService;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;


public class GropesViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewTitle;
    private ImageButton imageButtonIcon;
    private StorageService storageService;
    //itemView est la vue correspondante à 1 cellule
    public GropesViewHolder(View itemView) {
        super(itemView);
        storageService=StorageService.getInstance(itemView.getContext());
        //c'est ici que l'on fait nos findView
        textViewTitle = (TextView) itemView.findViewById(R.id.card_groupe_title);
        imageButtonIcon = (ImageButton) itemView.findViewById(R.id.card_groupe_icon);


    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(EntityGroupes entityGroupes) {
        textViewTitle.setText(entityGroupes.getLibelle_groupe());
        try{
            imageButtonIcon.setImageURI(this.storageService.getOutputMediaFile(entityGroupes.getPath_photo_groupe()));
        }
        catch (Exception e){}
    }
}
