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

import com.example.ufjf.model.Solicitacao;
import com.example.ufjf.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Chama1MapaFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private List<Solicitacao> novosPeladeiros;
    private Map<String, Marker> marcadoresPeladeiros;
    LatLng currentLoc;
    int areaBusca;
    User currentUser;
    int usuariosFaltando;

    private DatabaseReference mFirebaseDatabaseReference;

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        //private final View mWindow;
        private final View mContents;

        CustomInfoWindowAdapter(Bundle savedInstanceState) {
            //mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            mContents =  getLayoutInflater(savedInstanceState).inflate(R.layout.custom_info_contents, null);
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
            if(title != null){
                SpannableString titleText = new SpannableString("MARKER TITULO");
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            }
            else {
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


    private MapView gMapView;
    private GoogleMap gMap = null;
    Marker myMarker;

    public Chama1MapaFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peladas, container, false);

        areaBusca = Integer.parseInt(getArguments().getString("AreaBusca"));
        usuariosFaltando = Integer.parseInt(getArguments().getString("Chama+"));
        //currentUser = getResources().getString(R.string.current_user);
        currentUser = ((CustomApplication) getActivity().getApplication()).getCurrentUser();

        novosPeladeiros = new ArrayList<>();
        marcadoresPeladeiros = new HashMap<String, Marker>();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mFirebaseDatabaseReference.child("solicitacoes").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                novosPeladeiros.clear();
                for (com.google.firebase.database.DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Solicitacao solicitacao = postSnapshot.getValue(Solicitacao.class);
                    if(solicitacao != null
                            && !solicitacao.getSolicitante_username().equals(currentUser.getUid())
                            && !solicitacao.isAprovado()){
                        novosPeladeiros.add(solicitacao);
                    }
                }

            }
            @Override public void onCancelled(DatabaseError error) { }
        });

        gMapView = (MapView) view.findViewById(R.id.map);
        gMapView.onCreate(savedInstanceState);
        gMapView.getMapAsync(this);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // dispara notificações para usuários

        return view;

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.setMyLocationEnabled(true);
        gMap.setOnMyLocationChangeListener(myLocationChangeListener);
        //gMap.setOnMarkerClickListener(this);
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
        @Override
        public void onMyLocationChange(Location location) {
            currentLoc = new LatLng(location.getLatitude(), location.getLongitude());

            for (Solicitacao solicitacao : novosPeladeiros) {
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

                // Somente exibe solicitação se ela estiver dentro da área de busca definida para
                // esta pelada
                if(distancia < Double.valueOf(areaBusca) || areaBusca == 0){
                    int height = 100;
                    int width = 100;
                    BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.default_profile_image);
                    Bitmap b=bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                    LatLng myLocal = new LatLng(solicitacao.getLatitude(), solicitacao.getLongitude());
                    myMarker = gMap.addMarker(new MarkerOptions().position(myLocal).title(solicitacao.getSolicitante_username()).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                    marcadoresPeladeiros.put(solicitacao.getSolicitante_username(), myMarker);
                }
            }
        }
    };


    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getContext(),  "TESTE", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getContext(),  "Solicitação Aprovada", Toast.LENGTH_SHORT).show();

        String username_currentUser = currentUser.getUsername();

        // usuario foi aprovado, reduz quantidade de usuarios faltando
        usuariosFaltando = usuariosFaltando - 1;
        String solicitante = marker.getTitle();
        String solicitacao = solicitante+"-"+username_currentUser;

        // atualiza objetos de solicitacao e busca
        Map<String, Object> solicitacaoAprovada = new HashMap<String, Object>();
        solicitacaoAprovada.put("solicitacoes/"+solicitacao+"/aprovado", true);
        solicitacaoAprovada.put("buscas/"+username_currentUser+"/usuariosFaltando", usuariosFaltando );
        mFirebaseDatabaseReference.updateChildren(solicitacaoAprovada);
        marker.remove();

        // adiciona o dono da pelada aos contatos do peladeiro
        Map<String, Object> cadastroContatos = new HashMap<String, Object>();
        cadastroContatos.put("username", solicitante);
        cadastroContatos.put("contato", username_currentUser);
        mFirebaseDatabaseReference.child("contatos").push().setValue(cadastroContatos);

        // adiciona o peladeiro aos contatos do dono da pelada
        cadastroContatos = new HashMap<String, Object>();
        cadastroContatos.put("username",username_currentUser);
        cadastroContatos.put("contato", solicitante);
        mFirebaseDatabaseReference.child("contatos").push().setValue(cadastroContatos);

        // caso não falte mais usuarios, exclui a busca do bd e redirecina pra contatos
        if(usuariosFaltando == 0){
            mFirebaseDatabaseReference.child("buscas").child(username_currentUser).removeValue();
            ViewPager view = (ViewPager) getActivity().findViewById(R.id.viewPager);
            view.setCurrentItem(4, true);
        }

    }

}

