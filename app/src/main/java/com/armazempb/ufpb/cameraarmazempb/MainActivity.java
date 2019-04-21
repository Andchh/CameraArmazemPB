package com.armazempb.ufpb.cameraarmazempb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;


import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends Activity {

    //private ImageView myImageView;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int INPUT_SIZE = 224;

    private CameraView cameraView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraView = findViewById(R.id.cameraView);
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                //pegar data e usar como nome da imagem
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ROOT);
                Date now = new Date();
                String filename = formatter.format(now) + ".png";
                File directory = new File("/sdcard/Armazempb/");
                directory.mkdirs();

                File dest = new File(directory, filename);

                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

                //pega o URI do bitmap
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                //salva o uri do bitmap caso precise usar no futuro
                File finalFile = new File(getRealPathFromURI(tempUri));

                System.out.println(dest.toString());  //dest == path to photo

                try {
                    FileOutputStream out = new FileOutputStream(dest);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        //myImageView = findViewById(R.id.CameraImage);


    }
    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }


    public void takePicture(View view) {
        cameraView.captureImage();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //for retrieve the data
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras(); //pega o intent
            Bitmap bitmap = (Bitmap) extras.get("data");

            //Bitmap ImageBitmap = (Bitmap)extras.get("data");  //salva e converte com bitmap
            //myImageView.setImageBitmap(ImageBitmap);  //mostra no imageview da tela
        }

    }
}
