package com.example.login;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class Adapter_View_AllCourses extends FirebaseRecyclerAdapter<module, Adapter_View_AllCourses.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    String which;
    public Adapter_View_AllCourses(@NonNull FirebaseRecyclerOptions<module> options, Context context,String which) {
        super(options);
        this.context=context;
        this.which=which;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull module model) {
        Random rand = new Random();

        if(which=="mycourses"){
            holder.name.setText(model.getModName());
            holder.course.setText(model.getModCode());
            holder.email.setText(model.getModTeacher());
            holder.ratingBar.setRating(rand.nextInt((5 - 1) + 1) + 1);

        }else{
            holder.name.setText(model.getModCode());
            holder.course.setText(model.getModName());
            holder.email.setText(model.getModTeacher());
            holder.ratingBar.setRating(rand.nextInt((5 - 1) + 1) + 1);
        }


        int randomNum = rand.nextInt((7 - 0) + 1) + 0;

        switch (randomNum){
            case 0:
                holder.imageView.setImageResource(R.drawable.blue);
                break;
            case 1:
                holder.imageView.setImageResource(R.drawable.darkblue);
                break;
            case 2:
                holder.imageView.setImageResource(R.drawable.green);
                break;
            case 3:
                holder.imageView.setImageResource(R.drawable.lightgreen);
                break;
            case 4:
                holder.imageView.setImageResource(R.drawable.maroon);
                break;
            case 5:
                holder.imageView.setImageResource(R.drawable.purple);
                break;
            case 6:
                holder.imageView.setImageResource(R.drawable.red);
                break;
            case 7:
                holder.imageView.setImageResource(R.drawable.yellow);
                break;
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(which=="edit_course"){
                    Intent intent=new Intent(context,TeacherCourse_content.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("course_name",holder.name.getText().toString());
                    intent.putExtra("course_teacher",holder.email.getText().toString());
                    intent.putExtra("course_code",holder.course.getText().toString());
                    intent.putExtra("course_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    context.startActivity(intent);
                }else{
                    Intent intent=new Intent(context, StudentCourse_content.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("course_name",holder.name.getText().toString());
                    intent.putExtra("course_teacher",holder.email.getText().toString());
                    intent.putExtra("course_code",holder.course.getText().toString());
                    intent.putExtra("dashboard_or_mycourses","subscribe");
                    intent.putExtra("course_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    context.startActivity(intent);
                }
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_rc,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView name,course,email;
        ImageView imageView;
        RatingBar ratingBar;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);
            name = (TextView)itemView.findViewById(R.id.nametext2);
            course = (TextView)itemView.findViewById(R.id.coursetext2);
            email = (TextView)itemView.findViewById(R.id.emailtext2);
            imageView=(ImageView)itemView.findViewById(R.id.image_view);
        }
    }
}
