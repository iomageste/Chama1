package com.example.eduar.chama1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduar.model.Solicitacao;
import com.example.eduar.model.User;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;

/**
 * Created by eduar on 7/19/2016.
 */
public class SolicitacoesAdapter  extends ArrayAdapter<Solicitacao> {

    TextView userName;
    TextView userTelefone;

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

        if(solicitacao.isAprovado()){
            Toast.makeText(getContext(), "Buscando usuario", Toast.LENGTH_SHORT).show();
            Firebase myFirebaseRef = new Firebase("https://chama1-e883c.firebaseio.com/users");
            Query queryRef = myFirebaseRef.orderByChild("username").equalTo("mageste");

            queryRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                    User user = snapshot.getValue(User.class);
                    if(user != null){
                        userName.setText(user.getNome()+" "+user.getTelefone());
                        userTelefone.setText("Solicitação aprovada!");
                    }
                }

                @Override public void onChildRemoved(DataSnapshot dataSnapshot) { }
                @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
                @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
                @Override public void onCancelled(FirebaseError firebaseError) { }
            });
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
