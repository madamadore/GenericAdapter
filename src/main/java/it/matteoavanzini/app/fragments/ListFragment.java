package it.matteoavanzini.app.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.matteoavanzini.app.genericadapter.ItemArrayAdapter;
import it.matteoavanzini.app.genericadapter.R;

/**
 * Created by emme on 19/02/16.
 */
public abstract class ListFragment<T extends Parcelable, H extends RecyclerView.ViewHolder>
        extends android.support.v4.app.Fragment implements
        AdapterView.OnItemClickListener {

    public static final String LIST = "list";

    protected List<T> mList;
    protected String mQuery;
    protected ItemArrayAdapter<T, H> mAdapter;
    protected OnListFragmentInteractionListener mListener;
    protected RecyclerView mRecyclerView;

    public interface OnListFragmentInteractionListener<T> {
        void onListItemClick(T item);
    }

    public static <C extends ListFragment> ListFragment newInstance(Class<C> cClass,
                                                                    ArrayList<? extends Parcelable> list,
                                                                    OnListFragmentInteractionListener listener) throws IllegalAccessException, java.lang.InstantiationException {
        C myFragment = (C) cClass.newInstance();

        Bundle args = new Bundle();
        args.putParcelableArrayList(LIST, list);
        myFragment.setArguments(args);
        myFragment.OnListFragmentInteractionListener(listener);

        return myFragment;
    }

    protected void OnListFragmentInteractionListener(OnListFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public void setListAdapter(ItemArrayAdapter<T, H> adapter) {
        this.mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mList = getArguments().getParcelableArrayList(ListFragment.LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = view.findViewById(R.id.list);
        return view;
    }

    public void notifyDataSetChanged() {
        if (mQuery != null) {
            mAdapter.getFilter().filter(mQuery);
        }
        mAdapter.notifyDataSetChanged();
    }

    public List<T> getFilteredData() {
        return mAdapter.getFilteredData();
    }

    public void filterData(String query) {
        mQuery = query;
        mAdapter.getFilter().filter(mQuery);
    }

    public void refreshData(List<T> list) {
        if (mList != null) {
            mList.clear();
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();
            filterData(mQuery);
        }
    }

    public void sort(Comparator<T> c) {
        Collections.sort(mList, c);
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    public void add(T item) {
        mList.add(item);
    }

    public void remove(T item) {
        mList.remove(item);
    }

    public boolean contains(T item) {
        return mList.contains(item);
    }

    public int getCount() { return mAdapter.getItemCount(); }

    public RecyclerView getRecyclerView() { return mRecyclerView;  }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        T item = (T) mAdapter.getItem(position);
        mListener.onListItemClick(item);
    }
}
