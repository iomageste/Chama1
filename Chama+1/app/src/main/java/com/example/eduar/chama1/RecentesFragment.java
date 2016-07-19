package com.example.eduar.chama1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eduar.model.User;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecentesFragment extends Fragment {

    ListView listRecentes;

    public RecentesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recentes, container, false);

       listRecentes = (ListView) view.findViewById(R.id.listRecentes);


        ArrayList<User> contatos = new ArrayList<User>();
        ContatosRecentesAdapter contatosAdapter = new ContatosRecentesAdapter(getContext(), contatos);

        listRecentes.setAdapter(contatosAdapter);

        User user1 = new User("Pedro da Silva", "(32)3232-3232");
        User user2 = new User("João Antônio", "(32)3131-3131");
        User user3 = new User("Felipe Rezende", "(32)3030-3030");

        contatosAdapter.add(user1);
        contatosAdapter.add(user2);
        contatosAdapter.add(user3);

        return view;
    }

}
