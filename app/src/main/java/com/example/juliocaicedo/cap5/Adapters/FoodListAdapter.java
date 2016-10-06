package com.example.juliocaicedo.cap5.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.juliocaicedo.cap5.Models.Food;
import com.example.juliocaicedo.cap5.R;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by juliocaicedo on 29/9/16.
 */
public class FoodListAdapter extends ArrayAdapter<Food> {

    private List<Food> foods;

    public FoodListAdapter(Context context, int resource, List<Food> objects) {
        super(context, resource, objects);
        foods = objects;
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Food getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Food food = getItem(position);
        //Aqui entra cuando hay nuevos convertView por llenas
        if(convertView == null){
            int resource = food.isFeatured()?R.layout.list_item_food_featured:R.layout.list_item_food;
            convertView = getInflater().inflate(resource, null);
        }
        //Cuando no es null hace las otras funciones de la seccion Reutilizables

        //customize view


        TextView labelName = (TextView) convertView.findViewById(R.id.label_name);
        labelName.setText(food.getName());
        TextView labelType = (TextView) convertView.findViewById(R.id.label_type);
        labelType.setText(food.getType().toString());
        TextView labelPrice = (TextView) convertView.findViewById(R.id.label_price);
        labelPrice.setText(food.getPrice() + "Bs");

        return convertView;
    }

    private LayoutInflater getInflater(){
        return LayoutInflater.from(getContext());
    }

    //Reutilizable
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isFeatured()?1:0;
    }
}
