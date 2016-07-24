package com.example.eduar.chama1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eduar.model.Solicitacao;
import com.example.eduar.model.User;
import java.util.ArrayList;

/**
 * Created by eduar on 7/19/2016.
 */
public class SolicitacoesAdapter  extends ArrayAdapter<Solicitacao> {

    public SolicitacoesAdapter(Context context, ArrayList<Solicitacao> solicitacoes){
        super(context,0, solicitacoes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Solicitacao solicitacao = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, parent, false);
        }

        TextView userName = (TextView) convertView.findViewById(R.id.userName);
        TextView userTelefone = (TextView) convertView.findViewById(R.id.userTelefone);
        // Populate the data into the template view using the data object
        userName.setText(String.valueOf(solicitacao.getBusca_user_id()));
        String pendente = (solicitacao.isAprovado() ? "Solicitação aprovada!": "Solicitação pendente...");
        userTelefone.setText(pendente);
        // Return the completed view to render on screen
        return convertView;
    }
}
