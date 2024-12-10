package com.dicoding.butgetin.ui.signup

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Paint
import android.os.Bundle
import android.text.InputType
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.butgetin.MainActivity
import com.dicoding.butgetin.R
import com.dicoding.butgetin.databinding.ActivitySignUpBinding
import com.dicoding.butgetin.ui.login.LogInActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLoginLink.setOnClickListener {
            binding.tvLoginLink.paintFlags =
                binding.tvLoginLink.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        binding.ivPasswordVisibility.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivPasswordVisibility.setImageResource(R.drawable.ic_visibility) // Ikon mata terbuka
            } else {
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.ivPasswordVisibility.setImageResource(R.drawable.ic_visibility_off) // Ikon mata tertutup
            }
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }

        binding.ivConfirmPasswordVisibility.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            if (isConfirmPasswordVisible) {
                binding.etConfirmPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivConfirmPasswordVisibility.setImageResource(R.drawable.ic_visibility) // Ikon mata terbuka
            } else {
                binding.etConfirmPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.ivConfirmPasswordVisibility.setImageResource(R.drawable.ic_visibility_off) // Ikon mata tertutup
            }
            binding.etConfirmPassword.setSelection(binding.etConfirmPassword.text.length)
        }
    }

    private fun showSignUpDialog(isSuccess: Boolean) {
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
            titleText.text = getString(R.string.sign_up_successful)
            titleText.setTextColor(resources.getColor(R.color.green))
            descriptionText.text =
                getString(R.string.start_tracking_your_money_and_building_better_financial_habits)
            nextButton.text = getString(R.string.next)
            nextButton.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.green)))
        } else {
            imageView.setImageResource(R.drawable.ic_failed)
            imageView.setColorFilter(resources.getColor(R.color.red))
            titleText.text = getString(R.string.sign_up_failed)
            titleText.setTextColor(resources.getColor(R.color.red))
            descriptionText.text =
                getString(R.string.make_sure_the_information_you_entered_is_correct_then_try_again)
            nextButton.text = getString(R.string.back)
            nextButton.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.red)))
        }

        nextButton.setOnClickListener {
            dialog.dismiss()
            if (isSuccess) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        dialog.show()
    }

}