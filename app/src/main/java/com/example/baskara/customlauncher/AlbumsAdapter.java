package com.example.baskara.customlauncher;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide.*;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class AlbumsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Data> dataList;


    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView title, description;

        public ArticleViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            thumbnail = view.findViewById(R.id.thumbnail);
            title.setTypeface(Typeface.DEFAULT_BOLD);
            title.setTextSize(20);
            description.setTextSize(15);
        }
    }


    public AlbumsAdapter(Context mContext, List<Data> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        if(viewType == 0) {
             itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_card, parent, false);
            return new ArticleViewHolder(itemView);
        }

        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ArticleViewHolder) {
            Article data = (Article)dataList.get(position);
            ((ArticleViewHolder) holder).title.setText(data.getTitle());
            ((ArticleViewHolder) holder).description.setText(data.getDescription());

            Glide.with(mContext)
                    .load(data.getThumbnail())
                    .into(((ArticleViewHolder) holder).thumbnail);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Tapped", Toast.LENGTH_LONG);
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
