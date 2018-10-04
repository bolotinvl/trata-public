package com.bolotin.trata.ui.changeCategory.addCategory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.bolotin.trata.R;
import com.bolotin.trata.ui.changeCategory.ChangeCategoryActivity;
import com.bolotin.trata.utils.CommonUtils;

import butterknife.OnClick;

public class AddCategoryActivity extends ChangeCategoryActivity {

    public static Intent getStartIntent(Context context) {
        return new Intent(context, AddCategoryActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp(toolbar);
    }

    @Override
    protected void setUp(Toolbar toolbar) {
        super.setUp(toolbar);
        addOnIconTouchListener(this);
    }

    @OnClick(R.id.add_category_save_button)
    void onSaveButtonClick() {
        String categoryNameStr = categoryName.getText().toString();
        String categoryIconId = CommonUtils.replaceUnderscores(getResources().getResourceEntryName(adapter.getSelectedCategoryIconId()));
        presenter.createCategory(categoryNameStr, categoryIconId);
        onBackButtonClick();
    }
}
