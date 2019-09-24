package com.example.zwoopit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookListAdapter extends ArrayAdapter<Book> {
    private Context mContext;
    private int mRes;

    public BookListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Book> objects) {
        super(context, resource, objects);
        mContext = context;
        mRes = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position).getBookName().toUpperCase();
        String aName = getItem(position).getAuthorName().toUpperCase();
        String edition = getItem(position).getEdition();
        String publication = getItem(position).getPublication().toUpperCase();
        String discount = getItem(position).getDiscount();
        String price = getItem(position).getPrice();


        if(discount.equals(0))
        {
            Double cost;
            cost = (1 - Double.parseDouble(discount) / 100) * Double.parseDouble(price);
            price += " => " + cost.toString();
        }
        Book book= new Book();
        book.setBookName(name);
        book.setAuthorName(aName);
        book.setEdition(edition);
        book.setPublication(publication);
        book.setDiscount(discount);
        book.setPrice(price);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mRes,parent,false);


        TextView tvName = convertView.findViewById(R.id.view_bookname);
        TextView tvAName = convertView.findViewById(R.id.view_authorname);
        TextView tvEdition = convertView.findViewById(R.id.view_edition);
        TextView tvPublication = convertView.findViewById(R.id.view_publication);
        TextView tvDiscount = convertView.findViewById(R.id.view_discount);
        TextView tvPrice = convertView.findViewById(R.id.view_price);

        tvName.setText(name);
        tvAName.setText(aName);
        tvEdition.setText(edition);
        tvPublication.setText(publication);
        tvDiscount.setText(discount);
        tvPrice.setText(price);
        return convertView;

    }
}
