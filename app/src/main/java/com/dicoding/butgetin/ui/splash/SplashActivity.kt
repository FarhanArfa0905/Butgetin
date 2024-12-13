package com.dicoding.butgetin.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.butgetin.MainActivity
import com.dicoding.butgetin.R
import com.dicoding.butgetin.ui.welcome.WelcomeActivity // Pastikan Anda sudah menambahkan WelcomeActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val textView1 = findViewById<TextView>(R.id.textView1)
        val textView2 = findViewById<TextView>(R.id.textView2)

        val alphaAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val scaleAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        textView1.startAnimation(alphaAnim)
        textView2.startAnimation(scaleAnim)

        scaleAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                // Mengecek status login pengguna
                val isLoggedIn = checkUserLoginStatus()

                // Tentukan tujuan Activity berdasarkan status login
                val intent = if (isLoggedIn) {
                    Intent(this@SplashActivity, MainActivity::class.java)
                } else {
                    Intent(this@SplashActivity, WelcomeActivity::class.java)
                }

                // Mulai Activity yang sesuai dan tutup SplashActivity
                startActivity(intent)
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    private fun checkUserLoginStatus(): Boolean {
        val sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
        return sharedPreferences.getBoolean("is_logged_in", false)
    }
}