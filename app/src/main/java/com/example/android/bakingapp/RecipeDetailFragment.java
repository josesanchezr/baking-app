package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.bakingapp.adapter.RecipeIngredientsAdapter;
import com.example.android.bakingapp.adapter.RecipeStepsAdapter;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.widget.UpdateBakingService;

public class RecipeDetailFragment extends Fragment implements RecipeStepsAdapter.OnItemClickListener {

    public static final String RECIPE_FRAGMENT = "recipe_fragment";

    private Recipe recipe;

    private HearRecipeDetailFragment hearRecipeDetailFragment;

    public RecipeDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(RECIPE_FRAGMENT)) {
            recipe = getArguments().getParcelable(RECIPE_FRAGMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rooView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        if (recipe != null) {
            ListView recipeIngredientsListView = rooView.findViewById(R.id.recipe_ingredients_listview);
            RecipeIngredientsAdapter recipeIngredientsAdapter = new RecipeIngredientsAdapter(getContext(), recipe.ingredients);
            recipeIngredientsListView.setAdapter(recipeIngredientsAdapter);

            RecyclerView recipeStepRecyclerView = rooView.findViewById(R.id.recipe_steps_recyclerview);
            RecipeStepsAdapter adapter = new RecipeStepsAdapter(recipe.steps, this);
            recipeStepRecyclerView.setAdapter(adapter);

            UpdateBakingService.updateBakingWidget(getContext(), recipe.ingredients, recipe.name);
        }

        return rooView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HearRecipeDetailFragment) {
            hearRecipeDetailFragment = (HearRecipeDetailFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " You must implement HearRecipeDetailFragment interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hearRecipeDetailFragment = null;
    }

    public void loadRecipeStepDetail(Recipe.Step step) {
        if (hearRecipeDetailFragment != null) {
            hearRecipeDetailFragment.onSelectRecipeStep(step);
        }
    }

    @Override
    public void onClick(RecipeStepsAdapter.RecipeStepsViewHolder viewHolder, Recipe.Step step) {
        loadRecipeStepDetail(step);
    }

    public interface HearRecipeDetailFragment {
        void onSelectRecipeStep(Recipe.Step step);
    }
}
