package com.bolotin.trata.ui.changeCategory;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.listeners.RecyclerItemClickListener;
import com.bolotin.trata.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class ChangeCategoryActivity extends BaseActivity implements ChangeCategoryMvpView {

    @Inject
    protected ChangeCategoryMvpPresenter<ChangeCategoryMvpView> presenter;

    @Inject
    protected GridLayoutManager layoutManager;

    @Inject
    protected ChangeCategoryAdapter adapter;

    @BindView(R.id.add_category_toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.add_category_recycler_view)
    protected RecyclerView recyclerView;

    @BindView(R.id.add_category_name_view)
    protected EditText categoryName;

    @BindView(R.id.add_category_save_button)
    protected TextView saveButton;

    protected boolean saveButtonIsDisabled = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_category);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp(Toolbar toolbar) {
        super.setUp(toolbar);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        presenter.onViewPrepared();
        invalidateSaveButton();
        addTextListenerToCategoryNameField();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void updateCategoryIcons(List<Integer> categoryIcons) {
        adapter.addItems(categoryIcons);
    }

    @OnClick(R.id.add_category_back_button)
    protected void onBackButtonClick() {
        onBackPressed();
    }

    protected void addOnIconTouchListener(Context context) {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, (view, position) -> {
            adapter.toggleSelection(position);
            if (categoryName.getText().length() != 0) {
                saveButtonIsDisabled = false;
                invalidateSaveButton();
            }
        }));
    }

    protected void invalidateSaveButton() {
        if (!saveButtonIsDisabled) {
            saveButton.setEnabled(true);
            saveButton.setTextColor(presenter.getTheme().contentEquals("default") ?
                    getResources().getColor(R.color.titleTextColorDefault) : getResources().getColor(R.color.titleTextColorDark));
        } else {
            saveButton.setEnabled(false);
            saveButton.setTextColor(presenter.getTheme().contentEquals("default") ?
                    getResources().getColor(R.color.disabledButtonColorDefault) : getResources().getColor(R.color.disabledButtonColorDark));
        }
    }

    private void addTextListenerToCategoryNameField() {
        categoryName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && adapter.isItemSelected()) {
                    saveButtonIsDisabled = false;
                    invalidateSaveButton();
                } else {
                    saveButtonIsDisabled = true;
                    invalidateSaveButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


}
