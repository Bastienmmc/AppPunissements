package com.ldnr.punissement.ui.main;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.entity.EntityStagiaires;

import java.util.List;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<List> groupes = Transformations.map(mIndex, new Function<Integer, List>() {
        @Override
        public List apply(Integer input) {
            switch (input){
                case(1):return EntityPunissement.getList();
                    //break;
                case(2):return EntityStagiaires.getList();
                    //break;
                case(3):return EntityGroupes.getList();
                    //break;
                default:return null;
            }
//            List a = new ArrayList();
//            a.add(new EntityGroupes("coucou"));
//            return a;
        }
    });
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
        return groupes;
    }
}