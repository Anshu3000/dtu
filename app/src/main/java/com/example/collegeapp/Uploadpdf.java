package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Uploadpdf extends AppCompatActivity implements View.OnClickListener {

    private ImageView addpdf;
    private EditText pdftitle;
    private TextView t1;
    private MaterialButton b1;
    public static final int permissioncode=9001;
    public static final int imagecode=890;
    private Uri uri;
    private Bitmap bitma=null;
    private DatabaseReference db1;
    private StorageReference st1;
    private StorageReference Filepath1;
    private  String Downloadur1="",displayName ="";
     private  String tile="";
    private ProgressDialog pd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpdf);

          addpdf=findViewById(R.id.addpdf1);
          pdftitle=findViewById(R.id.pdftitle);
           b1=findViewById(R.id.uplodpdf1);
           t1=findViewById(R.id.ebooktitle1);
            db1= FirebaseDatabase.getInstance().getReference();
            st1=FirebaseStorage.getInstance().getReference();
            pd1=new ProgressDialog(this);
             pd1.setMax(100);
             pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
          addpdf.setOnClickListener(this);
           b1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.addpdf1 :
                permisionimg();
                break;

            case R.id.uplodpdf1 :
                check1();
                break;
        }
    }

    private void check1() {
        tile=pdftitle.getText().toString();

         if(tile.length()==0){
             pdftitle.setError("please add title");
             return;
         }
           pdftitle.setError(null);

         if(uri==null){
             Toast.makeText(getApplicationContext(),"please add pdf ",Toast.LENGTH_SHORT).show();
             return;
         }
              pd1.setMessage("data uploading ...");
              pd1.setTitle("File uploading progress");
               pd1.show();
         final StorageReference st23=st1.child("pdf/"+displayName+"-"+System.currentTimeMillis()+".pdf");

        UploadTask uploadTask1 = st23.putFile(uri);

        uploadTask1.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                      Toast.makeText(getApplicationContext(),"check1",Toast.LENGTH_SHORT).show();
                         double d=(snapshot.getBytesTransferred()*100.0)/(snapshot.getTotalByteCount());
                           pd1.incrementProgressBy((int)d);
                      Log.d("testfire", "onProgress: completion "+d);
                  }
              }).addOnSuccessListener(Uploadpdf.this,new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                      st23.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                          @Override
                          public void onSuccess(Uri uri) {
                              Toast.makeText(getApplicationContext(),"check1234",Toast.LENGTH_SHORT).show();
                              Downloadur1=uri.toString();
                              uploddata1();
                          }
                      }).addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                               pd1.dismiss();
                              Log.d("testfire", "onFailure: 120 ..."+e.getMessage());
                              Toast.makeText(getApplicationContext(),"error in dowloading url  ",Toast.LENGTH_SHORT).show();
                          }
                      });

                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      pd1.dismiss();
                      Log.d("testfire", "onFailure: 128... "+e.getMessage());
                      Toast.makeText(getApplicationContext(),"error in uploading data ",Toast.LENGTH_SHORT).show();
                  }
              });

//              pd1.dismiss();

        Toast.makeText(getApplicationContext(),"check1",Toast.LENGTH_SHORT).show();
    }

    private void uploddata1() {
        db1 =db1.child("pdf");
        String s1=db1.push().getKey();

        HashMap<String,Object> u1=new HashMap<>();
         u1.put("title",tile);
         u1.put("downloadurl",Downloadur1);

         db1.child(s1).setValue(u1).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void aVoid) {
                 pd1.dismiss();
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 pd1.dismiss();
                 Log.d("testfire", "onFailure: 128... "+e.getMessage());
                 Toast.makeText(getApplicationContext(),"error in uploading data ",Toast.LENGTH_SHORT).show();
             }
         });

    }

    public void permisionimg() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
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
        Intent gal=new Intent(Intent.ACTION_GET_CONTENT);
        gal.setType("application/pdf");
//        gal.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(gal,"choose pdf file "),imagecode);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imagecode && resultCode == RESULT_OK && data!=null) {

            String str12="";
            uri=data.getData();
            if(uri!=null)
             str12=uri.toString();
            File fil1=new File(str12);
            String path=fil1.getAbsolutePath();

             if(str12.startsWith("content://")){

                 Cursor cursor = null;
                 try {
                     cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
                     if (cursor != null && cursor.moveToFirst()) {
                         displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                     }
                 } finally {
                     if (cursor!=null)
                       cursor.close();
                 }

             }else if (str12.startsWith("file://")){

                 displayName=fil1.getName();
             }
                  t1.setVisibility(View.VISIBLE);
                  t1.setText(displayName);

                Toast.makeText(getApplicationContext(),""+displayName,Toast.LENGTH_SHORT).show();
//            try {
//                bitma= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
    }

}
