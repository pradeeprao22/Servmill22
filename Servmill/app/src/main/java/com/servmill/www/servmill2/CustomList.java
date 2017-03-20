package com.servmill.www.servmill2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by PRADEEP RAO on 9/28/2016.
 */

public class CustomList extends ArrayAdapter<String> {
    private String[] categoryname;
    private Activity context;

    public CustomList(Activity context, String[] categoryname) {
        super(context, R.layout.nav_list_item_layout, categoryname);
        this.context = context;
        this.categoryname = categoryname;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        convertView = inflater.inflate(R.layout.nav_list_item_layout, null, true);
        TextView textViewId = (TextView) convertView.findViewById(R.id.listText);

        textViewId.setText(categoryname[position]);

        return convertView;
    }
}



