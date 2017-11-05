package com.example.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.BakingWidgetProvider;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.api.RecipeDetailController;
import com.example.android.bakingapp.data.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by joseluis on 30/10/2017.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private List<Recipe.Ingredient> ingredientList;
    private Context context;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDataSetChanged() {
        ingredientList = BakingWidgetProvider.ingredientArrayList;
    }

    @Override
    public int getCount() {
        if (ingredientList == null) {
            Log.d("RECIPE", "2. Cero ingredientes");
            return 0;
        }
        Log.d("RECIPE", "3. ingredientes");
        return ingredientList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (ingredientList.size() == 0) {
            Log.d("RECIPE", "Cero ingredientes");
            return null;
        }
        Log.d("RECIPE", "Creando el RemoteView");
        Recipe.Ingredient ingredient = ingredientList.get(position);

        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.item_baking_widget);
        remoteViews.setTextViewText(R.id.recipe_ingredient_name, ingredient.ingredient);
        remoteViews.setTextViewText(R.id.recipe_ingredient_quantity,
                context.getString(R.string.ingredient_quantity_text,
                        String.valueOf(ingredient.quantity), ingredient.measure));
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
