package com.example.zwoopit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class addBook extends AppCompatActivity {

    EditText bookName;
    EditText authorName;
    EditText category;
    EditText edition;
    EditText price;
    EditText publication;
    EditText imagename;
    Button chooseFile;
    Button addTheBook;
    ImageView imageView;
    Uri mImageuri;


    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 111 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            mImageuri = data.getData();
            //Toast.makeText(addBook.this,mImageuri.toString(),Toast.LENGTH_SHORT).show();
            imageView.setImageURI(mImageuri);
            imagename.setText(mImageuri.toString());
        }
    }

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
        imagename = findViewById(R.id.imagename);
        chooseFile = findViewById(R.id.mBimg);
        addTheBook = findViewById(R.id.add);
        imageView = findViewById(R.id.mImageView);

        final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final DatabaseReference dbref;
        dbref = FirebaseDatabase.getInstance().getReference("Books");
        final StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference().child("Images/");


        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,111);
            }
        });


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

                    final Book book = new Book(BookName, AuthorName, Category, Edition, Price, ownerID, Publication);
                    book.setBookID(ID);

                    if(mImageuri != null)
                    {
                        //final StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(mImageuri));
                        storageReference.putFile(mImageuri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                            @Override
//                                            public void onSuccess(Uri uri) {
//                                                Toast.makeText(addBook.this,"in DownloadUrl",Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
                                        Toast.makeText(addBook.this,"in OnSuccess",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(addBook.this,"error occurred!",Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                    else{
                        Toast.makeText(addBook.this,"No file selected",Toast.LENGTH_SHORT).show();
                    }


                    String search = BookName + "_" + AuthorName + "_" + Category + "_" + Publication;
                    book.setSearch(search);
                    String filter = Edition + "_" + Price + "_" + book.getDiscount();
                    book.setFilter(filter);

                    dbref.child(ID).setValue(book);
                    Toast.makeText(addBook.this, "BOOK ADDED TO APPLICATION...", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    startActivity(new Intent(addBook.this, home.class));
                }
            }
        });

    }
}
