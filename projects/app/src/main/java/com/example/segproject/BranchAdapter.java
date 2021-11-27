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

    public BranchAdapter(ArrayList<BranchProfile> list){
        this.list = list;
    }

    public ArrayList<BranchProfile> updateData(ArrayList<BranchProfile> list){
        this.list = list;
        return this.list;
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_search_layout, parent, false);
        return new BranchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder holder, int position) {
        holder.address.setText(list.get(position).getWholeAddress());
        holder.phone.setText(list.get(position).getPhoneNum());
        holder.services.setText(list.get(position).getServices());

    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        }else{
            return list.size();
        }
    }

    class BranchViewHolder extends RecyclerView.ViewHolder{
        TextView address, phone, services;
        public BranchViewHolder(@NonNull View itemView){
            super(itemView);
            address = itemView.findViewById(R.id.searchAddress);
            phone = itemView.findViewById(R.id.searchPhoneNumber);
            services = itemView.findViewById(R.id.searchServices);

        }
    }

}
