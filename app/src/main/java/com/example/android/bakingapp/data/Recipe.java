package com.example.android.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joseluis on 27/10/2017.
 */

public class Recipe implements Parcelable {

    @SerializedName("id")
    public final int id;

    @SerializedName("name")
    public final String name;

    @SerializedName("ingredients")
    public final List<Ingredient> ingredients;

    @SerializedName("steps")
    public final List<Step> steps;

    @SerializedName("servings")
    public final int servings;

    @SerializedName("image")
    public final String image;

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = new ArrayList<>();
        in.readTypedList(ingredients, Ingredient.CREATOR);
        steps = new ArrayList<>();
        in.readTypedList(steps, Step.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    public static final class Ingredient implements Parcelable {

        @SerializedName("quantity")
        public final double quantity;

        @SerializedName("measure")
        public final String measure;

        @SerializedName("ingredient")
        public final String ingredient;

        protected Ingredient(Parcel in) {
            quantity = in.readDouble();
            measure = in.readString();
            ingredient = in.readString();
        }

        public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
            @Override
            public Ingredient createFromParcel(Parcel in) {
                return new Ingredient(in);
            }

            @Override
            public Ingredient[] newArray(int size) {
                return new Ingredient[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeDouble(quantity);
            parcel.writeString(measure);
            parcel.writeString(ingredient);
        }
    }

    public static final class Step implements Parcelable {

        @SerializedName("id")
        public int id;

        @SerializedName("shortDescription")
        public String shortDescription;

        @SerializedName("description")
        public String description;

        @SerializedName("videoURL")
        public String videoURL;

        @SerializedName("thumbnailURL")
        public String thumbnailURL;

        protected Step(Parcel in) {
            id = in.readInt();
            shortDescription = in.readString();
            description = in.readString();
            videoURL = in.readString();
            thumbnailURL = in.readString();
        }

        public static final Creator<Step> CREATOR = new Creator<Step>() {
            @Override
            public Step createFromParcel(Parcel in) {
                return new Step(in);
            }

            @Override
            public Step[] newArray(int size) {
                return new Step[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeString(shortDescription);
            parcel.writeString(description);
            parcel.writeString(videoURL);
            parcel.writeString(thumbnailURL);
        }
    }
}
