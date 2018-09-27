package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich parsedSandwichData = new Sandwich();

        final String KEY_MAIN_NAME = "mainName";
        final String KEY_NAME = "name";
        final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
        final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String KEY_DESCRIPTION = "description";
        final String KEY_IMAGE = "image";
        final String KEY_INGREDIENTS = "ingredients";

        try {
            JSONObject sandwichJsonObj = new JSONObject(json);
            JSONArray sandwichNameArr = sandwichJsonObj.getJSONArray("name");
            String sandwichMainName = sandwichNameArr.getString(0);

            List<String> sandwichAKAList = null;

            if (!sandwichNameArr.getString(1).isEmpty()) {
                JSONArray sandwichAKA = sandwichJsonObj.getJSONArray("alsoKnownAs");
                if (sandwichAKA.length() > 0 && sandwichAKA != null) {
                    for (int i = 0; i <= sandwichAKA.length(); i++) {
                        sandwichAKAList.add(sandwichAKA.getString(i));
                    }
                }
            }

            String sandwichPlaceOfOrigin = sandwichJsonObj.getString("placeOfOrigin");
            String sandwichDescription = sandwichJsonObj.getString("description");
            String sandwichImage = sandwichJsonObj.getString("image");
            JSONArray sandwichIngredients = sandwichJsonObj.getJSONArray("ingredients");

            List<String> sandwichIngList = null;

            for (int x=0; x<=sandwichIngredients.length(); x++) {
                sandwichIngList.add(sandwichIngredients.getString(x));
            }

            Log.d(TAG, "--------- parseSandwichJson: mainName: " + sandwichMainName);
            Log.d(TAG, "--------- parseSandwichJson: AKA: " + sandwichAKAList.toString());
            Log.d(TAG, "--------- parseSandwichJson: descp: " + sandwichDescription);
            Log.d(TAG, "--------- parseSandwichJson: image: " + sandwichImage);
            Log.d(TAG, "--------- parseSandwichJson: ing: " + sandwichIngList.toString());

            parsedSandwichData.setMainName(sandwichMainName);
            parsedSandwichData.setAlsoKnownAs(sandwichAKAList);
            parsedSandwichData.setPlaceOfOrigin(sandwichPlaceOfOrigin);
            parsedSandwichData.setDescription(sandwichDescription);
            parsedSandwichData.setImage(sandwichImage);
            parsedSandwichData.setIngredients(sandwichIngList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parsedSandwichData;
    }
}
