package com.dicoding.butgetin.ui.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.butgetin.R
import com.dicoding.butgetin.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.CustomTheme)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.notification)
            setDisplayHomeAsUpEnabled(true)
        }

        val notifications = listOf<String>()

        if (notifications.isEmpty()) {
            binding.rafikiNotification.visibility = android.view.View.VISIBLE
        } else {
            binding.rafikiNotification.visibility = android.view.View.GONE
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}