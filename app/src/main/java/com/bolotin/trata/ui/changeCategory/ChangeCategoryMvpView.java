package com.bolotin.trata.ui.changeCategory;

import com.bolotin.trata.ui.base.MvpView;

import java.util.List;

public interface ChangeCategoryMvpView extends MvpView {

    void updateCategoryIcons(List<Integer> categoryIcons);

    default void prepareEditCategoryActivity(String categoryName, String categoryIconId) {}
}
