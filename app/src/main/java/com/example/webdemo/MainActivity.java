package com.example.webdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Download task = new Download();
        String result = null;
        try {
            result = task.execute("https://www.zappycode.com/").get();
        }catch (Exception e){
            e.printStackTrace();
        }
        TextView textView = findViewById(R.id.textView);
        textView.setText(result);
        Log.i("Result:",result); //display result
    }

    public class Download extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) { //runs in the background

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null; //remember to grant permission in the android manifest
            try {
                url = new URL(strings[0]); //strings[0] contains the url passed in from the onCreate method
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data; //reader.read() will return an integer
                do{
                    data = reader.read();
                    char current = (char) data; //convert the integer into char value
                    result += current; //append stream of char values
                }while(data != -1); //int value for end of file is -1
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return "failed"; //in case connection fails
            }
        }
    }
}