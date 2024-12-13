package com.dicoding.butgetin.ui.home

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.butgetin.R
import com.dicoding.butgetin.data_class.Article
import com.dicoding.butgetin.databinding.FragmentHomeBinding
import com.dicoding.butgetin.ui.article.ArticleActivity
import com.dicoding.butgetin.ui.article.DetailActivity
import com.dicoding.butgetin.ui.article.ListArticleAdapter
import com.dicoding.butgetin.ui.notification.NotificationActivity
import com.dicoding.butgetin.ui.profile.ProfileActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleAdapter: ListArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupMenu()
        setupRecyclerView()

        setupCardViewClicks()

        return root
    }

    private fun setupCardViewClicks() {
        binding.cardView1?.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_incomeMonthlyReportFragment)
        }

        binding.cardView2?.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_expenseMonthlyReportFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val horizontalScrollView =
            view.findViewById<HorizontalScrollView>(R.id.horizontal_scroll_view)
        horizontalScrollView?.postDelayed({
            horizontalScrollView.smoothScrollTo(400, 0)
            horizontalScrollView.postDelayed({
                horizontalScrollView.smoothScrollTo(0, 0)
            }, 500)
        }, 1000)
    }

    private fun setupRecyclerView() {
        val articles = loadArticles()

        articleAdapter = ListArticleAdapter(requireContext(), articles)
        articleAdapter.setOnItemClickCallback { article ->
            val intent = DetailActivity.createIntent(requireContext(), article)
            startActivity(intent)
        }

        binding.rvArticles.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = articleAdapter
        }

        binding.seeAll.setOnClickListener {
            binding.seeAll.paintFlags = binding.seeAll.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            val intent = Intent(requireContext(), ArticleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadArticles(): List<Article> {
        val names = resources.getStringArray(R.array.data_name)
        val descriptions = resources.getStringArray(R.array.data_description)
        val photos = resources.obtainTypedArray(R.array.data_photo)

        val articles = mutableListOf<Article>()
        for (i in names.indices) {
            if (i >= 6) break
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

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_toolbar_menu, menu)

                menu.findItem(R.id.action_article)?.icon?.setTint(
                    ContextCompat.getColor(
                        requireContext(), R.color.colorOnPrimary
                    )
                )
                menu.findItem(R.id.action_notification)?.icon?.setTint(
                    ContextCompat.getColor(
                        requireContext(), R.color.colorOnPrimary
                    )
                )
                menu.findItem(R.id.action_profile)?.icon?.setTint(
                    ContextCompat.getColor(
                        requireContext(), R.color.colorOnPrimary
                    )
                )
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.action_article -> {
                        val intent = Intent(requireContext(), ArticleActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.action_notification -> {
                        val intent = Intent(requireContext(), NotificationActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.action_profile -> {
                        val intent = Intent(requireContext(), ProfileActivity::class.java)
                        startActivity(intent)
                    }
                    else -> return false
                }
                menuItem.icon?.setTint(
                    ContextCompat.getColor(
                        requireContext(), R.color.dark_green
                    )
                )
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}