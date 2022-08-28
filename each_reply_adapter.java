package com.example.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class each_reply_adapter extends FirebaseRecyclerAdapter<model_reply_question,each_reply_adapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public each_reply_adapter(@NonNull FirebaseRecyclerOptions<model_reply_question> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull model_reply_question model) {
        holder.who_created_reply.setText(model.getEmail());
        holder.reply_content.setText(model.getAnswer());
        holder.heading.setText("!-- replying ----!");

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_question,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView who_created_reply,reply_content,heading;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            who_created_reply = (TextView)itemView.findViewById(R.id.who_created_question);
            reply_content = (TextView)itemView.findViewById(R.id.question_content);
            heading = (TextView)itemView.findViewById(R.id.heading_for_each_question);
        }
    }
}
