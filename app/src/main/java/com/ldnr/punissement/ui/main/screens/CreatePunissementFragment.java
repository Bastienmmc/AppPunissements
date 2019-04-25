package com.ldnr.punissement.ui.main.screens;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.DAO.PunitionHelper;
import com.ldnr.punissement.ui.main.ViewModel.PunissementsViewModel;
import com.ldnr.punissement.ui.main.adapter.AdapterPunissements;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.entity.EntitySpinner;
import com.ldnr.punissement.ui.main.entity.EntityStagiaires;
import com.ldnr.punissement.ui.main.entity.EntityTypePunition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePunissementFragment extends Fragment {
    private boolean insert;
    private int pos;
    private String operation;
    private EditText editTitle;
    private EditText editLibelle;
    private EditText editLieu;
    private EditText editDate;
    private Spinner spinnerSelectEntity;
    private Spinner spinnerSelectType;

    private ImageButton iconGroupe;
    private ImageButton iconStagiaire;


    private FloatingActionButton fab;

    private EntityPunissement entityPunissement;
    private ArrayAdapter<String> adapterStagiaire;
    private ArrayAdapter<String> adapterGroupe;
    private List spinnerStagiaires;
    private List spinnerGroupes;
    private List spinnerType;
    private int selectedAdapter;

    private int mYear, mMonth, mDay;

    public CreatePunissementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            //storageService = StorageService.getInstance(getContext());
            editTitle = (EditText) getView().findViewById(R.id.title);
            editLibelle = (EditText) getView().findViewById(R.id.libelle);
            editLieu = (EditText) getView().findViewById(R.id.lieu);
            editDate = (EditText) getView().findViewById(R.id.date);

            editDate.setFocusable(true);
            editDate.setFocusableInTouchMode(true);
            editDate.setClickable(true);
            editDate.setInputType(InputType.TYPE_NULL);

            spinnerSelectEntity = (Spinner) getView().findViewById(R.id.select_entity);
            spinnerSelectType = (Spinner) getView().findViewById(R.id.select_type);

            iconGroupe = (ImageButton) getView().findViewById(R.id.icon_groupe);
            iconStagiaire = (ImageButton) getView().findViewById(R.id.icon_stagiaire);

            fab = (FloatingActionButton) getView().findViewById(R.id.floatingActionButton);

            this.initSpinner();

            iconGroupe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchSpinnerSelectEntity(2);
                }
            });
            iconStagiaire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchSpinnerSelectEntity(1);
                }
            });


            editDate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Launch Date Picker Dialog
                    DatePickerDialog dpd = new DatePickerDialog(v.getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    Calendar myCalendar = Calendar.getInstance();
                                    myCalendar.set(Calendar.YEAR, year);
                                    myCalendar.set(Calendar.MONTH, monthOfYear);
                                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                    // myCalendar.add(Calendar.DATE, 0);
                                    String myFormat = "dd-MM-yyyy"; //In which you need put here
                                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                                    editDate.setText(sdf.format(myCalendar.getTime()));

                                }
                            }, mYear, mMonth, mDay);
                    dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                    dpd.show();

                }
            });


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // dialogfragment
                    save();
                    DialogSendFragment dialogSendFragment = new DialogSendFragment();
                    Bundle args = new Bundle();
                    args.putInt("id", entityPunissement.getId());
                    dialogSendFragment.setArguments(args);
                    dialogSendFragment.show(getFragmentManager(), "Send");
                    //getActivity().finish();
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
                    PunitionHelper.getInstance(this.getContext()).delete(AdapterPunissements.getInstance(null).getList().get(pos));
                    AdapterPunissements.getInstance(null).getList().remove(pos);
                    PunissementsViewModel.getStaticAdapter().notifyDataSetChanged();
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
        return inflater.inflate(R.layout.fragment_create_punissement, container, false);
    }

    private void initInsert() {
        this.insert = true;
        this.entityPunissement = new EntityPunissement();
        Date currentTime = Calendar.getInstance().getTime();
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        editDate.setText(sdf.format(currentTime));
        this.switchSpinnerSelectEntity(1);
    }

    private void initUpdate(int pos) {
        this.insert = false;
        this.entityPunissement = (EntityPunissement) AdapterPunissements.getInstance(null).getList().get(pos);

        editTitle.setText(entityPunissement.getTitle());
        editLibelle.setText(entityPunissement.getDescription());
        editLieu.setText(entityPunissement.getLieu());
        editDate.setText(entityPunissement.getDate());

        //TODO SPINNER SELECTED ET IMAGE SELECTED!!!!
        int id_stagiaire = entityPunissement.getId_stagiaire();
        int id_groupe = entityPunissement.getId_groupe();
        int id_type = entityPunissement.getId_type();


        if (id_stagiaire != 0) {    //est un stagiaire
            switchSpinnerSelectEntity(1);
            selectedSpinnerSelectEntity(1, id_stagiaire);
        }
        if (id_groupe != 0) {   //est un groupe
            switchSpinnerSelectEntity(2);
            selectedSpinnerSelectEntity(1, id_groupe);
        }
        if (id_type != 0) {
            selectedSpinnerSelectType(id_type);
        }

    }

    private void save() {
        String title = editTitle.getText().toString();
        String description = editLibelle.getText().toString();
        String lieu = editLieu.getText().toString();
        String date = editDate.getText().toString();
        if (Validator(title, description, lieu, date)) {
            this.entityPunissement.setTitle(title);
            this.entityPunissement.setDescription(description);
            this.entityPunissement.setLieu(lieu);
            this.entityPunissement.setDate(date);

            EntitySpinner objSelectedSpinner = ((EntitySpinner) spinnerSelectEntity.getSelectedItem());
            switch (objSelectedSpinner.getType()) {
                case 1:
                    this.entityPunissement.setId_stagiaire(objSelectedSpinner.getIndex());
                    this.entityPunissement.setId_groupe(0);
                    break;
                case 2:
                    this.entityPunissement.setId_stagiaire(0);
                    this.entityPunissement.setId_groupe(objSelectedSpinner.getIndex());
                    break;
            }

            EntitySpinner objSelectedSpinner1 = ((EntitySpinner) spinnerSelectType.getSelectedItem());
            this.entityPunissement.setId_type(objSelectedSpinner1.getIndex());


            if (insert) {
                PunitionHelper.getInstance(this.getContext()).insert(this.entityPunissement);
                EntityPunissement.getList().add(this.entityPunissement);
            } else {
                PunitionHelper.getInstance(this.getContext()).update(this.entityPunissement);
            }
            AdapterPunissements.getInstance(null).setList(EntityPunissement.getList());
            PunissementsViewModel.getStaticAdapter().notifyDataSetChanged();

        }
    }

    private void initSpinner() {
        List<EntityStagiaires> listStagiares = EntityStagiaires.getList();
        List<EntityGroupes> listGroupes = EntityGroupes.getList();
        List<EntityTypePunition> listTypePunition = EntityTypePunition.getList();
        if (listStagiares.size() == 0 || listGroupes.size() == 0 || listTypePunition.size() == 0) {
            Toast.makeText(getContext(), "List Groupe ou List Stagiaire ou ListTypePunition vide!!", Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

        spinnerStagiaires = new ArrayList();
        for (EntityStagiaires stagiaire : listStagiares) {
            spinnerStagiaires.add(new EntitySpinner(stagiaire.getLastAndFirstName(), stagiaire.getId(), 1));
        }

        spinnerGroupes = new ArrayList();
        for (EntityGroupes groupe : listGroupes) {
            spinnerGroupes.add(new EntitySpinner(groupe.getLibelle_groupe(), groupe.getId(), 2));
        }

        spinnerType = new ArrayList();
        for (EntityTypePunition typePunition : listTypePunition) {
            spinnerType.add(new EntitySpinner(typePunition.getTitle(), typePunition.getId(), 3));
        }

        this.adapterStagiaire = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item, spinnerStagiaires);

        this.adapterStagiaire.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.adapterGroupe = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item, spinnerGroupes);

        this.adapterGroupe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item, spinnerType);

        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectType.setAdapter(adapterType);
    }

    private void selectedSpinnerSelectEntity(int i, int find_id) {
        if (i == 1) {
            int count = 0;
            for (Object stagiaire : spinnerStagiaires) {
                if (((EntitySpinner) stagiaire).getIndex() == find_id) {
                    break;
                }

                count++;
            }
            spinnerSelectEntity.setSelection(count);
        }
        if (i == 2) {
            int count = 0;
            for (Object groupe : spinnerGroupes) {
                if (((EntitySpinner) groupe).getIndex() == find_id) {
                    break;
                }

                count++;
            }
            spinnerSelectEntity.setSelection(count);
        }
    }

    private void selectedSpinnerSelectType(int find_id) {
        int count = 0;
        for (Object type : spinnerType) {
            if (((EntitySpinner) type).getIndex() == find_id) {
                break;
            }
            count++;
        }
        spinnerSelectType.setSelection(count);

    }

    private void switchSpinnerSelectEntity(int i) {
        this.selectedAdapter = i;
        switch (i) {
            case 1:
                spinnerSelectEntity.setAdapter(this.adapterStagiaire);
                this.iconStagiaire.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.colorButtonDone));
                this.iconGroupe.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.colorBleuDark));
                break;
            case 2:
                spinnerSelectEntity.setAdapter(this.adapterGroupe);
                this.iconGroupe.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.colorButtonDone));
                this.iconStagiaire.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.colorBleuDark));
                break;
        }
    }

    private boolean Validator(String title, String description, String lieu, String date) {
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

        if (lieu != null && !lieu.isEmpty()) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._' -!?,;:\\-]{2,60}$");

            Matcher matcher = pattern.matcher(lieu);
            if (!matcher.find()) {
                Toast.makeText(getContext(), "Lieu pas valide", Toast.LENGTH_LONG).show();
                valid = false;
            }

        } else {
            Toast.makeText(getContext(), "Lieu pas valide", Toast.LENGTH_LONG).show();
            valid = false;

        }

        if (date != null && !date.isEmpty()) {

            Pattern pattern = Pattern.compile("^[0-9._' \\/\\-]{2,60}$");

            Matcher matcher = pattern.matcher(date);
            if (!matcher.find()) {
                Toast.makeText(getContext(), "Date pas valide", Toast.LENGTH_LONG).show();
                valid = false;
            }

        } else {
            Toast.makeText(getContext(), "Date pas valide", Toast.LENGTH_LONG).show();
            valid = false;

        }

        return valid;
    }


}

