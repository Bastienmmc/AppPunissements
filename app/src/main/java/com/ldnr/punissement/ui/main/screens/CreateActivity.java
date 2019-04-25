package com.ldnr.punissement.ui.main.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ldnr.punissement.R;

public class CreateActivity extends AppCompatActivity {
    public final CreateGroupesFragment fragmentCreateGroupes = new CreateGroupesFragment();
    public final CreatePunissementFragment fragmentCreatePunissements = new CreatePunissementFragment();
    public final CreateStagiaresFragment fragmentCreateStagiaires = new CreateStagiaresFragment();
    public final CreateTypePunitionFragment fragmentCreateTypePunition = new CreateTypePunitionFragment();

    public FragmentManager fragmentManager;
    private int pos_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        fragmentManager = getSupportFragmentManager();


        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                pos_tab = extras.getInt("pos_tab");
                int pos_list = extras.getInt("pos_list");
                String operation = extras.getString("operation");
                switch (pos_tab) {
                    case (1):
                        this.openFragmentTab1(pos_list, operation);
                        break;
                    case (2):
                        this.openFragmentTab2(pos_list, operation);
                        break;
                    case (3):
                        this.openFragmentTab3(pos_list, operation);
                        break;
                    case (4):
                        this.openFragmentTab4(pos_list, operation);
                        break;
                    default:
                        Toast.makeText(this, "Error lecture!", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                }


            }
        } catch (Exception e) {
            finish();
        }

    }

    public void openFragmentTab1(int pos, String operation) {
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("pos_list", pos);  //-1 if for new data
        dataBundle.putString("operation", operation);  //insert / delete / update
        fragmentCreatePunissements.setArguments(dataBundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, this.fragmentCreatePunissements);
        fragmentTransaction.commit();
    }

    public void openFragmentTab2(int pos, String operation) {
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("pos_list", pos);  //-1 if for new data
        dataBundle.putString("operation", operation);  //insert / delete / update
        fragmentCreateStagiaires.setArguments(dataBundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, this.fragmentCreateStagiaires);
        fragmentTransaction.commit();
    }

    public void openFragmentTab3(int pos, String operation) {
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("pos_list", pos);  //-1 if for new data
        dataBundle.putString("operation", operation);  //insert / delete / update
        fragmentCreateGroupes.setArguments(dataBundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragmentCreateGroupes);
        fragmentTransaction.commit();
    }

    public void openFragmentTab4(int pos, String operation) {
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("pos_list", pos);  //-1 if for new data
        dataBundle.putString("operation", operation);  //insert / delete / update
        fragmentCreateTypePunition.setArguments(dataBundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragmentCreateTypePunition);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (pos_tab) {
            case (1):
                fragmentCreatePunissements.onActivityResult(requestCode, resultCode, data);
                break;
            case (2):
                fragmentCreateStagiaires.onActivityResult(requestCode, resultCode, data);
                break;
            case (3):
                fragmentCreateGroupes.onActivityResult(requestCode, resultCode, data);
                break;
            case (4):
                fragmentCreateTypePunition.onActivityResult(requestCode, resultCode, data);
                break;
            default:
                Toast.makeText(this, "Error lecture!", Toast.LENGTH_LONG).show();
                finish();
                break;

        }
    }
}