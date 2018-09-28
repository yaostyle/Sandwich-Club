package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {
    // Init vars
    // Define static position to identify if there's data received
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // Create private TextViews
    private TextView mainNameTextView;
    private TextView alsoKnownAsTextView;
    private TextView placeOfOriginTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Connect image's View
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        // If there's no intent, show error and close
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        // Retrieve intent extra data
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        // Create a sandwiches string var from string array
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        // Create a json var from the position that was received from intent extra
        String json = sandwiches[position];
        // Parse json data
        Sandwich sandwich = JsonUtils.parseSandwichJson(json, getApplicationContext());

        // If there's no sandwich data, show error and close
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        // Populate UI views
        populateUI(sandwich);

        // Grab the image using Picasso
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        // Set the current title based on current sandwich name
        setTitle(sandwich.getMainName());
    }

    // method to close the activity and make a Toast message
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    // method to populate ui views
    private void populateUI(Sandwich sandwichData) {
        // Connect all views by its id
        mainNameTextView = findViewById(R.id.tv_main_name);
        alsoKnownAsTextView = findViewById(R.id.tv_also_known_as);
        placeOfOriginTextView = findViewById(R.id.tv_place_of_origin);
        descriptionTextView = findViewById(R.id.tv_description);
        ingredientsTextView = findViewById(R.id.tv_ingredients);

        // Concatenating aka list into a single string
        String akaList = "";
        akaList = TextUtils.join(", ", sandwichData.getAlsoKnownAs());

        // Concatenating ingredient list into a single string
        String ingredientList = "";
        ingredientList = TextUtils.join(", ", sandwichData.getIngredients());

        // Set current data to the views
        mainNameTextView.setText(sandwichData.getMainName());
        alsoKnownAsTextView.setText(akaList);
        placeOfOriginTextView.setText(sandwichData.getPlaceOfOrigin());
        descriptionTextView.setText(sandwichData.getDescription());
        ingredientsTextView.setText(ingredientList);
    }
}
