package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Uploadimage extends AppCompatActivity implements View.OnClickListener {

     private Spinner spi;
     private Button btnupl;
     private ImageView imgpre,imgbtn;
     private String catechoose;
     private Uri ur1;
     private Bitmap bit1;
     private DatabaseReference db1;
    private DatabaseReference db23;
     private StorageReference st1;
     private  StorageReference filepath1;
     private String downloadUrl1;
     private ProgressDialog pd2;
    private static final int Rescode=9001;
    private static final int imagecode1=9801;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadimage);

        spi=findViewById(R.id.category1);
        btnupl=findViewById(R.id.uplodimage);
        imgpre=findViewById(R.id.imagepreview);
        imgbtn=findViewById(R.id.addimage1);
         db1= FirebaseDatabase.getInstance().getReference().child("Gallery");
         st1= FirebaseStorage.getInstance().getReference().child("Gallery");
         pd2=new ProgressDialog(this);

        ArrayAdapter<CharSequence> ar1=ArrayAdapter.createFromResource(this,R.array.category1,android.R.layout.simple_spinner_item);
        ar1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi.setAdapter(ar1);

        spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                catechoose=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                 catechoose=adapterView.getItemAtPosition(1).toString();
                 Toast.makeText(getApplicationContext(),"option 1 is default category",Toast.LENGTH_SHORT).show();
            }
        });
//        catechoose=adapterView.getItemAtPosition(i).toString();
        btnupl.setOnClickListener(this);
        imgbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id1=view.getId();
        switch (id1){

            case R.id.uplodimage :
                   chec1();
               break;

            case R.id.addimage1 :
                 permisionimg();
                break;
        }

    }

    private void chec1() {
        if(ur1==null){
            Toast.makeText(this,"please add photo",Toast.LENGTH_SHORT).show();
            return ;
        }
          pd2.setMessage("UPLOADING...");
           pd2.show();
        ByteArrayOutputStream  bos1=new ByteArrayOutputStream();
         bit1.compress(Bitmap.CompressFormat.JPEG,100,bos1);
        byte [] fil1=bos1.toByteArray();

       filepath1 =st1.child(fil1+".jpg");

        final UploadTask uploadTask1 = filepath1.putBytes(fil1);

             uploadTask1.addOnCompleteListener(Uploadimage.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                           if(task.isSuccessful()){

                               uploadTask1.addOnSuccessListener(Uploadimage.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                   @Override
                                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                       filepath1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                           @Override
                                           public void onSuccess(Uri uri) {
                                               downloadUrl1=uri.toString();
                                               uploaddata1();
                                           }
                                       }).addOnFailureListener(Uploadimage.this, new OnFailureListener() {
                                           @Override
                                           public void onFailure(@NonNull Exception e) {
                                               pd2.dismiss();
                                               Log.d("firefail", "onFailure: "+e.getMessage());
                                               Toast.makeText(getApplicationContext(),"url donload failure",Toast.LENGTH_SHORT).show();
                                           }
                                       });


                                   }
                               }).addOnFailureListener(Uploadimage.this, new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       pd2.dismiss();
                                       Log.d("firefail", "onFailure: "+e.getMessage());
                                       Toast.makeText(getApplicationContext(),"url donload failure",Toast.LENGTH_SHORT).show();
                                   }
                               });


                           }else{
                               Toast.makeText(getApplicationContext(),"uploadtion unsuccesfull ",Toast.LENGTH_SHORT).show();
                           }
                 }
             });

    }

    private void uploaddata1() {

        db23=db1.child(catechoose);

        String key12=db23.push().getKey();

        db23.child(key12).setValue(downloadUrl1).addOnSuccessListener( new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd2.dismiss();
                Toast.makeText(getApplicationContext(),"Successfull..",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd2.dismiss();
                Log.d("firebasefail", "onFailure: "+e.getMessage());
                Toast.makeText(getApplicationContext(),"UnSuccessfull..",Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void permisionimg() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
              if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    String per[]={Manifest.permission.READ_EXTERNAL_STORAGE};
                     requestPermissions(per,Rescode);
              }else{
                  chooseimg1();
              }

        }else{
            chooseimg1();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

          if(requestCode==Rescode){
                 if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED ){
                     Log.d("per", "onRequestPermissionsResult: "+grantResults[0]);
                     chooseimg1();
                 }else {
                     Toast.makeText(this,"permission denied...",Toast.LENGTH_SHORT).show();
                 }

          }

    }

    private void chooseimg1() {
        Intent choos1=new Intent(Intent.ACTION_GET_CONTENT);
        choos1.setType("image/*");
        startActivityForResult(choos1, imagecode1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==imagecode1){
              if(resultCode==RESULT_OK&& data!=null){
                   imgpre.setImageURI(data.getData());
                   ur1=data.getData();

                  try {
                      bit1= MediaStore.Images.Media.getBitmap(getContentResolver(),ur1);
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
        }

    }
}
