package com.bolotin.trata.ui.changeCategory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bolotin.trata.R;
import com.bolotin.trata.ui.base.BaseViewHolder;
import com.bolotin.trata.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeCategoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context context;
    private List<Integer> categoryIcons;
    private int selectedCategoryPosition = -1;

    public ChangeCategoryAdapter(List<Integer> categoryIcons, Context context) {
        this.context = context;
        this.categoryIcons = categoryIcons;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddOrEditCategoryViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.add_category_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return categoryIcons.size();
    }

    public void toggleSelection(String categoryIconId) {
        toggleSelection(categoryIcons.indexOf(context.getResources().getIdentifier(
                CommonUtils.replaceHyphen(categoryIconId), "drawable", context.getPackageName())));
        notifyDataSetChanged();
    }

    public int getSelectedCategoryIconId() {
        return categoryIcons.get(selectedCategoryPosition);
    }

    void addItems(List<Integer> categoryIcons) {
        this.categoryIcons = categoryIcons;
        notifyDataSetChanged();
    }

    void toggleSelection(int position) {
        selectedCategoryPosition = position;
        notifyDataSetChanged();
    }

    boolean isItemSelected() {
        return selectedCategoryPosition >= 0;
    }

    public class AddOrEditCategoryViewHolder extends BaseViewHolder {

        @BindView(R.id.add_category_list_item_view)
        LinearLayout linearLayout;

        @BindView(R.id.add_category_image_view)
        ImageView categoryIconView;

        AddOrEditCategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            categoryIconView.setImageResource(categoryIcons.get(position));
            linearLayout.setSelected(selectedCategoryPosition == position);
        }
    }
}
