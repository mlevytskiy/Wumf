package io.wumf.wumf.friends;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by max on 21.06.15.
 */
public class Friend implements Parcelable {

    private List<String> apps;
    private String name;
    private String phone;
    private String url;

    public List<String> getApps() {
        return apps;
    }

    public void setApps(List<String> apps) {
        this.apps = apps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.url);
    }

    public Friend() {
    }

    protected Friend(Parcel in) {
        this.name = in.readString();
        this.phone = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Friend> CREATOR = new Parcelable.Creator<Friend>() {
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }

        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    public String toString() {
        return name + "|" + phone;
    }
}
