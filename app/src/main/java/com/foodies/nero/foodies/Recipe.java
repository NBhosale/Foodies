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

    public String getNameOfRecipe() {
        return NameOfRecipe;
    }

    public void setNameOfRecipe(String nameOfRecipe) {
        NameOfRecipe = nameOfRecipe;
    }

    public String getTypeOfRecipe() {
        return TypeOfRecipe;
    }

    public void setTypeOfRecipe(String typeOfRecipe) {
        TypeOfRecipe = typeOfRecipe;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getOriginOfRecipe() {
        return OriginOfRecipe;
    }

    public void setOriginOfRecipe(String originOfRecipe) {
        OriginOfRecipe = originOfRecipe;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

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
