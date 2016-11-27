package com.foodies.nero.foodies;

/**
 * Created by Nero on 26/11/2016.
 */

public class Recipe {

    public String NameOfRecipe;
    public String TypeOfRecipe;
    public String ImageURL;
    public String OriginOfRecipe;
    public String Description;

    public Recipe(String nameOfRecipe, String typeOfRecipe, String imageURL, String originOfRecipe, String description) {
        NameOfRecipe = nameOfRecipe;
        TypeOfRecipe = typeOfRecipe;
        ImageURL = imageURL;
        OriginOfRecipe = originOfRecipe;
        Description = description;
    }

    public Recipe() {
    }
}
