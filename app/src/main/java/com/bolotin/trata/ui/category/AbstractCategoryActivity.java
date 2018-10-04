package com.bolotin.trata.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.bolotin.trata.R;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.listeners.OnClickListener;
import com.bolotin.trata.ui.base.BaseActivity;
import com.bolotin.trata.ui.category.deleteDialog.DeleteCategoryDialog;
import com.bolotin.trata.ui.category.dragAndDrop.OnDragListener;
import com.bolotin.trata.ui.changeCategory.addCategory.AddCategoryActivity;
import com.bolotin.trata.ui.changeCategory.editCategory.EditCategoryActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class AbstractCategoryActivity extends BaseActivity
        implements CategoryMvpView, ActionMode.Callback, OnDragListener,
        OnClickListener {

    @Inject
    protected CategoryMvpPresenter<CategoryMvpView> presenter;

    @Inject
    protected LinearLayoutManager layoutManager;

    @Inject
    protected CategoryAdapter adapter;

    @Inject
    protected ItemTouchHelper itemTouchHelper;

    @BindView(R.id.category_toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.category_recycler_view)
    protected RecyclerView recyclerView;

    protected ActionMode actionMode;

    protected boolean wasCategoryDeleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_category);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (actionMode != null) {
            actionMode.finish();
        }
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
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.setOnClickListener(this);
        adapter.setOnDragListener(this);
        presenter.onViewPrepared();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_contextual, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        MenuItem menuEditCategoryButton = menu.findItem(R.id.edit_item);
        MenuItem menuDeleteCategoryButton = menu.findItem(R.id.delete_item);
        if (adapter.getSelectedPosition() < 0) {
            menuEditCategoryButton.setVisible(false);
            menuDeleteCategoryButton.setVisible(false);
        } else {
            menuEditCategoryButton.setVisible(true);
            menuDeleteCategoryButton.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_item:
                presenter.deleteCategory(adapter.getCategoryId(adapter.getSelectedPosition()));
                return true;

            case R.id.add_item:
                startActivity(AddCategoryActivity.getStartIntent(this));
                return true;

            case R.id.edit_item:
                Intent intent = EditCategoryActivity.getStartIntent(this);
                intent.putExtra("categoryId", adapter.getCategoryId(adapter.getSelectedPosition()));
                startActivity(intent);
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        adapter.clearSelections();
        adapter.setActionModeState(false);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        adapter.saveSelectedCategoryPositionBeforeMove();
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onFinishDrag(List<Category> categories) {
        presenter.updateCategories(categories);
    }

    @OnClick(R.id.category_edit_button)
    void onEditButtonClick() {
        actionMode = startSupportActionMode(this);
        adapter.setActionModeState(true);
        String title = getString(R.string.edit_category_activity_title);
        actionMode.setTitle(title);
    }

    @Override
    public void updateCategories(List<Category> categories) {
        adapter.addItems(categories);
    }

    @Override
    public void showUnsuccessfulDeleteSnackbar() {
        showSnackBar(getString(R.string.delete_category_error));
    }

    @Override
    public void setWasCategoryDeleted() {
        wasCategoryDeleted = true;
    }

    @Override
    public void showDeleteFragment(String categoryId) {
        wasCategoryDeleted = true;
        DeleteCategoryDialog.newInstance(categoryId).show(getSupportFragmentManager());
    }

    protected void toggleSelection(int position) {
        adapter.removeSavedSelectedCategory();
        adapter.toggleSelection(position);
        String title = getString(R.string.edit_category_activity_title);
        actionMode.setTitle(title);
    }
}
