package com.dicoding.butgetin.ui.monthlyreport

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.butgetin.databinding.FragmentMonthlyReportBinding
import com.dicoding.butgetin.ui.tab.TabActivity

class MonthlyReportFragment : Fragment() {

    private var _binding: FragmentMonthlyReportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this).get(MonthlyReportViewModel::class.java)

        _binding = FragmentMonthlyReportBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            if (transactions.isNullOrEmpty()) {
                binding.illustrationTransaction.visibility = View.VISIBLE
                binding.textView.visibility = View.VISIBLE
            } else {
                binding.illustrationTransaction.visibility = View.GONE
                binding.textView.visibility = View.GONE
            }
        }

        binding.btnShowMore.setOnClickListener {
            val intent = Intent(requireContext(), TabActivity::class.java)
            intent.putExtra("isMonthlyReport", true)
            intent.putExtra("selected_tab", 1)
            startActivityForResult(intent, 100)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}