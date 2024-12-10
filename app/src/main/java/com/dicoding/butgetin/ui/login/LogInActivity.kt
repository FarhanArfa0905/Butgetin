package com.dicoding.butgetin.ui.login

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.butgetin.MainActivity
import com.dicoding.butgetin.R
import com.dicoding.butgetin.databinding.ActivityLogInBinding
import com.dicoding.butgetin.ui.signup.SignUpActivity

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivPasswordVisibility.setOnClickListener {
            if (binding.etPassword.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                binding.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivPasswordVisibility.setImageResource(R.drawable.ic_visibility)
            } else {
                binding.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.ivPasswordVisibility.setImageResource(R.drawable.ic_visibility_off)
            }
        }

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateLogin(email, password)) {
                showCustomDialog(true)
            } else {
                showCustomDialog(false)
            }
        }
    }

    private fun validateLogin(email: String, password: String): Boolean {
        return email == "user@example.com" && password == "password123"
    }

    private fun showCustomDialog(isSuccess: Boolean) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_success)
        dialog.setCancelable(false)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val imageView = dialog.findViewById<ImageView>(R.id.imageView)
        val titleText = dialog.findViewById<TextView>(R.id.titleText)
        val descriptionText = dialog.findViewById<TextView>(R.id.descriptionText)
        val nextButton = dialog.findViewById<Button>(R.id.nextButton)

        if (isSuccess) {
            imageView.setImageResource(R.drawable.ic_successful)
            imageView.setColorFilter(resources.getColor(R.color.green))
            titleText.text = getString(R.string.log_in_successful)
            titleText.setTextColor(resources.getColor(R.color.green))
            descriptionText.text =
                getString(R.string.continue_tracking_your_money_and_building_better_financial_habits)
            nextButton.text = getString(R.string.next)
            nextButton.setBackgroundTintList(
                ColorStateList.valueOf(resources.getColor(R.color.green))
            )
        } else {
            imageView.setImageResource(R.drawable.ic_failed)
            imageView.setColorFilter(resources.getColor(R.color.red))
            titleText.text = getString(R.string.log_in_failed)
            titleText.setTextColor(resources.getColor(R.color.red))
            descriptionText.text =
                getString(R.string.make_sure_the_information_you_entered_is_correct_then_try_again)
            nextButton.text = getString(R.string.back)
            nextButton.setBackgroundTintList(
                ColorStateList.valueOf(resources.getColor(R.color.red))
            )
        }

        nextButton.setOnClickListener {
            dialog.dismiss()
            if (isSuccess) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

        dialog.show()
    }
}