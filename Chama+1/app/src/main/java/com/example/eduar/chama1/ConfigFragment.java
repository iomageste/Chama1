package com.example.eduar.chama1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigFragment extends Fragment {

    Button buttonSalvar;
    CheckBox checkBoxNotificacao;
    SeekBar seekArea;

    public ConfigFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config, container, false);

        buttonSalvar = (Button) view.findViewById(R.id.buttonSalvar);
        checkBoxNotificacao = (CheckBox) view.findViewById(R.id.checkBoxNotificacao);
        seekArea = (SeekBar) view.findViewById(R.id.seekArea);

        addListenerOnButton(view);

        return view;
    }

    public void addListenerOnButton(View view) {

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String dados = "Notificacao?"+(checkBoxNotificacao.isChecked()?"True":"False");
                dados += ", Area:"+seekArea.getProgress();
                Toast.makeText(getContext(), dados, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
