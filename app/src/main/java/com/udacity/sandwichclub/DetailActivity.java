package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mainNameTextView;
    private TextView alsoKnownAsTextView;
    private TextView placeOfOriginTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;

    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json, getApplicationContext());
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwichData) {

        mainNameTextView = findViewById(R.id.tv_main_name);
        alsoKnownAsTextView = findViewById(R.id.tv_also_known_as);
        placeOfOriginTextView = findViewById(R.id.tv_place_of_origin);
        descriptionTextView = findViewById(R.id.tv_description);
        ingredientsTextView = findViewById(R.id.tv_ingredients);

        String akaList = "";
        int akaDataCount = sandwichData.getAlsoKnownAs().size();
        for (int i=0; i<akaDataCount; i++) {
            akaList += sandwichData.getAlsoKnownAs().get(i);
            if (i>0 && i<akaDataCount-1){
                akaList += ", ";
            }
        }

        String ingredientList = "";
        int ingCount = sandwichData.getIngredients().size();
        for (int x=0; x<ingCount; x++) {
            ingredientList += sandwichData.getIngredients().get(x);
            if (x>0 && x<ingCount-1) {
                ingredientList += ", ";
            }
        }

        mainNameTextView.setText(sandwichData.getMainName());
        alsoKnownAsTextView.setText(akaList);
        placeOfOriginTextView.setText(sandwichData.getPlaceOfOrigin());
        descriptionTextView.setText(sandwichData.getDescription());
        ingredientsTextView.setText(ingredientList);

    }
}
