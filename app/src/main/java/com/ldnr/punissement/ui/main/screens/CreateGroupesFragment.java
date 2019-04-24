package com.ldnr.punissement.ui.main.screens;


import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldnr.punissement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupesFragment extends Fragment {


    public CreateGroupesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_groupes, container, false);
    }

}
