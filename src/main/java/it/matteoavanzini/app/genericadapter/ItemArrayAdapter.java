package it.matteoavanzini.app.genericadapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

/**
 * A BaseAdapter with Filterable interface
 */
public abstract
        class ItemArrayAdapter<T extends Parcelable, H extends RecyclerView.ViewHolder>
        extends RecyclerViewAdapter<T, H>
        implements Filterable {

    protected Context context;
    protected int layoutResourceId;
    protected List<T> filteredData;
    protected List<T> originalData;
    protected Matcher<T> matcher;

    protected Filter mFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence query) {
            FilterResults results = new FilterResults();

            int count = originalData.size();
            filteredData = new ArrayList<T>(count);
            if (null != query) {
                query = query.toString().toLowerCase();
                filteredData = searchIn(originalData, matcher, query.toString());
            } else {
                filteredData = originalData;
            }

            results.values = filteredData;
            results.count = filteredData.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (List<T>) results.values;
            notifyDataSetChanged();
        }
    };

    public List<T> getFilteredData() {
        return filteredData;
    }

    public static <T> List<T> searchIn(List<T> list, Matcher<T> m, String search) {
        List<T> r = new ArrayList<T>();
        for(T t : list) {
            if( m.matches(t, search) ) {
                r.add(t);
            }
        }
        return r;
    }

    public abstract void onBindViewHolder(H holder, T item);

    @Override
    public void onBindViewHolder(H holder, int position) {
        final T item = filteredData.get(position);
        onBindViewHolder(holder, item);

        holder.itemView.setTag(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRecyclerItemSelected(item);
            }
        });
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    public ItemArrayAdapter(Context context,
                            OnItemRecyclerListener listener,
                            int layoutResourceId,
                            List<T> objects, Matcher<T> matcher) {
        super(listener, objects);
        this.context = context;
        this.matcher = matcher;
        this.layoutResourceId = layoutResourceId;
        this.originalData = objects;
        this.filteredData = objects;
    }

    public ItemArrayAdapter(Context context,
                            OnItemRecyclerListener listener,
                            List<T> objects, Matcher<T> matcher) {
        super(listener, objects);
        this.context = context;
        this.matcher = matcher;
        this.originalData = objects;
        this.filteredData = objects;
    }

    @Override
    public int getItemCount() {
        return filteredData==null ? 0 : filteredData.size();
    }

    public T getItem(int position) {
        return filteredData.get(position);
    }

}
