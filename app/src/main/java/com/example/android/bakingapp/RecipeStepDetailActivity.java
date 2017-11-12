package com.example.android.bakingapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.android.bakingapp.data.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailActivity extends AppCompatActivity {

    public static final String RECIPE_STEP_ACTIVITY = "recipe_step_activity";

    @BindView(R.id.toolbar_recipe_step)
    Toolbar toolbar_recipe_step;

    RecipeStepDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_recipe_step);

        if (!getResources().getBoolean(R.bool.esTablet)) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            Recipe.Step step = getIntent().getParcelableExtra(RECIPE_STEP_ACTIVITY);
            arguments.putParcelable(RecipeStepDetailFragment.RECIPE_STEP_FRAGMENT, step);

            fragment = new RecipeStepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_step_fragment, fragment)
                    .commit();
        } else {
            fragment = (RecipeStepDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "recipeStepDetailFragment");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_step_fragment, fragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, "recipeStepDetailFragment", fragment);
        super.onSaveInstanceState(outState);
    }
}
