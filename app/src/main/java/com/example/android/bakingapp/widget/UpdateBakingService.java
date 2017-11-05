package com.example.android.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.android.bakingapp.data.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joseluis on 3/11/2017.
 */

public class UpdateBakingService extends IntentService {
    public static String UPDATE_WIDGET_FROM_DETAIL_ACTIVITY = "updateWidgetFromDetailActivity";
    public static String UPDATE_WIDGET_TITLE_FROM_RECIPE = "UpdateWidgetTileFromRecipe";

    public UpdateBakingService() {
        super("UpdateBakingService");
    }

    public static void updateBakingWidget(Context context, List<Recipe.Ingredient> ingredients, String titleRecipe) {
        Intent intent = new Intent(context, UpdateBakingService.class);

        final ArrayList<Recipe.Ingredient> arrayListIngredients = new ArrayList<>();
        for (Recipe.Ingredient ingredient : ingredients) {
            arrayListIngredients.add(ingredient);
        }

        intent.putExtra(UPDATE_WIDGET_FROM_DETAIL_ACTIVITY, arrayListIngredients);
        intent.putExtra(UPDATE_WIDGET_TITLE_FROM_RECIPE, titleRecipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<Recipe.Ingredient> ingredientArrayList = (ArrayList<Recipe.Ingredient>) intent.getExtras().get(UPDATE_WIDGET_FROM_DETAIL_ACTIVITY);
            String titleRecipe = intent.getExtras().getString(UPDATE_WIDGET_TITLE_FROM_RECIPE);
            handleActionUpdateBakingWidget(ingredientArrayList, titleRecipe);
        }
    }

    private void handleActionUpdateBakingWidget(ArrayList<Recipe.Ingredient> ingredients, String titleRecipe) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra(UPDATE_WIDGET_FROM_DETAIL_ACTIVITY, ingredients);
        intent.putExtra(UPDATE_WIDGET_TITLE_FROM_RECIPE, titleRecipe);
        sendBroadcast(intent);
    }
}
