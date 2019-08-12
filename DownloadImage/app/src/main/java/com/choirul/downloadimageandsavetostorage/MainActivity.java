package com.choirul.downloadimageandsavetostorage;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    ImageView imageView;
    Button btnDownload;
    EditText edtUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.imageView);
        btnDownload = findViewById(R.id.btnDownload);
        edtUrl = findViewById(R.id.edtUrl);

        //save to external storage
        StorageChecker.checkStorageAvailability(MainActivity.this);
        if (isStoragePermissionGranted()) {
            btnDownload.setOnClickListener(MainActivity.this);
        }else{
            btnDownload.setEnabled(false);
        }
    }

    public boolean isStoragePermissionGranted(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Log.v("LOG", "Permission granted");
                return true;
            } else {
                Log.v("LOG", "Permission revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }else{
            Log.v("LOG", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        ProcessDownloadImage processDownloadImage = new ProcessDownloadImage(MainActivity.this);
        String url = (edtUrl.getText().toString());
        //String url = "https://images-na.ssl-images-amazon.com/images/I/71%2BqAJehpkL._SY355_.jpg";
        processDownloadImage.execute(url);

    }

    public void saveImageToDownloadFolder(String imageFile, Bitmap ibitmap){
        try {
            File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), imageFile);
            OutputStream outputStream = new FileOutputStream(filePath);
            ibitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(MainActivity.this, imageFile + "Sucessfully saved in Download Folder", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private class ProcessDownloadImage extends AsyncTask<String, Void, Bitmap> {

        ProgressDialog progressDialog;
        Context context;
        String urlAddress;

        public ProcessDownloadImage(Context context){
            this.context = context;
            progressDialog = new ProgressDialog(this.context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Download in progress...please wait");
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            urlAddress = strings[0];
            Bitmap bitmap = null;

            try {
                URL url = new URL(urlAddress);
                InputStream inputStream = url.openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            imageView.setImageBitmap(bitmap);
            //save image to download folder
            String fileName = urlAddress.substring(urlAddress.lastIndexOf("/")+1);
            Log.i("FILE", fileName);

            saveImageToDownloadFolder(fileName, bitmap);


            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
