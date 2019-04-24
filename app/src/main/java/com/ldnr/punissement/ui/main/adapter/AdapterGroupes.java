package com.ldnr.punissement.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.viewHolder.GropesViewHolder;

import java.util.List;

public class AdapterGroupes extends RecyclerView.Adapter<GropesViewHolder> {

    List<EntityGroupes> list;

    public AdapterGroupes(List<EntityGroupes> list) {
        this.list = list;
    }

    @Override
    public GropesViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_groupe,viewGroup,false);
        return new GropesViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(GropesViewHolder myViewHolder, int position) {
        EntityGroupes entityGroupes = list.get(position);
        myViewHolder.bind(entityGroupes);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}