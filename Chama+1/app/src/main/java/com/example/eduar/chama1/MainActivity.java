package com.example.eduar.chama1;

import android.os.storage.StorageManager;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.eduar.model.Busca;
import com.example.eduar.model.Solicitacao;
import com.example.eduar.model.User;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends FragmentActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Firebase.setAndroidContext(this);

        //Firebase myFirebaseRef = new Firebase("https://chama1-e883c.firebaseio.com/");

      /* myFirebaseRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Toast.makeText(getApplicationContext(),"There are " + snapshot.getChildrenCount() + " users", Toast.LENGTH_SHORT).show();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    String resultado = user.getNome()+user.getTelefone();
                    Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();

                }
            }
            @Override public void onCancelled(FirebaseError error) { }
        });*/

        /*myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Toast.makeText(getApplicationContext(),"There are " + snapshot.getChildrenCount() + " users", Toast.LENGTH_SHORT).show();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                   // System.out.println(post.getAuthor() + " - " + post.getTitle());
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });*/




        //toolbar = (Toolbar)findViewById(R.id.toolBar);
        //setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new PeladasFragment(), "Peladas");
        viewPagerAdapter.addFragments(new Chama1Fragment(), "Chama+1");
        viewPagerAdapter.addFragments(new ConfigFragment(), "Config");
        viewPagerAdapter.addFragments(new SolicitacoesFragment(), "Solicitações");
        viewPagerAdapter.addFragments(new RecentesFragment(), "Recentes");
        viewPagerAdapter.addFragments(new PerfilFragment(), "Perfil");

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);



        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        fragmentManager = getSupportFragmentManager();


    }
}
