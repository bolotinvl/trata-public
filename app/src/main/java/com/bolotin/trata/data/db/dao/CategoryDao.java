package com.bolotin.trata.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bolotin.trata.data.db.model.Category;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface CategoryDao {

    @Insert
    void insertCategory(Category category);

    @Insert
    void insertCategories(List<Category> categories);

    @Update
    void updateCategory(Category category);

    @Update
    void updateCategories(List<Category> categories);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM categories ORDER BY `order`")
    Flowable<List<Category>> getCategories();

    @Query("SELECT COUNT(*) FROM categories")
    Integer getCategoriesCount();

    @Query("SELECT * FROM categories WHERE id != :categoryId ORDER BY `order`")
    Single<List<Category>> getCategoriesExceptOne(String categoryId);

    @Query("SELECT * FROM categories ORDER BY `order` LIMIT 1")
    Maybe<Category> getDefaultCategory();

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    Flowable<Category> getCategoryById(String categoryId);

    @Query("SELECT icon FROM categories WHERE id = :categoryId")
    Single<String> getCategoryIcon(String categoryId);

    @Query("SELECT name FROM categories WHERE id = :categoryId")
    Single<String> getCategoryName(String categoryId);

    @Query("DELETE FROM categories")
    void clearCategories();
}