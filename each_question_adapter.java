package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class each_question_adapter extends FirebaseRecyclerAdapter<model_posting_question,each_question_adapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    String coursecode;
    String coursename;
    String courseinstructor;
    String identifier;
    String whichactivity;
    public each_question_adapter(@NonNull FirebaseRecyclerOptions<model_posting_question> options, Context context, String coursecode,String coursename,String identifier,String courseinstrutor,String whichactivity) {
        super(options);
        this.context=context;
        this.coursecode=coursecode;
        this.coursename=coursename;
        this.courseinstructor=courseinstrutor;
        this.identifier=identifier;
        this.whichactivity=whichactivity;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull model_posting_question model) {
        holder.who_created_course.setText("by "+model.getEmail());
        holder.question.setText(model.getQuestion());
        holder.heading.setText(model.getHeading());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, inside_question.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("email",holder.who_created_course.getText().toString());
                intent.putExtra("question",holder.question.getText().toString());
                intent.putExtra("heading",holder.heading.getText().toString());
                intent.putExtra("course_code",coursecode);
                intent.putExtra("course_name",coursename);
                intent.putExtra("course_teacher",courseinstructor);
                intent.putExtra("identifier",identifier);
                intent.putExtra("dashboard_or_mycourses",whichactivity);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_question,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView who_created_course,question,heading;
        LinearLayout layout;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            who_created_course = (TextView)itemView.findViewById(R.id.who_created_question);
            question = (TextView)itemView.findViewById(R.id.question_content);
            heading=(TextView) itemView.findViewById(R.id.heading_for_each_question);
            layout=(LinearLayout) itemView.findViewById(R.id.eachitemlayout);
        }
    }
}
