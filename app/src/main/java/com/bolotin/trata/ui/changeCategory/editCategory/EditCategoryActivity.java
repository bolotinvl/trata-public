package com.bolotin.trata.ui.changeCategory.editCategory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.ui.changeCategory.ChangeCategoryActivity;
import com.bolotin.trata.utils.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class EditCategoryActivity extends ChangeCategoryActivity {

    @BindView(R.id.add_category_title)
    protected TextView title;

    private String categoryId;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, EditCategoryActivity.class);
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

        categoryId = getIntent().getStringExtra("categoryId");
        if (categoryId != null) {
            presenter.onViewPrepared(categoryId);
        }
    }

    @Override
    public void prepareEditCategoryActivity(String categoryNameStr, String categoryIconId) {
        title.setText(R.string.edit_category_activity_title);
        categoryName.setText(categoryNameStr);
        adapter.toggleSelection(categoryIconId);
        saveButtonIsDisabled = false;
        invalidateSaveButton();
    }

    @OnClick(R.id.add_category_save_button)
    void onSaveButtonClick() {
        String categoryNameStr = categoryName.getText().toString();
        String categoryIconId = CommonUtils.replaceUnderscores(getResources().getResourceEntryName(adapter.getSelectedCategoryIconId()));
        presenter.updateCategory(categoryId, categoryNameStr, categoryIconId);
        onBackButtonClick();
    }
}
