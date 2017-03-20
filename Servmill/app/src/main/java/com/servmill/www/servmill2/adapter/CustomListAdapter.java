package com.servmill.www.servmill2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.servmill.www.servmill2.R;
import com.servmill.www.servmill2.customlistview.AppController;
import com.servmill.www.servmill2.model.Categories;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Categories> categoriesItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();



    public CustomListAdapter(Activity activity, List<Categories> categoriesItems) {
        this.activity = activity;
        this.categoriesItems = categoriesItems;
    }

    @Override
    public int getCount() {
        return categoriesItems.size();
    }

    @Override
    public Object getItem(int location) {
        return categoriesItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
       NetworkImageView thumbNail = (NetworkImageView) convertView
               .findViewById(R.id.thumbnail);
        TextView servicename = (TextView) convertView.findViewById(R.id.name);
        TextView description = (TextView) convertView.findViewById(R.id.worth);
       // TextView source = (TextView) convertView.findViewById(R.id.source);
        TextView year = (TextView) convertView.findViewById(R.id.inYear);

        // getting billionaires data for the row
        Categories m = categoriesItems.get(position);

        //thumbnail image
      thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // name
       servicename.setText(m.getTitle());

        // Wealth Source
       // source.setText("Wealth Source: " + String.valueOf(m.getSource()));


        description.setText(String.valueOf(m.getDescription()));

        // release year
       year.setText("posted in:"+String.valueOf(m.getYear()));

        return convertView;
    }

}
