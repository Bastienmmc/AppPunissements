package com.ldnr.punissement.ui.main.screens;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ldnr.punissement.R;

public class CreateActivity extends AppCompatActivity {
    public final CreateGroupesFragment fragmentCreateGroupes = new CreateGroupesFragment();
    public final CreatePunissementFragment fragmentCreatePunissements = new CreatePunissementFragment();
    public final CreateStagiaresFragment fragmentCreateStagiaires = new CreateStagiaresFragment();

    public FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        fragmentManager = getSupportFragmentManager();


        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                int pos_tab = extras.getInt("pos_tab");
                int pos_list= extras.getInt("pos_list");
                switch (pos_tab){
                    case(1):
                        this.openFragmentTab1(pos_list);
                        Toast.makeText(this, "Apri 1!", Toast.LENGTH_LONG).show();
                        break;
                    case(2):
                        this.openFragmentTab2(pos_list);
                        Toast.makeText(this, "Apri 2!", Toast.LENGTH_LONG).show();
                        break;
                    case(3):
                        this.openFragmentTab3(pos_list);
                        //Toast.makeText(this, "Apri 3!", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(this, "Error lecture!", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                }

             /*   if (pos_tab < 1) {

                }
                if (pos_tab == 1) {
                    //new picture
                    this.pos_list = -1;
                    this.pictureRead = null;
                    bDelete.setVisibility(View.INVISIBLE);
                    bUpdate.setVisibility(View.INVISIBLE);
                }
                if (pos_tab >= 0) {
                    //update delete
                    readPicture(pos_list);
                }*/

            }
        } catch (Exception e) {
            finish();
        }

    }

    public void openFragmentTab1(int pos){
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("pos_list", pos);  //-1 if for new data
        fragmentCreatePunissements.setArguments(dataBundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, this.fragmentCreatePunissements);
        fragmentTransaction.commit();
    }
    public void openFragmentTab2(int pos){
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("pos_list", pos);  //-1 if for new data
        fragmentCreateStagiaires.setArguments(dataBundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, this.fragmentCreateStagiaires);
        fragmentTransaction.commit();
    }
    public void openFragmentTab3(int pos){
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("pos_list", pos);  //-1 if for new data
        fragmentCreateGroupes.setArguments(dataBundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragmentCreateGroupes);
        fragmentTransaction.commit();
    }
}
