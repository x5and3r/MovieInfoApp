package com.example.sanya.ua.movieinformerjb;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/* Adapter for filling the rows in list that should contain results of queries:
Popular/Upcoming/Top_rated/Search */
public class MovieAdapter extends ArrayAdapter<MovieResult>{
    Context context;
    int layoutResourceId;
    ArrayList<MovieResult> data = null;
    public static ThreadPolicy tp = ThreadPolicy.LAX;

    public MovieAdapter(Context context, int layoutResourceId, ArrayList<MovieResult> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MovieHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new MovieHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtDate = (TextView)row.findViewById(R.id.tvDate);
            holder.txtVoteAver = (TextView)row.findViewById(R.id.tvVote);

            row.setTag(holder);
        }
        else
            holder = (MovieHolder)row.getTag();

        MovieResult movie = data.get(position);
        Bitmap bmp = loadBitmap(movie.getBackdropPath());
        holder.txtTitle.setText(movie.getTitle());
        holder.txtDate.setText(movie.getReleaseDate());
        holder.txtVoteAver.setText("" + movie.getVoteAverage());
        holder.imgIcon.setImageBitmap(bmp);

        return row;
    }

    static class MovieHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtDate;
        TextView txtVoteAver;
    }
    //Function for loading Bitmap image from url
    public static Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            StrictMode.setThreadPolicy(tp);
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }
}