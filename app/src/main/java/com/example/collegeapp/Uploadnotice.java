package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class Uploadnotice extends AppCompatActivity implements View.OnClickListener {
   private ImageView addphoto,noticepreview;
   private EditText noticetitle;
   private MaterialButton b1;
   public static final int permissioncode=9001;
   public static final int imagecode=890;
   private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadnotice);
        addphoto=findViewById(R.id.addphoto);
        noticepreview=findViewById(R.id.noticepreview);
        noticetitle=findViewById(R.id.noticetitle);
         b1=findViewById(R.id.uplophoto);


         b1.setOnClickListener(this);
        addphoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.addphoto :
                 permisionimg();
              break;

            case R.id.uplophoto :
                    check1();
                 break;
        }
    }

    private void check1() {
        if(noticetitle.getText().toString().isEmpty()){
            noticetitle.setError("plese add title");
            return;
        }else{
            noticetitle.setError("");
        }

        if(uri==null){
             Toast.makeText(this,"please add image",Toast.LENGTH_SHORT).show();
             return;
        }



    }

    public void permisionimg() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                  String per[]={Manifest.permission.READ_EXTERNAL_STORAGE};
                  requestPermissions(per,permissioncode);
            }else{
               chooseimge();
            }
        }else{
            chooseimge();
        }
    }

    public void chooseimge() {
       Intent gal=new Intent(Intent.ACTION_PICK);
         gal.setType("image/*");
        startActivityForResult(gal,imagecode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imagecode && resultCode == RESULT_OK && data!=null) {
              noticepreview.setImageURI(data.getData());
              uri=data.getData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case permissioncode :
                   if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                        chooseimge();
                       Toast.makeText(this,""+grantResults[0],Toast.LENGTH_SHORT).show();
                   }else{
                       Toast.makeText(this,"Permission denied...!",Toast.LENGTH_SHORT).show();
                   }
        }

    }
}
