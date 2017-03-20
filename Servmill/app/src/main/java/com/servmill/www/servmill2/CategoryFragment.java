package com.servmill.www.servmill2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.servmill.www.servmill2.customlistview.AppController;
import com.servmill.www.servmill2.model.Categories;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    public CategoryFragment() {
        // Required empty public constructor
    }

    View view;
    TextView categoryTitle, categoryDescription, categoryDatePosted;
    String categoryImageUrl;
    Categories categories;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);

        categoryTitle = (TextView) view.findViewById(R.id.categoryName);
        categoryDescription = (TextView) view.findViewById(R.id.categoryDecription);
        categoryDatePosted = (TextView) view.findViewById(R.id.categoryDatePosted);
        // thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

        categories = ((CategoryActivity) getActivity()).getCategoryData();
        categories.printCategory("Category Fragment");
        NetworkImageView thumbNail = (NetworkImageView) view
                .findViewById(R.id.thumbnail);

        categoryTitle.setText(categories.getTitle());
        categoryDatePosted.setText(categories.getYear());
        categoryDescription.setText(categories.getDescription());

        if (imageLoader == null) {
            imageLoader = AppController.getInstance().getImageLoader();


        }
        String imagepath=categories.getThumbnailUrl();
        Log.d("IMAGEPATH",imagepath);
        thumbNail.setImageUrl(imagepath, imageLoader);

        return view;
        }

}
