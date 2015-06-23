package com.hesoftgroup.bpcontrol.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by adrian on 15/3/15.
 */
public class Center implements Parcelable {

    private int id;
    private String name;
    private String address;
    private String zipcode;
    private String province;
    private String city;
    private String tlf;
    private LatLng location;
    private String webpage;
    private String email;
    public float distance;
    public String measureType = "km";

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Center createFromParcel(Parcel in) {
            return new Center(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };

    public Center(){}

    private Center(Parcel in){
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        zipcode = in.readString();
        province = in.readString();
        city = in.readString();
        tlf = in.readString();
        location = in.readParcelable(LatLng.class.getClassLoader());
        webpage = in.readString();
        email = in.readString();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactAddress() {
        return address;
    }

    public void setContactAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(zipcode);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(tlf);
        dest.writeParcelable(location,flags);
        dest.writeString(webpage);
        dest.writeString(email);

    }
}
