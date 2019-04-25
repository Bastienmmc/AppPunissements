package com.ldnr.punissement.ui.main.ViewModel;

import android.app.Activity;
import android.app.Dialog;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.ldnr.punissement.R;
import com.ldnr.punissement.ui.main.DAO.PunitionHelper;
import com.ldnr.punissement.ui.main.RecyclerItemClickListener;
import com.ldnr.punissement.ui.main.adapter.AdapterPunissements;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.screens.CreateActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PunissementsViewModel extends ViewModel implements IViewModel {
    private static RecyclerView.Adapter adapter = AdapterPunissements.getInstance(EntityPunissement.getList());

    public PunissementsViewModel() {
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
                openCreateActivity(view, 1,-1,"insert");
            }
        };
    }

    public RecyclerItemClickListener.OnItemClickListener getTouchListenerFunction() {
        return new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                openCreateActivity(view, 1, position, "update");
            }

            @Override
            public void onLongItemClick(View view, int position) {
                showActionsDialog(view, 1,position);
            }
        };

    }

    public TextWatcher getTextWatcherListener(){
        return new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                //search(s);
                Log.d("testing","########text" + s);
               /* if(s.length() != 0)
                    field2.setText("");*/
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
    private void showActionsDialog(View view,final int tab, final int pos) {

        CharSequence userOptions[] = new CharSequence[]{"Delete", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        // Je définis le titre de mon AlertDialog
        builder.setTitle("Choose Option");
        // Et je définis les options proposées à l'utlisateur + j'instancie le listener correspondant à ces options
        builder.setItems(userOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int userChoice) {
                // Si l'utilisateur choisit la première option (donc Edit)
                if(userChoice == 0) {
                    Dialog dialog  = (Dialog) dialogInterface;
                    Context context = dialog.getContext();
                    Activity activity= scanForActivity(context);

                    View rootView = (View) activity.findViewById(R.id.tabs);

                    openCreateActivity(rootView, tab, pos, "delete");
                } else {

                }
            }
        });

        builder.show();
    }

    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity)cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)cont).getBaseContext());

        return null;
    }

    private void search(CharSequence str){
        List lista = PunitionHelper.getInstance(null).getList();

        //Pattern pattern = Pattern.compile("^[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._' -!?,;:\\-]{2,60}$");
        Pattern pattern = Pattern.compile(str.toString());

        List<EntityPunissement> listaok = new ArrayList();

        for(Object el:lista){
            Log.d("testing",((EntityPunissement)el).toString());
            Matcher matcher = pattern.matcher(((EntityPunissement)el).toString());
            if(matcher.find())
            {
                Log.d("testing","match");
                listaok.add((EntityPunissement)el);
            }

        }
        //EntityPunissement.setList(listaok);
        adapter = AdapterPunissements.getInstance(listaok);

    }
}
