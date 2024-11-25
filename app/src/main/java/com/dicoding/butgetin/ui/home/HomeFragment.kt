package com.dicoding.butgetin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dicoding.butgetin.R
import com.dicoding.butgetin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        setHasOptionsMenu(true)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_toolbar_menu, menu)

        menu.findItem(R.id.action_article)?.icon?.setTint(ContextCompat.getColor(requireContext(), R.color.white))
        menu.findItem(R.id.action_notification)?.icon?.setTint(ContextCompat.getColor(requireContext(), R.color.white))
        menu.findItem(R.id.action_profile)?.icon?.setTint(ContextCompat.getColor(requireContext(), R.color.white))

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_article -> {
                findNavController().navigate(R.id.action_navigation_home_to_articleFragment)
                true
            }
            R.id.action_notification -> {
                findNavController().navigate(R.id.action_navigation_home_to_notificationFragment)
                true
            }
            R.id.action_profile -> {
                findNavController().navigate(R.id.action_navigation_home_to_profileFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}