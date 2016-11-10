package com.example.juliocaicedo.cap5;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.juliocaicedo.cap5.Adapters.FoodListAdapter;
import com.example.juliocaicedo.cap5.Models.Food;
import com.example.juliocaicedo.cap5.Models.FoodFactory;
import com.example.juliocaicedo.cap5.Models.FoodType;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    FoodListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new HttpAsyncTask().execute("http://androidlabs.miro.beecloud.me/api/food.json");
        //configure app bar
        
        configureAppBar();
        
        
        //set view references

        //final List<Food> foods = FoodFactory.getJokes();
        //Log.d("Lab06", String.valueOf(foods));
        //listView = (ListView) findViewById(R.id.listView);
        //configureListView(foods);

    }

    private void configureAppBar() {
        getSupportActionBar().setTitle(R.string.home_title);
    }

    private void configureListView(List<Food> foods){
        adapter = new FoodListAdapter(this, 0, foods);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Food food = adapter.getItem(i);
                Toast.makeText(MainActivity.this,"Item clicked: "+ food.toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("food", food);
                startActivity(intent);

            }
        });
    }



    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
            }
            else {
                result = "Did not work!";
            }

        }
        catch (Exception e) {
            Log.e("Lab06", e.getLocalizedMessage());
        }

        return result;
    }

    public static JSONObject getJSON(String url) {
        String responseText = GET(url);
        JSONObject json = null;
        try {
            json = new JSONObject(responseText);
            return json;
        }
        catch (JSONException e) {
            Log.e("Lab06", e.getMessage());
        }
        return null;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        inputStream.close();
        return result;
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        else {
            return false;
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... urls) {
            return getJSON(urls[0]);
            //return GET(urls[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if( result != null ) {
                try {
                    JSONArray list = result.getJSONArray("list");
                    List<Food> foods = new ArrayList<>();
                    for (int i=0; i<list.length();i++){
                        Food food = new Food ();
                        food.setId(list.getJSONObject(i).getInt("id"));
                        food.setName(list.getJSONObject(i).getString("name"));
                        food.setPicture(list.getJSONObject(i).getString("picture_url"));
                        food.setPrice((float) list.getJSONObject(i).getDouble("price"));
                        food.setFeatured(list.getJSONObject(i).getBoolean("featured"));
                        FoodType foodtype = FoodType.fromString(list.getJSONObject(i).getJSONObject("type").getString("name"));
                        food.setType(foodtype);

                        foods.add(food);
                    }

                    listView = (ListView) findViewById(R.id.listView);
                    configureListView(foods);

                }
                catch(JSONException e) {
                    // do nothing?
                }
                Toast.makeText(getBaseContext(), "Received Response!", Toast.LENGTH_LONG).show();
            }
        }

    }




}
