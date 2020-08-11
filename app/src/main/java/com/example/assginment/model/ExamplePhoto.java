
package com.example.assginment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamplePhoto {

    @SerializedName("photos")
    @Expose
    private PhotosGP photosGP;
    @SerializedName("stat")
    @Expose
    private String stat;

    public PhotosGP getPhotosGP() {
        return photosGP;
    }

    public void setPhotosGP(PhotosGP photosGP) {
        this.photosGP = photosGP;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}
