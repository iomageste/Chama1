package com.example.eduar.chama1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.eduar.model.Solicitacao;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SolicitacoesFragment extends Fragment {

    ListView listSolicitacoes;

    public SolicitacoesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solicitacoes, container, false);

        listSolicitacoes =  (ListView) view.findViewById(R.id.listSolicitacoes);

        ArrayList<Solicitacao> solicitacoes = new ArrayList<>();
        SolicitacoesAdapter adapter = new SolicitacoesAdapter(getContext(), solicitacoes);

        listSolicitacoes.setAdapter(adapter);

        Solicitacao solicitacao1 = new Solicitacao(1, 2);
        Solicitacao solicitacao2 = new Solicitacao(1, 3);

        adapter.add(solicitacao1);
        adapter.add(solicitacao2);

        return view;
    }

}
