package it.matteoavanzini.app.genericadapter;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by emme on 14/05/2018.
 */

public abstract
        class RecyclerViewAdapter<T extends Parcelable, H extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<H> {

    public interface OnItemRecyclerListener<T> {
        void onRecyclerItemSelected(T item);
    }

    protected OnItemRecyclerListener mListener;
    protected final List<T> mValues;

    public RecyclerViewAdapter(OnItemRecyclerListener listener,
                               List<T> items) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

}
