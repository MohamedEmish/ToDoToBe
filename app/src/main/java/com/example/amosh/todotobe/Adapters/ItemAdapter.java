package com.example.amosh.todotobe.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amosh.todotobe.Data.Items;
import com.example.amosh.todotobe.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context mContext;
    private List<Items> mItemsArrayList;
    private ItemClickListener mClickListener;
    private ItemLongClickListener mLongClickListener;


    public ItemAdapter(Context context, List<Items> arrayList) {
        mContext = context;
        mItemsArrayList = arrayList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.lists_list_item, parent, false);
        return new ItemAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        holder.itemName.setText(mItemsArrayList.get(position).getName());
        int state = mItemsArrayList.get(position).getState();
        final String category = mItemsArrayList.get(position).getCategory();
        if (state == 0) {
            holder.container.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
            holder.itemName.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.stateColor.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
            holder.check.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_check_gray_24dp));
        } else if (state == 1) {
            holder.container.setBackgroundColor(mContext.getResources().getColor(R.color.light_gary));
            holder.itemName.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.check.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_check_black_24dp));
            switch (category) {
                case "Auto":
                    holder.stateColor.setBackgroundColor(mContext.getResources().getColor(R.color.purble));
                    break;
                case "Bills":
                    holder.stateColor.setBackgroundColor(mContext.getResources().getColor(R.color.light_purple));
                    break;
                case "Health":
                    holder.stateColor.setBackgroundColor(mContext.getResources().getColor(R.color.light_blue));
                    break;
                case "Shop":
                    holder.stateColor.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    break;
                case "Travel":
                    holder.stateColor.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                    break;
                case "Work":
                    holder.stateColor.setBackgroundColor(mContext.getResources().getColor(R.color.dark_blue));
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return mItemsArrayList.size();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void setLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.mLongClickListener = itemLongClickListener;
    }

    // return data
    public String getItemName(int id) {
        return mItemsArrayList.get(id).getName();
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public LinearLayout container;
        public LinearLayout stateColor;
        public ImageView check;
        public TextView itemName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            container = (LinearLayout) itemView.findViewById(R.id.lists_item_container);
            stateColor = (LinearLayout) itemView.findViewById(R.id.lists_item_state_color);
            check = (ImageView) itemView.findViewById(R.id.lists_item_check);
            itemName = (TextView) itemView.findViewById(R.id.lists_item_name);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongClickListener != null) {
                mLongClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            } else {

                return false;
            }
        }
    }
}
