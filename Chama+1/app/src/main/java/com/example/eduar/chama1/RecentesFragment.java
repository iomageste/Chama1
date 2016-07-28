package com.example.eduar.chama1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eduar.model.Contato;
import com.example.eduar.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecentesFragment extends Fragment {

    ListView listRecentes;
    User currentUser;

    public RecentesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recentes, container, false);

        listRecentes = (ListView) view.findViewById(R.id.listRecentes);
        //currentUser = getResources().getString(R.string.current_user);
        currentUser = ((CustomApplication) getActivity().getApplication()).getCurrentUser();


        ArrayList<Contato> contatos = new ArrayList<Contato>();
        final ContatosRecentesAdapter contatosAdapter = new ContatosRecentesAdapter(getContext(), contatos);

        listRecentes.setAdapter(contatosAdapter);

        Firebase myFirebaseRef = new Firebase("https://chama1-e883c.firebaseio.com/");
        myFirebaseRef.child("contatos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                contatosAdapter.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Contato contato = postSnapshot.getValue(Contato.class);

                    if (contato != null && contato.getUsername().equals(currentUser.getUsername())) {
                        contatosAdapter.add(contato);
                    }
                }

            }

            @Override public void onCancelled(FirebaseError error) { }
        });


        return view;
    }

}
