package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
  ImageView i1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         i1=findViewById(R.id.noteimg);

         i1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent x=new Intent(getApplicationContext(),Uploadnotice.class);
                 startActivity(x);
             }
         });


    }
}
