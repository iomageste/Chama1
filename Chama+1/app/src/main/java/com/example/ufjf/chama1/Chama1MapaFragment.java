package com.example.ufjf.chama1;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ufjf.model.Contato;
import com.example.ufjf.model.Solicitacao;
import com.example.ufjf.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chama1MapaFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private Map<String, Marker> marcadoresPeladeiros;
    LatLng currentLoc;
    User currentUser;
    int areaBusca;
    int usuariosFaltando;

    private MapView gMapView;
    private GoogleMap gMap = null;

    private DatabaseReference mFirebaseDatabaseReference;


    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View mContents;

        CustomInfoWindowAdapter(Bundle savedInstanceState) {
            mContents = getLayoutInflater(savedInstanceState).inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                SpannableString titleText = new SpannableString("MARKER TITULO");
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("MARKER TITULO == NULL");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString("SNIPPET TEXTO");
                snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("SNIPPET TEXTO == NULL");
            }
        }

    }


    public Chama1MapaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peladas, container, false);

        areaBusca = Integer.parseInt(getArguments().getString("AreaBusca"));
        usuariosFaltando = Integer.parseInt(getArguments().getString("Chama+"));
        currentUser = ((CustomApplication) getActivity().getApplication()).getCurrentUser();
        marcadoresPeladeiros = new HashMap<String, Marker>();
        Double lat = getArguments().getDouble("Lat");
        Double lng = getArguments().getDouble("Lng");
        currentLoc = new LatLng(lat, lng);

        gMapView = (MapView) view.findViewById(R.id.map);
        gMapView.onCreate(savedInstanceState);
        gMapView.getMapAsync(this);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabaseReference.child("solicitacoes").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Solicitacao solicitacao = postSnapshot.getValue(Solicitacao.class);
                    if (solicitacao != null
                            && !solicitacao.getSolicitante_username().equals(currentUser.getUsername())
                            && !solicitacao.isAprovado()) {

                        // Se a pelada já foi adicnada anteriormente, remove
                        for (String username : marcadoresPeladeiros.keySet()) {
                            if (solicitacao.getSolicitante_username().equals(username)) {
                                marcadoresPeladeiros.get(username).remove();
                                break;
                            }
                        }

                        float[] results = new float[1];
                        Location.distanceBetween(currentLoc.latitude, currentLoc.longitude,
                                solicitacao.getLatitude(), solicitacao.getLongitude(), results);
                        double distancia = results[0];

                        // Somente exibe solicitação se ela estiver dentro da área de busca definida para esta pelada
                        if (distancia < Double.valueOf(areaBusca) || areaBusca == 0) {
                            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.default_profile_image);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);

                            LatLng myLocal = new LatLng(solicitacao.getLatitude(), solicitacao.getLongitude());
                            Marker myMarker = gMap.addMarker(new MarkerOptions().position(myLocal).title(solicitacao.getSolicitante_username()).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                            for (User user : (((CustomApplication) getActivity().getApplication()).getUserList())) {
                                if (user.getUsername().equals(solicitacao.getSolicitante_username())) {
                                    PicassoMarker marker = new PicassoMarker(myMarker);
                                    Picasso.with(getActivity()).load(user.getUser_image()).into(marker);
                                }
                            }
                            marcadoresPeladeiros.put(solicitacao.getSolicitante_username(), myMarker);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        //gMap.getUiSettings().setMyLocationButtonEnabled(true);
        //gMap.setMyLocationEnabled(true);
        //gMap.setOnMyLocationChangeListener(myLocationChangeListener);
        //gMap.setOnMarkerClickListener(this);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 15));
        gMap.addMarker(new MarkerOptions().position(currentLoc));
        gMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        gMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        gMapView.onLowMemory();
    }

    @Override
    public void onPause() {
        super.onPause();
        gMapView.onPause();
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override public void onMyLocationChange(Location location) {}
    };


    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getContext(), "Solicitação Aprovada", Toast.LENGTH_SHORT).show();

        String username_currentUser = currentUser.getUsername();

        // usuario foi aprovado, reduz quantidade de usuarios faltando
        usuariosFaltando = usuariosFaltando - 1;
        String solicitante = marker.getTitle();
        String solicitacao = solicitante + "-" + username_currentUser;

        // atualiza objetos de solicitacao e busca
        Map<String, Object> solicitacaoAprovada = new HashMap<String, Object>();
        solicitacaoAprovada.put("solicitacoes/" + solicitacao + "/aprovado", true);
        solicitacaoAprovada.put("buscas/" + username_currentUser + "/usuariosFaltando", usuariosFaltando);
        mFirebaseDatabaseReference.updateChildren(solicitacaoAprovada);
        marker.remove();

        Contato contato1 = new Contato(username_currentUser, solicitante);
        Contato contato2 = new Contato(solicitante, username_currentUser);

        mFirebaseDatabaseReference.child("contatos").child(username_currentUser + "-" + solicitante).setValue(contato1);
        mFirebaseDatabaseReference.child("contatos").child(solicitante + "-" + username_currentUser).setValue(contato2);

        // caso não falte mais usuarios, exclui a busca do bd e redirecina pra contatos
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("buscas/"+ username_currentUser +"/usuariosFaltando", usuariosFaltando);
        if(usuariosFaltando > 0){
            mFirebaseDatabaseReference.updateChildren(hashMap);
        }
        else {
            mFirebaseDatabaseReference.child("buscas").child(username_currentUser).removeValue();
            ViewPager view = (ViewPager) getActivity().findViewById(R.id.viewPager);
            view.setCurrentItem(4, true);
        }

    }

}

