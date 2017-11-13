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
    public static final String RECIPE_STEP_ACTIVITY = "recipe_step_activity";
    private Recipe recipe;
    private Recipe.Step step;
    private boolean twoPanels;

    private static final String FRAGMENT_RECIPE_DETAIL = "fragmentRecipeDetail";
    private static final String FRAGMENT_RECIPE_STEP_DETAIL = "fragmentRecipeStepDetail";
    private RecipeDetailFragment fragmentRecipeDetail;
    private RecipeStepDetailFragment fragmentRecipeStepDetail;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RECIPE_ACTIVITY, recipe);
        outState.putParcelable(RECIPE_STEP_ACTIVITY, step);
        Log.d("RECIPE-DETAIL", "Guardando recipe " + recipe.name);
        Log.d("RECIPE-DETAIL", "Guardando recipe step" + step.shortDescription);
        getSupportFragmentManager().putFragment(outState, FRAGMENT_RECIPE_DETAIL, fragmentRecipeDetail);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipe = (Recipe) savedInstanceState.getParcelable(RECIPE_ACTIVITY);
        step = (Recipe.Step) savedInstanceState.getParcelable(RECIPE_STEP_ACTIVITY);
        Log.d("RECIPE-DETAIL", "Restaurando recipe " + recipe.name);
        Log.d("RECIPE-DETAIL", "Restaurando recipe step" + step.shortDescription);
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

            fragmentRecipeDetail = new RecipeDetailFragment();
            fragmentRecipeDetail.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_fragment, fragmentRecipeDetail)
                    .commit();

            step = recipe.steps.get(0);

        } else {
            recipe = (Recipe) savedInstanceState.getParcelable(RECIPE_ACTIVITY);
            step = (Recipe.Step) savedInstanceState.getParcelable(RECIPE_STEP_ACTIVITY);
            Log.d("RECIPE-DETAIL", "Restaurando recipe " + recipe.name);
            Log.d("RECIPE-DETAIL", "Restaurando recipe step" + step.shortDescription);

            fragmentRecipeDetail = (RecipeDetailFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, FRAGMENT_RECIPE_DETAIL);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_fragment, fragmentRecipeDetail)
                    .commit();
        }

        boolean isTable = getResources().getBoolean(R.bool.esTablet);
        if (isTable) {
            twoPanels = true;
            loadRecipeStepDetail(step);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("RECIPE-DETAIL", recipe.name);
        getSupportActionBar().setTitle(recipe.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadRecipeStepDetail(Recipe.Step step) {
        Bundle argumentsDetail = new Bundle();
        argumentsDetail.putParcelable(RecipeStepDetailFragment.RECIPE_STEP_FRAGMENT, step);

        fragmentRecipeStepDetail = new RecipeStepDetailFragment();
        fragmentRecipeStepDetail.setArguments(argumentsDetail);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_step_fragment, fragmentRecipeStepDetail)
                .commit();
    }

    @Override
    public void onSelectRecipeStep(Recipe.Step step) {
        this.step = step;
        if (twoPanels) {
            loadRecipeStepDetail(step);
        } else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(RecipeStepDetailActivity.RECIPE_STEP_ACTIVITY, step);
            startActivity(intent);
        }
    }
}
