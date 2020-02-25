package lk.applife.kidssafety.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lk.applife.kidssafety.R;
import lk.applife.kidssafety.model.ArticleTitle;
import lk.applife.kidssafety.ui.SingleArticleActivity;

import java.util.ArrayList;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

    private Context context;
    private ArrayList<ArticleTitle> articles;

    private int checkedPosition = 0;

    public ArticlesAdapter(Context context, ArrayList<ArticleTitle> articleTitleArrayList){
        this.context = context;
        this.articles = articleTitleArrayList;
    }

    public void setArticles(ArrayList<ArticleTitle> articles){
        this.articles = new ArrayList<>();
        this.articles = articles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticlesAdapter.ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesAdapter.ArticleViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder{

        private TextView articleNumber;
        private TextView articleTitleTv;
        private ImageView imageView;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            articleNumber = itemView.findViewById(R.id.textViewCount);
            articleTitleTv = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void bind(final ArticleTitle articleTitle){
            if (checkedPosition == -1) {
                imageView.setVisibility(View.GONE);
            }
            articleNumber.setText(String.valueOf(articleTitle.getId()));
            articleTitleTv.setText(articleTitle.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageView.setVisibility(View.VISIBLE);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                    Intent detailView = new Intent(context, SingleArticleActivity.class);
                    detailView.putExtra("Id", getSelectedArticle().getId());
                    detailView.putExtra("articleTitle", getSelectedArticle().getTitle());
                    detailView.putExtra("artId", getSelectedArticle().getArtId());
                    detailView.putExtra("artCategory", getSelectedArticle().getArtCategory());
                    context.startActivity(detailView);

                }
            });
        }
    }

    public ArticleTitle getSelectedArticle(){
        if (checkedPosition != -1){
            return articles.get(checkedPosition);
        }
        return null;
    }
}
