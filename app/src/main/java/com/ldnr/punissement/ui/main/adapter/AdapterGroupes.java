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
    public static AdapterGroupes instance;

    private List<EntityGroupes> list;


    public AdapterGroupes(List<EntityGroupes> list) {
        this.list = list;
    }

    public static AdapterGroupes getInstance(List<EntityGroupes> list) {
        if (instance == null) {
            instance = new AdapterGroupes(list);
        }
        return instance;
    }

    @Override
    public GropesViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_groupe, viewGroup, false);
        return new GropesViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(GropesViewHolder gropesViewHolder, int position) {
        EntityGroupes entityGroupes = (EntityGroupes) list.get(position);
        gropesViewHolder.bind(entityGroupes);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}