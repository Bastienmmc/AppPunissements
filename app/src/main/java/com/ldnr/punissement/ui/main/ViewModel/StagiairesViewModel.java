package com.ldnr.punissement.ui.main.ViewModel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ldnr.punissement.ui.main.RecyclerItemClickListener;
import com.ldnr.punissement.ui.main.adapter.AdapterStagiaires;
import com.ldnr.punissement.ui.main.entity.EntityStagiaires;

import java.util.ArrayList;
import java.util.List;


public class StagiairesViewModel extends ViewModel implements IViewModel {
    private static final RecyclerView.Adapter adapter = AdapterStagiaires.getInstance(EntityStagiaires.getList());
    private static final List tab3 = new ArrayList();
    public static boolean bool = false;
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<List> tabs = Transformations.map(mIndex, new Function<Integer, List>() {
        @Override
        public List apply(Integer input) {
            return tab3;
        }
    });


    public StagiairesViewModel() {
        if (bool == false) {
            EntityStagiaires.addElement(new EntityStagiaires("StagiairesViewModel"));
            tab3.add(adapter);
            bool = true;
        }

    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<List> getText() {
        return tabs;
    }


    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public View.OnClickListener getFabFunction() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own actionTabstag", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };
    }

    public RecyclerItemClickListener.OnItemClickListener getTouchListenerFunction() {
        return new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Snackbar.make(view, "Click StagiairesViewModel", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Snackbar.make(view, "Click StagiairesViewModel", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };

    }
}
