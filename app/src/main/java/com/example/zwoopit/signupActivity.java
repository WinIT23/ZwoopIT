package com.example.zwoopit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.FirstPartyScopes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signupActivity extends AppCompatActivity {

    EditText firstName, lastName, emailID, password, password2;
    Button signup;
    Boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        password2 = findViewById(R.id.password2);
        emailID = findViewById(R.id.emailID);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                flag=validate();
                if(flag) {
                    final String email = emailID.getText().toString().trim();
                    String pass = password.getText().toString().trim();

                    final FirebaseAuth firebaseAuth;
                    firebaseAuth = FirebaseAuth.getInstance();
                    final DatabaseReference dbUser;
                    dbUser = FirebaseDatabase.getInstance().getReference("Users");

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String EMAIL = emailID.getText().toString().trim();
                                String fname = firstName.getText().toString().trim();
                                String lname = lastName.getText().toString().trim();

                                String id = dbUser.push().getKey();
                                User user = new User(EMAIL,fname,lname);
                                dbUser.child(id).setValue(user);
                                Toast.makeText(signupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(signupActivity.this, MainActivity.class));
                            }
                            else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(signupActivity.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }


    public Boolean validate(){
        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String pass2 = password2.getText().toString();
        String email = emailID.getText().toString();
        String pass = password.getText().toString();

        Boolean flag = false;
        if (fName.isEmpty() || lName.isEmpty() || pass2.isEmpty() || email.isEmpty() || pass.isEmpty())
        {
            Toast.makeText(signupActivity.this, "Please Enter all the Details!", Toast.LENGTH_SHORT).show();
        }
        else {
            if(pass.equals(pass2))
                flag = true;
            else {
                Toast.makeText(signupActivity.this, "Passwords aren't matching! Re-Enter password!", Toast.LENGTH_SHORT).show();
                password2.setText(null);
            }
        }

        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher mat = pattern.matcher(email);
        if(!mat.matches()) {
            flag = false;
            Toast.makeText(signupActivity.this, "Please enter valid email address!", Toast.LENGTH_SHORT).show();
            emailID.setText(null);
        }

        return flag;
    }
}
