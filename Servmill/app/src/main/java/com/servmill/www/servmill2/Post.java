package com.servmill.www.servmill2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Post extends AppCompatActivity {
    TextView userpost;
    private Bitmap bitmap;

    ImageView serviceimage;
    EditText userCategory, title,description, servicelocation;
    Button post;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final String url = "https://www.servmill.com/webservice/servmill.php";
    public static final String KEY_Title = "servicename";
    public static final String KEY_Description = "servicedescription";
    public static final String KEY_Location = "location";
    public static final String KEY_Category = "categoryid";
    public static final String KEY_Image = "serviceimage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        servicelocation = (EditText) findViewById(R.id.userLocation);
        userpost = (TextView) findViewById(R.id.userpost);
        serviceimage = (ImageView) findViewById(R.id.postImage);
        userCategory = (EditText) findViewById(R.id.userEdit);
        title = (EditText) findViewById(R.id.userTitle);
        description = (EditText) findViewById(R.id.userDes);
        post = (Button) findViewById(R.id.post);


        serviceimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                serviceImage(view);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PostService();
            }
        });
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void PostService() {

        final String serviceimage = getStringImage(bitmap);
        final String servicename = title.getText().toString().trim();
        final String servicedescription = description.getText().toString().trim();
        final String location = servicelocation.getText().toString().trim();
        final String categoryid = userCategory.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject userData = new JSONObject(response);

                            Toast.makeText(Post.this, response, Toast.LENGTH_LONG).show();
                        }
                        catch(JSONException exception) {
                            exception.printStackTrace();
                        }
                        Log.d("Response: ", response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Post.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
             // final String serviceimage = getStringImage(bitmap);

                params.put(KEY_Image, serviceimage);
                params.put(KEY_Category, categoryid);
                params.put(KEY_Title, servicename);
                params.put(KEY_Description, servicedescription);
                params.put(KEY_Location, location);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public boolean checkPermissions() {
        // check runtime permissions in Android 6+
        return true;
    }

    public void serviceImage(View view) {

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
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //   image = (CircleImageView) findViewById(R.id.profile_image);
            serviceimage.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
        }

    }



