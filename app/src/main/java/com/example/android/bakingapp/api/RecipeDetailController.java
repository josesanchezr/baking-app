package com.example.android.bakingapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by joseluis on 28/10/2017.
 */

public class RecipeDetailController {

    public static final String RECIPE_DETAIL_API_URL = "https://d17h27t6h515a5.cloudfront.net/topher/";

    public final RecipeDetailApi recipeDetailApi;

    public RecipeDetailController() {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPE_DETAIL_API_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        recipeDetailApi = retrofit.create(RecipeDetailApi.class);
    }
}
