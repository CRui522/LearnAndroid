package com.rui.learnandroid.bean.news;

import android.annotation.SuppressLint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rui.learnandroid.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News.RowsDTO> rows;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_items, parent, false);
        return new ViewHolder(inflate);
    }

    public NewsAdapter(List<News.RowsDTO> rows) {
        this.rows = rows;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(rows.get(position).getTitle());
        holder.content.setText(Html.fromHtml(rows.get(position).getContent(),1));
        holder.comment.setText(rows.get(position).getCommentNum().toString());
        holder.like.setText(rows.get(position).getLikeNum().toString());
        holder.read.setText(rows.get(position).getReadNum().toString());
        Glide.with(holder.cover.getContext())
                .load("http://124.93.196.45:10001" + rows.get(position).getCover())
                .placeholder(R.drawable.rui)
                .into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;
        private TextView comment;
        private TextView like;
        private TextView read;
        private ImageView cover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            comment = itemView.findViewById(R.id.comment);
            cover = itemView.findViewById(R.id.cover);
            like = itemView.findViewById(R.id.like);
            read = itemView.findViewById(R.id.read);
        }
    }
}
