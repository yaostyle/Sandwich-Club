package com.udacity.sandwichclub.utils;

import android.content.Context;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json, Context context) {
        // Init vars
        Sandwich parsedSandwichData = new Sandwich();

        // Create json key vars
        final String KEY_MAIN_NAME          = context.getString(R.string.json_key_main_name);
        final String KEY_NAME               = context.getString(R.string.json_key_name);
        final String KEY_ALSO_KNOWN_AS      = context.getString(R.string.json_key_also_known_as);
        final String KEY_PLACE_OF_ORIGIN    = context.getString(R.string.json_key_place_of_origin);
        final String KEY_DESCRIPTION        = context.getString(R.string.json_key_description);
        final String KEY_IMAGE              = context.getString(R.string.json_key_image);
        final String KEY_INGREDIENTS        = context.getString(R.string.json_key_ingredients);

        // Parsing json string
        try {
            // Create a JSONObject from supplied json string
            JSONObject sandwichJsonBaseObj  = new JSONObject(json);
            // Create a JSONObject for "name" key
            JSONObject sandwichJsonNamesObj = sandwichJsonBaseObj.getJSONObject(KEY_NAME);
            // Extract mainName string
            String mainNameString           = sandwichJsonNamesObj.getString(KEY_MAIN_NAME);
            // Create a JSONArray for alsoKnownAs
            JSONArray alsoKnownAsArr        = sandwichJsonNamesObj.getJSONArray(KEY_ALSO_KNOWN_AS);
            // Extract placeOfOrigin string
            String placeOfOriginString      = sandwichJsonBaseObj.getString(KEY_PLACE_OF_ORIGIN);
            // Extract description string
            String descriptionString        = sandwichJsonBaseObj.getString(KEY_DESCRIPTION);
            // Extract image string
            String imageString              = sandwichJsonBaseObj.getString(KEY_IMAGE);
            // Create a JSONArray for ingredients
            JSONArray ingredientsArr        = sandwichJsonBaseObj.getJSONArray(KEY_INGREDIENTS);

            // Create a new ArrayList for alosKnownAs
            ArrayList<String> alsoKnownAsList = new ArrayList<>();
            // Iterate through the json array and add it to ArrayList
            if (alsoKnownAsArr.length() > 0) {
                for (int i = 0; i < alsoKnownAsArr.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsArr.getString(i));
                }
            } else {
                // If json array is empty, set a default value called N/A
                alsoKnownAsList.add(context.getString(R.string.aka_na));
            }

            // Create a new ArrayList for ingredient
            ArrayList<String> ingredientsList = new ArrayList<>();
            // Loop through the json array and add it to ArrayList
            if (ingredientsArr.length() > 0) {
                for (int x = 0; x < ingredientsArr.length(); x++) {
                    ingredientsList.add(ingredientsArr.getString(x));
                }
            }

            // If placeOfOrigin is empty, set a default value called Unknow
            if (placeOfOriginString.isEmpty()) {
                placeOfOriginString = context.getString(R.string.place_of_origin_unknown);
            }

            // Gather all the data and set to parsedSandwichData
            parsedSandwichData = new Sandwich(mainNameString
                                                , alsoKnownAsList
                                                , placeOfOriginString
                                                , descriptionString
                                                , imageString
                                                , ingredientsList);

        } catch (JSONException e) {
            // If there's JSON exception, print to stack
            e.printStackTrace();
        }

        // Return the final Sandwich data
        return parsedSandwichData;
    }

}
