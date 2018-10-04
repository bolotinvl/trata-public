package com.bolotin.trata.ui.category.deleteDialog;

import com.bolotin.trata.ui.base.MvpPresenter;

public interface DeleteCategoryMvpPresenter<V extends DeleteCategoryMvpView> extends MvpPresenter<V> {

    void onViewPrepared(String deletedCategoryId);

    void deleteCategory(String deletedCategoryId, String newCategoryId);
}
