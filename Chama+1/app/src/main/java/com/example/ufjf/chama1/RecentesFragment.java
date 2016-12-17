package com.example.ufjf.chama1;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ufjf.model.Contato;
import com.example.ufjf.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecentesFragment extends Fragment {

    ListView listRecentes;
    User currentUser;

    private DatabaseReference mFirebaseDatabaseReference;

    public RecentesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recentes, container, false);

        listRecentes = (ListView) view.findViewById(R.id.listRecentes);
        currentUser = ((CustomApplication) getActivity().getApplication()).getCurrentUser();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        ArrayList<Contato> contatos = new ArrayList<Contato>();
        List<User> userList = ((CustomApplication)getActivity().getApplication()).getUserList();
        final ContatosRecentesAdapter contatosAdapter = new ContatosRecentesAdapter(getContext(), contatos, userList);

        listRecentes.setAdapter(contatosAdapter);

        mFirebaseDatabaseReference.child("contatos").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                contatosAdapter.clear();
                for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Contato contato = postSnapshot.getValue(Contato.class);
                    if (contato != null && contato.getUsername().equals(currentUser.getUsername())) {
                        contatosAdapter.add(contato);
                    }
                }
            }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });


        return view;
    }

}
