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

        //toolbar = (Toolbar)findViewById(R.id.toolBar);
        //setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new PeladasFragment(), "Peladas");
        viewPagerAdapter.addFragments(new Chama1Fragment(), "Chama+1");
        viewPagerAdapter.addFragments(new ConfigFragment(), "Config");
        viewPagerAdapter.addFragments(new SolicitacoesFragment(), "Solicitações");
        viewPagerAdapter.addFragments(new RecentesFragment(), "Contatos");
        viewPagerAdapter.addFragments(new PerfilFragment(), "Perfil");

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);



        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        fragmentManager = getSupportFragmentManager();


    }
}
