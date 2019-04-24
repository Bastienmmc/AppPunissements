package com.ldnr.punissement.ui.main;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.RecyclerView;

import com.ldnr.punissement.ui.main.adapter.AdapterGroupes;
import com.ldnr.punissement.ui.main.adapter.AdapterPunissements;
import com.ldnr.punissement.ui.main.adapter.AdapterStagiaires;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.entity.EntityStagiaires;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel {
    private RecyclerView.Adapter adapter1= AdapterPunissements.getInstance(EntityPunissement.getList());
    private RecyclerView.Adapter adapter2= AdapterStagiaires.getInstance(EntityStagiaires.getList());
    private RecyclerView.Adapter adapter3= AdapterGroupes.getInstance(EntityGroupes.getList());

    private List tab1 = new ArrayList();
    private List tab2 = new ArrayList();
    private List tab3 = new ArrayList();

    public PageViewModel(){
        tab1.add(adapter1);
        tab2.add(adapter2);
        tab3.add(adapter3);
    }

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();


    private LiveData<List> tabs = Transformations.map(mIndex, new Function<Integer, List>() {
        @Override
        public List apply(Integer input) {
            switch (input) {
                case (1):
                    return tab1;
                //break;
                case (2):
                    return tab2;
                //break;
                case (3):
                    return tab3;
                //break;
                default:
                    return null;
            }
        }
    });


    /*


    private LiveData<List> groupes = Transformations.map(mIndex, new Function<Integer, List>() {
        @Override
        public List apply(Integer input) {
            switch (input) {
                case (1):
                    return EntityPunissement.getList();
                //break;
                case (2):
                    return EntityStagiaires.getList();
                //break;
                case (3):
                    return EntityGroupes.getList();
                //break;
                default:
                    return null;
            }
        }
    });

    */
//    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
//        @Override
//        public String apply(Integer input) {
//            return "Coucou 16h40 " + input;
//        }
//    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

//    public LiveData<String> getText() {
//        return mText;
//    }

    public LiveData<List> getText() {
        return tabs;
    }

}