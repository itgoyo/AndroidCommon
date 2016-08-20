package com.ywg.androidcommon.widget.RecyclerViewUndoSwipe;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
