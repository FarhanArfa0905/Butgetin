package com.dicoding.butgetin.ui.article

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.util.Linkify
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.butgetin.R
import com.dicoding.butgetin.data_class.Article
import com.dicoding.butgetin.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.article_details)
            setDisplayHomeAsUpEnabled(true)
        }

        val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_ARTICLE, Article::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra(EXTRA_ARTICLE)
        }

        if (article != null) {
            binding.articleImage.setImageResource(article.photo)
            binding.articleTitle.text = article.name
            binding.articleDescription.text = article.description
            Linkify.addLinks(binding.articleDescription, Linkify.WEB_URLS)
        } else {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"

        fun createIntent(context: Context, article: Article): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_ARTICLE, article)
            }
        }
    }
}