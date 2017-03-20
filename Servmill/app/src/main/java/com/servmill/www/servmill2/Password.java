package com.servmill.www.servmill2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class Password extends Activity {

    TextView password;
    EditText editpass1,editpass2;
    Button button;
    SharedPreferences prefs;
    String memberid;

    public  static  final   String MyPREFERENCES   =  "MyPrefs";
    public  static  final   String KEY_MEMBERID    =  "memberid";
    private static  final   String REGISTER_URL    =  "https://www.servmill.com/webservice/pwdupdate.php";
    public  static  final   String KEY_PASSWORD    =  "password";
    public  static  final   String KEY_NEWPASSWORD =  "newpassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);
        password = (TextView) findViewById(R.id.pass);
        editpass1 = (EditText) findViewById(R.id.oldpass);
        editpass2 = (EditText) findViewById(R.id.newpass);
        button = (Button) findViewById(R.id.passbutton);
        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE); //1
        memberid = prefs.getString(KEY_MEMBERID, " ");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .4));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword();
            }
        });
    }

    public void newPassword() {
        final String password  =editpass1 .getText().toString().trim();
        final String newpassword  = editpass2.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Password.this, response, Toast.LENGTH_LONG).show();

                        try
                        {
                            JSONObject userData = new JSONObject(response);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response: ", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Password.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_PASSWORD, password);
                params.put(KEY_NEWPASSWORD,newpassword );
                params.put(KEY_MEMBERID,memberid );

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
