package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
  ImageView i1 ,i2,i3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         i1=findViewById(R.id.noteimg);
         i2=findViewById(R.id.galleryimg);
         i3=findViewById(R.id.ebook);
         i1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent x=new Intent(getApplicationContext(),Uploadnotice.class);
                 startActivity(x);
             }
         });

         i2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent x1=new Intent(getApplicationContext(),Uploadimage.class);
                 startActivity(x1);
             }
         });

        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x=new Intent(getApplicationContext(),Uploadpdf.class);
                startActivity(x);
            }
        });

    }
}
