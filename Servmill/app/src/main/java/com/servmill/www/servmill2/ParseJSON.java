package com.servmill.www.servmill2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PRADEEP RAO on 9/28/2016.
 */

public class ParseJSON {
    public static String[] categoryname;

    public static final String JSON_ARRAY = "info";
    public static final String KEY_categoryname= "categoryname";

    private JSONArray CategoriesList = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    public String[] parseJSON(){
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            CategoriesList = jsonObject.getJSONArray(JSON_ARRAY);

            categoryname = new String[CategoriesList.length()];
            // names = new String[users.length()];
            //emails = new String[users.length()];

            for(int i=0;i<CategoriesList.length();i++){
                JSONObject jo = CategoriesList.getJSONObject(i);
                categoryname[i] = jo.getString(KEY_categoryname);
                Log.d("List",categoryname[i]);

                //  names[i] = jo.getString(KEY_NAME);
                //  emails[i] = jo.getString(KEY_EMAIL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categoryname;
    }
}

