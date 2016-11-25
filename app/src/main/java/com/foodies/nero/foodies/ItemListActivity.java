package com.foodies.nero.foodies;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ItemListActivity extends AppCompatActivity implements View.OnClickListener{

    private Button logoutButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String userNameHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initiateView();
    }

    public void initiateView(){
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userNameHolder = user.toString();
        logoutButton = (Button)findViewById(R.id.buttonLogOut);
        logoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonLogOut:
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivityPage.class));
                finish();
                break;
        }
    }
}
