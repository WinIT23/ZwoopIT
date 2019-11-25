package com.example.zwoopit.ui.home;

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
import com.example.zwoopit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView listView2;
    DatabaseReference databaseReference;
    ArrayList<Book> list;
    Book book;
    public static int where = 0;
    public static Book showBook;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        listView2 = root.findViewById(R.id.ListView2);
        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Books");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    book = ds.getValue(Book.class);
                    list.add(book);
                }
                //BookListAdapter adapter = new BookListAdapter(HomeFragment.this,R.layout.adapter_view_layout,list);
                BookListAdapter adapter = new BookListAdapter(getContext(),R.layout.adapter_view_layout,list);
                listView2.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();
            }
        });

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showBook = list.get(i);
                where = 0;
                startActivity(new Intent(getContext(), BookShowActivity.class));
            }
        });
        return root;
    }

    public static Book getBook()
    {
        return showBook;
    }

}