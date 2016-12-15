package com.example.ufjf.chama1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.ufjf.model.User;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigFragment extends Fragment {

    Button buttonSalvar;
    CheckBox checkBoxNotificacao;
    SeekBar seekArea;
    User currentUser;

    private DatabaseReference mFirebaseDatabaseReference;

    public ConfigFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config, container, false);
        currentUser = ((CustomApplication) getActivity().getApplication()).getCurrentUser();

        buttonSalvar = (Button) view.findViewById(R.id.buttonSalvar);
        checkBoxNotificacao = (CheckBox) view.findViewById(R.id.checkBoxNotificacao);
        seekArea = (SeekBar) view.findViewById(R.id.seekArea);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        com.google.firebase.database.Query queryRef = mFirebaseDatabaseReference.orderByChild("username").equalTo(currentUser.getUsername());
        queryRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {

            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                if(user != null) {
                    checkBoxNotificacao.setChecked(user.isNotificacoes());
                    seekArea.setProgress(user.getAreaBusca());
                }
            }
            @Override public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) { }
            @Override public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {}
            @Override public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {}
            @Override public void onCancelled(DatabaseError databaseError) {}
        });

        addListenerOnButton(view);

        return view;
    }

    public void addListenerOnButton(View view) {

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String dados = "Notificacao?"+(checkBoxNotificacao.isChecked()?"True":"False");
                dados += ", Area:"+seekArea.getProgress();

                Map<String, Object> updatedUser = new HashMap<String, Object>();
                updatedUser.put("notificacoes", checkBoxNotificacao.isChecked());
                updatedUser.put("areaBusca", seekArea.getProgress());
                mFirebaseDatabaseReference.child("users").child(currentUser.getUsername()).updateChildren(updatedUser);

                Toast.makeText(getContext(), dados, Toast.LENGTH_SHORT).show();
                currentUser.setAreaBusca(seekArea.getProgress());
                currentUser.setNotificacoes(checkBoxNotificacao.isChecked());
            }
        });

    }

}
