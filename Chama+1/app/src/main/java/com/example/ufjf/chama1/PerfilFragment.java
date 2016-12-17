package com.example.ufjf.chama1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ufjf.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PerfilFragment extends Fragment {

    TextView editUserName;
    TextView editEmail;
    EditText editTelefone;
    Button buttonSalvar;
    Button buttonLogout;
    View view;
    ImageView imageView;
    User currentUser;

    private DatabaseReference mFirebaseDatabaseReference;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_perfil, container, false);

        currentUser = ((CustomApplication) getActivity().getApplication()).getCurrentUser();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        editUserName = (TextView) view.findViewById(R.id.textNome);
        editEmail = (TextView) view.findViewById(R.id.textEmail);
        editTelefone = (EditText) view.findViewById(R.id.textTelefone);
        buttonSalvar = (Button) view.findViewById(R.id.buttonSalvar);
        buttonLogout = (Button) view.findViewById(R.id.buttonLogout);
        addListenerOnButton(view);

        editUserName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        editEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        editTelefone.setText(currentUser.getTelefone());

        imageView = (ImageView) view.findViewById(R.id.imageView);
        Picasso.with(getActivity()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                        imageView.setImageDrawable(imageDrawable);
                    }
                    @Override
                    public void onError() {
                        imageView.setImageResource(R.drawable.default_profile_image);
                    }
                });

        return view;
    }

    public void addListenerOnButton(View view) {
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String new_tel = editTelefone.getText().toString();
                currentUser.setTelefone(new_tel);
                mFirebaseDatabaseReference.child("users").child(currentUser.getUsername()).setValue(currentUser);
                Toast.makeText(getContext(), "Seus dados de perfil foram atualizados.", Toast.LENGTH_SHORT).show();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Auth.GoogleSignInApi.signOut(((CustomApplication)getActivity().getApplication()).getmGoogleApiClient());
                FirebaseAuth.getInstance().signOut();
                ((CustomApplication)getActivity().getApplication()).setCurrentUser(null);
                Intent myIntent = new Intent(getActivity(),SignInActivity.class);
                getActivity().startActivity(myIntent);
            }
        });
    }

}
