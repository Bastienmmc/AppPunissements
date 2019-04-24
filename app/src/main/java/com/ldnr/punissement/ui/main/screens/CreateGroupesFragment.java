package com.ldnr.punissement.ui.main.screens;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.Services.StorageService;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupesFragment extends Fragment {
    private  EditText nom_groupe;
    private ImageButton photo_groupe;
    private StorageService storageService;

    public CreateGroupesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            storageService=StorageService.getInstance(getContext());
            nom_groupe= (EditText) getView().findViewById(R.id.nom_groupe);
            photo_groupe= (ImageButton) getView().findViewById(R.id.photo_groupe);

            int pos=0;
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                pos = bundle.getInt("pos_list");

                Toast.makeText(this.getContext(), "Apri pos!" + pos, Toast.LENGTH_LONG).show();

                //  Toast.makeText(this, "pos_list" + pos,Toast.LENGTH_LONG).show();
            }else{
                pos=-1;
            }
            if(pos>-1){
                //edit or consultation
                this.init(pos);

            }else if(pos==-1){
                //new


            }



        }catch(Exception e){


        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_groupes, container, false);
    }
    private void init(int pos){
        EntityGroupes groupe = EntityGroupes.getList().get(pos);

        nom_groupe.setText(groupe.getLibelle_groupe());
        photo_groupe.setImageURI(this.storageService.getOutputMediaFile(groupe.getLibelle_groupe()));
    }
}
