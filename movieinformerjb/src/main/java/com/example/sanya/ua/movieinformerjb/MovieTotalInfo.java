package com.example.sanya.ua.movieinformerjb;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.RemoteException;
import android.widget.ImageView;
import android.widget.TextView;


public class MovieTotalInfo extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        //Intent from ListOfMovies.class
        String title = getIntent().getStringExtra("title");
        String dateRel = getIntent().getStringExtra("date");
        double voteAver = getIntent().getDoubleExtra("vote", 0.0);
        double pop = getIntent().getDoubleExtra("popularity", 0.0);
        String posterPath = getIntent().getStringExtra("poster");
        int voteCount = getIntent().getIntExtra("vcount", 0);
        String over = getIntent().getStringExtra("overview");
        ((TextView)findViewById(R.id.txtTitle1)).setText(title);
        ((TextView)findViewById(R.id.tvDate1)).setText(dateRel);
        ((TextView)findViewById(R.id.tvVote1)).setText("" + voteAver);
        ((TextView)findViewById(R.id.tvPop1)).setText("" + pop);
        ((TextView)findViewById(R.id.tvCount1)).setText("" + voteCount);
        ((TextView)findViewById(R.id.tvOver1)).setText(over);
        Bitmap bmp = MovieAdapter.loadBitmap(posterPath);
        ((ImageView)findViewById(R.id.imgIcon1)).setImageBitmap(bmp);

    }

}


