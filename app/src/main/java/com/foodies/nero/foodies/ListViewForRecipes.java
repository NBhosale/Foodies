package com.foodies.nero.foodies;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListViewForRecipes extends AppCompatActivity {
    private ArrayList<String> itemName;
    private ArrayList<String> origin;
    ArrayList<Bitmap> bitmapArray;
    ArrayList<String> discription;
    ArrayList<String> urlOfRecipe;
    private ListView list;
    String IMAGE_URL = "";
    String arrayOfRecipeObjects[];
    String cousingTypes[];
    String foodToLaunch;
    static String foodType;
    CustomListAdapter adapter;
    int activityToLaunch;
    private ProgressDialog progressDialog;
    //FirebaseDatabase ref = new FirebaseDatabase();
    Firebase fireBaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        arrayOfRecipeObjects = new String [5];
        setContentView(R.layout.activity_list_view_for_recipes);
        cousingTypes = getResources().getStringArray(R.array.differentcousins);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        Intent intent = getIntent();
        foodToLaunch = intent.getStringExtra("CousinName");
        foodType = intent.getStringExtra("typeOfFood");
        if(foodType.equals("Starter")){
            activityToLaunch = 1;
        }else if(foodType.equals("Dessert")){
            activityToLaunch = 2;
        }
        else if(foodType.equals("Main Course")){
            activityToLaunch = 3;
        }
        list = (ListView) findViewById(R.id.list);
        Firebase.setAndroidContext(this);
        bitmapArray = new ArrayList<Bitmap>();
        discription = new ArrayList<String>();
        urlOfRecipe = new ArrayList<String>();
        itemName = new ArrayList<String>();
        origin  = new ArrayList<String>();
        fireBaseReference =  new Firebase("https://foodies-2f4d9.firebaseio.com/Recipes");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
                adapter = new CustomListAdapter(ListViewForRecipes.this, itemName, origin, bitmapArray);
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);

            }

            @Override
            protected Void doInBackground(Void... params) {

                fireBaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        int i  =0;
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            Recipe recipe = postSnapshot.getValue(Recipe.class);
                            //System.out.println(recipe.getNameOfRecipe() + " - " + recipe.getImageURL() + " - " + recipe.getDescription() + " - "+recipe.OriginOfRecipe);
                            String foodTypeholder = recipe.getTypeOfRecipe().toString();
                            String originOfRecipeHolder = recipe.getOriginOfRecipe().toString();
                            Log.d("Debug", originOfRecipeHolder);
                            if(foodTypeholder.equals(foodType) && originOfRecipeHolder.equals(foodToLaunch)){
                                //Log.d("Debug","Type of food "+foodType +" Name of recipe: "+recipe.getNameOfRecipe() +" ----- "+ recipe.getImageURL()+ " Cousine type "+recipe.getOriginOfRecipe().toString());
                                itemName.add(recipe.getNameOfRecipe());
                                origin.add(recipe.getOriginOfRecipe());
                                discription.add(recipe.getDescription());
                                urlOfRecipe.add(recipe.getImageURL());
                                IMAGE_URL = recipe.getImageURL();
                                try{
                                    URL url = new URL(IMAGE_URL);
                                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                    connection.setDoInput(true);
                                    connection.connect();
                                    InputStream input = connection.getInputStream();
                                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                                    //InputStream in = new URL(IMAGE_URL).openStream();
                                    //bitmap = BitmapFactory.decodeStream(in);
                                    bitmapArray.add(bitmap);
                                }
                                catch(Exception E){
                                    Bitmap bitmap =BitmapFactory.decodeResource(getResources(),R.mipmap.foodieslogo);
                                    bitmapArray.add(bitmap);
                                }


                            }

                        }

                        progressDialog.dismiss();
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
                return null;
            }
        }.execute();
        adapter = new CustomListAdapter(ListViewForRecipes.this, itemName, origin, bitmapArray);

        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("debug", itemName.size() +" -- "+ discription.size()  +" -- "+ urlOfRecipe.size()   +" -- "+  origin.size());
                if(itemName.size() > 0 && discription.size() >0 && urlOfRecipe.size() > 0 && origin.size() > 0){
                arrayOfRecipeObjects[0] = itemName.get(position);
                arrayOfRecipeObjects[1] = discription.get(position);
                arrayOfRecipeObjects[2] = urlOfRecipe.get(position);
                arrayOfRecipeObjects[3] = origin.get(position);
                arrayOfRecipeObjects[4] = foodType;}
                Intent openDisplayRecipe = new Intent(ListViewForRecipes.this, DisplayRecipe.class);
                openDisplayRecipe.putExtra("RecipeArray", arrayOfRecipeObjects);
                startActivity(openDisplayRecipe);
             }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivityNavigation.class);
                intent.putExtra("StartFragmentNUmber",activityToLaunch);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
