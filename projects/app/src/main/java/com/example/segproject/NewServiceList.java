package com.example.segproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class NewServiceList extends ArrayAdapter<NewService> {
    private Activity context;
    List<NewService> services;

    public NewServiceList (Activity context, List<NewService> services){
        super(context, R.layout.layout_services_list, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.layout_services_list, null, true);

        TextView textViewName = (TextView) convertView.findViewById(R.id.nameDeleteLayoutTextView);
        TextView textViewRate = (TextView) convertView.findViewById(R.id.rateDeleteLayoutTextView);

        NewService ns = services.get(position);

        textViewName.setText(ns.getName());
        //t//

        return convertView;
    }
}
