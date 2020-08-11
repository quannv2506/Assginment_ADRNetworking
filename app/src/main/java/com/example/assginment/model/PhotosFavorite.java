
package com.example.assginment.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotosFavorite {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("pages")
    @Expose
    private Integer pages;
    @SerializedName("perpage")
    @Expose
    private Integer perpage;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("photo")
    @Expose
    private List<PhotoFavorite> photoFavorite = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPerpage() {
        return perpage;
    }

    public void setPerpage(Integer perpage) {
        this.perpage = perpage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<PhotoFavorite> getPhotoFavorite() {
        return photoFavorite;
    }

    public void setPhotoFavorite(List<PhotoFavorite> photoFavorite) {
        this.photoFavorite = photoFavorite;
    }

}
