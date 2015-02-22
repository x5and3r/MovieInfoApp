package com.example.sanya.ua.movieinformerjb;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity extends Activity {
    public static final String EXTRA_QUERY = "com.example.sanya.ua.movieinformerjb.QUERY";

    private EditText editText;
    private Button searchB;
    private String titleFind;

    //TextWatcher
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldForEmptyValues();
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        titleFind = editText.getText().toString();
        editText.addTextChangedListener(textWatcher);
        checkFieldForEmptyValues();
    }
    private  void checkFieldForEmptyValues() {
        searchB = (Button) findViewById(R.id.button);
        titleFind = editText.getText().toString();
        titleFind = titleFind.replaceAll("[\\s]{2,}", " ");
        if(titleFind.equals("") || titleFind.equals(" "))
            searchB.setEnabled(false);
        else
            searchB.setEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buttonClick(View view) {
        String query = "";
        switch (view.getId()) {
            case R.id.button:
                query = "http://api.themoviedb.org/3/search/movie?";
                titleFind = titleFind.replace(" ","+");
                callToTMDB(query + "query=" + titleFind + "&");
                break;
            case R.id.button2:
                query = "http://api.themoviedb.org/3/movie/popular?";
                callToTMDB(query);
                break;
            case R.id.button3:
                query = "http://api.themoviedb.org/3/movie/upcoming?";
                callToTMDB(query);
                break;
            case  R.id.button4:
                query = "http://api.themoviedb.org/3/movie/top_rated?";
                callToTMDB(query);
                break;
        }
    }
    public void callToTMDB(String query) {
        Intent intent = new Intent(this, ListsOfMovies.class);
        intent.putExtra(EXTRA_QUERY, query);
        startActivity(intent);
    }
}
