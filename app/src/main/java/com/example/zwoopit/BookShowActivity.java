package com.example.zwoopit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zwoopit.ui.gallery.GalleryFragment;
import com.example.zwoopit.ui.home.HomeFragment;
import com.example.zwoopit.ui.tools.ToolsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class BookShowActivity extends AppCompatActivity {

    String username;
    String email;
    Book book;
    DatabaseReference databaseReference;
    Boolean flag = false;
    Boolean flagInner = false;

    TextView tvOwner;
    TextView tvOwnerEmail;

    DatabaseReference dbRef;
    DatabaseReference dbRef1;
    String userID;
    boolean userpresent;
    boolean bookpresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_show);

        if(HomeFragment.where == 0)
            book = HomeFragment.getBook();
        else if(HomeFragment.where == 1)
            book = searchActivity.getBook();
        else if(HomeFragment.where == 2)
            book = filterActivity.getBook();
        else if(HomeFragment.where == 3)
            book = GalleryFragment.getBook();
        else
            book = ToolsFragment.getBook();

        TextView tvName = findViewById(R.id.view_bookname);
        TextView tvAName = findViewById(R.id.view_authorname);
        TextView tvEdition = findViewById(R.id.view_edition);
        TextView tvPublication = findViewById(R.id.view_publication);
        TextView tvDiscount = findViewById(R.id.view_discount);
        TextView tvPrice = findViewById(R.id.view_price);
        TextView tvcategory = findViewById(R.id.view_category);
        TextView tvsem = findViewById(R.id.view_sem);
        tvOwner = findViewById(R.id.view_owner);
        tvOwnerEmail = findViewById(R.id.view_email);
        Button wishlistThis = findViewById(R.id.wishlistThis);
        Button chat = findViewById(R.id.chatWithOwner);
        Button cartAdd = findViewById(R.id.cartAdd);
        Button update = findViewById(R.id.update);

        if(!( HomeFragment.where == 3 || HomeFragment.where == 4))
        {
            update.setVisibility(View.GONE);
        }

        if(HomeFragment.where == 4)
        {
            update.setText("Delete This Book");
        }


        tvName.setText(book.getBookName().toUpperCase());
        tvAName.setText(book.getAuthorName().toUpperCase());
        tvEdition.setText(book.getEdition().toUpperCase());
        tvPublication.setText(book.getPublication().toUpperCase());
        tvDiscount.setText(book.getDiscount().toUpperCase());
        tvPrice.setText(book.getPrice().toUpperCase());
        tvcategory.setText(book.getCategory().toUpperCase());
        tvsem.setText(book.getSem().toUpperCase());

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    User myuser = ds.getValue(User.class);
                    try {
                        if (myuser.getUserID().equals(book.getOwnerID())) {
                            username = myuser.getFname() + " " + myuser.getLname();
                            email = myuser.getEmail();
                            tvOwner.setText(username.toUpperCase());
                            tvOwnerEmail.setText(email.toUpperCase());
                        }
                    }
                    catch (Exception e)
                    {
                        Log.e("BOOKOWNER","exception");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BookShowActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HomeFragment.where == 4)
                {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Books").child(book.getBookID());
                    databaseReference.setValue(null);
                    Toast.makeText(BookShowActivity.this, "Book Has Been Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BookShowActivity.this,home.class));
                }
                startActivity(new Intent(BookShowActivity.this,updateBookActivity.class));
            }
        });



        wishlistThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Wishlist");
//                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Wishlist");
//                databaseReference1.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for(DataSnapshot ds : dataSnapshot.getChildren())
//                        {
//                            if(!flagInner) {
//                                String key = ds.getKey();
//                                Log.e("UNIQUEID", key + " " + user.getUid());
//                                final Wishlist wishlist1;
//                                wishlist1 = ds.getValue(Wishlist.class);
//                                if (wishlist1.getUserID().equals(user.getUid())) {
//                                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Wishlist").child(key).child("Wishlist");
//                                    myRef.addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            for (DataSnapshot dsIN : dataSnapshot.getChildren())
//                                            {
//                                                Book innerBook = dsIN.getValue(Book.class);
//                                                for(Book Comparebook : wishlist1.getWishlist())
//                                                {
//                                                    if(Comparebook.getBookID().equals(innerBook.getOwnerID()))
//                                                    {
//                                                        flagInner = true;
//                                                        break;
//                                                    }
//                                                }
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                                            Toast.makeText(BookShowActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                    flag = true;
//                                    wishlist1.addToWishList(book);
//                                    dataSnapshot.getRef().child(key).setValue(wishlist1);
//                                    break;
//                                }
//                            }
//                        }
//                        if(!flag) {
//                            Wishlist wishlist = new Wishlist();
//                            wishlist.setUserID(user.getUid());
//                            wishlist.addToWishList(book);
//                            String ID = dbRef.push().getKey();
//                            dbRef.child(ID).setValue(wishlist);
//                        }
//                        Toast.makeText(BookShowActivity.this, "Added to Wishlist", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(BookShowActivity.this,home.class));
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Toast.makeText(BookShowActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
//                    }
//                });

                dbRef = FirebaseDatabase.getInstance().getReference("Wishlist");
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                userID = user.getUid();
                userpresent = false;
                bookpresent = false;

                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Wishlist wishlist;
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            wishlist = ds.getValue(Wishlist.class);
                            if(wishlist.getUserID().equals(userID))
                            {
                                userpresent = true;
                                break;
                            }
                        }
                        if(userpresent)
                        {
                            dbRef1 = FirebaseDatabase.getInstance().getReference("Wishlist");
                            dbRef1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Wishlist wishlist = new Wishlist();
                                    for(DataSnapshot ds : dataSnapshot.getChildren())
                                    {
                                        wishlist = ds.getValue(Wishlist.class);
                                        ArrayList<Book> temp = wishlist.getWishlist();
                                        for(Book tempbook : temp) {
                                            if (tempbook.getBookID().equals(book.getBookID())) {
                                                bookpresent = true;
                                                break;
                                            }
                                        }
                                    }
                                    if(!bookpresent) {

                                        Wishlist ws1 = new Wishlist();
                                        ArrayList<Book> temp = wishlist.getWishlist();
                                        temp.add(book);

                                        DatabaseReference dbref2 = FirebaseDatabase.getInstance().getReference("Wishlist/"+userID+"/wishlist");
                                        dbref2.setValue(temp);
                                        Toast.makeText(BookShowActivity.this, "Book Added To Wishlist", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(BookShowActivity.this,home.class));
                                    }
                                     else {
                                        Toast.makeText(BookShowActivity.this, "Book Already In Wishlist", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(BookShowActivity.this,home.class));
                                     }
                                    }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else
                        {
                            Wishlist wishlist1 = new Wishlist();
                            wishlist1.setUserID(userID);
                            wishlist1.addToWishList(book);

                            dbRef.child(userID).setValue(wishlist1);
                            Toast.makeText(BookShowActivity.this, "Added to Wishlist", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(BookShowActivity.this,home.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to be filled!!
            }
        });

        cartAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference("Cart");
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                userID = user.getUid();
                userpresent = false;
                bookpresent = false;

                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Cart cart;
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            cart = ds.getValue(Cart.class);
                            if(cart.getUserId().equals(userID))
                            {
                                userpresent = true;
                                break;
                            }
                        }
                        if(userpresent)
                        {
                            dbRef1 = FirebaseDatabase.getInstance().getReference("Cart");
                            dbRef1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Cart cart = new Cart();
                                    for(DataSnapshot ds : dataSnapshot.getChildren())
                                    {
                                        cart = ds.getValue(Cart.class);
                                        ArrayList<Book> temp = cart.getCart();
                                        for(Book tempbook : temp) {
                                            if (tempbook.getBookID().equals(book.getBookID())) {
                                                bookpresent = true;
                                                break;
                                            }
                                        }
                                    }
                                    if(!bookpresent) {

                                        Cart ws1 = new Cart();
                                        ArrayList<Book> temp = cart.getCart();
                                        temp.add(book);

                                        DatabaseReference dbref2 = FirebaseDatabase.getInstance().getReference("Cart/"+userID+"/cart");
                                        dbref2.setValue(temp);
                                        Toast.makeText(BookShowActivity.this, "Book Added To Cart", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(BookShowActivity.this,home.class));
                                    }
                                    else {
                                        Toast.makeText(BookShowActivity.this, "Book Already In Cart", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(BookShowActivity.this,home.class));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else
                        {
                            Cart cart1 = new Cart();
                            cart1.setUserId(userID);
                            cart1.addToCart(book);

                            dbRef.child(userID).setValue(cart1);
                            Toast.makeText(BookShowActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(BookShowActivity.this,home.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }
}
