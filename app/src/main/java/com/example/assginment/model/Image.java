package com.example.assginment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image  implements Parcelable {
    String url,width,height,title;

    public Image() {
    }

    protected Image(Parcel in) {
        url = in.readString();
        width = in.readString();
        height = in.readString();
        title = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image(String url, String width, String height, String title) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(width);
        parcel.writeString(height);
    }
}
