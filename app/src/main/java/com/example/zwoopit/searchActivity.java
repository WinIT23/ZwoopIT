package com.example.zwoopit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.example.zwoopit.ui.home.HomeFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Vector;

public class searchActivity extends AppCompatActivity {

    TextView t_bookname;
    TextView t_authorname;
    TextView t_bookcat;
    TextView t_bookPublication;

    Button b_search;
    DatabaseReference databaseReference;
    ArrayList<Book> list;
    ListView listView;

    String bookname;
    String authorname;
    String bookcat;
    String bookpub;

    public static Book publicBook;

    boolean outerflag = false;
    Vector<String> vector = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        t_bookname = findViewById(R.id.s_bookname);
        t_authorname = findViewById(R.id.s_authorname);
        t_bookcat = findViewById(R.id.s_bookcat);
        t_bookPublication = findViewById(R.id.s_bookPublication);



        b_search = findViewById(R.id.b_search);
        databaseReference = FirebaseDatabase.getInstance().getReference("Books");
        list = new ArrayList<>();
        listView = findViewById(R.id.searchlistView);


        b_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookname = t_bookname.getText().toString();
                authorname = t_authorname.getText().toString().trim();
                bookcat = t_bookcat.getText().toString().trim();
                bookpub = t_bookPublication.getText().toString().trim();

                t_bookname.setVisibility(View.GONE);
                t_authorname.setVisibility(View.GONE);
                t_bookcat.setVisibility(View.GONE);
                t_bookPublication.setVisibility(View.GONE);
                b_search.setVisibility(View.GONE);

                list.clear();
                databaseReference.orderByChild("bookName").endAt("\\uf8ff").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        boolean flag = true;
                        Book book = dataSnapshot.getValue(Book.class);

                        vector.add(book.getBookName().toLowerCase());
                        if(!check(vector,bookname.toLowerCase())) {
                            if(!(book.getBookName().toLowerCase()).contains(bookname.toLowerCase()) && !bookname.equals(""))
                                flag = false;
                        }

                        vector.remove(0);
                        vector.add(book.getAuthorName().toLowerCase());
                        if(!check(vector,authorname.toLowerCase()))
                        {
                            if(!(book.getAuthorName().toLowerCase()).contains(authorname.toLowerCase()) && !authorname.equals(""))
                                flag = false;
                        }

                        vector.remove(0);
                        vector.add(book.getCategory().toLowerCase());
                        if(!check(vector,bookcat.toLowerCase()))
                        {
                            if(!(book.getCategory().toLowerCase()).contains(bookcat.toLowerCase()) && !bookcat.equals(""))
                                flag = false;
                        }

                        vector.remove(0);
                        vector.add(book.getPublication().toLowerCase());
                        if(!check(vector,bookpub.toLowerCase()))
                        {
                            if(!(book.getPublication().toLowerCase()).contains(bookpub.toLowerCase()) && !bookpub.equals(""))
                                flag = false;
                        }

                        vector.remove(0);

                        if(flag) {
                            list.add(book);
                            BookListAdapter adapter = new BookListAdapter(searchActivity.this, R.layout.adapter_view_layout, list);
                            listView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if(list.isEmpty())
                {
                    t_bookcat.setVisibility(View.VISIBLE);
                    t_bookcat.setText("No Match Found!");
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                publicBook = list.get(i);
                HomeFragment.where = 1;
                startActivity(new Intent(searchActivity.this,BookShowActivity.class));
            }
        });

    }

    public static Book getBook()
    {
        return publicBook;
    }

    static boolean check(Vector<String> list, String s)
    {
        int n = (int) list.size();

        // If the array is empty
        if (n == 0)
        {
            return false;
        }

        for (int i = 0; i < n; i++)
        {

            // If sizes are same
            if (list.get(i).length() != s.length())
            {
                continue;
            }

            boolean diff = false;
            for (int j = 0; j < (int) list.get(i).length(); j++)
            {

                if (list.get(i).charAt(j) != s.charAt(j))
                {

                    // If first mismatch
                    if (!diff)
                    {
                        diff = true;
                    }

                    // Second mismatch
                    else
                    {
                        diff = false;
                        break;
                    }
                }
            }

            if (diff) {
                return true;
            }
        }

        return false;
    }
}
