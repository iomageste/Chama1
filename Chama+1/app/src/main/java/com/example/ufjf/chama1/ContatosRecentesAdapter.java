package com.example.ufjf.chama1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ufjf.model.Contato;
import com.example.ufjf.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by eduar on 7/19/2016.
 */
public class ContatosRecentesAdapter extends ArrayAdapter<Contato>{

    TextView userName;
    TextView userTelefone;

    private DatabaseReference mFirebaseDatabaseReference;

    public ContatosRecentesAdapter(Context context, ArrayList<Contato> contatos){
        super(context,0, contatos);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Contato contato = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, parent, false);
        }
        // Lookup view for data population
        userName = (TextView) convertView.findViewById(R.id.userName);
        userTelefone = (TextView) convertView.findViewById(R.id.userTelefone);
        // Populate the data into the template view using the data object
        userName.setText(contato.getContato());
        userTelefone.setText("Carregando...");
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference queryRef = mFirebaseDatabaseReference.child("users").child(contato.getContato());

        queryRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user != null){
                    userName.setText(user.getUsername());
                    userTelefone.setText(user.getTelefone());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
