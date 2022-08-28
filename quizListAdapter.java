package com.example.login;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class quizListAdapter extends FirebaseRecyclerAdapter<quizName, quizListAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    String which;
    String coursecode;
    public quizListAdapter(@NonNull FirebaseRecyclerOptions<quizName> options, Context context, String which, String coursecode) {
        super(options);
        this.context=context;
        this.which=which;
        this.coursecode=coursecode;
    }
    //private int count=0;
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull quizName model) {


        holder.quizName.setText(model.getQuizname());
        holder.quizName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //take you to Quiz_preview class
                Intent intent = new Intent(context, Quiz_Preview.class);
                intent.putExtra("course_code",coursecode );
                intent.putExtra("quiz_name", model.getQuizname());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quizmodel,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView quizName;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            quizName = (TextView)itemView.findViewById(R.id.quizNameOnList);

        }
    }
}


