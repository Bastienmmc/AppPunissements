package com.ldnr.punissement.ui.main.ViewModel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ldnr.punissement.ui.main.adapter.AdapterGroupes;
import com.ldnr.punissement.ui.main.adapter.AdapterPunissements;
import com.ldnr.punissement.ui.main.adapter.AdapterStagiaires;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.entity.EntityStagiaires;
import com.ldnr.punissement.ui.main.screens.CreateActivity;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel {
    private static final RecyclerView.Adapter adapter1 = AdapterPunissements.getInstance(EntityPunissement.getList());
    private static final RecyclerView.Adapter adapter2 = AdapterStagiaires.getInstance(EntityStagiaires.getList());
    private static final RecyclerView.Adapter adapter3 = AdapterGroupes.getInstance(EntityGroupes.getList());
    private static final List tab1 = new ArrayList();
    private static final List tab2 = new ArrayList();
    private static final List tab3 = new ArrayList();
    public static boolean bool = false;
    String fabfunction[] = {"tab1", "tab2", "tab3"};
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


    public PageViewModel() {
        if (bool == false) {
            EntityPunissement.addElement(new EntityPunissement("punissement"));
            EntityPunissement.addElement(new EntityPunissement("punissement1"));
            EntityPunissement.addElement(new EntityPunissement("punissement2"));
            EntityStagiaires.addElement(new EntityStagiaires("stagiaires"));
            EntityGroupes.addElement(new EntityGroupes("groupes"));

            tab1.add(adapter1);
            //tab1.add("je suis le adapter1");
            tab2.add(adapter2);
            // tab2.add("je suis le adapter2");
            tab3.add(adapter3);
            // tab3.add("je suis le adapter3");

            View.OnClickListener click1 = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action adapter 3", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            };

            tab1.add(click1);


            tab2.add(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action adapter 3", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            tab3.add(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action adapter 3", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            bool = true;
            Log.d("ecco11", tab1.get(0).getClass().toString());
            Log.d("ecco11", tab1.get(1).getClass().toString());
            Log.d("ecco11cast", ((View.OnClickListener) click1).getClass().toString());

        }

    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<List> getText() {
        return tabs;
    }

    public String getFabFunction() {
        return fabfunction[mIndex.getValue() - 1];
    }

    public void openCreateActivity(View view,int tab , int pos) {
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("pos_tab", tab);  //1,2,3
        dataBundle.putInt("pos_list", pos);  //-1 if for new data
        Context context = view.getContext();
        Intent openIntent = new Intent(context, CreateActivity.class);
        openIntent.putExtras(dataBundle);
        context.startActivity(openIntent);
    }
}