package com.dicoding.butgetin.ui.tracking.part

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dicoding.butgetin.R
import com.dicoding.butgetin.api.Transaction
import com.dicoding.butgetin.ui.tracking.TransactionViewModel
import java.text.DecimalFormat
import java.util.Calendar

class ExpenseTrackingFragment : Fragment() {

    private val decimalFormat = DecimalFormat("#,###")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_tracking, container, false)

        val etAmount = view.findViewById<EditText>(R.id.et_amount)

        etAmount.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return

                isFormatting = true
                val originalText = s.toString().replace(",", "") // Remove existing commas
                try {
                    val formattedText = decimalFormat.format(originalText.toDouble())
                    etAmount.setText(formattedText)
                    etAmount.setSelection(formattedText.length) // Move cursor to the end
                } catch (e: NumberFormatException) {
                    etAmount.setText("")
                }
                isFormatting = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val clDate = view.findViewById<View>(R.id.cl_date)
        val etDate = view.findViewById<EditText>(R.id.et_date)
        val ivDate: ImageView = view.findViewById(R.id.iv_date)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerDialogExpense,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etDate.setText(selectedDate)
            }, year, month, day
        )

        clDate.setOnClickListener {
            datePickerDialog.show()
        }

        etDate.setOnClickListener {
            datePickerDialog.show()
        }

        ivDate.setOnClickListener {
            datePickerDialog.show()
        }

        val clCategory = view.findViewById<View>(R.id.cl_category)
        val etCategory = view.findViewById<EditText>(R.id.et_category)
        val ivCategory = view.findViewById<ImageView>(R.id.iv_category)

        clCategory.setOnClickListener {
            showCategoryDialog(etCategory)
        }

        etCategory.setOnClickListener {
            showCategoryDialog(etCategory)
        }

        ivCategory.setOnClickListener {
            showCategoryDialog(etCategory)
        }

        val btnSave = view.findViewById<Button>(R.id.btn_save)

        btnSave.setOnClickListener {
            val date = etDate.text.toString()
            val category = etCategory.text.toString()
            val amount = etAmount.text.toString().replace(",", "").toDoubleOrNull()

            if (date.isBlank() || category.isBlank()) {
                Toast.makeText(requireContext(),
                    getString(R.string.please_fill_in_all_fields), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (amount == null || amount <= 0.0) {
                Toast.makeText(requireContext(),
                    getString(R.string.invalid_amount), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isExpense = false
            val transaction = Transaction(date, category, amount, isExpense)

            val viewModel: TransactionViewModel by activityViewModels()
            viewModel.addTransaction(transaction)

            Toast.makeText(requireContext(),
                getString(R.string.transaction_added), Toast.LENGTH_SHORT)
                .show()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return view
    }

    private fun showCategoryDialog(etCategory: EditText) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.category_expense, null)
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radioGroup)
        val okButton = dialogView.findViewById<Button>(R.id.okButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = dialogBuilder.create()

        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        okButton.setOnClickListener {
            val selectedOptionId = radioGroup.checkedRadioButtonId
            if (selectedOptionId != -1) {
                val selectedRadioButton = dialogView.findViewById<RadioButton>(selectedOptionId)
                etCategory.setText(selectedRadioButton.text)
            }
            alertDialog.dismiss()
        }

        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}