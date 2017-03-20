package com.servmill.www.servmill2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class AddressActivity extends AppCompatActivity {
    LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            ProfileDetailsFragment profiledetails = new ProfileDetailsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profiledetails).commit();
        }

    }
    public void loadProfileDetails() {
        EditProfileDetailsFragment editProfileDetails = new EditProfileDetailsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editProfileDetails).commit();

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

         if (id == R.id.action_edit)
        {
             loadProfileDetails();

            return true;
        }


        else if (id == R.id.action_Logout)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
