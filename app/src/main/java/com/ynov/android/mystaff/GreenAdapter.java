package com.ynov.android.mystaff;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.android.mystaff.data.StaffListContract;
import com.ynov.android.mystaff.utilities.RoundedImageView;

import static com.ynov.android.mystaff.R.id.avatar_bmp;


/**
 * Created by Alex on 31/12/2016.
 */

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.ViewHolder> {

    private final Context mContext;
    private Cursor mCursor;


    //private String[] mDataset;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;
        public ImageView iconView;
        public ImageView presenceView;

        public ViewHolder(View v) {
            super(v);
            iconView = (ImageView) v.findViewById(avatar_bmp);
            presenceView = (ImageView) v.findViewById(R.id.presence_icon);
            mTextView = (TextView) v.findViewById(R.id.staff_list_item);
            v.setOnClickListener(this);
            v.setBackgroundColor(Color.parseColor("#E6E6E6"));
        }


        void bind(int listIndex) {
            // COMPLETED (17) Within bind, set the text of listItemNumberView to the listIndex
            // COMPLETED (18) Be careful to get the String representation of listIndex, as using setText with an int does something different
            mTextView.setText(String.valueOf(listIndex));
        }

        public void onClick(View view){
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }



    // Provide a suitable constructor (depends on the kind of dataset)

    public GreenAdapter(Context context, Cursor cursor,  ListItemClickListener listener) {
        //mDataset = myDataset;
        mOnClickListener = listener;
        this.mContext = context;
        this.mCursor = cursor;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public GreenAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.staff_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);
            //holder.bind(position);
        if (!mCursor.moveToPosition(position))
            return;

        String name = mCursor.getString(mCursor.getColumnIndex(StaffListContract.StaffListEntry.STAFF_NAME));

        holder.mTextView.setText(name);

        Bitmap circleBitmap = BitmapFactory.decodeResource(holder.iconView.getResources(), R.drawable.arnaud);

        Bitmap roundedBitmap = RoundedImageView.getCroppedBitmap(circleBitmap, 120);

        holder.iconView.setImageBitmap(roundedBitmap);

        holder.presenceView.setImageResource(R.drawable.circlepresence);

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }



}
