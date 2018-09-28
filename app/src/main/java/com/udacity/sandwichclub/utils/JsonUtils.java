package com.udacity.sandwichclub.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json, Context context) {
        Sandwich parsedSandwichData = new Sandwich();

        final String KEY_MAIN_NAME          = context.getString(R.string.json_key_main_name);
        final String KEY_NAME               = context.getString(R.string.json_key_name);
        final String KEY_ALSO_KNOWN_AS      = context.getString(R.string.json_key_also_known_as);
        final String KEY_PLACE_OF_ORIGIN    = context.getString(R.string.json_key_place_of_origin);
        final String KEY_DESCRIPTION        = context.getString(R.string.json_key_description);
        final String KEY_IMAGE              = context.getString(R.string.json_key_image);
        final String KEY_INGREDIENTS        = context.getString(R.string.json_key_ingredients);

        try {
            JSONObject sandwichJsonBaseObj  = new JSONObject(json);
            JSONObject sandwichJsonNamesObj = sandwichJsonBaseObj.getJSONObject(KEY_NAME);
            String mainNameString           = sandwichJsonNamesObj.getString(KEY_MAIN_NAME);
            JSONArray alsoKnownAsArr        = sandwichJsonNamesObj.getJSONArray(KEY_ALSO_KNOWN_AS);
            String placeOfOriginString      = sandwichJsonBaseObj.getString(KEY_PLACE_OF_ORIGIN);
            String descriptionString        = sandwichJsonBaseObj.getString(KEY_DESCRIPTION);
            String imageString              = sandwichJsonBaseObj.getString(KEY_IMAGE);
            JSONArray ingredientsArr        = sandwichJsonBaseObj.getJSONArray(KEY_INGREDIENTS);

            ArrayList<String> alsoKnownAsList = new ArrayList<String>();
            if (alsoKnownAsArr.length() > 0) {
                for (int i = 0; i < alsoKnownAsArr.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsArr.getString(i));
                }
            }

            ArrayList<String> ingredientsList = new ArrayList<String>();
            if (ingredientsArr.length() > 0) {
                for (int x = 0; x < ingredientsArr.length(); x++) {
                    ingredientsList.add(ingredientsArr.getString(x));
                }
            }

            parsedSandwichData = new Sandwich(mainNameString
                                                , alsoKnownAsList
                                                , placeOfOriginString
                                                , descriptionString
                                                , imageString
                                                , ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parsedSandwichData;
    }

}
