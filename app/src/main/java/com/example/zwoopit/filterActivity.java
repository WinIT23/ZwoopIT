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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zwoopit.ui.home.HomeFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class filterActivity extends AppCompatActivity {

    SeekBar s_price;
    TextView t_selPrice;
    EditText tv2;
    Button filter;
    Spinner sem;
    Spinner discount;

    String str_price;
    String s_sem;
    String s_discount;

    Boolean b_price = true;
    Boolean b_sem = true;
    Boolean b_discount = true;

    DatabaseReference databaseReference;
    Boolean flag;
    ArrayList<Book> list = new ArrayList<>();
    ListView listView;

    public static Book publicBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_filter);
        s_price = findViewById(R.id.price_seekBar);
        t_selPrice = findViewById(R.id.selPrice);
        sem = findViewById(R.id.semester);
        discount = findViewById(R.id.discount);
        filter = findViewById(R.id.filter);
        listView = findViewById(R.id.listviewfilter);
        tv2 = findViewById(R.id.textView2);

        listView.setVisibility(View.GONE);

        s_price.setMax(2000);
        s_price.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                i = (Math.round(i/50))*50;
                seekBar.setProgress(i);
                t_selPrice.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    s_sem = sem.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        discount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    s_discount = discount.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                str_price = t_selPrice.getText().toString();

                s_price.setVisibility(View.GONE);
                t_selPrice.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                sem.setVisibility(View.GONE);
                discount.setVisibility(View.GONE);
                filter.setVisibility(View.GONE);

                listView.setVisibility(View.VISIBLE);


                if(str_price.equals(""))
                {
                    str_price = "0";
                    b_price = false;
                }
                if(s_sem.equals("Choose Semester"))
                {
                    b_sem = false;
                }
                if(s_discount.equals("Choose Discount"))
                {
                    s_discount = "0";
                    b_discount = false;
                }


                databaseReference = FirebaseDatabase.getInstance().getReference("Books");
                databaseReference.orderByChild("bookName").endAt("\\uf8ff").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Book book = dataSnapshot.getValue(Book.class);
                        flag = true;

                        if(b_price && !(Integer.parseInt(book.getPrice()) <= Integer.parseInt(str_price)))
                        {
                            flag = false;
                        }
                        if(b_sem  && !book.getSem().equals(s_sem))
                        {
                            flag = false;
                        }
                        if(b_discount && !(Integer.parseInt(book.getDiscount()) >= Integer.parseInt(s_discount.substring(10,12))))
                        {
                            flag = false;
                        }

                        if(flag)
                        {
                            list.add(book);
                            BookListAdapter adapter = new BookListAdapter(filterActivity.this, R.layout.adapter_view_layout, list);
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
                    t_selPrice.setVisibility(View.VISIBLE);
                    t_selPrice.setText("No Match Found!");
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                publicBook = list.get(i);
                HomeFragment.where = 2;
                startActivity(new Intent(filterActivity.this,BookShowActivity.class));
            }
        });
    }

    public static Book getBook()
    {
        return publicBook;
    }

}
