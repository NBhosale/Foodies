package com.foodies.nero.foodies;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.actions.ItemListIntents;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivityPage extends AppCompatActivity implements View.OnClickListener{
    private ImageView backgroundOne;
    private ImageView backgroundTwo;
    private TextView onCreateAccount;
    private Button logInUser;
    private EditText emailTextField, passwordTextField;
    private String passwordHolder, emailHolder;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(this, ItemListActivity.class));
            finish();
        }


        initiateViews();
        startAnimation();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textViewCreateAccount:
                Intent intent = new Intent(this, RegisterUser.class);
                startActivity(intent);
                finish();
                break;

            case R.id.buttonLogin:
                logInUserAfterAuthentication();
                break;
        }
    }



    public void startAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                float width = backgroundOne.getWidth();
                float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();
    }

    private void logInUserAfterAuthentication(){
        passwordHolder = passwordTextField.getText().toString();
        emailHolder = emailTextField.getText().toString();

        if(TextUtils.isEmpty(passwordHolder)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(emailHolder)){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(emailHolder, passwordHolder)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ItemListActivity.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Email or password incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void initiateViews(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        progressDialog = new ProgressDialog(this);
        backgroundOne = (ImageView) findViewById(R.id.imageViewBackground1);
        logInUser = (Button)findViewById(R.id.buttonLogin);
        logInUser.setOnClickListener(this);
        backgroundTwo = (ImageView) findViewById(R.id.imageViewBackground2);
        onCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        onCreateAccount.setOnClickListener(this);
        emailTextField = (EditText)findViewById(R.id.editTextEmail);
        passwordTextField = (EditText)findViewById(R.id.editTextPassword);
    }
}
