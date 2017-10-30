package com.example.android.bakingapp.api;

import com.example.android.bakingapp.data.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by joseluis on 28/10/2017.
 */

public interface RecipeDetailApi {

    @GET("2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}
