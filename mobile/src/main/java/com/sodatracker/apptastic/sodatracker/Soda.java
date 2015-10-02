package com.sodatracker.apptastic.sodatracker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mgarner on 10/1/2015.
 */
public class Soda implements Parcelable {

    private String mBrandName = null;
    private int mNumTwelvePacks = 0;
    private int mNumCans = 0;

    public Soda(String brandName, int numTwelvePacks, int numCans) {
        mBrandName = brandName;
        mNumTwelvePacks = numTwelvePacks;
        mNumCans = numCans;
    }


    protected Soda(Parcel in) {
        mBrandName = in.readString();
        mNumTwelvePacks = in.readInt();
        mNumCans = in.readInt();
    }

    public static final Creator<Soda> CREATOR = new Creator<Soda>() {
        @Override
        public Soda createFromParcel(Parcel in) {
            return new Soda(in);
        }

        @Override
        public Soda[] newArray(int size) {
            return new Soda[size];
        }
    };

    public String getBrandName() {
        return mBrandName;
    }

    public void setBrandName(String mBrandName) {
        this.mBrandName = mBrandName;
    }

    public int getNumTwelvePacks() {
        return mNumTwelvePacks;
    }

    public void setNumTwelvePacks(int mNumTwelvePacks) {
        this.mNumTwelvePacks = mNumTwelvePacks;
    }

    public int getNumCans() {
        return mNumCans;
    }

    public void setNumCans(int mNumCans) {
        this.mNumCans = mNumCans;
    }

    public void subtractTwelvePack() {
        if (this.mNumTwelvePacks > 0) {
            this.mNumTwelvePacks--;
        }
    }

    public void addTwelvePack() {
        this.mNumTwelvePacks++;
    }

    public void subtractCan() {
        if (this.mNumCans > 0) {
            this.mNumCans--;
        } else if (this.mNumTwelvePacks > 0) {
            mNumTwelvePacks--;
            mNumCans = 11;
        }
    }

    public void addCan() {
        if (this.mNumCans < 11) {
            this.mNumCans++;
        } else {
            this.mNumCans = 0;
            this.mNumTwelvePacks++;
        }
    }

    public void save() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBrandName);
        dest.writeInt(mNumTwelvePacks);
        dest.writeInt(mNumCans);
    }
}
