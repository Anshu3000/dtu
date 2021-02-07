package com.example.collegeapp.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class Updatefaculty extends AppCompatActivity {

    private RecyclerView cs,mech,elec,other,mathe;
    private LinearLayout csl1,mechl1,elecl1,otherl1,mathel1;
    private DatabaseReference d1;
    private List<Teacher12> malist12,mechlist12,cslist12,elelist12,othlist12;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatefaculty);
        fab=findViewById(R.id.openTeacher);
        cs=findViewById(R.id.csteacher);
        mech=findViewById(R.id.mechteacher);
        elec=findViewById(R.id.electeacher);
        other=findViewById(R.id.othteacher);
       mathe=findViewById(R.id.mathteacher);

       csl1=findViewById(R.id.csnodatafound);
       mechl1=findViewById(R.id.mechnodatafound);
       elecl1=findViewById(R.id.electnodatafound);
       otherl1=findViewById(R.id.ohternodatafound);
       mathel1=findViewById(R.id.mathnodatafound);

       d1= FirebaseDatabase.getInstance().getReference().child("Teacher");

        mathdata();
        mechdata();
        csdepdata();
        electdata();
        otherdata();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),Addteacher.class);
                  startActivity(in);
            }
        });



    }

    private void otherdata() {
        DatabaseReference d2oth = d1.child("Other");
        d2oth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                othlist12=new ArrayList<>();

                if(snapshot.exists()){
                    other.setVisibility(View.VISIBLE);
                    otherl1.setVisibility(View.GONE);

                    for(DataSnapshot x:snapshot.getChildren()){
                        othlist12.add(x.getValue(Teacher12.class));
                    }

                    other.setHasFixedSize(true);
                    other.setLayoutManager(new LinearLayoutManager(Updatefaculty.this));
                    other.setAdapter(new Teacheradapter(othlist12,Updatefaculty.this));

                }else {
                    other.setVisibility(View.GONE);
                    otherl1.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void electdata() {

        DatabaseReference d2ele = d1.child("Electrical engineering");
        d2ele.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                elelist12=new ArrayList<>();

                if(snapshot.exists()){
                    elec.setVisibility(View.VISIBLE);
                    elecl1.setVisibility(View.GONE);

                    for(DataSnapshot x:snapshot.getChildren()){
                        elelist12.add(x.getValue(Teacher12.class));
                    }

                    elec.setHasFixedSize(true);
                    elec.setLayoutManager(new LinearLayoutManager(Updatefaculty.this));
                    elec.setAdapter(new Teacheradapter(elelist12,Updatefaculty.this));

                }else {
                    elec.setVisibility(View.GONE);
                    elecl1.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void csdepdata() {

        DatabaseReference d2csd = d1.child("Computer science engineering");
        d2csd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                cslist12=new ArrayList<>();

                if(snapshot.exists()){
                    cs.setVisibility(View.VISIBLE);
                    csl1.setVisibility(View.GONE);

                    for(DataSnapshot x:snapshot.getChildren()){
                        cslist12.add(x.getValue(Teacher12.class));
                    }

                    cs.setHasFixedSize(true);
                    cs.setLayoutManager(new LinearLayoutManager(Updatefaculty.this));
                    cs.setAdapter(new Teacheradapter(cslist12,Updatefaculty.this));

                }else {
                    mathe.setVisibility(View.GONE);
                    csl1.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void mechdata() {

        DatabaseReference d2elec = d1.child("Mechanical engineering");
        d2elec.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mechlist12=new ArrayList<>();

                if(snapshot.exists()){
                    mech.setVisibility(View.VISIBLE);
                    mechl1.setVisibility(View.GONE);

                    for(DataSnapshot x:snapshot.getChildren()){
                        mechlist12.add(x.getValue(Teacher12.class));
                    }

                    mech.setHasFixedSize(true);
                    mech.setLayoutManager(new LinearLayoutManager(Updatefaculty.this));
                    mech.setAdapter(new Teacheradapter(mechlist12,Updatefaculty.this));

                }else {
                    mathe.setVisibility(View.GONE);
                    mechl1.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void mathdata() {
        DatabaseReference d2math = d1.child("Mathematicians");
       d2math.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               malist12=new ArrayList<>();

                if(snapshot.exists()){
                    mathe.setVisibility(View.VISIBLE);
                    mathel1.setVisibility(View.GONE);

                    for(DataSnapshot x:snapshot.getChildren()){
                        malist12.add(x.getValue(Teacher12.class));
                    }

                    mathe.setHasFixedSize(true);
                    mathe.setLayoutManager(new LinearLayoutManager(Updatefaculty.this));
                    mathe.setAdapter(new Teacheradapter(malist12,Updatefaculty.this));

                }else {
                    mathe.setVisibility(View.GONE);
                    mathel1.setVisibility(View.VISIBLE);
                }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }
}
