package com.bolotin.trata.ui.category;

import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.ui.base.MvpView;

import java.util.List;

public interface CategoryMvpView extends MvpView{

    void updateCategories(List<Category> categories);

    void showUnsuccessfulDeleteSnackbar();

    void showDeleteFragment(String categoryId);

    void setWasCategoryDeleted();
}
