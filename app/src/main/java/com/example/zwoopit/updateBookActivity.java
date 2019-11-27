package com.example.zwoopit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class updateBookActivity extends AppCompatActivity {

    public static Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        final EditText tvName = findViewById(R.id.view_bookname);
        final EditText tvAName = findViewById(R.id.view_authorname);
        final EditText tvEdition = findViewById(R.id.view_edition);
        final EditText tvPublication = findViewById(R.id.view_publication);
        final EditText tvDiscount = findViewById(R.id.view_discount);
        final EditText tvPrice = findViewById(R.id.view_price);
        final EditText tvcategory = findViewById(R.id.view_category);
        final EditText tvsem = findViewById(R.id.view_sem);
        final Button update = findViewById(R.id.update);
        final Button delete = findViewById(R.id.delete);


        tvName.setText(book.getBookName().toUpperCase());
        tvAName.setText(book.getAuthorName().toUpperCase());
        tvEdition.setText(book.getEdition().toUpperCase());
        tvPublication.setText(book.getPublication().toUpperCase());
        tvDiscount.setText(book.getDiscount().toUpperCase());
        tvPrice.setText(book.getPrice().toUpperCase());
        tvcategory.setText(book.getCategory().toUpperCase());
        tvsem.setText(book.getSem().toUpperCase());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Books").child(book.getBookID());
                Book updateBook = new Book();

                updateBook.setBookName(tvName.getText().toString());
                updateBook.setAuthorName(tvAName.getText().toString());
                updateBook.setEdition(tvEdition.getText().toString());
                updateBook.setPublication(tvPublication.getText().toString());
                updateBook.setDiscount(tvDiscount.getText().toString());

                String discount = tvDiscount.getText().toString();
                String price = book.getPrice();
                if(!tvDiscount.getText().toString().equals("0"))
                {
                    Double cost;
                    cost = (1 - Double.parseDouble(discount) / 100) * Double.parseDouble(price);
                    tvPrice.setText(cost.toString());
                }

                updateBook.setPrice(tvPrice.getText().toString());
                updateBook.setCategory(tvcategory.getText().toString());
                updateBook.setSem(tvsem.getText().toString());
                updateBook.setOwnerID(book.getOwnerID());
                updateBook.setBookID(book.getBookID());

                databaseReference.setValue(updateBook);

                Toast.makeText(updateBookActivity.this, "Book Information is updated!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(updateBookActivity.this,home.class));

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Books").child(book.getBookID());
                databaseReference.setValue(null);
                Toast.makeText(updateBookActivity.this, "Book Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(updateBookActivity.this,home.class));
            }
        });


    }
}
