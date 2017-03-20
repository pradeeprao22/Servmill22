package com.servmill.www.servmill2;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileDetailsFragment extends Fragment {
    EditText Name, Email, Contact;
    EditText userStreet, userAddress;
    TextView userdetails, member;
    View view;
    Button update;
    String user_name, user_Address, user_Street;
    String user_email;
    String user_contactno;
   static String member_id;
    SharedPreferences prefs;
    public static final String REGISTER_URL = "https://www.servmill.com/webservice/put.php";
    public static final String MyPREFERENCES = "MyPrefs";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CONTACTNO = "contactno";
    public static final String KEY_MEMBERID = "memberid";

    public static final String KEY_ADDRESS = "address";

    public static final String KEY_STREET = "street";

    public EditProfileDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_profile_details, container, false);

        Name = (EditText) view.findViewById(R.id.username);
        Email = (EditText) view.findViewById(R.id.useremail);
        Contact = (EditText) view.findViewById(R.id.usercontact);

       // member = (TextView) view.findViewById(R.id.memberid);
        userAddress = (EditText) view.findViewById(R.id.address);
        userdetails = (TextView) view.findViewById(R.id.userdetails);
        userStreet = (EditText) view.findViewById(R.id.street);

        update = (Button) view.findViewById(R.id.addressButton);

        prefs = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE); //1
        user_name = prefs.getString(KEY_USERNAME, "username"); //2
        user_email = prefs.getString(KEY_EMAIL, "email ");
        member_id = prefs.getString(KEY_MEMBERID, "0");
        user_contactno = prefs.getString(KEY_CONTACTNO, " contactno");//2
        user_Address = prefs.getString(KEY_ADDRESS, "address");
        user_Street = prefs.getString(KEY_STREET, "street");

        Name.setText(user_name);
        Email.setText(user_email);
        Contact.setText(user_contactno);

        userAddress.setText(user_Address);
        userStreet.setText(user_Street);
       // member.setText(member_id);
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Update();
            }
        });
        return view;
    }


    public void Update() {
        final String username    = Name.getText().toString().trim();
        final String contactno   = Contact.getText().toString().trim();
        final String useremailid = Email.getText().toString().trim();
        final String street      = userStreet.getText().toString().trim();
        final String address     = userAddress.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject userData = new JSONObject(response);
                       //  Toast.makeText(EditProfileDetailsFragment.this,response.toString(), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response: ", response.toString());

                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response: ", error.toString());

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(KEY_USERNAME, username);
                params.put(KEY_EMAIL, useremailid);
                params.put(KEY_CONTACTNO, contactno);
                params.put(KEY_ADDRESS, address);
                params.put(KEY_MEMBERID, member_id);
                params.put(KEY_STREET, street);
                Log.d("print params", params.toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}


