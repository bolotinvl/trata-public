package com.bolotin.trata.ui.category.dragAndDrop;

import android.support.v7.widget.RecyclerView;

import com.bolotin.trata.data.db.model.Category;

import java.util.List;

public interface OnDragListener {

    void onStartDrag(RecyclerView.ViewHolder viewHolder);

    void onFinishDrag(List<Category> categories);
}
