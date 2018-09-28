package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich parsedSandwichData = new Sandwich();

        final String KEY_MAIN_NAME          = "mainName";
        final String KEY_NAME               = "name";
        final String KEY_ALSO_KNOWN_AS      = "alsoKnownAs";
        final String KEY_PLACE_OF_ORIGIN    = "placeOfOrigin";
        final String KEY_DESCRIPTION        = "description";
        final String KEY_IMAGE              = "image";
        final String KEY_INGREDIENTS        = "ingredients";


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

            if (alsoKnownAsArr.length() > 0 && alsoKnownAsArr != null) {
                for (int i=0; i<alsoKnownAsArr.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsArr.getString(i));
                }
            }

            ArrayList<String> ingredientsList = new ArrayList<String>();
            if (ingredientsArr.length() > 0 && ingredientsArr != null) {
                for (int x=0; x<ingredientsArr.length(); x++) {
                    ingredientsList.add(ingredientsArr.getString(x));
                }
            }

            parsedSandwichData = new Sandwich(mainNameString
                                                , alsoKnownAsList
                                                , placeOfOriginString
                                                , descriptionString
                                                , imageString
                                                , ingredientsList);

//            Log.d(TAG, "parseSandwichJson: mainName: "
//                    + mainNameString
//                    + "\n, AKA: " + alsoKnownAsArr.toString()
//                    + "\n, origin: " + placeOfOriginString
//                    + "\n, desc: " + descriptionString
//                    + "\n, img: " + imageString
//                    + "\n, ing: "+ ingredientsArr.toString() +"\n");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parsedSandwichData;
    }
}
