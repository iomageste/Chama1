package com.example.ufjf.chama1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ufjf.model.Solicitacao;
import com.example.ufjf.model.User;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by eduar on 7/19/2016.
 */
public class SolicitacoesAdapter  extends ArrayAdapter<Solicitacao> {

    TextView userName;
    TextView userTelefone;

    private DatabaseReference mFirebaseDatabaseReference;

    public SolicitacoesAdapter(Context context, ArrayList<Solicitacao> solicitacoes){
        super(context,0, solicitacoes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Solicitacao solicitacao = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, parent, false);
        }

        userName = (TextView) convertView.findViewById(R.id.userName);
        userTelefone = (TextView) convertView.findViewById(R.id.userTelefone);
        // Populate the data into the template view using the data object
        userName.setText(String.valueOf(solicitacao.getUsername_busca()));
        String pendente = (solicitacao.isAprovado() ? "Solicitação aprovada!": "Solicitação pendente...");
        userTelefone.setText(pendente);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        if(solicitacao.isAprovado()){
            com.google.firebase.database.Query queryRef = mFirebaseDatabaseReference.child("users").orderByChild("username").equalTo(solicitacao.getUsername_busca());

            queryRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
                @Override
                public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                    User user = dataSnapshot.getValue(User.class);
                    if(user != null){
                        userName.setText(user.getUsername()+" "+user.getTelefone());
                        userTelefone.setText("Solicitação aprovada!");
                    }
                }

                @Override public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {}
                @Override public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {}
                @Override public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {}
                @Override public void onCancelled(DatabaseError databaseError) {}
            });
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
