package com.servmill.www.servmill2.model;

import android.util.Log;

public class Categories {

    private String servicename = "", serviceimage = "";
    private String dateposted = "";
    // private String source;
    private String servicedescription = "";

    public String getTitle() {
        return servicename;
    }

    public void setTitle(String servicename) {
        this.servicename = servicename;
    }

    public String getThumbnailUrl() {
        return serviceimage;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.serviceimage = thumbnailUrl;
    }

    public String getYear() {
        return dateposted;
    }

    public void setYear(String year) {
        this.dateposted = year;
    }
    // public String getSource() {
    //    return source;
    // }

    ///  public void setSource(String source) {
    //  this.source = source;
    ///// }

    public String getDescription() {
        return servicedescription;
    }

    public void setDescription(String description) {
        this.servicedescription = description;
    }

    public String printCategory(String className) {
        Log.d("printing category",
                servicename + serviceimage +
                        dateposted +
                        servicedescription+" In "+className);
        return "Selected Category" +
                servicename + serviceimage +
                dateposted +
                servicedescription+" In "+className;
    }


}
