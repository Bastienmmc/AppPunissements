package com.ldnr.punissement.ui.main.ViewModel;

import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;

import com.ldnr.punissement.ui.main.RecyclerItemClickListener;


public interface IViewModel {
    public void setIndex(int index);

    //public LiveData<List> getText();

    public View.OnClickListener getFabFunction();

    public RecyclerView.Adapter getAdapter();

    public RecyclerItemClickListener.OnItemClickListener getTouchListenerFunction();

    //public void openCreateActivity(View view, int tab, int pos);
    public void openCreateActivity(View view, int tab, int pos, String operation);
    public TextWatcher getTextWatcherListener();

}
