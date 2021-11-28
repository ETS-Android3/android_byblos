package com.example.segproject;

import android.app.Activity;
import android.app.Service;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ServiceRequestList extends ArrayAdapter<ServiceRequest> {
    private Activity context;
    List<ServiceRequest> requests;

    public ServiceRequestList (Activity context, List<ServiceRequest> requests){
        super(context, R.layout.request_list_layout, requests);
        this.context = context;
        this.requests = requests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.request_list_layout, null, true);

        TextView textViewName = (TextView) convertView.findViewById(R.id.customerName);
        TextView textViewRate = (TextView) convertView.findViewById(R.id.serviceName);

        ServiceRequest sr = requests.get(position);

        textViewName.setText(sr.getUsername());
        textViewRate.setText(sr.getServiceName());

        return convertView;
    }
}

