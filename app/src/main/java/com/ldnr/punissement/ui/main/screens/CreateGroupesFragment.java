package com.ldnr.punissement.ui.main.screens;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ldnr.punissement.BuildConfig;
import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.DAO.GroupeHelper;
import com.ldnr.punissement.ui.main.Services.StorageService;
import com.ldnr.punissement.ui.main.ViewModel.GroupesViewModel;
import com.ldnr.punissement.ui.main.ViewModel.StagiairesViewModel;
import com.ldnr.punissement.ui.main.adapter.AdapterGroupes;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupesFragment extends Fragment {
    private boolean insert;
    private int pos;
    private String operation;
    private EditText nom_groupe;

    private ImageButton photo_groupe;
    private FloatingActionButton fab;
    private EntityGroupes entityGroupes;
    private File filePhoto;
    private Uri uriPhoto;
    private String path_photo;
    private StorageService storageService;

    public CreateGroupesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            storageService = StorageService.getInstance(getContext());
            nom_groupe = (EditText) getView().findViewById(R.id.nom_groupe);
            photo_groupe = (ImageButton) getView().findViewById(R.id.photo_groupe);
            fab = (FloatingActionButton) getView().findViewById(R.id.floatingActionButton);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    save();
                }
            });
            photo_groupe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTakePhotoClicked(view);
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
                    /*GroupeHelper.getInstance(this.getContext()).delete(EntityGroupes.getList().get(pos));
                    EntityGroupes.setList(GroupeHelper.getInstance(this.getContext()).getList());
                    AdapterGroupes.getInstance(null).getList().remove(pos);*/
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
        return inflater.inflate(R.layout.fragment_create_groupes, container, false);
    }

    private void initUpdate(int pos) {
        this.insert = false;
        this.entityGroupes = (EntityGroupes) AdapterGroupes.getInstance(null).getList().get(pos);

        // this.entityGroupes = EntityGroupes.getList().get(pos);

        nom_groupe.setText(entityGroupes.getLibelle_groupe());

        try {
            photo_groupe.setImageURI(this.storageService.getOutputMediaFile(entityGroupes.getPath_photo_groupe()));
            this.path_photo = entityGroupes.getPath_photo_groupe();
        } catch (Exception e) {
        }

    }

    private void initInsert() {
        this.insert = true;
        this.entityGroupes = new EntityGroupes();
    }

    public void onTakePhotoClicked(View view) {
        this.filePhoto = storageService.getOutputMediaFile();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.uriPhoto = FileProvider.getUriForFile(view.getContext(),
                BuildConfig.APPLICATION_ID + ".provider", filePhoto);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto);
        getActivity().startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == -1) {
                this.photo_groupe.setImageURI(this.uriPhoto);
                this.path_photo = (this.filePhoto.toString());
            }
        }
    }


    private void save() {
        if (Validator(nom_groupe.getText().toString())) {
            this.entityGroupes.setLibelle_groupe(nom_groupe.getText().toString());
            this.entityGroupes.setPath_photo_groupe(path_photo);
            if (insert) {
                GroupeHelper.getInstance(this.getContext()).insert(this.entityGroupes);
                EntityGroupes.getList().add(this.entityGroupes);
            } else {
                GroupeHelper.getInstance(this.getContext()).update(this.entityGroupes);
            }

            AdapterGroupes.getInstance(null).setList(EntityGroupes.getList());
            GroupesViewModel.getStaticAdapter().notifyDataSetChanged();
            getActivity().finish();
        }
    }

    private boolean Validator(String libelle) {
        boolean valid = true;
        //String regexp[] = {"[\\d]{2,}","/\\w{2,}/u","","" };
        if (libelle != null && !libelle.isEmpty()) {

            Pattern pattern = Pattern.compile("^[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._' -!?,;:\\-]{2,60}$");

            Matcher matcher = pattern.matcher(libelle);
            if (!matcher.find()) {
                Toast.makeText(getContext(), "Libelle pas valide", Toast.LENGTH_LONG).show();
                valid = false;
            }
            return valid;
        } else {
            Toast.makeText(getContext(), "Libelle pas valide", Toast.LENGTH_LONG).show();
            return false;
        }


    }


}
