package com.example.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class All_pdf_adapter extends FirebaseRecyclerAdapter<uploadpdf, All_pdf_adapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    boolean canDeleteItem;
    Intent intent;
    public All_pdf_adapter(@NonNull FirebaseRecyclerOptions<uploadpdf> options, Context context, boolean canDeleteItem) {
        super(options);
        this.context=context;
        this.canDeleteItem = canDeleteItem;
    }
    public All_pdf_adapter(@NonNull FirebaseRecyclerOptions<uploadpdf> options, Context context,Intent intent, boolean canDeleteItem) {
        super(options);
        this.context=context;
        this.canDeleteItem = canDeleteItem;
        this.intent = intent;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull uploadpdf model) {
        holder.pdfname.setText(model.getPdfname());
        holder.pdfname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToURL(model.getPdfurl());
            }
        });
        holder.pdf_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL(model.getPdfurl());
            }
        });
        if (canDeleteItem)
        {
            holder.delete_pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String course_code = intent.getStringExtra("course_name");
                    String pdf_name = model.getPdfname();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Query applesQuery = ref.child("Course Material").child(course_code).child(pdf_name);

                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("Delete pdf", "onCancelled", databaseError.toException());
                        }
                    });
                }
            });
        }

    }

    private void delete_content()
    {

    }


    private void goToURL(String URI)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("*/*");
        intent.setData(Uri.parse(URI));
        context.startActivity(intent);
        context.startActivity(intent);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (canDeleteItem) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_course_pdf,parent,false);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_enrolled_course_pdf,parent,false);
        }

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView pdfname;
        ImageView pdf_image;
        ImageView delete_pdf = null;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            pdf_image = (ImageView) itemView.findViewById(R.id.dummy_image);
            if (canDeleteItem) {
                pdfname = (TextView) itemView.findViewById(R.id.teacher_course_pdfname);
                delete_pdf = (ImageView) itemView.findViewById(R.id.teacher_course_pdf_delete);
            }
            else {
                pdfname = (TextView) itemView.findViewById(R.id.student_enrolled_course_pdfname);
            }
        }
    }
}
