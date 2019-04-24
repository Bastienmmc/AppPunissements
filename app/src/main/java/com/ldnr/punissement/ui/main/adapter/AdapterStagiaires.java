package com.ldnr.punissement.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.entity.EntityStagiaires;
import com.ldnr.punissement.ui.main.viewHolder.StagiairesViewHolder;

import java.util.List;

public class AdapterStagiaires extends RecyclerView.Adapter<StagiairesViewHolder> {
    public static AdapterStagiaires instance;

    public static AdapterStagiaires getInstance(List<EntityStagiaires> list){
        if (instance == null){
            instance=new AdapterStagiaires(list);
        }
        return instance;
    }


    List<EntityStagiaires> list;

    public AdapterStagiaires(List<EntityStagiaires> list) {
        this.list = list;
    }

    @Override
    public StagiairesViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_stagiaire, viewGroup, false);
        return new StagiairesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StagiairesViewHolder viewHolderStagiaires, int position) {
        EntityStagiaires entityStagiaires = list.get(position);
        viewHolderStagiaires.bind(entityStagiaires);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}