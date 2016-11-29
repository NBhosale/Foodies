package com.foodies.nero.foodies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddRecipesPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button saveButton;
    private EditText nameRecipe, typeRecipe, imageRecipe, originRecipe, descriptionRecipe;
    public DatabaseReference firebaseDatabase;
    private String nameRecipeHolder, typeRecipeHolder, imageRecipeHolder, originRecipeHolder, descriptionRecipeHolder;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipes_page, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        saveButton = (Button) view.findViewById(R.id.buttonSave);
        nameRecipe = (EditText) view.findViewById(R.id.editTextRecipeName);
        typeRecipe = (EditText) view.findViewById(R.id.editTextRecipeType);
        imageRecipe = (EditText) view.findViewById(R.id.editTextImageUrl);
        originRecipe = (EditText) view.findViewById(R.id.editTextRecipeOrigin);
        descriptionRecipe = (EditText) view.findViewById(R.id.editTextDescription);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameRecipeHolder = nameRecipe.getText().toString();
                typeRecipeHolder = typeRecipe.getText().toString();
                imageRecipeHolder = imageRecipe.getText().toString();
                originRecipeHolder = originRecipe.getText().toString();
                descriptionRecipeHolder = descriptionRecipe.getText().toString();

                if(TextUtils.isEmpty(nameRecipeHolder)){
                    Toast.makeText(getActivity().getApplication(), "Recipe Name Cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(typeRecipeHolder)){
                    Toast.makeText(getActivity().getApplication(), "Recipe Type Cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(imageRecipeHolder)){
                    Toast.makeText(getActivity().getApplication(), "Recipe image Cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(TextUtils.isEmpty(originRecipeHolder)){
                    Toast.makeText(getActivity().getApplication(), "Recipe origin Cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(descriptionRecipeHolder)){
                    Toast.makeText(getActivity().getApplication(), "Recipe description Cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Recipe recipe = new Recipe(nameRecipeHolder, typeRecipeHolder, imageRecipeHolder, originRecipeHolder, descriptionRecipeHolder);
                firebaseDatabase.child("Recipes").child(nameRecipeHolder).setValue(recipe);
                Toast.makeText(getActivity().getApplication(), "Recipe Added!!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
