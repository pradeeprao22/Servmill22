package com.servmill.www.servmill2;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileDetailsFragment extends Fragment {


    public ProfileDetailsFragment() {
        // Required empty public constructor
    }

    View view;
    TextView Address, Name, Email, Contact,Street;
    String user_email, user_contactno,user_Address,user_Street;
    String user_name;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CONTACTNO = "contactno";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_STREET = "street";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_details, container, false);

        Name = (TextView) view.findViewById(R.id.username);
        Email = (TextView) view.findViewById(R.id.useremail);
        Contact = (TextView) view.findViewById(R.id.usercontact);

        Address = (TextView) view.findViewById(R.id.address);
        Street = (TextView) view.findViewById(R.id.street);

        prefs = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE); //1
        user_name = prefs.getString(KEY_USERNAME, "username"); //2
        user_email = prefs.getString(KEY_EMAIL, "email ");
        user_contactno = prefs.getString(KEY_CONTACTNO, " contactno");//2
        user_Address=prefs.getString(KEY_ADDRESS,"address");
        user_Street=prefs.getString(KEY_STREET,"street");
        Name.setText(user_name);
        Email.setText(user_email);
        Contact.setText(user_contactno);
        Address.setText(user_Address);
        Street.setText(user_Street);
        return view;



    }

}

