package com.dicoding.butgetin.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.butgetin.R
import com.dicoding.butgetin.data_class.Article
import com.dicoding.butgetin.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.CustomTheme)

        setContentView(R.layout.activity_article)

        supportActionBar?.apply {
            title = getString(R.string.article)
            setDisplayHomeAsUpEnabled(true)
        }

        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articles = loadArticles()

        binding.rvArticles.layoutManager = GridLayoutManager(this, 2)
        val adapter = ListArticleAdapter(this, articles)
        binding.rvArticles.adapter = adapter

        adapter.setOnItemClickCallback { article ->
            val intent = DetailActivity.createIntent(this, article)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadArticles(): List<Article> {
        val names = resources.getStringArray(R.array.data_name)
        val descriptions = resources.getStringArray(R.array.data_description)
        val photos = resources.obtainTypedArray(R.array.data_photo)

        val articles = mutableListOf<Article>()
        for (i in names.indices) {
            articles.add(
                Article(
                    name = names[i],
                    description = descriptions[i],
                    photo = photos.getResourceId(i, -1)
                )
            )
        }
        photos.recycle()
        return articles
    }
}