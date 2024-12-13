package com.dicoding.butgetin.ui.monthlyreport

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.butgetin.data.repository.RecommendationRepository
import com.dicoding.butgetin.databinding.FragmentMonthlyReportBinding
import com.dicoding.butgetin.ui.tab.TabActivity
import java.io.File

class MonthlyReportFragment : Fragment() {

    private var _binding: FragmentMonthlyReportBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MonthlyReportViewModel
    private lateinit var recommendationViewModel: RecommendationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi ViewModel untuk laporan bulanan
        viewModel = ViewModelProvider(this).get(MonthlyReportViewModel::class.java)

        // Inisialisasi RecommendationRepository dan RecommendationViewModel menggunakan Factory
        val repository = RecommendationRepository()
        val factory = RecommendationViewModelFactory(repository)
        recommendationViewModel = ViewModelProvider(this, factory).get(RecommendationViewModel::class.java)

        _binding = FragmentMonthlyReportBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnShowMore.setOnClickListener {
            val intent = Intent(requireContext(), TabActivity::class.java)
            intent.putExtra("isMonthlyReport", true)
            intent.putExtra("selected_tab", 1)
            startActivityForResult(intent, 100)
        }

        binding.btnExport.setOnClickListener {
            exportMonthlyReport()
        }

        getRecommendation()

        return root
    }

    private fun getRecommendation() {
        val userId = 1

        recommendationViewModel.getRecommendation(userId)

        recommendationViewModel.recommendationLiveData.observe(viewLifecycleOwner) { recommendation ->
            val recommendedBudget = recommendation.recommendation[0].budgetRecommendation
            binding.tvRecommendedBudget.text = "Rekomendasi Anggaran: $recommendedBudget"
        }

        recommendationViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun exportMonthlyReport() {
        val userId = "1" // Ganti sesuai dengan ID pengguna
        val startDate = "2024-01-01" // Ganti dengan tanggal mulai yang relevan
        val endDate = "2024-12-31" // Ganti dengan tanggal akhir yang relevan
        val directory = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val filePath = File(directory, "Transactions2024.xlsx")

        viewModel.exportTransaction(userId, startDate, endDate, filePath)

        // Observasi hasil proses ekspor
        Toast.makeText(requireContext(), "File sedang diunduh...", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
