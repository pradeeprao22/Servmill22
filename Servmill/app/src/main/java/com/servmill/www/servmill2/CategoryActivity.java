package com.servmill.www.servmill2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.servmill.www.servmill2.model.Categories;

public class CategoryActivity extends AppCompatActivity {


    String categoryName = "";
    CategoryFragment categoryFragment;
    Categories categories;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categories = new Categories();

//        Intent intent = getIntent();
//        categoryName = intent.getStringExtra(GlobalConstants.CATEGORY_NAME);
//        categories = new Categories();

        Intent intent1 = getIntent();
        if (intent1.getBooleanExtra("main", false))
        {
            String name = intent1.getStringExtra("name");
            Log.d("Name", name);
            categories.setTitle(name);
            categories.setYear(intent1.getStringExtra("date"));
            categories.setDescription(intent1.getStringExtra("description"));
            categories.setThumbnailUrl(intent1.getStringExtra("url"));

        }
        else
        {
            categories.setTitle(intent1.getStringExtra(GlobalConstants.CATEGORY_NAME));

        }

        if (findViewById(R.id.fragment_container) != null)
        {
            if (savedInstanceState != null)
            {
                return;
            }
            categoryFragment = new CategoryFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, categoryFragment).commit();
        }
    }

    public Categories getCategoryData()
    {
        return categories;
    }

    public void loadCategoryFragment()
    {

    }

}
