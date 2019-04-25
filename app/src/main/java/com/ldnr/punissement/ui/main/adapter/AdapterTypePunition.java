package com.ldnr.punissement.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.entity.EntityTypePunition;
import com.ldnr.punissement.ui.main.viewHolder.GropesViewHolder;
import com.ldnr.punissement.ui.main.viewHolder.TypePunitionViewHolder;

import java.util.List;


public class AdapterTypePunition extends RecyclerView.Adapter<TypePunitionViewHolder> {
    public static AdapterTypePunition instance;

    private List<EntityTypePunition> list;


    public AdapterTypePunition(List<EntityTypePunition> list) {
        this.list = list;
    }

    public static AdapterTypePunition getInstance(List<EntityTypePunition> list) {
        if (instance == null) {
            instance = new AdapterTypePunition(list);
        }
        return instance;
    }

    @Override
    public TypePunitionViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_typepunition, viewGroup, false);
        return new TypePunitionViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(TypePunitionViewHolder typePunitionViewHolder, int position) {
        EntityTypePunition entityTypePunition = (EntityTypePunition) list.get(position);
        typePunitionViewHolder.bind(entityTypePunition);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}