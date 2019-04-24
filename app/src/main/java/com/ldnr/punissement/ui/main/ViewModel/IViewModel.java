package com.ldnr.punissement.ui.main.ViewModel;

import android.arch.lifecycle.LiveData;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ldnr.punissement.ui.main.RecyclerItemClickListener;

import java.util.List;


public interface IViewModel {
    public void setIndex(int index);

    public LiveData<List> getText();

    public View.OnClickListener getFabFunction();

    public RecyclerView.Adapter getAdapter();


    public RecyclerItemClickListener.OnItemClickListener getTouchListenerFunction();


/*
    recyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            openAddActivity(position);
        }

        @Override
        public void onLongItemClick(View view, int position) {
            openAddActivity(position);
        }
    })
            );*/


}