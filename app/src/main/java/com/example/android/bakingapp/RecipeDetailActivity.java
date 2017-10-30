package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.android.bakingapp.data.Recipe;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailFragment.HearRecipeDetailFragment {

    public static final String RECIPE_ACTIVITY = "recipe_activity";
    private Recipe recipe;
    private boolean twoPanels;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RECIPE_ACTIVITY, recipe);
        Log.d("RECIPE-DETAIL", "Guardando recipe " + recipe.name);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipe = (Recipe) savedInstanceState.getParcelable(RECIPE_ACTIVITY);
        Log.d("RECIPE-DETAIL", "Restaurando recipe " + recipe.name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            if (getIntent().getParcelableExtra(RECIPE_ACTIVITY) != null) {
                recipe = getIntent().getParcelableExtra(RECIPE_ACTIVITY);
            }

            arguments.putParcelable(RecipeDetailFragment.RECIPE_FRAGMENT, recipe);

            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_fragment, fragment)
                    .commit();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Log.d("RECIPE-DETAIL", recipe.name);
            getSupportActionBar().setTitle(recipe.name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (findViewById(R.id.recipe_step_fragment) != null) {
                twoPanels = true;
                loadRecipeStepDetail(recipe.steps.get(0));
            }
        }
    }

    private void loadRecipeStepDetail(Recipe.Step step) {
        Bundle argumentsDetail = new Bundle();
        argumentsDetail.putParcelable(RecipeStepDetailFragment.RECIPE_STEP_FRAGMENT, step);

        RecipeStepDetailFragment fragmentStepDetail = new RecipeStepDetailFragment();
        fragmentStepDetail.setArguments(argumentsDetail);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_step_fragment, fragmentStepDetail)
                .commit();
    }

    @Override
    public void onSelectRecipeStep(Recipe.Step step) {
        if (twoPanels) {
            loadRecipeStepDetail(step);
        } else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(RecipeStepDetailActivity.RECIPE_STEP_ACTIVITY, step);
            startActivity(intent);
        }
    }
}
