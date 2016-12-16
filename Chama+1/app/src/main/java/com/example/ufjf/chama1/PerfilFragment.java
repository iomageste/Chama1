package com.example.ufjf.chama1;


import android.app.Application;
import android.content.Intent;
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
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    Button buttonLogout;
    View view;
    User currentUser;

    private DatabaseReference mFirebaseDatabaseReference;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_perfil, container, false);

        currentUser = ((CustomApplication) getActivity().getApplication()).getCurrentUser();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        editUserName = (EditText) view.findViewById(R.id.textNome);
        editTelefone = (EditText) view.findViewById(R.id.textTelefone);
        buttonSalvar = (Button) view.findViewById(R.id.buttonSalvar);
        buttonLogout = (Button) view.findViewById(R.id.buttonLogout);
        addListenerOnButton(view);

        editUserName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        editTelefone.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        return view;
    }

    public void addListenerOnButton(View view) {
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*String new_name = editUserName.getText().toString();
                String new_tel = editTelefone.getText().toString();

                Map<String, Object> updatedUser = new HashMap<String, Object>();
                updatedUser.put("nome", new_name);
                updatedUser.put("telefone", new_tel);
                mFirebaseDatabaseReference.child("users").child(username).updateChildren(updatedUser);*/
                MyFirebaseMessagingService svc = new MyFirebaseMessagingService();
                svc.sendNotification("MinhaMensagem", getActivity(), getContext());

            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Auth.GoogleSignInApi.signOut(((CustomApplication)getActivity().getApplication()).getmGoogleApiClient());
                FirebaseAuth.getInstance().signOut();
                ((CustomApplication)getActivity().getApplication()).setCurrentUser(null);
                Intent myIntent = new Intent(getActivity(),SignInActivity.class);
                getActivity().startActivity(myIntent);
            }
        });
    }

}
