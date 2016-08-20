package com.ywg.androidcommon.sample;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.RecyclerViewUndoSwipe.Item;
import com.ywg.androidcommon.widget.RecyclerViewUndoSwipe.ItemAdapter;
import com.ywg.androidcommon.widget.RecyclerViewUndoSwipe.SimpleItemTouchHelperCallback;

public class RecyclerViewUndoSwipeActivity extends AppCompatActivity implements ItemAdapter.OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;
    private int nu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_undo_swipe);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardList);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        final ItemAdapter itemAdapter = new ItemAdapter(getApplicationContext(), this);
        recyclerView.setAdapter(itemAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(itemAdapter, this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        loadItems();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Item item = new Item();
                nu = ItemAdapter.itemList.size();
                nu++;
                item.setItemName("item" + nu);
                llm.scrollToPositionWithOffset(0, dpToPx(56));
                itemAdapter.addItem(0, item);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ItemAdapter.itemList.clear();

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);

    }

    private void loadItems() {
        //Initial items
        for (int i = 10; i > 0; i--) {
            Item item = new Item();
            item.setItemName("item" + i);
            ItemAdapter.itemList.add(item);
        }
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
