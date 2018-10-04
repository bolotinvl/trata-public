package com.bolotin.trata.ui.changeCategory;

import com.bolotin.trata.ui.base.MvpPresenter;

import java.util.List;

public interface ChangeCategoryMvpPresenter<V extends ChangeCategoryMvpView> extends MvpPresenter<V> {

    void onViewPrepared();

    void onViewPrepared(String categoryId);

    void createCategory(String categoryName, String categoryIcon);

    void updateCategory(String categoryId, String categoryName, String categoryIcon);

    List<Integer> getCategoryIcons();
}
