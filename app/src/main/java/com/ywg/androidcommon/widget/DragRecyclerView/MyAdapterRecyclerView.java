package com.ywg.androidcommon.widget.DragRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ywg.androidcommon.R;

import java.util.List;

public class MyAdapterRecyclerView extends RecyclerView.Adapter<MyAdapterRecyclerView.MyViewHolder> {

    private List<Item> mList;

    private Context mContext;

    private LayoutInflater mInflater;

    public MyAdapterRecyclerView(Context context, List<Item> mList) {
        this.mContext = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_drag_recyclerview_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = mList.get(position); // Object Item
        holder.setName(item.getName()); // Name
        holder.setDescription(item.getDescription()); // Description
        holder.setImage(item.getIdImage()); // Image
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUser;
        TextView tvName, tvDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivUser = (ImageView) itemView.findViewById(R.id.ivUser);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        }

        public void setName(String name) {
            tvName.setText(name);
        }

        public void setDescription(String description) {
            tvDescription.setText(description);
        }

        public void setImage(int idImage) {
            Picasso.with(ivUser.getContext()).
                    load(idImage).
                    centerCrop().
                    resize(60, 60).
                    transform(new CircleTransform()).
                    into(ivUser);
        }

    }

}