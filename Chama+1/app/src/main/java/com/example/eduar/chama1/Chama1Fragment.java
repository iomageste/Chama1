package com.example.eduar.chama1;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduar.model.Busca;
import com.firebase.client.Firebase;
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
public class Chama1Fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Button buttonBuscar;
    Spinner spinnerChamaMais;
    Spinner spinnerTipo;
    SeekBar seekArea;
    SeekBar seekComecaEm;
    int chamaMais = 0;
    String tipo = "";

    public Chama1Fragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chama1, container, false);

        buttonBuscar = (Button) view.findViewById(R.id.buttonBuscar);
        spinnerChamaMais = (Spinner) view.findViewById(R.id.spinnerChamaMais);
        ArrayAdapter<CharSequence> adapterChamaMais = ArrayAdapter.createFromResource(getContext(),
                R.array.chamaMais_array, android.R.layout.simple_spinner_item);

        adapterChamaMais.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChamaMais.setAdapter(adapterChamaMais);
        spinnerChamaMais.setOnItemSelectedListener(this);

        spinnerTipo = (Spinner) view.findViewById(R.id.spinnerTipo);
        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(getContext(),
                R.array.tipo_array, android.R.layout.simple_spinner_item);

        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapterTipo);
        spinnerTipo.setOnItemSelectedListener(this);

        //editTipo = (EditText) view.findViewById(R.id.textTipo);
        seekArea = (SeekBar) view.findViewById(R.id.seekArea);
        seekComecaEm = (SeekBar) view.findViewById(R.id.seekComecaEm);

        addListenerOnButton(view);

        return view;
    }

    public void addListenerOnButton(View view) {


        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String dados = "Chama+"+chamaMais+", Tipo:"+tipo;
                dados += ", Area:"+seekArea.getProgress();
                dados += ", ComecaEm:"+seekComecaEm.getProgress();
                Busca busca = new Busca("eduardo", chamaMais, tipo, seekArea.getProgress(), -21.751809, -43.353663);
                //Toast.makeText(getContext(), dados, Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Busca iniciada...", Toast.LENGTH_SHORT).show();
                Firebase myFirebaseRef = new Firebase("https://chama1-e883c.firebaseio.com/");
                myFirebaseRef.child("buscas").child("eduardo").setValue(busca);

                /*getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.viewPager, new Chama1MapaFragment())
                        .commit();*/
                ViewPager view = (ViewPager) getActivity().findViewById(R.id.viewPager);
                view.setCurrentItem(4, true);
                //ViewPagerAdapter viewAdapter = (ViewPagerAdapter) view.getAdapter();
                //viewAdapter.set
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinnerChamaMais)
        {
            chamaMais =  Integer.valueOf(parent.getItemAtPosition(pos).toString());
            //Toast.makeText(getContext(),  parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
        }
        else if(spinner.getId() == R.id.spinnerTipo)
        {
            tipo = parent.getItemAtPosition(pos).toString();
            //Toast.makeText(getContext(),  parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
        }


    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }



}
