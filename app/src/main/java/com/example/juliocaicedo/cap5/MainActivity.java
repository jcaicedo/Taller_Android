package com.example.juliocaicedo.cap5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.juliocaicedo.cap5.Adapters.FoodListAdapter;
import com.example.juliocaicedo.cap5.Models.Food;
import com.example.juliocaicedo.cap5.Models.FoodFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    FoodListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //configure app bar
        
        configureAppBar();
        
        
        //set view references

        final List<Food> foods = FoodFactory.getJokes();
        listView = (ListView) findViewById(R.id.listView);
        configureListView(foods);

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
}
