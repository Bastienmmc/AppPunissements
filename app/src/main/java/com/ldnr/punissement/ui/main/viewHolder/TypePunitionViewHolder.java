package com.ldnr.punissement.ui.main.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.entity.EntityTypePunition;


public class TypePunitionViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewTitle;
    private TextView textViewDescription;
    //itemView est la vue correspondante à 1 cellule
    public TypePunitionViewHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView
        textViewTitle = (TextView) itemView.findViewById(R.id.card_type_title);
        textViewDescription = (TextView) itemView.findViewById(R.id.card_type_description);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(EntityTypePunition entityTypePunition) {
        textViewTitle.setText(entityTypePunition.getTitle());
        textViewDescription.setText("Description : "+entityTypePunition.getDescription());
    }
}