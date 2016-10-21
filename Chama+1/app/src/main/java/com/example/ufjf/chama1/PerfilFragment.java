package com.example.ufjf.chama1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ufjf.model.User;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    EditText editUserName;
    EditText editTelefone;
    String username;
    String telefone;
    Button buttonSalvar;
    View view;
    User currentUser;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_perfil, container, false);

        currentUser = ((CustomApplication) getActivity().getApplication()).getCurrentUser();

        editUserName = (EditText) view.findViewById(R.id.textNome);
        editTelefone = (EditText) view.findViewById(R.id.textTelefone);
        buttonSalvar = (Button) view.findViewById(R.id.buttonSalvar);
        addListenerOnButton(view);

        Firebase myFirebaseRef = new Firebase("https://chama1-e883c.firebaseio.com/users");
        Query queryRef = myFirebaseRef.orderByChild("username").equalTo(currentUser.getUsername());

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                if(user != null) {
                    editUserName.setText(user.getNome());
                    editTelefone.setText(user.getTelefone());
                    username = user.getUsername();
                    telefone = user.getTelefone();
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

    public void addListenerOnButton(View view) {
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String new_name = editUserName.getText().toString();
                String new_tel = editTelefone.getText().toString();

                Firebase myFirebaseRef = new Firebase("https://chama1-e883c.firebaseio.com/");
                Firebase myref = myFirebaseRef.child("users").child(username);
                Map<String, Object> updatedUser = new HashMap<String, Object>();
                updatedUser.put("nome", new_name);
                updatedUser.put("telefone", new_tel);
                myref.updateChildren(updatedUser);

            }
        });
    }

}
