package com.ldnr.punissement.ui.main.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;

public class PunissementsViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewView;

    //itemView est la vue correspondante à 1 cellule
    public PunissementsViewHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView
        textViewView = (TextView) itemView.findViewById(R.id.text);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(EntityPunissement entityPunissement){
        textViewView.setText(entityPunissement.getTitle());

    }
}