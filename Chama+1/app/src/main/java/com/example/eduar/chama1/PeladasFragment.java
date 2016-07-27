package com.example.eduar.chama1;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduar.model.Busca;
import com.example.eduar.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PeladasFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private List<Busca> peladas;

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

    public PeladasFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peladas, container, false);

        peladas = new ArrayList<>();
        Firebase myFirebaseRef = new Firebase("https://chama1-e883c.firebaseio.com/");

        myFirebaseRef.child("buscas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //Toast.makeText(getContext(),"There are " + snapshot.getChildrenCount() + " users", Toast.LENGTH_SHORT).show();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Busca busca = postSnapshot.getValue(Busca.class);
                    if(busca != null){
                        peladas.add(busca);
                    }
                    /*User user = postSnapshot.getValue(User.class);
                    String resultado = user.getNome()+user.getTelefone();
                    Toast.makeText(getContext(), resultado, Toast.LENGTH_SHORT).show();*/

                }
            }
            @Override public void onCancelled(FirebaseError error) { }
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
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
        gMap.animateCamera(cameraUpdate);
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
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

            for(Busca pelada: peladas){
                float[] results = new float[1];
                Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                        pelada.getLatitude(), pelada.getLongitude(), results);

                int height = 100;
                int width = 100;
                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.default_profile_image);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                LatLng myLocal = new LatLng(pelada.getLatitude(), pelada.getLongitude());
                myMarker = gMap.addMarker(new MarkerOptions().position(myLocal).title(pelada.getUsername()).snippet("Faltando: "+pelada.getUsuariosFaltando()).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

            }

            //Marker mMarker = gMap.addMarker(new MarkerOptions().position(loc));
            if(gMap != null){
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
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
        Toast.makeText(getContext(),  "Solicitação Enviada", Toast.LENGTH_SHORT).show();
        // insere no banco
        // solicitante_id (usuario logado), candidato_id (usuario associado ao marker),
        // aprovado (false, até o outro solicitante dar o ok)
        ViewPager view = (ViewPager) getActivity().findViewById(R.id.viewPager);
        view.setCurrentItem(3, true);
    }

}
