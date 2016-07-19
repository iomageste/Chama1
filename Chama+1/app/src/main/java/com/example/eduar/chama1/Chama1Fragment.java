package com.example.eduar.chama1;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class Chama1Fragment extends Fragment{

    Button buttonBuscar;
    EditText editChamaMais;
    EditText editTipo;
    SeekBar seekArea;
    SeekBar seekComecaEm;

    public Chama1Fragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chama1, container, false);

        buttonBuscar = (Button) view.findViewById(R.id.buttonBuscar);
        editChamaMais = (EditText) view.findViewById(R.id.textChamaMais);
        editTipo = (EditText) view.findViewById(R.id.textTipo);
        seekArea = (SeekBar) view.findViewById(R.id.seekArea);
        seekComecaEm = (SeekBar) view.findViewById(R.id.seekComecaEm);

        addListenerOnButton(view);

        return view;
    }

    public void addListenerOnButton(View view) {


        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String dados = "Chama+"+editChamaMais.getText().toString();
                dados += ", Tipo:"+editTipo.getText().toString();
                dados += ", Area:"+seekArea.getProgress();
                dados += ", ComecaEm:"+seekComecaEm.getProgress();
                Toast.makeText(getContext(), dados, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
