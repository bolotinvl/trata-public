package com.bolotin.trata.ui.category.deleteDialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bolotin.trata.R;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.di.component.ActivityComponent;
import com.bolotin.trata.ui.base.BaseDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteCategoryDialog extends BaseDialog implements DeleteCategoryMvpView {

    private static final String TAG = "DeleteCategoryDialog";

    @Inject
    DeleteCategoryMvpPresenter<DeleteCategoryMvpView> presenter;

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    DeleteCategoryAdapter adapter;

    @BindView(R.id.delete_category_recycler_view)
    RecyclerView recyclerView;

    String deletedCategoryId;

    public static DeleteCategoryDialog newInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString("deletedCategoryId", categoryId);
        DeleteCategoryDialog dialog = new DeleteCategoryDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_delete_category, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {

            component.inject(this);

            setUnBinder(ButterKnife.bind(this, view));

            presenter.onAttach(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            deletedCategoryId = bundle.getString("deletedCategoryId");
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        presenter.onViewPrepared(deletedCategoryId);
    }

    @Override
    public void updateCategories(List<Category> categories) {
        adapter.addItems(categories);
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @OnClick(R.id.cancel_delete_category_button)
    void onCancelButtonClick() {
        dismiss();
    }

    @OnClick(R.id.delete_category_button)
    void onDeleteButtonClick() {
        dismiss();
        presenter.deleteCategory(deletedCategoryId, adapter.getSelectedCategoryId());
    }
}