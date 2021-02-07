package com.example.collegeapp.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;

import com.example.collegeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Addteacher extends AppCompatActivity implements View.OnClickListener {
     private  AppCompatSpinner catego;
     private ImageView teah;
     private TextInputEditText nam,email1;
     private String strem;
     private MaterialButton upl;
     private Uri urimage;
     private Bitmap bitmap1=null;
     static final int  permissoncode=10023;
    private static final int Imgerequestcode=20002;
     private String teachername1,teacheremail,Downloadurl="";
     private DatabaseReference fir1,fir2;
     private StorageReference st1,db1;
     private String dowload1=null;
     private ProgressDialog pd;

    //    String[] cate =getResources().getStringArray(R.array.category12);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addteacher);
        final String[] cate =getResources().getStringArray(R.array.category12);
        catego=findViewById(R.id.cateteacher);
        teah=findViewById(R.id.circularimage1);
        nam=findViewById(R.id.teahername1);
        email1=findViewById(R.id.email);
        upl=findViewById(R.id.upladteacher);
         fir1= FirebaseDatabase.getInstance().getReference().child("Teacher");
         st1= FirebaseStorage.getInstance().getReference().child("Teache1/");

        pd=new ProgressDialog(this);

        teah.setOnClickListener(this);
        upl.setOnClickListener(this);


   //ArrayAdapter<CharSequence> ad1=ArrayAdapter.createFromResource(this,R.array.category12,android.R.layout.simple_spinner_item);
       ArrayAdapter<String> ad1=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,cate);
            ad1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            catego.setAdapter(ad1);

         catego.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 strem=(String)adapterView.getItemAtPosition(i);
                 Toast.makeText(getApplicationContext(),strem,Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {
                 strem=(String) adapterView.getItemAtPosition(0);
             }
         });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.circularimage1 :
                          checkpermission();
                    break;

            case R.id.upladteacher :
                      uploaddata1();
                 break;

        }

    }

    private void uploaddata1() {
          final StorageReference file12p=st1.child(String.valueOf(System.currentTimeMillis()));
          if(bitmap1==null){
             Toast.makeText(this,"Please add image...",Toast.LENGTH_SHORT).show();
             return;
          }

          if(nam.getText().toString().isEmpty()){
               nam.setError("Please enter name");
               nam.requestFocus();
               return;
          }

        if(email1.getText().toString().isEmpty()){
            email1.setError("Please enter email");
            email1.requestFocus();
            return;
        }


          if(strem.equals("Select category")){
              Toast.makeText(this,"Please choose category..",Toast.LENGTH_SHORT).show();
              return ;
          }

          email1.setError(null);
          nam.setError(null);

//          Toast.makeText(this,""+nam.getText().toString()+"//"+email1.getText().toString(),Toast.LENGTH_LONG).show();

//        teacheremail=email1.getText().toString();
//        teachername1=nam.getText().toString();


         pd.setTitle("uploading....");

        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG,100,bos);
         byte [] final1=bos.toByteArray();

           pd.show();
        final UploadTask upl=file12p.putBytes(final1);
         upl.addOnCompleteListener(Addteacher.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        upl.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                              file12p.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                  @Override
                                  public void onSuccess(Uri uri) {
                                       dowload1=uri.toString();
                                       uplodteacherdata();
                                  }
                              })   ;
                            }
                        });


                    }else{
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"error in uploading image...",Toast.LENGTH_SHORT).show();
                    }
             }
         });


    }

    private void uplodteacherdata() {
        String nam1=nam.getText().toString();
        String email23=email1.getText().toString();
           fir2=fir1.child(strem);
        String key1=fir2.push().getKey();

        Teacher12 t12=new Teacher12(nam1,email23,strem,key1,dowload1);

        assert key1 != null;
        fir2.child(key1).setValue(t12).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"data Uploading successfull...",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                      pd.dismiss();
                Toast.makeText(getApplicationContext(),"error in uploading data...",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void checkpermission() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
             if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                 String[]per={Manifest.permission.READ_EXTERNAL_STORAGE};
                 requestPermissions(per,permissoncode);
             }else{
                 chooseimg();
             }

        }else{
            chooseimg();
        }

    }

    private void chooseimg() {
        Intent in =new Intent(Intent.ACTION_GET_CONTENT);
        in.setType("image/*");
        startActivityForResult(in,Imgerequestcode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Imgerequestcode&&resultCode==RESULT_OK&&data!=null){
            teah.setImageURI(data.getData());
            urimage=data.getData();

            try {
                bitmap1= MediaStore.Images.Media.getBitmap(getContentResolver(),urimage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            Toast.makeText(getApplicationContext(),"image upload cancel..",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==permissoncode&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            chooseimg();
        }else{
            Toast.makeText(this,"Permission denied...",Toast.LENGTH_SHORT).show();
        }

    }


}
