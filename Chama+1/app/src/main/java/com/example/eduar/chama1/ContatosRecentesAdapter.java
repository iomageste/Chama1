package com.example.eduar.chama1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eduar.model.Contato;
import com.example.eduar.model.User;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;

/**
 * Created by eduar on 7/19/2016.
 */
public class ContatosRecentesAdapter extends ArrayAdapter<Contato>{

    TextView userName;
    TextView userTelefone;

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

        Firebase myFirebaseRef = new Firebase("https://chama1-e883c.firebaseio.com/users");
        Query queryRef = myFirebaseRef.orderByChild("username").equalTo(contato.getContato());

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                if(user != null){
                    userName.setText(user.getNome());
                    userTelefone.setText(user.getTelefone());
                }
            }

            @Override public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override public void onCancelled(FirebaseError firebaseError) { }
        });


        // Return the completed view to render on screen
        return convertView;
    }
}
