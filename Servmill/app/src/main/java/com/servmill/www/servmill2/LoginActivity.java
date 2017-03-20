package com.servmill.www.servmill2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.makeText;
import static com.servmill.www.servmill2.R.id.username;


public class LoginActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    TextView logtext,newuser;
    EditText loguserId, logpass;
    Button logbutton;
    ImageView logoimage;
    LoginButton loginButton;
    public static final String MyPREFERENCES = "MyPrefs";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CONTACTNO = "contactno";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MEMBERID = "memberid";
    public static final String KEY_VERIFICATION= "verification";
    public static final String KEY_ADDRESS= "address";
    public static final String KEY_STREET= "street";

    private static final String REGISTER_URL = "https://www.servmill.com/webservice/member.php";
     CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        logoimage=(ImageView)findViewById(R.id.imgLogo);
        loginButton=(LoginButton)findViewById(R.id.fblogin_button);
        loginButton.setReadPermissions("email");

       // logtext = (TextView) findViewById(R.id.login);
        loguserId = (EditText) findViewById(R.id.loguser);
        logpass = (EditText) findViewById(R.id.logpass);
        logbutton = (Button) findViewById(R.id.logbutton);
        newuser = (TextView) findViewById(R.id.newuser);

        // prefs = getSharedPreferences("loginDetails", MODE_PRIVATE);
        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE); //1
        editor = prefs.edit();

        editor.putString(KEY_USERNAME, String.valueOf(username)).commit();//3

        newuser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                makeText(getApplicationContext(), "You Have To Register First  ", android.widget.Toast.LENGTH_SHORT).show();
            }
        });
        // Callback registration
       loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {



                @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                    makeText(getApplicationContext(), "Login sucessfull ", android.widget.Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                }

            @Override
            public void onCancel() {
                makeText(getApplicationContext(), "Login unsucessfull", android.widget.Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void checkLogin(View arg0) {
        final String username = loguserId.getText().toString().trim();
        final String password = logpass.getText().toString().trim();
        if (username.replaceAll("\\s", "").trim().length() < 2)
        {
            Toast.makeText(getApplicationContext(), "You Have To enter correct details  ", android.widget.Toast.LENGTH_SHORT).show();
        }
        else {
           editor.putString(KEY_USERNAME, String.valueOf(username)).commit();//3

        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String status, info;
                        String memberid, contactno, email,verification,street,Address;
                        try {
                            JSONObject object = new JSONObject(response);

                            status = object.getString("status");
                            JSONArray infoArray = object.getJSONArray("info");
                            JSONObject infoobject = (JSONObject)infoArray.get(0);

                          //  Toast.makeText(LoginActivity.this, infoobject.get("memberid").toString(), Toast.LENGTH_LONG).show();
                         //   System.out.println("Member Id: " + infoobject.get("memberid"));
                            verification=infoobject.getString("verification");
                            memberid = infoobject.getString("memberid");
                            contactno = infoobject.getString("contactno");
                            email = infoobject.getString("email");
                            street=infoobject.getString("street");
                            Address=infoobject.getString("address");
                            editor.putString(KEY_USERNAME, String.valueOf(username)).commit();//3
                            editor.putString(KEY_EMAIL, String.valueOf(email)).commit();//3
                            editor.putString(KEY_CONTACTNO, String.valueOf(contactno)).commit();//3
                            editor.putString(KEY_MEMBERID, String.valueOf(memberid)).commit();//3
                            editor.putString(KEY_ADDRESS, String.valueOf(Address)).commit();//3
                            editor.putString(KEY_STREET, String.valueOf(street)).commit();//3

                            if (verification.equalsIgnoreCase("yes")) {
                                //     Here launching another activity when login successful. If you persist login state
                                //  use sharedPreferences of Android. and logout button to clear sharedPreferences.

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            //   Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                LoginActivity.this.finish();
                            }
                            else if (verification.equalsIgnoreCase("No")) {
                                // If username and password does not match display a error message
                                makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                            }
                            else if (response.equalsIgnoreCase("exception") || response.equalsIgnoreCase("unsuccessful")) {
                                makeText(LoginActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this,"Invalid username or password",Toast.LENGTH_LONG).show();
                        }
                       // Log.d("Response:", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, String.valueOf(username));
                params.put(KEY_PASSWORD, String.valueOf(password));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
