package com.example.sendsms_with_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Context context;
    String[] nums;

    public ContactAdapter(Context context, String[] nums) {
        this.context = context;
        this.nums = nums;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText numberPhone;
        EditText multiline;

        TextView numContact;
        ConstraintLayout consLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numContact = itemView.findViewById(R.id.textView);
            consLayout = itemView.findViewById(R.id.conslay);


            consLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.contact_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.numContact.setText(nums[position]);
    }

    @Override
    public int getItemCount() {
        return nums.length;
    }


}
