package com.android.bpcontrol.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adrian on 15/3/15.
 */
public class Center implements Parcelable {

    private String name;
    private String address;
    private String zipcode;
    private String province;
    private String city;
    private String tlf;
    private Location location;

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
        name = in.readString();
        address = in.readString();
        zipcode = in.readString();
        province = in.readString();
        city = in.readString();
        tlf = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(zipcode);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(tlf);
        dest.writeParcelable(location,flags);

    }
}
