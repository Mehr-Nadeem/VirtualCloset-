package com.example.mehr.virtualcloset;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Mehr on 2017-12-26.
 */

@Entity(tableName="photos")
public class ItemPhoto implements Serializable {

    //Clothing category identifiers
    public static final int HATS = 1;

    public static final int JACKETS = 2;

    public static final int SHOES = 3;

    //Temperature scale identifiers
    public static final int FREEZING = 1;

    public static final int CHILLY = 2;

    public static final int MILD = 3;

    public static final int WARM = 4;

    public static final int HOT = 5;

    private static final int NONE = -1;

    @PrimaryKey
    @ColumnInfo(name="photo_path")
    private String mPhotoPath;

    @ColumnInfo(name="category")
    private int mCategory;

    @ColumnInfo(name="temp")
    private int mTempScale;

    public ItemPhoto(String photoPath){
        mPhotoPath =  photoPath;
    }

    public ItemPhoto(String photoPath, int category, int tempScale){
        mPhotoPath =  photoPath;
        mCategory = category;
        mTempScale = tempScale;
    }

    public String getmPhotoPath() {
        return mPhotoPath;
    }

    public void setmPhotoPath(String mPhotoPath) {
        this.mPhotoPath = mPhotoPath;
    }

    public int getmCategory() {
        return mCategory;
    }

    public void setmCategory(int mCategory) {
        this.mCategory = mCategory;
    }

    public int getmTempScale() {
        return mTempScale;
    }

    public void setmTempScale(int mTempScale) {
        this.mTempScale = mTempScale;
    }

    public static ItemPhoto[] getItemPhotos(){
        return new ItemPhoto[]{
                new ItemPhoto("http://i.imgur.com/zuG2bGQ.jpg"),
                new ItemPhoto("http://i.imgur.com/ovr0NAF.jpg"),
                new ItemPhoto("http://i.imgur.com/n6RfJX2.jpg"),
                new ItemPhoto("http://i.imgur.com/qpr5LR2.jpg")
        };
    }
}
