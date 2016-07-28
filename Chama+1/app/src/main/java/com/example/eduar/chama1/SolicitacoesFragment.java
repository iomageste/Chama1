package com.example.eduar.chama1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eduar.model.Busca;
import com.example.eduar.model.Solicitacao;
import com.example.eduar.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SolicitacoesFragment extends Fragment {

    ListView listSolicitacoes;
    SolicitacoesAdapter adapter;
    User currentUser;

    public SolicitacoesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solicitacoes, container, false);
        //currentUser = getResources().getString(R.string.current_user);
        currentUser = ((CustomApplication) getActivity().getApplication()).getCurrentUser();

        listSolicitacoes =  (ListView) view.findViewById(R.id.listSolicitacoes);

        ArrayList<Solicitacao> solicitacoes = new ArrayList<>();
        adapter = new SolicitacoesAdapter(getContext(), solicitacoes);

        listSolicitacoes.setAdapter(adapter);
        Firebase myFirebaseRef = new Firebase("https://chama1-e883c.firebaseio.com/");
        myFirebaseRef.child("solicitacoes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    adapter.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Solicitacao solicitacao = postSnapshot.getValue(Solicitacao.class);

                        if (solicitacao != null && solicitacao.getSolicitante_username().equals(currentUser.getUsername())) {
                            adapter.add(solicitacao);
                        }
                    }

                }

                @Override public void onCancelled(FirebaseError error) { }
        });

        return view;
    }

}
