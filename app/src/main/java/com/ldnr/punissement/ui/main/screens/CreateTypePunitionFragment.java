package com.ldnr.punissement.ui.main.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.DAO.TypePunitionHelper;
import com.ldnr.punissement.ui.main.ViewModel.StagiairesViewModel;
import com.ldnr.punissement.ui.main.ViewModel.TypePunitionViewModel;
import com.ldnr.punissement.ui.main.adapter.AdapterGroupes;
import com.ldnr.punissement.ui.main.adapter.AdapterTypePunition;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntityTypePunition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateTypePunitionFragment extends Fragment {
    private boolean insert;
    private int pos;
    private String operation;
    private EditText editTitle;
    private EditText editDescription;

    private FloatingActionButton fab;

    private EntityTypePunition entityTypePunition;

    public CreateTypePunitionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            editTitle = (EditText) getView().findViewById(R.id.title);
            editDescription = (EditText) getView().findViewById(R.id.description);

            fab = (FloatingActionButton) getView().findViewById(R.id.floatingActionButton);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    save();
                }
            });

            pos = 0;
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                pos = bundle.getInt("pos_list");
                operation = bundle.getString("operation");
            } else {
                pos = -1;
                operation = "insert";
            }
            switch (operation) {
                case "insert":
                    this.initInsert();
                    break;
                case "update":
                    this.initUpdate(pos);
                    break;
                case "delete":
                   /* TypePunitionHelper.getInstance(this.getContext()).delete(EntityTypePunition.getList().get(pos));
                    EntityTypePunition.getList().remove(pos);*/
                    getActivity().finish();
                    break;
                default:
                    getActivity().finish();
                    break;
            }
        } catch (Exception e) {
            Log.d("testing", e.getMessage());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_typepunition, container, false);
    }

    private void initInsert() {
        this.insert = true;
        this.entityTypePunition = new EntityTypePunition();
    }

    private void initUpdate(int pos) {
        this.insert = false;
        this.entityTypePunition = (EntityTypePunition) AdapterTypePunition.getInstance(null).getList().get(pos);

        editTitle.setText(entityTypePunition.getTitle());
        editDescription.setText(entityTypePunition.getDescription());
    }

    private void save() {
        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();

        if (Validator(title, description)) {
            this.entityTypePunition.setTitle(title);
            this.entityTypePunition.setDescription(description);
            if (insert) {
                TypePunitionHelper.getInstance(this.getContext()).insert(this.entityTypePunition);
                EntityTypePunition.getList().add(this.entityTypePunition);
            } else {
                TypePunitionHelper.getInstance(this.getContext()).update(this.entityTypePunition);

            }
            AdapterTypePunition.getInstance(null).setList(EntityTypePunition.getList());
            TypePunitionViewModel.getStaticAdapter().notifyDataSetChanged();
            getActivity().finish();
        }

    }

    private boolean Validator(String title, String description) {
        boolean valid = true;
        //String regexp[] = {"[\\d]{2,}","/\\w{2,}/u","","" };
        if (title != null && !title.isEmpty()) {

            Pattern pattern = Pattern.compile("^[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._' -!?,;:\\-]{2,60}$");

            Matcher matcher = pattern.matcher(title);
            if (!matcher.find()) {
                Toast.makeText(getContext(), "Title pas valide", Toast.LENGTH_LONG).show();
                valid = false;
            }

        } else {
            Toast.makeText(getContext(), "Title pas valide", Toast.LENGTH_LONG).show();
            valid = false;

        }

        if (description != null && !description.isEmpty()) {

            Pattern pattern = Pattern.compile("^[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._' -!?,;:\\-]{2,60}$");

            Matcher matcher = pattern.matcher(description);
            if (!matcher.find()) {
                Toast.makeText(getContext(), "Description pas valide", Toast.LENGTH_LONG).show();
                valid = false;
            }


        } else {
            Toast.makeText(getContext(), "Description pas valide", Toast.LENGTH_LONG).show();
            valid = false;

        }

        return valid;
    }
}
