package com.mayer.recognition.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mayer.recognition.util.Logger;

import java.util.Collections;
import java.util.List;

/**
 * Created by irikhmayer on 17.11.2014.
 */
public abstract class MenuAdapter<ViewModel, ViewType extends View> extends RecyclerView.Adapter<MenuAdapter.ViewHolder<ViewType>> {

    private List<ViewModel> list = Collections.emptyList();
    private Context ctx;

    private IListClickListener listener;

    public MenuAdapter(Context ctx) {
        super();
        this.ctx = ctx;
    }

    public MenuAdapter setOnListItemClickListener(IListClickListener listener) {
        this.listener = listener;
        return this;
    }

    public void setList(List<ViewModel> list) {
        this.list = list;
    }

    public List<ViewModel> getList() {
        return list;
    }

    @Override
    public ViewHolder<ViewType> onCreateViewHolder(ViewGroup viewGroup, final int pos) {
        Logger.d("debug pos + " + pos);
        ViewType view = newView(ctx, viewGroup, pos);
        return new ViewHolder<ViewType>(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder<ViewType> viewHolder, final int pos) {
        Logger.d("debug pos1 + " + pos);
        viewHolder.setListener(new ViewHolder.IItemViewClickListener() {

            @Override
            public void onClick(final View caller) {
                if (listener != null) {
                    listener.onClick(caller, pos);
                }
            }

            @Override
            public void onLongClick(final View caller) {
                if (listener != null) {
                    listener.onLongClick(caller, pos);
                }
            }
        });
        bind(list.get(pos), viewHolder.view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected abstract ViewType newView(Context ctx, ViewGroup viewGroup, int pos);

    protected abstract void bind(ViewModel value, ViewType view);

    public static class ViewHolder<ViewType extends View> extends RecyclerView.ViewHolder {

        protected ViewType view;

        public ViewHolder(ViewType view) {
            super(view);
            this.view = view;
        }

        private ViewHolder<ViewType> setListener(final IItemViewClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.requestFocus();
                    listener.onClick(v);
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClick(v);
                    return true;
                }
            });
            return this;
        }

        static interface IItemViewClickListener {
            public void onClick(View caller);

            public void onLongClick(View caller);
        }

    }

    public static interface IListClickListener {

        void onClick(View caller, int pos);

        void onLongClick(View caller, int pos);
    }
}