package com.servmill.www.servmill2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class SignUpActivity extends AppCompatActivity {

    TextView textview, signText,hidtext,registerd;
    EditText mobilenumber1, username1, email1, password1, repassword;
    Button button;
    public String pass, repass, user, mobile, e_mail,hiddentext;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String pattern = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$";
    SharedPreferences prefs;

    private static final String REGISTER_URL = "https://www.servmill.com/webservice/register.php";

    public static final String KEY_USERNAME = "userid";
    public static final String KEY_MOBILE = "contactno";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_VERIFICATION = "verification";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        prefs = getSharedPreferences("loginDetails", MODE_PRIVATE);

        signText      = (TextView) findViewById(R.id.signtext);
        hidtext       =  (TextView)findViewById(R.id.hidden_value);
        textview      = (TextView) findViewById(R.id.text);
        email1        = (EditText) findViewById(R.id.email_id);
        mobilenumber1 = (EditText) findViewById(R.id.mobile);
        username1     = (EditText) findViewById(R.id.username);
        password1     = (EditText) findViewById(R.id.password);
        repassword    = (EditText) findViewById(R.id.repassword);
        hidtext.setText("yes");
        button    = (Button) findViewById(R.id.button);
        registerd = (TextView) findViewById(R.id.already);
        registerd.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "You can login now", android.widget.Toast.LENGTH_SHORT).show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                e_mail = email1.getText().toString();
                user   = username1.getText().toString();
                mobile = mobilenumber1.getText().toString();
                pass   = password1.getText().toString();
                repass = repassword.getText().toString();
                hiddentext=hidtext.getText().toString();
                if (!(mobile.matches(pattern))) {
                    Toast.makeText(SignUpActivity.this, "Invalid Mobile Number.", Toast.LENGTH_SHORT).show();
                    mobilenumber1.setError("Please enter valid mobile number");
                } else if (!(repass.length() >= 6 && (pass.length() >= 6))) {
                    Toast.makeText(SignUpActivity.this, "Password should contain at-least 6 characters.", Toast.LENGTH_SHORT).show();
                    password1.setError("Please enter minimum 6 characters.");
                    repassword.setError("Please enter minimum 6 charcters.");
                } else if (!(pass.matches(repass))) {
                    Toast.makeText(SignUpActivity.this, "Passwords did not match.", Toast.LENGTH_SHORT).show();
                    password1.setError("Passwords did not match.");
                    repassword.setError("Passwords did not match.");
                } else if (!(e_mail.matches(emailPattern))) {
                    Toast.makeText(SignUpActivity.this, "Invalid E-Mail", Toast.LENGTH_SHORT).show();
                    email1.setError("Please enter valid E-Mail");
                } else if (!(user.length() >= 3)) {
                    Toast.makeText(SignUpActivity.this, "Username should be at-least 3 chracters", Toast.LENGTH_SHORT).show();
                    username1.setError("Username should be at-least 3 chracters");
                } else {
                    registerUser();
                }
            }
        });
    }
    public void startLoginActivity() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void registerUser() {

        final String userid    = username1.getText().toString().trim();
        final String contactno = mobilenumber1.getText().toString().trim();
        final String password  = password1.getText().toString().trim();
        final String email     = email1.getText().toString().trim();
        final String verification    = hidtext.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_LONG).show();
                        startLoginActivity();

                        try {
                            JSONObject userData = new JSONObject(response);
                            prefs.edit().putString("userid", userData.getString("userid"));
                            prefs.edit().putString("password", userData.getString("password"));
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
                        Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, userid);
                params.put(KEY_PASSWORD, password);
                params.put(KEY_EMAIL, email);
                params.put(KEY_MOBILE, contactno);
                params.put(KEY_VERIFICATION, verification);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}





