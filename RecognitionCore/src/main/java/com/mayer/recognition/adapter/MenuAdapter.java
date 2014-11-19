package com.mayer.recognition.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.mayer.recognition.util.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by irikhmayer on 17.11.2014.
 */
public abstract class MenuAdapter<ViewModel, ViewType extends View> extends RecyclerView.Adapter<MenuAdapter.ViewHolder<ViewType>> {

    private List<ViewModel> list = new ArrayList<ViewModel>();
    private Context ctx;

    private AdapterView.OnItemClickListener listener;

    public MenuAdapter(Context ctx) {
        super();
        this.ctx = ctx;
    }

    public MenuAdapter setOnListItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
        return this;
    }

    public void setList(List<ViewModel> list) {
        this.list.clear();
        this.list.addAll(list);

        notifyDataSetChanged();
    }

    /*
     * Inserting a new item at the head of the list. This uses a specialized
     * RecyclerView method, notifyItemInserted(), to trigger any enabled item
     * animations in addition to updating the view.
     */
    public void addItem(ViewModel item) {
        list.add(0, item);
        notifyItemInserted(0);
    }

    /*
     * Inserting a new item at the head of the list. This uses a specialized
     * RecyclerView method, notifyItemRemoved(), to trigger any enabled item
     * animations in addition to updating the view.
     */
    public void removeItem() {
        if (list.isEmpty()) return;

        list.remove(0);
        notifyItemRemoved(0);
    }

    public void removeAllItems() {
        if (list.isEmpty()) return;

        list.clear();
        notifyDataSetChanged();
    }

    public List<ViewModel> getList() {
        return list;
    }

    @Override
    public ViewHolder<ViewType> onCreateViewHolder(ViewGroup viewGroup, final int pos) {
        ViewType view = newView(ctx, viewGroup, pos);
        return new ViewHolder<ViewType>(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder<ViewType> viewHolder, final int pos) {
        viewHolder.setListener(new ViewHolder.IItemViewClickListener() {

            @Override
            public void onClick(final View caller) {
                if (listener != null) {
                    listener.onItemClick(null, viewHolder.itemView, viewHolder.getPosition(), viewHolder.getItemId());
                }
            }

            @Override
            public void onLongClick(final View caller) {
                if (listener != null) {
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
}