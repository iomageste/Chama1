package com.example.eduar.chama1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eduar.model.User;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    EditText userName;
    EditText telefone;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        userName = (EditText) view.findViewById(R.id.textNome);
        telefone = (EditText) view.findViewById(R.id.textTelefone);

        Firebase myFirebaseRef = new Firebase("https://chama1-e883c.firebaseio.com/");
        Query queryRef = myFirebaseRef.orderByChild("nome").equalTo("eduardo");

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                if(user != null) {
                    userName.setText(user.getUsername());
                    telefone.setText(user.getTelefone());
                }
            }

            @Override public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override public void onCancelled(FirebaseError firebaseError) { }
        });



        // Inflate the layout for this fragment
        return view;
    }

}
