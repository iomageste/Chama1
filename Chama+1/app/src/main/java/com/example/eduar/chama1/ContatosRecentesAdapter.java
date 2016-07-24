package com.example.eduar.chama1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eduar.model.User;
import java.util.ArrayList;

/**
 * Created by eduar on 7/19/2016.
 */
public class ContatosRecentesAdapter extends ArrayAdapter<User>{

    public ContatosRecentesAdapter(Context context, ArrayList<User> users){
        super(context,0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, parent, false);
        }
        // Lookup view for data population
        TextView userName = (TextView) convertView.findViewById(R.id.userName);
        TextView userTelefone = (TextView) convertView.findViewById(R.id.userTelefone);
        // Populate the data into the template view using the data object
        userName.setText(user.getNome());
        userTelefone.setText(user.getTelefone());
        // Return the completed view to render on screen
        return convertView;
    }
}
