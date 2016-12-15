package com.example.ufjf.chama1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ufjf.model.Solicitacao;
import com.example.ufjf.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SolicitacoesFragment extends Fragment {

    ListView listSolicitacoes;
    SolicitacoesAdapter adapter;
    User currentUser;

    private DatabaseReference mFirebaseDatabaseReference;

    public SolicitacoesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solicitacoes, container, false);
        currentUser = ((CustomApplication) getActivity().getApplication()).getCurrentUser();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        listSolicitacoes =  (ListView) view.findViewById(R.id.listSolicitacoes);

        ArrayList<Solicitacao> solicitacoes = new ArrayList<>();
        adapter = new SolicitacoesAdapter(getContext(), solicitacoes);

        listSolicitacoes.setAdapter(adapter);
        mFirebaseDatabaseReference.child("solicitacoes").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                adapter.clear();
                for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Solicitacao solicitacao = postSnapshot.getValue(Solicitacao.class);

                    if (solicitacao != null && solicitacao.getSolicitante_username().equals(currentUser.getUsername())) {
                        adapter.add(solicitacao);
                    }
                }
            }

            @Override public void onCancelled(DatabaseError databaseError) { }
        });


        return view;
    }

}
