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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ldnr.punissement.BuildConfig;
import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.DAO.StagiaireHelper;
import com.ldnr.punissement.ui.main.Services.StorageService;
import com.ldnr.punissement.ui.main.ViewModel.PunissementsViewModel;
import com.ldnr.punissement.ui.main.ViewModel.StagiairesViewModel;
import com.ldnr.punissement.ui.main.adapter.AdapterStagiaires;
import com.ldnr.punissement.ui.main.adapter.AdapterTypePunition;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntitySpinner;
import com.ldnr.punissement.ui.main.entity.EntityStagiaires;
import com.ldnr.punissement.ui.main.entity.EntityTypePunition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */


public class CreateStagiaresFragment extends Fragment {

    private boolean insert;
    private int pos;
    private String operation;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editEmailStagiaire;
    private EditText editMobileStagiaire;

    private Spinner spinnerSelectGroup;

    private ImageButton photoStagiaire;

    private FloatingActionButton fab;

    private EntityStagiaires entityStagiaires;
    private ArrayAdapter<String> adapterGroupe;
    private List spinnerGroupes;
    private File filePhoto;
    private Uri uriPhoto;
    private String path_photo = "";
    private StorageService storageService;

    public CreateStagiaresFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            storageService = StorageService.getInstance(getContext());

            editLastName = (EditText) getView().findViewById(R.id.nom_stagiaire);
            editFirstName = (EditText) getView().findViewById(R.id.prenom_stagiaire);
            editEmailStagiaire = (EditText) getView().findViewById(R.id.email_stagiaire);
            editMobileStagiaire = (EditText) getView().findViewById(R.id.mobile_stagiaire);

            spinnerSelectGroup = (Spinner) getView().findViewById(R.id.select_group);
            //spinnerSelectType = (Spinner) getView().findViewById(R.id.select_type);

            photoStagiaire = (ImageButton) getView().findViewById(R.id.photo_stagiaire);
            // iconStagiaire = (ImageButton) getView().findViewById(R.id.icon_stagiaire);

            fab = (FloatingActionButton) getView().findViewById(R.id.floatingActionButton);

            this.initSpinner();

            photoStagiaire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTakePhotoClicked(view);
                }
            });

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
                   /* StagiaireHelper.getInstance(this.getContext()).delete(EntityStagiaires.getList().get(pos));
                    EntityStagiaires.getList().remove(pos);*/
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
        return inflater.inflate(R.layout.fragment_create_stagiares, container, false);
    }

    private void initInsert() {
        this.insert = true;
        this.entityStagiaires = new EntityStagiaires();
    }

    private void initUpdate(int pos) {
        this.insert = false;
        this.entityStagiaires = (EntityStagiaires) AdapterStagiaires.getInstance(null).getList().get(pos);

        //this.entityStagiaires = EntityStagiaires.getList().get(pos);

        editLastName.setText(entityStagiaires.getLastname());
        editFirstName.setText(entityStagiaires.getFirstname());
        editEmailStagiaire.setText(entityStagiaires.getMail());
        editMobileStagiaire.setText(entityStagiaires.getSms());

        int id_groupe = entityStagiaires.getId_groupe();

        if (id_groupe != 0) {   //est un groupe
            selectedSpinnerSelectGroup(id_groupe);
        }
        try {
            photoStagiaire.setImageURI(this.storageService.getOutputMediaFile(entityStagiaires.getPath_photo()));
            this.path_photo = entityStagiaires.getPath_photo();
        } catch (Exception e) {
        }

    }

    private void save() {
        String lastname = editLastName.getText().toString();
        String firstname = editFirstName.getText().toString();
        String mail = editEmailStagiaire.getText().toString();
        String telephone = editMobileStagiaire.getText().toString();
        if (Validator(lastname, firstname, mail, telephone)) {
            this.entityStagiaires.setLastname(lastname);
            this.entityStagiaires.setFirstname(firstname);
            this.entityStagiaires.setMail(mail);
            this.entityStagiaires.setSms(telephone);
            this.entityStagiaires.setPath_photo(this.path_photo);

            EntitySpinner objSelectedSpinner = ((EntitySpinner) spinnerSelectGroup.getSelectedItem());
            this.entityStagiaires.setId_groupe(objSelectedSpinner.getIndex());


            if (insert) {
                StagiaireHelper.getInstance(this.getContext()).insert(this.entityStagiaires);
                Log.d("testing", "###################" + this.entityStagiaires.getId());
                EntityStagiaires.getList().add(this.entityStagiaires);
            } else {
                StagiaireHelper.getInstance(this.getContext()).update(this.entityStagiaires);
                // EntityStagiaires.getList().add(this.pos, this.entityStagiaires);
            }
            AdapterStagiaires.getInstance(null).setList(EntityStagiaires.getList());
            StagiairesViewModel.getStaticAdapter().notifyDataSetChanged();
            getActivity().finish();
        }
    }

    private void initSpinner() {
        List<EntityGroupes> listGroupes = EntityGroupes.getList();
        if (listGroupes.size() == 0) {
            Toast.makeText(getContext(), "List Groupe vide!!", Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
        spinnerGroupes = new ArrayList();//.asList("Germany", "Panama", "Australia");
        for (EntityGroupes groupe : listGroupes) {
            spinnerGroupes.add(new EntitySpinner(groupe.getLibelle_groupe(), groupe.getId(), 2));
        }

        this.adapterGroupe = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item, spinnerGroupes);

        this.adapterGroupe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerSelectGroup.setAdapter(this.adapterGroupe);

    }


    private void selectedSpinnerSelectGroup(int find_id) {
        int count = 0;
        for (Object type : spinnerGroupes) {
            if (((EntitySpinner) type).getIndex() == find_id) {
                break;
            }
            count++;
        }
        spinnerSelectGroup.setSelection(count);

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
                this.photoStagiaire.setImageURI(this.uriPhoto);
                this.path_photo = (this.filePhoto.toString());
            }
        }
    }

    private boolean Validator(String lastName, String firstName, String mail, String telephone) {
        boolean valid = true;
        //String regexp[] = {"[\\d]{2,}","/\\w{2,}/u","","" };
        if (lastName != null && !lastName.isEmpty()) {

            Pattern pattern = Pattern.compile("^[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._' -!?,;:\\-]{2,60}$");

            Matcher matcher = pattern.matcher(lastName);
            if (!matcher.find()) {
                Toast.makeText(getContext(), "Lastname pas valide", Toast.LENGTH_LONG).show();
                valid = false;
            }

        } else {
            Toast.makeText(getContext(), "Lastname pas valide", Toast.LENGTH_LONG).show();
            valid = false;

        }

        if (firstName != null && !firstName.isEmpty()) {

            Pattern pattern = Pattern.compile("^[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._' -!?,;:\\-]{2,60}$");

            Matcher matcher = pattern.matcher(firstName);
            if (!matcher.find()) {
                Toast.makeText(getContext(), "Prénom pas valide", Toast.LENGTH_LONG).show();
                valid = false;
            }

        } else {
            Toast.makeText(getContext(), "Prénom pas valide", Toast.LENGTH_LONG).show();
            valid = false;

        }

        if (mail != null && !mail.isEmpty()) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._' -!?,;:@\\-]{2,60}$");
            Matcher matcher = pattern.matcher(mail);
            if (!matcher.find()) {
                Toast.makeText(getContext(), "Email pas valide", Toast.LENGTH_LONG).show();
                valid = false;
            }

        } else {
            Toast.makeText(getContext(), "Email pas valide", Toast.LENGTH_LONG).show();
            valid = false;

        }

        if (telephone != null && !telephone.isEmpty()) {
            Pattern pattern = Pattern.compile("^[0-9+.\\-_:]{2,60}$");

            Matcher matcher = pattern.matcher(telephone);
            if (!matcher.find()) {
                Toast.makeText(getContext(), "Telephone pas valide", Toast.LENGTH_LONG).show();
                valid = false;
            }

        } else {
            Toast.makeText(getContext(), "Telephone pas valide", Toast.LENGTH_LONG).show();
            valid = false;

        }

        return valid;
    }

}
