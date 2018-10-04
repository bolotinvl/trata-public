package com.bolotin.trata.ui.category.deleteDialog;

import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.ui.base.MvpView;

import java.util.List;

public interface DeleteCategoryMvpView extends MvpView {

    void updateCategories(List<Category> categories);
}
