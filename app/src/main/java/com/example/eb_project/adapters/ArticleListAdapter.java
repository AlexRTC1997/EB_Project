package com.example.eb_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eb_project.R;
import com.example.eb_project.entities.Article;

import java.util.ArrayList;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {
    ArrayList<Article> articleList;

    public ArticleListAdapter(ArrayList<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, null, false);

        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.tvArticleId.setText(String.valueOf(articleList.get(position).getId()));
        holder.tvArticleName.setText(articleList.get(position).getName());
        holder.tvArticleStatus.setText(articleList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView tvArticleId, tvArticleName, tvArticleStatus;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);

            tvArticleId = itemView.findViewById(R.id.tv_article_list_item_id);
            tvArticleName = itemView.findViewById(R.id.tv_article_list_item_name);
            tvArticleStatus = itemView.findViewById(R.id.tv_article_list_item_status);

        }
    }

}
