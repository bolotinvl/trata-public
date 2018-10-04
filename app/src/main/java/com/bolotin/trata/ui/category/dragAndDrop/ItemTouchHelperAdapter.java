package com.bolotin.trata.ui.category.dragAndDrop;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onDrop(int fromPosition, int toPosition);
}
