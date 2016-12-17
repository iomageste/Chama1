package com.example.ufjf.chama1;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ufjf.model.Busca;
import com.example.ufjf.model.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class Chama1Fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Button buttonBuscar;
    EditText editAddres;
    Spinner spinnerChamaMais;
    Spinner spinnerTipo;
    SeekBar seekArea;
    int chamaMais = 0;
    User currentUser;
    LatLng currentLoc;
    String address = "";
    String tipo = "";

    private DatabaseReference mFirebaseDatabaseReference;

    public Chama1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chama1, container, false);
        currentUser = ((CustomApplication) getActivity().getApplication()).getCurrentUser();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        buttonBuscar = (Button) view.findViewById(R.id.buttonBuscar);
        spinnerChamaMais = (Spinner) view.findViewById(R.id.spinnerChamaMais);
        ArrayAdapter<CharSequence> adapterChamaMais = ArrayAdapter.createFromResource(getContext(),
                R.array.chamaMais_array, android.R.layout.simple_spinner_item);

        adapterChamaMais.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChamaMais.setAdapter(adapterChamaMais);
        spinnerChamaMais.setOnItemSelectedListener(this);
        editAddres = (EditText) view.findViewById(R.id.address);

        spinnerTipo = (Spinner) view.findViewById(R.id.spinnerTipo);
        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(getContext(),
                R.array.tipo_array, android.R.layout.simple_spinner_item);

        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapterTipo);
        spinnerTipo.setOnItemSelectedListener(this);

        seekArea = (SeekBar) view.findViewById(R.id.seekArea);
        seekArea.setProgress(50);
        addListenerOnButton(view);

        return view;
    }

    public void addListenerOnButton(View view) {


        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                address = editAddres.getText().toString();

                currentLoc = searchAddress(address);
                if (currentLoc == null) {
                    currentLoc = ((CustomApplication) getActivity().getApplication()).getCurrentLocation();
                }
                Busca busca = new Busca(currentUser.getUsername(), chamaMais, tipo, seekArea.getProgress(), currentLoc.latitude, currentLoc.longitude, address);

                Toast.makeText(getContext(), "Busca iniciada..." + address, Toast.LENGTH_SHORT).show();
                mFirebaseDatabaseReference.child("buscas").child(currentUser.getUsername()).setValue(busca);

                Bundle args = new Bundle();
                int areaBusca = seekArea.getProgress() * 1000; // quilometros
                args.putString("AreaBusca", String.valueOf(areaBusca));
                args.putDouble("Lat", currentLoc.latitude);
                args.putDouble("Lng", currentLoc.longitude);
                args.putString("Chama+", String.valueOf(chamaMais));
                Fragment fragment = new Chama1MapaFragment();
                fragment.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.chama1, fragment, "chama1mapafragment").addToBackStack("");
                fragmentTransaction.commit();


            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.spinnerChamaMais) {
            chamaMais = Integer.valueOf(parent.getItemAtPosition(pos).toString());
        } else if (spinner.getId() == R.id.spinnerTipo) {
            tipo = parent.getItemAtPosition(pos).toString();
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public LatLng searchAddress(String strAddress) {
        Geocoder coder = new Geocoder(getContext());
        List<Address> address;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (!address.isEmpty()) {
                Address loc = address.get(0);
                loc.getLatitude();
                loc.getLongitude();
                return new LatLng(loc.getLatitude(), loc.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
