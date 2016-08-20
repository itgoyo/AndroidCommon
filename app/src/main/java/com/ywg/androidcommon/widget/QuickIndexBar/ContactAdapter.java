package com.ywg.androidcommon.widget.QuickIndexBar;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ywg.androidcommon.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    List<ConatactModel> data;

    public ContactAdapter(List<ConatactModel> data) {
        this.data = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nickname;
        TextView tag;

        public ViewHolder(View itemView) {
            super(itemView);
            nickname = (TextView) itemView.findViewById(R.id.nickname);
            tag = (TextView) itemView.findViewById(R.id.tag);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.contact_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ConatactModel person = data.get(position);
        // 获取首字母的assii值
        int selection = person.getFirstPinYin().charAt(0);
        // 通过首字母的assii值来判断是否显示字母
        int positionForSelection = getPositionForSelection(selection);
        if (position == positionForSelection) {// 相等说明需要显示字母
            holder.tag.setVisibility(View.VISIBLE);
            holder.tag.setText(person.getFirstPinYin());
        } else {
            holder.tag.setVisibility(View.GONE);

        }
        holder.nickname.setText(data.get(position).getName());
    }

    public int getPositionForSelection(int selection) {
        for (int i = 0; i < data.size(); i++) {
            String Fpinyin = data.get(i).getFirstPinYin();

            char first = Fpinyin.toUpperCase().charAt(0);
            if (first == selection) {
                return i;
            }
        }
        return -1;

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}