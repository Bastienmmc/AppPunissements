package com.ldnr.punissement.ui.main.ViewModel;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.RecyclerItemClickListener;
import com.ldnr.punissement.ui.main.adapter.AdapterTypePunition;
import com.ldnr.punissement.ui.main.entity.EntityTypePunition;
import com.ldnr.punissement.ui.main.screens.CreateActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TypePunitionViewModel extends ViewModel implements IViewModel {
    private static RecyclerView.Adapter adapter = AdapterTypePunition.getInstance(EntityTypePunition.getList());

    public static RecyclerView.Adapter getStaticAdapter(){
        return adapter;
    }

    public TypePunitionViewModel() {
    }

    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }

    public void setIndex(int index) {

    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public View.OnClickListener getFabFunction() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateActivity(view, 4, -1, "insert");
            }
        };
    }

    public RecyclerItemClickListener.OnItemClickListener getTouchListenerFunction() {
        return new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                openCreateActivity(view, 4, position, "update");
            }

            @Override
            public void onLongItemClick(View view, int position) {
                showActionsDialog(view, 4, position);
            }
        };

    }

    public TextWatcher getTextWatcherListener() {
        return new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                search(s);
            }
        };
    }

    public void openCreateActivity(View view, int tab, int pos, String operation) {
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("pos_tab", tab);  //1,2,3
        dataBundle.putInt("pos_list", pos);  //-1 if for new data
        dataBundle.putString("operation", operation);  //insert / delete / update
        Context context = view.getContext();
        Intent openIntent = new Intent(context, CreateActivity.class);
        openIntent.putExtras(dataBundle);
        context.startActivity(openIntent);
    }

    // Affichage d'une boite de dialogue
    private void showActionsDialog(View view, final int tab, final int pos) {

        CharSequence userOptions[] = new CharSequence[]{"Delete", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        // Je définis le titre de mon AlertDialog
        builder.setTitle("Choose Option");
        // Et je définis les options proposées à l'utlisateur + j'instancie le listener correspondant à ces options
        builder.setItems(userOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int userChoice) {
                // Si l'utilisateur choisit la première option (donc Edit)
                if (userChoice == 0) {
                    Dialog dialog = (Dialog) dialogInterface;
                    Context context = dialog.getContext();
                    Activity activity = scanForActivity(context);

                    View rootView = (View) activity.findViewById(R.id.tabs);

                    openCreateActivity(rootView, tab, pos, "delete");
                } else {

                }
            }
        });

        builder.show();
    }

    private void search(CharSequence str) {
        List<EntityTypePunition> lista = EntityTypePunition.getList();//getInstance(null).getList();

        Pattern pattern = Pattern.compile(str.toString());
        List<EntityTypePunition> listaSearch = new ArrayList();

        for (EntityTypePunition el : lista) {
            Matcher matcher = pattern.matcher((el).toString());
            if (matcher.find()) {
                listaSearch.add(el);
            }
        }

        AdapterTypePunition.getInstance(listaSearch).setList(listaSearch);
        adapter.notifyDataSetChanged();
    }


}