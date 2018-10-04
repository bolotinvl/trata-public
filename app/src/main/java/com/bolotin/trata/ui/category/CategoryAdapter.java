package com.bolotin.trata.ui.category;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.listeners.OnClickListener;
import com.bolotin.trata.ui.base.BaseViewHolder;
import com.bolotin.trata.ui.category.dragAndDrop.ItemTouchHelperAdapter;
import com.bolotin.trata.ui.category.dragAndDrop.OnDragListener;
import com.bolotin.trata.utils.CommonUtils;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<BaseViewHolder>
        implements ItemTouchHelperAdapter {

    private OnDragListener onDragListener;
    private OnClickListener onClickListener;
    private List<Category> categories;
    private Category savedSelectedCategory;

    private boolean actionModeState = false;
    private boolean isArrowsVisible = false;
    private int selectedPosition = -1;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (categories != null) {
            return categories.size();
        } else {
            return 1;
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(categories, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }


    @Override
    public void onDrop(int fromPosition, int toPosition) {

        if (fromPosition == toPosition) {
            return;
        }

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i <= toPosition; i++) {
                Category category = categories.get(i);
                category.setOrder(i);
                categories.set(i, category);
            }
        }

        if (fromPosition > toPosition) {
            for (int i = fromPosition; i >= toPosition; i--) {
                Category category = categories.get(i);
                category.setOrder(i);
                categories.set(i, category);
            }
        }

        onDragListener.onFinishDrag(categories);

        //Change selected category position after moving if it's necessary
        int selectedIndex = categories.indexOf(savedSelectedCategory);
        if (savedSelectedCategory != null && selectedIndex != selectedPosition) {
            toggleSelection(selectedIndex);
            savedSelectedCategory = null;
        }
    }

    public void toggleSelection(int position) {
        if (selectedPosition == position) {
            selectedPosition = -1;
        } else {
            selectedPosition = position;
        }
        notifyDataSetChanged();
    }

    public void setArrowsVisible() {
        isArrowsVisible = true;
        notifyDataSetChanged();
    }

    void addItems(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public String getCategoryId(int position) {
        return categories.get(position).getId();
    }

    public String getCategoryName(int position) {
        return categories.get(position).getName();
    }

    public void setActionModeState(boolean actionModeState) {
        this.actionModeState = actionModeState;
        notifyDataSetChanged();
    }

    void clearSelections() {
        selectedPosition = -1;
        notifyDataSetChanged();
    }

    int getSelectedPosition() {
        return selectedPosition;
    }

    void saveSelectedCategoryPositionBeforeMove() {
        if (selectedPosition >= 0) {
            savedSelectedCategory = categories.get(selectedPosition);
        } else {
            savedSelectedCategory = null;
        }
    }

    void removeSavedSelectedCategory() {
        savedSelectedCategory = null;
    }

    void setOnDragListener(OnDragListener onDragListener) {
        this.onDragListener = onDragListener;
    }

    void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class CategoryViewHolder extends BaseViewHolder {

        @BindView(R.id.category_list_item_view)
        LinearLayout linearLayout;

        @BindView(R.id.category_icon_view)
        ImageView categoryIconView;

        @BindView(R.id.category_tick_view)
        ImageView categoryTickView;

        @BindView(R.id.category_name_view)
        TextView categoryNameView;

        @BindView(R.id.category_handle_view)
        ImageView categoryHandleView;

        @BindView(R.id.category_arrow_view)
        ImageView categoryArrowView;

        @BindView(R.id.category_line_divider)
        ImageView lineDividerView;

        CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void onBind(int position) {
            super.onBind(position);

            final Category category = categories.get(position);

            categoryIconView.setImageResource(
                    itemView.getContext().getResources()
                            .getIdentifier(CommonUtils.replaceHyphen(category.getIcon()),
                                    "drawable",
                                    itemView.getContext().getPackageName()));

            categoryNameView.setText(category.getName());

            linearLayout.setSelected(selectedPosition == position);

            if (linearLayout.isSelected()) {
                categoryTickView.setVisibility(View.VISIBLE);
            } else {
                categoryTickView.setVisibility(View.INVISIBLE);
            }

            if (actionModeState) {
                categoryHandleView.setVisibility(View.VISIBLE);
            } else {
                categoryHandleView.setVisibility(View.GONE);
            }

            if (isArrowsVisible) {
                categoryArrowView.setVisibility(View.VISIBLE);
            } else {
                categoryArrowView.setVisibility(View.GONE);
            }

            if (position == 0) {
                lineDividerView.setVisibility(View.GONE);
            } else {
                lineDividerView.setVisibility(View.VISIBLE);
            }

            linearLayout.setOnClickListener(v -> {
                if (onClickListener != null) {
                    onClickListener.onClicked(v, getAdapterPosition());
                }
            });

            linearLayout.setOnLongClickListener(v -> {
                if (onClickListener != null) {
                    onClickListener.onLongClicked(v, getAdapterPosition());
                    return true;
                }
                return false;
            });

            categoryHandleView.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onDragListener.onStartDrag(this);
                }
                return false;
            });
        }
    }
}