package com.example.android.bakingapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by joseluis on 28/10/2017.
 */

public class RecipesAdapter extends ArrayAdapter<Recipe> {

    public RecipesAdapter(Context context, List<Recipe> recipes) {
        super(context, 0, recipes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Recipe recipe = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_recipe, parent, false);
        }

        if (recipe != null) {
            ImageView iconView = convertView.findViewById(R.id.item_image);
            String imagen = recipe.image;
            if (TextUtils.isEmpty(recipe.image)) {
                imagen = "http://images.familycircle.mdpcdn.com/sites/familycircle.com/files/styles/slide/public/slide/R146601.jpg";
            }
            Picasso.with(getContext())
                    .load(imagen)
                    .resize(200, 200)
                    .centerCrop()
                    .into(iconView);

            TextView nameRecipe = (TextView) convertView.findViewById(R.id.name_recipe);
            nameRecipe.setText(recipe.name);
        }
        return convertView;
    }
}
