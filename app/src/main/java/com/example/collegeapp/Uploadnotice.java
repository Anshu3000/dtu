package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class Uploadnotice extends AppCompatActivity implements View.OnClickListener {
   private ImageView addphoto,noticepreview;
   private EditText noticetitle;
   private MaterialButton b1;
   public static final int permissioncode=9001;
   public static final int imagecode=890;
   private Uri uri;
   private Bitmap bitma=null;
   private DatabaseReference db1;
   private StorageReference st1;
   private StorageReference Filepath1;
 private  String Downloadur1="";
 private ProgressDialog pd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadnotice);
        addphoto=findViewById(R.id.addphoto);
        noticepreview=findViewById(R.id.noticepreview);
        noticetitle=findViewById(R.id.noticetitle);
         b1=findViewById(R.id.uplophoto);
       db1= FirebaseDatabase.getInstance().getReference();
       st1=FirebaseStorage.getInstance().getReference();
        pd1=new ProgressDialog(this);
        db1=db1.child("Notice1");
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
        }


        if(uri==null){
             Toast.makeText(this,"please add image",Toast.LENGTH_SHORT).show();
             return;
        }

//        Calendar caldate=Calendar.getInstance();
//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat calsidate=new SimpleDateFormat("dd-MM-yy");
//         date=calsidate.format(caldate.getTime());
//
//        Calendar caltime=Calendar.getInstance();
//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat calsitime=new SimpleDateFormat("HH:mm:ss");
//         time=calsidate.format(caltime.getTime());

      pd1.setMessage("loading...");
        pd1.show();

        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bitma.compress(Bitmap.CompressFormat.JPEG,100,bos);
        byte[] file1=bos.toByteArray();
        Filepath1=st1.child("Notice").child(file1+"jpg");

            final UploadTask uplo =Filepath1.putBytes(file1);

            uplo.addOnCompleteListener(Uploadnotice.this,new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if(task.isSuccessful()){
//                       pd1.show();
                        uplo.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                 Filepath1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                     @Override
                                     public void onSuccess(Uri uri) {
                                        Downloadur1=String.valueOf(uri);
                                        Uploaddata1();
                                     }
                                 }).addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception e) {
                                         Toast.makeText(getApplicationContext()," Download uri failure"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                     }
                                 });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext()," failure"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        pd1.dismiss();
                        Toast.makeText(getApplicationContext(),"upload failure",Toast.LENGTH_SHORT).show();
                    }

                }
            });

    }

    private void Uploaddata1() {
       String key1=db1.push().getKey();

       String titl=noticetitle.getText().toString();

        Calendar caldate=Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat calsidate=new SimpleDateFormat("dd-MM-yy");
         String date=calsidate.format(caldate.getTime());

        Calendar caltime=Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat calsitime=new SimpleDateFormat("hh:mm a");
       String  time=calsitime.format(caltime.getTime());

        Notice123 no1=new Notice123(date,time,key1,titl,Downloadur1);

        db1.child(key1).setValue(no1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               pd1.dismiss();
                Toast.makeText(getApplicationContext(),"data recorded successful",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd1.dismiss();
                Toast.makeText(getApplicationContext(),"failure"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

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
            try {
                bitma= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
