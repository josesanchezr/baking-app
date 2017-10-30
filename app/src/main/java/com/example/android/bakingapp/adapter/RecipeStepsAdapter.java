package com.example.android.bakingapp.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeStepDetailActivity;
import com.example.android.bakingapp.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by joseluis on 28/10/2017.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsViewHolder> {

    private List<Recipe.Step> stepList;

    public RecipeStepsAdapter(List<Recipe.Step> steps, OnItemClickListener outsideHear) {
        this.stepList = steps;
        this.outsideHear = outsideHear;
    }


    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder {

        public TextView shortDescription;
        public TextView description;
        public ImageView thumbnail;
        public View item;

        public RecipeStepsViewHolder(View itemView) {
            super(itemView);
            shortDescription = (TextView) itemView.findViewById(R.id.short_description_recipe_step);
            description = (TextView) itemView.findViewById(R.id.description_recipe_step);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail_recipe_step);
            item = itemView;
        }
    }

    @Override
    public RecipeStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_step, parent, false);

        return new RecipeStepsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecipeStepsViewHolder holder, int position) {
        final Recipe.Step step = stepList.get(position);
        holder.shortDescription.setText(step.id + ". " + step.shortDescription);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.description.setText(Html.fromHtml(step.description, Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.description.setText(Html.fromHtml(step.description));
        }

        String image = step.thumbnailURL;
        if (image.isEmpty() || image.substring(image.lastIndexOf('.') + 1).equals("mp4")) {
            image = "http://images.familycircle.mdpcdn.com/sites/familycircle.com/files/styles/slide/public/slide/R146601.jpg";
        }
        Picasso.with(holder.item.getContext())
                .load(image)
                .resize(120, 120)
                .centerCrop()
                .into(holder.thumbnail);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outsideHear.onClick(holder, step);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public interface OnItemClickListener {
        public void onClick(RecipeStepsViewHolder viewHolder, Recipe.Step step);
    }

    private OnItemClickListener outsideHear;
}
