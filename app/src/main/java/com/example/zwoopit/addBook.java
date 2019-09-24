package com.example.zwoopit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class addBook extends AppCompatActivity {

    EditText bookName;
    EditText authorName;
    EditText category;
    EditText edition;
    EditText price;
    EditText publication;

    Button addTheBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        bookName = findViewById(R.id.bookName);
        authorName = findViewById(R.id.AuthorName);
        category = findViewById(R.id.category);
        edition = findViewById(R.id.Edition);
        price = findViewById(R.id.price);
        publication = findViewById(R.id.Publication);


        final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final DatabaseReference dbref;
        dbref = FirebaseDatabase.getInstance().getReference("Books");

        addTheBook = findViewById(R.id.add);

        addTheBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String ownerID;
                String BookName = bookName.getText().toString().trim();
                String AuthorName = authorName.getText().toString().trim();
                String Category = category.getText().toString().trim();
                String Edition = edition.getText().toString().trim();
                String Price = price.getText().toString().trim();
                String Publication = publication.getText().toString().trim();


                boolean flag1=true;
                boolean flag2=true;
                if(BookName.isEmpty() || AuthorName.isEmpty() || Category.isEmpty() || Edition.isEmpty()) {
                    Toast.makeText(addBook.this,"Enter all the details!",Toast.LENGTH_SHORT).show();
                    flag1 = false;
                }
                Pattern pattern = Pattern.compile("\\d{1,2}");
                Matcher matcher = pattern.matcher(Edition);
                if(!matcher.matches())
                {
                    Toast.makeText(addBook.this,"Enter edition(number only) of maximum two digits!",Toast.LENGTH_SHORT).show();
                    edition.setText(null);
                    flag1=false;
                }

                Pattern pattern1 = Pattern.compile("^\\d+(.\\d{1,2})?$");
                Matcher matcher1 = pattern1.matcher(Price);
                if(!matcher1.matches())
                {
                    Toast.makeText(addBook.this,"Enter price(number only) with only two point precision!",Toast.LENGTH_SHORT).show();
                    price.setText(null);
                    flag2=false;
                }
                if(flag1 && flag2) {

                    final ProgressDialog progressDialog = new ProgressDialog( addBook.this);
                    progressDialog.setMessage("Please Wait...Registration In Progress!");
                    progressDialog.show();

                    String ID = dbref.push().getKey();
                    ownerID = mAuth.getUid();

                    Book book = new Book(BookName, AuthorName, Category, Edition, Price, ownerID, Publication);
                    book.setBookID(ID);
                    dbref.child(ID).setValue(book);

                    Toast.makeText(addBook.this, "BOOK ADDED TO APPLICATION...", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    startActivity(new Intent(addBook.this, home.class));
                }
            }
        });
    }
}
