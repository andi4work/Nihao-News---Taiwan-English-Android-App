package com.cmrlabs.trendingtimes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmrlabs.trendingtimes.Config;
import com.cmrlabs.trendingtimes.R;
import com.cmrlabs.trendingtimes.models.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> {

    private List<Category> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Category obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterCategory(Context context, List<Category> items) {
        this.items = items;
        ctx = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public TextView post_count;
        public LinearLayout lyt_parent;
        public ImageView category_image;

        public ViewHolder(View v) {
            super(v);
            category_image = v.findViewById(R.id.category_image);
            name = v.findViewById(R.id.name);
            post_count = v.findViewById(R.id.post_count);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_category, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Category c = items.get(position);
        holder.name.setText(Html.fromHtml(c.category_name));

        if (Config.ENABLE_POST_COUNT_IN_CATEGORY) {
            holder.post_count.setText("( " + c.post_count + " )");
        } else {
            holder.post_count.setVisibility(View.GONE);
        }
        if (c.category_image.isEmpty()) {
            holder.category_image.setImageResource(R.drawable.ic_thumbnail);
        } else {

            Picasso.with(ctx)
                    .load(Config.ADMIN_PANEL_URL + "/upload/category/" + c.category_image.replace(" ", "%20"))
                    .placeholder(R.drawable.ic_thumbnail)
                    .into(holder.category_image);
       /* Picasso.with(ctx)
                .load(c.category_image.replace(" ", "%20"))
                .placeholder(R.drawable.ic_thumbnail)
                .into(holder.category_image);

*/
        }
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, c, position);
                }
            }
        });
    }

    public void setListData(List<Category> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void resetListData() {
        this.items = new ArrayList<>();
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }

}