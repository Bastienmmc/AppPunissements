package com.ldnr.punissement.ui.main.Services;


import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import java.text.SimpleDateFormat;
import com.ldnr.punissement.BuildConfig;
import java.util.Date;

import java.io.File;

public class StorageService {
    private static StorageService instance;
    private Context context;
    /**
     * Singleton implementation
     * @return
     */
    public static StorageService getInstance(Context context)     {
        if (instance == null) {
            instance = new StorageService(context);
        }
        return instance;
    }

    /**
     * Local constructor
     */
    private StorageService( Context context )     {
        this.context=context;
    }


    public File getOutputMediaFile(){
        // On instancie un nouveau fichier. Il sera stocké dans le répertoire de
        //notre appli
        File mediaStorageDir = new
                File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");
        // S'il n'existe pas, il est créé
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }
        // On retourne le fichier
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
    }

    public Uri getOutputMediaFile(String filepath){
        return FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider",
                new File(filepath));
    }







}