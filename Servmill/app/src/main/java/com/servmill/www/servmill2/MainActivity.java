package com.servmill.www.servmill2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.servmill.www.servmill2.adapter.CustomListAdapter;
import com.servmill.www.servmill2.customlistview.AppController;
import com.servmill.www.servmill2.model.Categories;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView listview;
    CircleImageView image;
    FrameLayout container;
    TextView prousername, proContact, proId, progmail;
    public static final String JSON_URL = "https://www.servmill.com/webservice/catapi.php";

    ArrayAdapter arrayAdapter;
    private static int RESULT_LOAD_IMAGE = 1;
    NavigationView navigationView;


    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url = "https://www.servmill.com/webservice/services.php";
    private ProgressDialog pDialog;
    private List<Categories> categoryList;
    private ListView listView1;
    private CustomListAdapter adapter;
    Categories selectedCategory;
    SharedPreferences prefs;
    String logemail, logcontact, logmemberid;
    String loguserid;
    public static final String MyPREFERENCES = "MyPrefs";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CONTACTNO = "contactno";
    public static final String KEY_MEMBERID = "memberid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_sing_up);
        setContentView(R.layout.activity_main);

        prousername = (TextView) findViewById(R.id.profile_username);
        progmail = (TextView) findViewById(R.id.profile_gmail);
        proContact = (TextView) findViewById(R.id.usercontact);
        proId = (TextView) findViewById(R.id.userid);

        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE); //1
        loguserid = prefs.getString(KEY_USERNAME, " "); //2
        logemail = prefs.getString(KEY_EMAIL, " "); //2
        logcontact = prefs.getString(KEY_CONTACTNO, " ");
        logmemberid = prefs.getString(KEY_MEMBERID, " ");

        prousername = (TextView) findViewById(R.id.profile_username);
        progmail = (TextView) findViewById(R.id.profile_gmail);

        prousername.setText(loguserid);
   //     progmail.setText(logemail);
//        proContact.setText(logcontact);
    //    proId.setText(logmemberid);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendRequest();
        sendRequest2();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        listview = (ListView) findViewById(R.id.list);
        selectedCategory = new Categories();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        CircleImageView image = (CircleImageView) findViewById(R.id.profile_image);


        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                profilePic(view);
            }
        });
    }

    public void startCategoryFragment(String category) {

    }


    public boolean checkPermissions() {
        // check runtime permissions in Android 6+
        return true;
    }

    public void profilePic(View view) {

        if (checkPermissions()) {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //   image = (CircleImageView) findViewById(R.id.profile_image);
            image.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }

    private void sendRequest() {

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json) {

        ParseJSON pj = new ParseJSON(json);
        arrayAdapter = new ArrayAdapter(this, R.layout.nav_list_item_layout, R.id.listText, pj.parseJSON());
        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(MainActivity.this, "Selected: " + listview.getAdapter().getItem(i), Toast.LENGTH_SHORT).show();
                String category = (String) listview.getAdapter().getItem(i);
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                intent.putExtra(GlobalConstants.CATEGORY_NAME, category);
                startActivity(intent);

            }
        });
    }

    private void sendRequest2() {

        listView1 = (ListView) findViewById(R.id.listview);
        categoryList = new ArrayList<>();


        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Creating volley request obj


        StringRequest categoriesReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseData = new JSONObject(response);
                    JSONArray data = responseData.getJSONArray("categories");

                    Log.d("Response", responseData.toString());
                    for (int i = 0; i < data.length(); i++) {//responseData.length()

                        Categories categories = new Categories();
                        JSONObject obj = data.getJSONObject(i);
                        categories.setTitle(obj.getString("servicename"));
                        String url = "https://www.servmill.com/administrator/images/servmill/" + obj.getString("serviceimage");
                        categories.setThumbnailUrl(url);
                        categories.setDescription(obj.getString("servicedescription"));
                        categories.setYear(obj.getString("dateposted"));
                        //  categories.setSource(obj.getString("source"));

                        // adding Billionaire to categories array
                        categoryList.add(categories);
                        // categoryList.get(i).printCategory();
                    }
                    adapter = new CustomListAdapter(MainActivity.this, categoryList);
                    listView1.setAdapter(adapter);
                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedCategory = (Categories) adapterView.getAdapter().getItem(i);
                            Toast.makeText(MainActivity.this, selectedCategory.printCategory("MainActivity"), Toast.LENGTH_SHORT).show();
                            startCategoryActivity();
                        }
                    });
                    hidePDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(categoriesReq);

    }

    public void startCategoryActivity() {

        Intent intent = new Intent(this, CategoryActivity.class);

        intent.putExtra("main", true);
        intent.putExtra("name", selectedCategory.getTitle());
        intent.putExtra("description", selectedCategory.getDescription());
        intent.putExtra("url", selectedCategory.getThumbnailUrl());
        intent.putExtra("date", selectedCategory.getYear());
        startActivity(intent);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog()
    {
        if (pDialog != null)
        {
            pDialog.dismiss();
            pDialog = null;
        }
    }


    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sing_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent intent=new Intent(MainActivity.this,Password.class);
            startActivity(intent);
            Toast.makeText(this, "Setting", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.action_details)
        {
             Intent intent = new Intent(this, AddressActivity.class);
            startActivity(intent);

            return true;
        }
        else if (id == R.id.action_About)
        {
            return true;
        }
        else if (id == R.id.action_post)
        {
            Intent intent = new Intent(this, Post.class);
            startActivity(intent);

            return true;
        }
        else if (id == R.id.action_Logout)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
