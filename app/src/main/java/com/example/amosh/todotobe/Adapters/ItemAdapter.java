package com.example.amosh.todotobe.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amosh.todotobe.Data.ItemsContract;
import com.example.amosh.todotobe.Data.MyUsersDbHelper;
import com.example.amosh.todotobe.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    MyUsersDbHelper usersDbHelper;
    private Context mContext;
    private Cursor mCursor;

    public ItemAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.lists_list_item, parent, false);
        return new ItemAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_ITEM));
        int state = mCursor.getInt(mCursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_STATE));
        String category = mCursor.getString(mCursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_CATEGORY));

        holder.itemName.setText(name);
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
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersDbHelper = new MyUsersDbHelper(mContext);
                int modPosition = holder.getLayoutPosition();
                mCursor.moveToPosition(modPosition);
                String name = mCursor.getString(mCursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_ITEM));
                int state = mCursor.getInt(mCursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_STATE));
                String username = mCursor.getString(mCursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_USERNAME));

                if (state == 0) {
                    usersDbHelper.updateItemState(username, name, 1);
                    ItemAdapter.this.notifyItemChanged(modPosition);
                    ItemAdapter.this.notifyDataSetChanged();
                } else if (state == 1) {
                    usersDbHelper.updateItemState(username, name, 0);
                    ItemAdapter.this.notifyItemChanged(modPosition);
                    ItemAdapter.this.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();

        }
        mCursor = newCursor;
        if (newCursor != null) {

            notifyDataSetChanged();
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout container;
        public LinearLayout stateColor;
        public ImageView check;
        public TextView itemName;

        public ItemViewHolder(View itemView) {
            super(itemView);

            container = (LinearLayout) itemView.findViewById(R.id.lists_item_container);
            stateColor = (LinearLayout) itemView.findViewById(R.id.lists_item_state_color);
            check = (ImageView) itemView.findViewById(R.id.lists_item_check);
            itemName = (TextView) itemView.findViewById(R.id.lists_item_name);
        }

    }
}
