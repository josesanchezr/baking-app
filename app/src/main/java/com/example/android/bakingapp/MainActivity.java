package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.bakingapp.adapter.RecipesAdapter;
import com.example.android.bakingapp.api.RecipeDetailController;
import com.example.android.bakingapp.data.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String RECIPE_LIST = "recipe_list";

    @BindView(R.id.recipes_grid)
    GridView recipesGridView;

    private RecipeDetailController recipeDetailController;
    private List<Recipe> recipeList;

    CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource("Network_Call");

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Parcelable> parcelablesRecipes = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            parcelablesRecipes.add(recipe);
        }
        outState.putParcelableArrayList(RECIPE_LIST, parcelablesRecipes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        espressoTestIdlingResource.increment();

        if (savedInstanceState == null) {

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
        } else {
            recipeList = savedInstanceState.getParcelableArrayList(RECIPE_LIST);
            RecipesAdapter adapter = new RecipesAdapter(getApplicationContext(), recipeList);
            recipesGridView.setAdapter(adapter);
            recipesGridView.deferNotifyDataSetChanged();
        }
    }

    private final Callback<List<Recipe>> recipesCallback = new Callback<List<Recipe>>() {
        @Override
        public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
            if (response.isSuccessful()) {
                recipeList = response.body();
                Log.d("RECIPE", "Cantidad de recetas " + String.valueOf(recipeList.size()));
                RecipesAdapter adapter = new RecipesAdapter(getApplicationContext(), recipeList);
                recipesGridView.setAdapter(adapter);

                espressoTestIdlingResource.decrement();
            } else {
                Log.d("RECIPE", "Ha ocurrido un error: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Recipe>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    public CountingIdlingResource getEspressoIdlingResourceForMainActivity() {
        return espressoTestIdlingResource;
    }
}
