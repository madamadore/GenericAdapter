package it.matteoavanzini.app.genericadapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SimpleItemArrayAdapter<T extends Parcelable> extends ItemArrayAdapter<T, SimpleItemArrayAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public SimpleItemArrayAdapter(Context context, OnItemRecyclerListener listener, List<T> playlist, Matcher<T> matcher) {
        super(context, listener, playlist, matcher);
    }

    @Override
    public void onBindViewHolder(SimpleItemArrayAdapter.ViewHolder holder, T item) {
        String mName = item.toString();
        holder.name.setText(mName);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }
}
