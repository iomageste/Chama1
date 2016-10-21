package com.example.ufjf.chama1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ufjf.model.User;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText editUsername;
    private EditText editNome;
    private EditText editTelefone;
    private EditText editPassword;
    private Button buttonRegistrar;
    private Button buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editUsername = (EditText) findViewById(R.id.username);
        editNome =  (EditText) findViewById(R.id.name);
        editTelefone = (EditText) findViewById(R.id.phone);
        editPassword = (EditText) findViewById(R.id.password);
        buttonRegistrar = (Button) findViewById(R.id.register);
        buttonVoltar = (Button) findViewById(R.id.go_back);

        addListenerOnButton(buttonRegistrar);
        addListenerOnButton(buttonVoltar);

    }

    public void addListenerOnButton(Button button) {
        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                User user = new User();
                String username = editUsername.getText().toString();
                String nome = editNome.getText().toString();
                String tel = editTelefone.getText().toString();
                String password = editPassword.getText().toString();
                user.setUsername(username);
                user.setTelefone(tel);
                user.setPassword(password);
                user.setNome(nome);

                Firebase myFirebaseRef = new Firebase("https://chama1-e883c.firebaseio.com/");
                myFirebaseRef.child("users").child(user.getUsername()).setValue(user);


                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(myIntent);

            }
        });

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(myIntent);

            }
        });
    }
}
