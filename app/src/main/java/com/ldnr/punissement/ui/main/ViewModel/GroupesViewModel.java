package com.ldnr.punissement.ui.main.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ldnr.punissement.ui.main.RecyclerItemClickListener;
import com.ldnr.punissement.ui.main.adapter.AdapterGroupes;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;

import java.util.List;

public class GroupesViewModel extends ViewModel implements IViewModel {
    private static final RecyclerView.Adapter adapter = AdapterGroupes.getInstance(EntityGroupes.getList());
    public static boolean bool = false;


    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    /*private LiveData<List> tabs = Transformations.map(mIndex, new Function<Integer, List>() {
        @Override
        public List apply(Integer input) {
            return adapter;
        }
    });*/


    public GroupesViewModel() {
        if (bool == false) {
            EntityGroupes.addElement(new EntityGroupes("groupes"));
            //tab3.add(adapter3);
            bool = true;
        }

    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<List> getText() {
        return EntityGroupes.getMutableLiveData();
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }


    public View.OnClickListener getFabFunction() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own actionTabgroupe", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };
    }


    public RecyclerItemClickListener.OnItemClickListener getTouchListenerFunction() {
        return new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Snackbar.make(view, "Click GroupesViewModel", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Snackbar.make(view, "Click GroupesViewModel", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };

    }
}