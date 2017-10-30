package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.bakingapp.adapter.RecipesAdapter;
import com.example.android.bakingapp.api.RecipeDetailController;
import com.example.android.bakingapp.data.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipes_grid)
    GridView recipesGridView;

    private RecipeDetailController recipeDetailController;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recipeDetailController = new RecipeDetailController();
        if (savedInstanceState == null) {
            recipeDetailController.recipeDetailApi.getRecipes().enqueue(recipesCallback);
        }

        recipesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Recipe recipe = (Recipe) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(), RecipeDetailActivity.class);
                intent.putExtra(RecipeDetailActivity.RECIPE_ACTIVITY, recipe);

                startActivity(intent);
            }
        });
    }

    private final Callback<List<Recipe>> recipesCallback = new Callback<List<Recipe>>() {
        @Override
        public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
            if (response.isSuccessful()) {
                recipeList = response.body();
                Log.d("RECIPE", "Cantidad de recetas " + String.valueOf(recipeList.size()));
                RecipesAdapter adapter = new RecipesAdapter(getApplicationContext(), recipeList);
                recipesGridView.setAdapter(adapter);
            } else {
                Log.d("RECIPE", "Ha ocurrido un error: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Recipe>> call, Throwable t) {
            t.printStackTrace();
        }
    };
}
