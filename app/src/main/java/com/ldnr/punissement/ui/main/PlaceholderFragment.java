package com.ldnr.punissement.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.ViewModel.GroupesViewModel;
import com.ldnr.punissement.ui.main.ViewModel.IViewModel;
import com.ldnr.punissement.ui.main.ViewModel.PunissementsViewModel;
import com.ldnr.punissement.ui.main.ViewModel.StagiairesViewModel;
import com.ldnr.punissement.ui.main.ViewModel.TypePunitionViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private IViewModel pageViewModel;
    private RecyclerView recyclerView;
    private EditText editTextSearch;
    private FloatingActionButton fab;
    private int index;

    public static PlaceholderFragment newInstance(int index) {

        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        switch (index) {
            case 1:
                this.pageViewModel = ViewModelProviders.of(this).get(PunissementsViewModel.class);
                break;
            case 2:
                this.pageViewModel = ViewModelProviders.of(this).get(StagiairesViewModel.class);
                break;
            case 3:
                this.pageViewModel = ViewModelProviders.of(this).get(GroupesViewModel.class);
                break;
            case 4:
                this.pageViewModel = ViewModelProviders.of(this).get(TypePunitionViewModel.class);
                break;
        }
        this.index = index;
        pageViewModel.setIndex(index);
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(pageViewModel.getAdapter());
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        //final TextView textView = root.findViewById(R.id.section_label);

        recyclerView = root.findViewById(R.id.recyclerView);
        fab = root.findViewById(R.id.fab);
        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));


        fab.setOnClickListener(pageViewModel.getFabFunction());

        recyclerView.setAdapter(pageViewModel.getAdapter());

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(root.getContext(), recyclerView, pageViewModel.getTouchListenerFunction()));


        editTextSearch = (EditText) root.getRootView().findViewById(R.id.editTextSearch);
        //root.getRootView().findViewById((R.id.editTextSearch);
        //editTextSearch.setText("dio boia");
        /*Activity.findViewById(R.id.editTextSearch);
        View.findViewById(R.id.editTextSearch);
        root.setContentView(R.layout.activity_main);*/

        editTextSearch.addTextChangedListener(pageViewModel.getTextWatcherListener());


        /*
                new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    field2.setText("");
            }
        });*/

        return root;
    }
}