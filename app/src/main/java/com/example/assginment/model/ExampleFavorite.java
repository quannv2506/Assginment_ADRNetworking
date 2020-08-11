
package com.example.assginment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleFavorite {

    @SerializedName("photos")
    @Expose
    private PhotosFavorite photosFavorite;
    @SerializedName("stat")
    @Expose
    private String stat;

    public PhotosFavorite getPhotosFavorite() {
        return photosFavorite;
    }

    public void setPhotosFavorite(PhotosFavorite photosFavorite) {
        this.photosFavorite = photosFavorite;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}
