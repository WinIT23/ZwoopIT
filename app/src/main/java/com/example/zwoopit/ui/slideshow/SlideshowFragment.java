package com.example.zwoopit.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.zwoopit.Book;
import com.example.zwoopit.BookListAdapter;
import com.example.zwoopit.R;
import com.example.zwoopit.Wishlist;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    ListView listViewWishlistedBooks;
    DatabaseReference databaseReference;
    ArrayList<Book> wishlistedBooksList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });

        listViewWishlistedBooks = root.findViewById(R.id.wishlistedBooksLV);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String myUID = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Wishlist");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Wishlist wishlist = ds.getValue(Wishlist.class);
                    if(myUID.equals(wishlist.getUserID()))
                    {
                        wishlistedBooksList = new ArrayList<Book>(wishlist.getWishlist());
                        BookListAdapter adapter = new BookListAdapter(getContext(),R.layout.adapter_view_layout,wishlistedBooksList);
                        listViewWishlistedBooks.setAdapter(adapter);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}