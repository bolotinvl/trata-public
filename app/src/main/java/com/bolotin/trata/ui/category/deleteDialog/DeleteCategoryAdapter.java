package com.bolotin.trata.ui.category.deleteDialog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.ui.base.BaseViewHolder;
import com.bolotin.trata.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeleteCategoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Category> categories;

    private int selectedPosition = 0;

    public DeleteCategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeleteCategoryViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_category_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    String getSelectedCategoryId() {
        Category selectedCategory = categories.get(selectedPosition);
        return selectedCategory.getId();
    }

    private void toggleSelection(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    void addItems(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public class DeleteCategoryViewHolder extends BaseViewHolder {

        @BindView(R.id.delete_category_list_item_view)
        LinearLayout categoryLayout;

        @BindView(R.id.delete_category_icon_view)
        ImageView categoryIcon;

        @BindView(R.id.delete_category_name_view)
        TextView categoryName;

        @BindView(R.id.delete_category_line_divider)
        ImageView lineDivider;

        DeleteCategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            final Category category = categories.get(position);

            categoryIcon.setImageResource(
                    itemView.getContext().getResources()
                            .getIdentifier(CommonUtils.replaceHyphen(category.getIcon()),
                                    "drawable",
                                    itemView.getContext().getPackageName()));

            categoryName.setText(category.getName());

            categoryLayout.setSelected(selectedPosition == position);

            if (position == 0) {
                lineDivider.setVisibility(View.GONE);
            } else {
                lineDivider.setVisibility(View.VISIBLE);
            }

            categoryLayout.setOnClickListener((view) -> toggleSelection(getAdapterPosition()));
        }
    }
}
