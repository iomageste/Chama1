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

import com.example.ufjf.model.Busca;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PeladasFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private List<Busca> novasPeladas;
    private List<Solicitacao> userSolicitacoes;
    private Map<String, Marker> marcadoresPeladas;
    LatLng currentLoc;
    User currentUser;

    private DatabaseReference mFirebaseDatabaseReference;

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private final View mContents;

        CustomInfoWindowAdapter(Bundle savedInstanceState) {
            mContents =  getLayoutInflater(savedInstanceState).inflate(R.layout.custom_info_contents, null);
            TextView lat_long = ((TextView) getView().findViewById(R.id.lat_long));
            lat_long.setText("Distance");
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
                SpannableString titleText = new SpannableString("Titulo Marker");
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            }
            else {
                titleUi.setText("Titulo Marker == null");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString("Texto Snippet");
                snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("Texto Snippet");
            }
        }

    }

    private MapView gMapView;
    private GoogleMap gMap = null;
    Marker myMarker;

    public PeladasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peladas, container, false);
        currentUser = ((CustomApplication) getActivity().getApplication()).getCurrentUser();

        novasPeladas = new ArrayList<>();
        userSolicitacoes = new ArrayList<>();
        marcadoresPeladas = new HashMap<String, Marker>();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        // Mantém registro de peladas ativas atualizado de maneira assíncrona
        mFirebaseDatabaseReference.child("buscas").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                novasPeladas.clear();
                for (com.google.firebase.database.DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Busca pelada = postSnapshot.getValue(Busca.class);
                    if(pelada != null && !pelada.getUsername().equals(currentUser.getUsername())){
                        novasPeladas.add(pelada);
                        MyFirebaseMessagingService svc = new MyFirebaseMessagingService();
                        svc.sendNotification("MinhaMensagem", getActivity(), getContext());
                    }
                }
            }
            @Override public void onCancelled(DatabaseError databaseError) { }
        });

        // Mantém registro de solicitações do usuário atualizado de maneira assíncrona
        mFirebaseDatabaseReference.child("solicitacoes").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                userSolicitacoes.clear();
                for (com.google.firebase.database.DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Solicitacao sol = postSnapshot.getValue(Solicitacao.class);
                    if(sol != null && sol.getSolicitante_username().equals(currentUser.getUsername())){
                        userSolicitacoes.add(sol);
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

        return view;

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
              ((CustomApplication) getActivity().getApplication()).setCurrentLocation(currentLoc);

              for(Busca pelada: novasPeladas){

                  // Se esta é uma busca do próprio usuário, remove o marcado
                  for (String username :marcadoresPeladas.keySet()) {
                      if(pelada.getUsername().equals(username)) {
                          marcadoresPeladas.get(username).remove();
                          break;
                      }
                  }
                  // Verifica se a distância dessa pelada é menor que a distância mínima definida
                  // pelo usuário para busca de peladas
                  float[] distancia = new float[1];
                  Location.distanceBetween(currentLoc.latitude, currentLoc.longitude,
                         pelada.getLatitude(), pelada.getLongitude(), distancia);

                  TextView lat_long = ((TextView) getView().findViewById(R.id.lat_long));
                  float distanciaKm = distancia[0]/1000;
                  lat_long.setText("Distance"+distanciaKm);

                  if (distanciaKm > currentUser.getAreaBusca() || distanciaKm > pelada.getAreaBusca())
                     continue;

                 float[] results = new float[1];
                 Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                        pelada.getLatitude(), pelada.getLongitude(), results);

                 int height = 100;
                 int width = 100;
                 BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.default_profile_image);
                 Bitmap b=bitmapdraw.getBitmap();
                 Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                 LatLng myLocal = new LatLng(pelada.getLatitude(), pelada.getLongitude());

                 // Preenche texto do balão de acordo com a situação
                 String marketText =  "Faltando: "+pelada.getUsuariosFaltando();
                 for(Solicitacao sol: userSolicitacoes) {
                     if(sol.getUsername_busca().equals(pelada.getUsername()))
                         marketText = "Solicitação enviada...";
                     break;
                 }

                  BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(smallMarker);
                 myMarker = gMap.addMarker(new MarkerOptions().position(myLocal).title(pelada.getUsername()).snippet(marketText).icon(icon));
                  for (User user: (((CustomApplication) getActivity().getApplication()).getUserList())){
                      if(user.getUsername().equals(pelada.getUsername())){
                          PicassoMarker marker = new PicassoMarker(myMarker);
                          Picasso.with(getActivity()).load(user.getUser_image()).into(marker);
                      }
                  }
                 marcadoresPeladas.put(pelada.getUsername(), myMarker);


            }
        }
    };


    @Override
    public boolean onMarkerClick(Marker marker) {
        //Toast.makeText(getContext(),  "TESTE", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getContext(),  "Solicitação Enviada", Toast.LENGTH_SHORT).show();

        Solicitacao solicitacao = new Solicitacao(currentUser.getUsername(), marker.getTitle(), currentLoc.latitude, currentLoc.longitude);
        mFirebaseDatabaseReference.child("solicitacoes").child(currentUser.getUsername()+"-"+marker.getTitle()).setValue(solicitacao);

        ViewPager view = (ViewPager) getActivity().findViewById(R.id.viewPager);
        view.setCurrentItem(3, true);
    }

}
