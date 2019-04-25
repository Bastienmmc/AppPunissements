package com.ldnr.punissement;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.ldnr.punissement.ui.main.DAO.GroupeHelper;
import com.ldnr.punissement.ui.main.DAO.PunitionHelper;
import com.ldnr.punissement.ui.main.DAO.StagiaireHelper;
import com.ldnr.punissement.ui.main.DAO.TypePunitionHelper;
import com.ldnr.punissement.ui.main.SectionsPagerAdapter;
import com.ldnr.punissement.ui.main.entity.EntityGroupes;
import com.ldnr.punissement.ui.main.entity.EntityPunissement;
import com.ldnr.punissement.ui.main.entity.EntityStagiaires;
import com.ldnr.punissement.ui.main.entity.EntityTypePunition;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EntityStagiaires.setList(StagiaireHelper.getInstance(this).getList());



        EntityGroupes.setList(GroupeHelper.getInstance(this).getList());

        EntityTypePunition.setList(TypePunitionHelper.getInstance(this).getList());
        EntityPunissement.setList(PunitionHelper.getInstance(this).getList());

    /*    List lista = PunitionHelper.getInstance(this).getList();

        //Pattern pattern = Pattern.compile("^[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._' -!?,;:\\-]{2,60}$");
        Pattern pattern = Pattern.compile("tiramisu");


        List listaok = new ArrayList();


        for(Object el:lista){
            Log.d("testing",((EntityPunissement)el).toString());
            Matcher matcher = pattern.matcher(((EntityPunissement)el).toString());
            if(matcher.find())
            {
                listaok.add((EntityPunissement)el);
            }

        }
        EntityPunissement.setList(listaok);*/


        // A la création de la vue, on vérifie les permissions.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            // Si aucune permission, on bloue le bouton, on redemande la permission
            // TODO
            // takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);


    }


    public void initDb(){
        //GroupeHelper.getInstance(getApplicationContext()).deleteAllElelementsTable();
        EntityGroupes.setList(GroupeHelper.getInstance(getApplicationContext()).getList());
        Log.d("testing", "##########EntityGroupeListSize" + EntityGroupes.getList().size());
        EntityStagiaires.setList(StagiaireHelper.getInstance(getApplicationContext()).getList());
        EntityPunissement.setList(PunitionHelper.getInstance(getApplicationContext()).getList());
        EntityTypePunition.setList(TypePunitionHelper.getInstance(getApplicationContext()).getList());



    }
}