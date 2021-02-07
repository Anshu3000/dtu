package com.example.collegeapp.faculty;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collegeapp.R;

import java.util.List;


public class Teacheradapter extends RecyclerView.Adapter<Teacheradapter.teacherviewholder> {

      private List<Teacher12> l1;
      private Context c1;

    public Teacheradapter(List<Teacher12> l1, Context c1) {
        this.l1 = l1;
        this.c1 = c1;
    }

    @NonNull
    @Override
    public teacherviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View v1= LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_facultyrecycle,parent,false);
        return new teacherviewholder(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull teacherviewholder holder, int position) {

        Teacher12 t12=l1.get(position);
        holder.name.setText(t12.getName());
        holder.post.setText(t12.getCategory());
        holder.email.setText(t12.getEmail());

         try {
             Glide.with(holder.itemView).load(t12.getImageurl()).into(holder.tea1);
         }catch (Exception e){
             Log.d("glideerror", "onBindViewHolder: "+e.getMessage());
         }
         holder.updateinfo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(c1,"update....",Toast.LENGTH_SHORT).show();
             }
         });
    }

    @Override
    public int getItemCount() {
        return l1.size();
    }

      public static class teacherviewholder extends RecyclerView.ViewHolder{

         private   TextView name,post,email;
          private Button updateinfo;
          private ImageView tea1;

          public teacherviewholder(@NonNull View itemView) {
              super(itemView);
              name=itemView.findViewById(R.id.teachernamerec);
              post=itemView.findViewById(R.id.teacherpostrec);
              email=itemView.findViewById(R.id.teacheremailrec);
              updateinfo=itemView.findViewById(R.id.updatebuttonrec);
              tea1=itemView.findViewById(R.id.teacherimagerec);
          }

      }
}
