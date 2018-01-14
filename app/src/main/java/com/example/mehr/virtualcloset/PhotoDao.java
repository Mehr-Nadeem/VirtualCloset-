package com.example.mehr.virtualcloset;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Mehr on 2017-12-31.
 */

@Dao
public interface PhotoDao {

    @Insert
    public void insertPhoto(ItemPhoto photo);

    @Insert
    public void insertPhotos(List<ItemPhoto> photos);

    @Update
    public void updatePhoto(ItemPhoto photo);

    @Update
    public void updatePhotos(List<ItemPhoto> photos);

    @Delete
    public void deletePhoto(ItemPhoto photo);

    @Delete
    public void deletePhotos(List<ItemPhoto> photos);

    @Query("SELECT * FROM photos")
    public ItemPhoto[] loadAllPhotos();

    @Query("SELECT * FROM photos WHERE category == :sCategory")
    public ItemPhoto[] loadPhotosInCategory(String sCategory);

    @Query("SELECT * FROM photos WHERE category == :sCategory AND temp == :sTemp")
    public ItemPhoto[] selectRightPhotos(String category, int sTemp);
}
