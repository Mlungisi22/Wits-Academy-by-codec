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

public class Student_cart_adapter extends FirebaseRecyclerAdapter<module, Student_cart_adapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public Student_cart_adapter(@NonNull FirebaseRecyclerOptions<module> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull module model) {
        holder.name.setText(model.getModName());
        holder.course.setText(model.getModCode());
        holder.email.setText(model.getModTeacher());
        holder.ratingBar.setRating(model.getRatingNum());

        Random rand = new Random();
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
                Intent intent=new Intent(context, StudentCourse_content.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("course_name",holder.name.getText().toString());
                intent.putExtra("course_teacher",holder.email.getText().toString());
                intent.putExtra("course_code",holder.course.getText().toString());
                intent.putExtra("dashboard_or_mycourses","subscribed");
                intent.putExtra("course_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                context.startActivity(intent);
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
