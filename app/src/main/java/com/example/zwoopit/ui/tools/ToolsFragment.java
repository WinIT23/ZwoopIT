package com.example.zwoopit.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.zwoopit.BookShowActivity;
import com.example.zwoopit.Cart;
import com.example.zwoopit.R;
import com.example.zwoopit.ui.home.HomeFragment;
import com.example.zwoopit.updateBookActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    ListView listViewCartBooks;
    DatabaseReference databaseReference;
    ArrayList<Book> cartBooksList;
    public static Book publicBook;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        listViewCartBooks = root.findViewById(R.id.cartBooks);

        databaseReference = FirebaseDatabase.getInstance().getReference("Cart");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String myUID = user.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Cart cart = ds.getValue(Cart.class);
                    if(myUID.equals(cart.getUserId()))
                    {
                        cartBooksList = new ArrayList<>(cart.getCart());
                        BookListAdapter adapter = new BookListAdapter(getContext(),R.layout.adapter_view_layout,cartBooksList);
                        listViewCartBooks.setAdapter(adapter);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        listViewCartBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                publicBook = cartBooksList.get(i);
                HomeFragment.where = 4;
                updateBookActivity.book = publicBook;
                startActivity(new Intent(getContext(), BookShowActivity.class));
            }
        });

        return root;
    }
    public static Book getBook()
    {
        return publicBook;
    }

}