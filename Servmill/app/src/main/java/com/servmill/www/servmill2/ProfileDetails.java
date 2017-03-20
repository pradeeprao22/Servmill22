package com.servmill.www.servmill2;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileDetails extends Fragment {


    public ProfileDetails() {
        // Required empty public constructor
    }

    View view;
    TextView Address, Name, Email, Contact;
    TextView Country, Street, Pin, Area, plotno, city;
    Button save;
    String user_email, user_contactno;
    String user_name;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CONTACTNO = "contactno";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_details, container, false);

        Name = (TextView) view.findViewById(R.id.username);
        Email = (TextView) view.findViewById(R.id.useremail);
        Contact = (TextView) view.findViewById(R.id.usercontact);

        Address = (TextView) view.findViewById(R.id.address);
        city = (TextView) view.findViewById(R.id.city);
        Country = (TextView) view.findViewById(R.id.country);
        Area = (TextView) view.findViewById(R.id.Area);
        plotno = (TextView) view.findViewById(R.id.plot_no);
        Street = (TextView) view.findViewById(R.id.street);
        Pin = (TextView) view.findViewById(R.id.pin);
//
//        city = (EditText) view.findViewById(R.id.city);
//        Country = (EditText) view.findViewById(R.id.country);
//        Area = (EditText) view.findViewById(R.id.Area);
//        plotno = (EditText) view.findViewById(R.id.plot_no);
//        Street = (EditText) view.findViewById(R.id.street);
//        Pin = (EditText) view.findViewById(R.id.pin);
       save = (Button) view.findViewById(R.id.addressButton);

        prefs = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE); //1
        user_name = prefs.getString(KEY_USERNAME, "username"); //2
        user_email = prefs.getString(KEY_EMAIL, "email ");
        user_contactno = prefs.getString(KEY_CONTACTNO, " contactno");//2

        //user_contact=prefs.getString(KEY_CONTACTNO);
        Name.setText(user_name);
        Email.setText(user_email);
        Contact.setText(user_contactno);
        return view;



    }

}

