package com.dicoding.butgetin.ui.profile

import android.app.AlarmManager
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.butgetin.MainActivity
import com.dicoding.butgetin.R
import com.dicoding.butgetin.databinding.ActivityProfileBinding
import com.dicoding.butgetin.model.profile.ProfileMenu
import com.dicoding.butgetin.ui.welcome.WelcomeActivity
import java.util.Calendar

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val REMINDER_CHANNEL_ID = "reminder_channel"
        const val PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.CustomTheme)
        supportActionBar?.apply {
            title = getString(R.string.profile)
            setDisplayHomeAsUpEnabled(true)
        }

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestNotificationPermission()
        createNotificationChannel()

        setupProfileMenu()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                REMINDER_CHANNEL_ID,
                getString(R.string.reminder_notifications),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = getString(R.string.channel_description)
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setupProfileMenu() {
        val menuList = listOf(
            ProfileMenu(R.drawable.ic_account, getString(R.string.account)),
            ProfileMenu(
                R.drawable.ic_linked_to_your_family_account,
                getString(R.string.linked_to_your_family)
            ),
            ProfileMenu(R.drawable.ic_set_reminder, getString(R.string.set_reminder)),
            ProfileMenu(R.drawable.ic_language, getString(R.string.language)),
            ProfileMenu(R.drawable.ic_logout, getString(R.string.logout))
        )

        binding.rvProfileMenu.layoutManager = LinearLayoutManager(this)
        binding.rvProfileMenu.adapter = ProfileMenuAdapter(menuList) { menu ->
            when (menu.name) {
                getString(R.string.account) -> showAccountDialog()
                getString(R.string.linked_to_your_family) -> showFamilyDialog()
                getString(R.string.set_reminder) -> showReminderDialog()
                getString(R.string.language) -> navigateToLanguageSettings()
                getString(R.string.logout) -> performLogout()
            }
        }
    }

    private fun showReminderDialog() {
        val dialog = createDialog(R.layout.category_reminder)
        val radioGroup = dialog.findViewById<RadioGroup>(R.id.radioGroup)
        val okButton = dialog.findViewById<Button>(R.id.okButton)
        val cancelButton = dialog.findViewById<Button>(R.id.cancelButton)

        okButton.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = dialog.findViewById<RadioButton>(selectedId)
            val selectedTime = selectedRadioButton?.text.toString()

            if (selectedTime.isNotEmpty()) {
                saveSelectedReminder(selectedTime)
                parseTimeToMillis(selectedTime)?.let {
                    scheduleDailyNotification(it)
                    Toast.makeText(this, getString(R.string.reminder_successfully_set), Toast.LENGTH_SHORT).show()
                } ?: Toast.makeText(this, getString(R.string.invalid_time_format), Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, getString(R.string.please_select_a_reminder_time), Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun saveSelectedReminder(selectedTime: String) {
        getSharedPreferences("user_preferences", MODE_PRIVATE)
            .edit()
            .putString("selected_reminder_time", selectedTime)
            .apply()
    }

    private fun parseTimeToMillis(time: String): Long? {
        val regex = Regex("\\d{2}:\\d{2}")
        if (!regex.matches(time)) return null

        val parts = time.split(":")
        val hour = parts[0].toIntOrNull()
        val minute = parts[1].toIntOrNull()

        if (hour !in 0..23 || minute !in 0..59) return null

        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour!!)
            set(Calendar.MINUTE, minute!!)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    private fun scheduleDailyNotification(reminderTimeInMillis: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = if (reminderTimeInMillis < this.timeInMillis) {
                reminderTimeInMillis + AlarmManager.INTERVAL_DAY
            } else {
                reminderTimeInMillis
            }
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun showAccountDialog() = showCustomDialog(
        R.drawable.account_illustarion,
        getString(R.string.your_account_is_registered),
        getString(R.string.continue_tracking_your_money_and_building_better_financial_habits)
    )

    private fun showFamilyDialog() = showCustomDialog(
        R.drawable.ic_family_dialog,
        getString(R.string.linked_to_your_family),
        getString(R.string.connect_with_your_family_and_make_financial_management_easier_and_more_transparent)
    )

    private fun performLogout() {
        showCustomDialog(
            R.drawable.ic_logout_illustration,
            getString(R.string.you_have_logged_out),
            getString(R.string.hope_to_see_you_again_soon)
        ) {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }

    private fun navigateToLanguageSettings() {
        startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
    }

    private fun showCustomDialog(
        imageRes: Int,
        title: String,
        description: String,
        onNextClick: (() -> Unit)? = null
    ) {
        val dialog = createDialog(R.layout.dialog_success)
        dialog.findViewById<ImageView>(R.id.imageView).setImageResource(imageRes)
        dialog.findViewById<TextView>(R.id.titleText).text = title
        dialog.findViewById<TextView>(R.id.descriptionText).text = description
        dialog.findViewById<Button>(R.id.nextButton).apply {
            setText(R.string.ok)
            setOnClickListener {
                dialog.dismiss()
                onNextClick?.invoke()
            }
        }
        dialog.show()
    }

    private fun createDialog(layoutRes: Int): Dialog {
        return Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(layoutRes)
            setCancelable(true)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val activityIntent = Intent(context, MainActivity::class.java)
            activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            val pendingIntent = PendingIntent.getActivity(
                context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
                .setContentTitle(context.getString(R.string.reminder))
                .setContentText(context.getString(R.string.track_your_money_reminder))
                .setSmallIcon(R.drawable.ic_notification_butgetin_green)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notification)
        }
    }
}