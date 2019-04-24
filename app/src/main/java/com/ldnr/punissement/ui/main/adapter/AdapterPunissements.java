package com.ldnr.punissement.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.viewHolder.PunissementsViewHolder;

import java.util.List;

public class AdapterPunissements extends RecyclerView.Adapter<PunissementsViewHolder> {
    public static AdapterPunissements instance;

    public static AdapterPunissements getInstance(List<EntityPunissement> list){
        if (instance == null){
            instance=new AdapterPunissements(list);
        }
        return instance;
    }



    List<EntityPunissement> list;

    public AdapterPunissements(List<EntityPunissement> list) {
        this.list = list;
    }

    @Override
    public PunissementsViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_punissement, viewGroup, false);
        return new PunissementsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PunissementsViewHolder viewHolderPunissement, int position) {
        EntityPunissement entityPunissement = list.get(position);
        viewHolderPunissement.bind(entityPunissement);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}