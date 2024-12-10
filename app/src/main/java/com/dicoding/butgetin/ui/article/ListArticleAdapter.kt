package com.dicoding.butgetin.ui.article

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.butgetin.R
import com.dicoding.butgetin.api.Article

class ListArticleAdapter(
    private val context: Context,
    private val articleList: List<Article>
) : RecyclerView.Adapter<ListArticleAdapter.ArticleViewHolder>() {

    private var onItemClickCallback: ((Article) -> Unit)? = null

    fun setOnItemClickCallback(callback: (Article) -> Unit) {
        onItemClickCallback = callback
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val articleImage: ImageView = itemView.findViewById(R.id.articleImage)
        val articleTitle: TextView = itemView.findViewById(R.id.articleTitle)
        val articleDescription: TextView = itemView.findViewById(R.id.articleDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]
        holder.articleTitle.text = article.name
        holder.articleDescription.text = article.description
        holder.articleImage.setImageResource(article.photo)

        holder.itemView.setOnClickListener {
            onItemClickCallback?.invoke(article)
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }
}