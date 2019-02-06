package it.matteoavanzini.app.genericadapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SimpleIconItemArrayAdapter<T extends Parcelable> extends ItemArrayAdapter<T, SimpleIconItemArrayAdapter.ViewHolder> {

    protected int iconResource;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.text);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }

    public SimpleIconItemArrayAdapter(Context context,
                                      OnItemRecyclerListener listener,
                                      int iconResource,
                                      List<T> playlist, Matcher<T> matcher) {
        super(context, listener, playlist, matcher);
        this.iconResource = iconResource;
    }

    @Override
    public void onBindViewHolder(SimpleIconItemArrayAdapter.ViewHolder holder, T item) {
        String mName = item.toString();
        holder.name.setText(mName);
        holder.icon.setImageResource(iconResource);
    }

    @NonNull
    @Override
    public SimpleIconItemArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_icon_item, parent, false);
        return new SimpleIconItemArrayAdapter.ViewHolder(view);
    }
}
