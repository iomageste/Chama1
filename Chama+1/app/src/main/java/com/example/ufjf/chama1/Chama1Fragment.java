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
import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Chama1Fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Button buttonBuscar;
    EditText editAddres;
    Spinner spinnerChamaMais;
    Spinner spinnerTipo;
    SeekBar seekArea;
    SeekBar seekComecaEm;
    int chamaMais = 0;
    String tipo = "";
    User currentUser;
    LatLng currentLoc;
    String address = "";

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
            //Toast.makeText(getContext(),  parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
        } else if (spinner.getId() == R.id.spinnerTipo) {
            tipo = parent.getItemAtPosition(pos).toString();
            //Toast.makeText(getContext(),  parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
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

//                LatLng myLocal =
                /* Centraliza a camera */
                //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocal, 15));
                return new LatLng(loc.getLatitude(), loc.getLongitude());
//                myMarker = gMap.addMarker(new MarkerOptions().position(myLocal).title("CHUPAAAAA MUNDO!"));
//                marcadoresPeladas.put("CHUPAAAAA MUNDO!", myMarker);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
