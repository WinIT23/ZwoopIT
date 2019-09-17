package com.example.zwoopit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText email,pass;
    Button login,signup;
    TextView info;
    int counter = 5;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    void checkUser(String emailid,String password)
    {
        progressDialog.setMessage("Verifying Data!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,home.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Entered wrong password, login failed.",Toast.LENGTH_SHORT).show();
                    counter--;
                    progressDialog.dismiss();
                    info.setText("Number Of attempts remaining: "+ counter);
                    if(counter==0)
                        login.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        info = findViewById(R.id.Info);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        if(user != null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,home.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailid = email.getText().toString();
                String password = pass.getText().toString();
                if(emailid.isEmpty() || password.isEmpty())
                    Toast.makeText(MainActivity.this,"Please Enter Details!",Toast.LENGTH_SHORT).show();
                else {
                    Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                    Matcher mat = pattern.matcher(emailid);
                    if(!mat.matches()) {
                        Toast.makeText(MainActivity.this, "Please enter valid email address!", Toast.LENGTH_SHORT).show();
                        email.setText(null);
                    }
                    else
                    checkUser(emailid, password);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(MainActivity.this, signupActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });



    }
}
