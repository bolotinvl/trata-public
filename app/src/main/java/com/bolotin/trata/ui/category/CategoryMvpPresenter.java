package com.bolotin.trata.ui.category;

import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.ui.base.MvpPresenter;

import java.util.List;

public interface CategoryMvpPresenter<V extends CategoryMvpView> extends MvpPresenter<V> {

    void onViewPrepared();

    void updateCategories(List<Category> categories);

    void deleteCategory(String categoryId);
}
