package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;

import java.util.List;

/**
 * Created by joseluis on 12/11/2017.
 */

public class RecipeIngredientsAdapter extends ArrayAdapter<Recipe.Ingredient> {

    private List<Recipe.Ingredient> ingredientList;

    public RecipeIngredientsAdapter(Context context, List<Recipe.Ingredient> ingredients) {
        super(context, 0, ingredients);
        this.ingredientList = ingredients;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Recipe.Ingredient ingredient = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_baking_widget, parent, false);
        }
        TextView recipeIngredientName = (TextView) convertView.findViewById(R.id.recipe_ingredient_name);
        TextView recipeIngredientQuantity = (TextView) convertView.findViewById(R.id.recipe_ingredient_quantity);

        recipeIngredientName.setText(ingredient.ingredient);
        recipeIngredientQuantity.setText(getContext().getString(R.string.ingredient_quantity_text,
                String.valueOf(ingredient.quantity), ingredient.measure));

        return convertView;
    }
}
