package com.example.segproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.BranchViewHolder>{
    ArrayList<BranchProfile> list;
    OnBranchListener mOnBranchListener;

    public BranchAdapter(ArrayList<BranchProfile> list, OnBranchListener onBranchListener){
        this.list = list;
        this.mOnBranchListener = onBranchListener;
    }

    public ArrayList<BranchProfile> updateData(ArrayList<BranchProfile> list){
        this.list = list;
        return this.list;
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_search_layout, parent, false);
        return new BranchViewHolder(view, mOnBranchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder holder, int position) {
        holder.address.setText(list.get(position).getWholeAddress());
        holder.phone.setText(list.get(position).getPhoneNum());
        holder.servicesNames.setText(list.get(position).getServicesNames().replaceFirst(", ", ""));
    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        }else{
            return list.size();
        }
    }

    class BranchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView address, phone, servicesNames;
        OnBranchListener onBranchListener;
        public BranchViewHolder(@NonNull View itemView, OnBranchListener onBranchListener){
            super(itemView);
            address = itemView.findViewById(R.id.searchAddress);
            phone = itemView.findViewById(R.id.searchPhoneNumber);
            servicesNames = itemView.findViewById(R.id.searchServices);
            this.onBranchListener = onBranchListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onBranchListener.onBranchCLick(getAdapterPosition());
        }
    }

    public interface OnBranchListener{
        void onBranchCLick(int position);
    }

}
