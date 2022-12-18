package com.example.eb_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eb_project.ArticleDetailsActivity;
import com.example.eb_project.R;
import com.example.eb_project.entities.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {
    ArrayList<Article> articleList;
    ArrayList<Article> articleListAux;    // SearchView

    public ArticleListAdapter(ArrayList<Article> articleList) {

        this.articleList = articleList;
        // SearchView
        articleListAux = new ArrayList<>();
        articleListAux.addAll(articleList);
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

            // ITEM LISTENER TO VIEW DETAILS OF EACH ITEM
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent detailsIntent = new Intent(context, ArticleDetailsActivity.class);
                    detailsIntent.putExtra("articleId", articleList.get(getAdapterPosition()).getId());
                    context.startActivity(detailsIntent);
                }
            });

        }
    }

    // SearchView - Filter
    public void filter (String textToSearch) {
        int length = textToSearch.length();

        if (length == 0) {
            articleList.clear();
            articleList.addAll(articleListAux);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Article> collection = articleList.stream().
                        filter(i -> i.getName().toLowerCase().contains(textToSearch.toLowerCase()))
                        .collect(Collectors.toList());

                articleList.clear();
                articleList.addAll(collection);

            } else {
                for (Article a: articleListAux) {
                    if(a.getName().toLowerCase().contains(textToSearch.toLowerCase())) {
                        articleList.add(a);
                    }
                }
            }
        }

        notifyDataSetChanged();
    }

}
