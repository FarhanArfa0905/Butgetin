package com.dicoding.butgetin.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.butgetin.R
import com.dicoding.butgetin.model.profile.ProfileMenu

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_profile_menu)

        val menuList = listOf(
            ProfileMenu(R.drawable.ic_account, "Account"),
            ProfileMenu(R.drawable.ic_linked_to_your_family_account, "Linked to Your Family Account"),
            ProfileMenu(R.drawable.ic_dark_mode, "Dark Mode"),
            ProfileMenu(R.drawable.ic_language, "Language"),
            ProfileMenu(R.drawable.ic_logout, "Logout")
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ProfileMenuAdapter(menuList) { menu ->
            when (menu.name) {
                "Account" -> navigateToAccount()
                "Linked to Your Family Account" -> navigateToLinkedToYourFamilyAccount()
                "Dark Mode" -> navigateToDarkMode()
                "Language" -> navigateToLanguage()
                "Logout" -> performLogout()
            }
        }

        return view
    }

    private fun navigateToLinkedToYourFamilyAccount() {
        TODO("Not yet implemented")
    }

    private fun navigateToLanguage() {
        TODO("Not yet implemented")
    }

    private fun navigateToDarkMode() {
        TODO("Not yet implemented")
    }

    private fun navigateToAccount() {
        // Navigate to account
    }

    private fun performLogout() {
        // Perform logout
    }
}