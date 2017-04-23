package com.example.ganesh.theverge;


import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TheVergeAdapter extends ArrayAdapter<TheVergePojo> {

    private static final String LOG_TAG = TheVergeAdapter.class.getName();


    public TheVergeAdapter(@NonNull Context context, List<TheVergePojo> theVergePojos) {
        super(context, 0, theVergePojos);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {

            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.the_verge_blog, parent, false);

        }

        TheVergePojo currentBlog = getItem(position);

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author_name);
        authorTextView.setText(currentBlog.getAuthor());

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.blog_title);
        titleTextView.setText(currentBlog.getTitle());

        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.blog_desc);
        descriptionTextView.setText(currentBlog.getDescription());

        ImageView urlToImageView = (ImageView) listItemView.findViewById(R.id.the_verge_imageview);

        Picasso.with(getContext()).load(currentBlog.getUrlToimage()).into(urlToImageView);

        return listItemView;

    }


}
