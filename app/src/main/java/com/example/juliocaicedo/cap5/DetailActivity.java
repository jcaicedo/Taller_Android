package com.example.juliocaicedo.cap5;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juliocaicedo.cap5.Models.Food;

/**
 * Created by juliocaicedo on 6/10/16.
 */
public class DetailActivity extends AppCompatActivity{

    Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.food_detail);

        food = (Food) getIntent().getSerializableExtra("food");

        //configure app bar
        configureAppBar();
        
        //display content
        displayContent(food);
    }

    private void displayContent(Food food) {
        TextView labelName = (TextView) findViewById(R.id.label_name);
        labelName.setText(food.getName());
        TextView labelType = (TextView) findViewById(R.id.label_type);
        labelType.setText(food.getType().getValue());
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.pizza);
        View viewType = findViewById(R.id.view_type);
        viewType.setBackgroundColor(Color.parseColor(food.getType().getBackground()));
    }

    private void configureAppBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(food.getName());
    }
    
    
}
