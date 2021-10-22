package com.example.segproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class UserList extends ArrayAdapter<User> {
    private Activity context;
    List<User> users;

    public UserList (Activity context, List<User> users){
        super(context, R.layout.layout_user_list, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewUser = inflater.inflate(R.layout.layout_user_list, null, true);

        TextView textViewUsername = (TextView) listViewUser.findViewById(R.id.usernameDeleteLayoutTextView);
        TextView textViewEmail = (TextView) listViewUser.findViewById(R.id.emailDeleteLayoutTextView);

        User user = users.get(position);

        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());

        return listViewUser;
    }
}
