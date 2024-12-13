package com.dicoding.butgetin.ui.tracking

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.butgetin.data.api.RetrofitClient
import com.dicoding.butgetin.databinding.FragmentTrackingBinding
import com.dicoding.butgetin.ui.tab.TabActivity

class TrackingFragment : Fragment() {

    private var _binding: FragmentTrackingBinding? = null
    private val binding get() = _binding!!

    private val apiService = RetrofitClient.instance
    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory(apiService)
    }
    private lateinit var adapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TransactionAdapter(
            listOf(),
            onDeleteClick = { transaction -> showDeleteConfirmationDialog(transaction.id) },
        )
        binding.rvTransactions.adapter = adapter
        binding.rvTransactions.layoutManager = LinearLayoutManager(requireContext())

        transactionViewModel.fetchTransactions()

        transactionViewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            Log.d("TrackingFragment", "Transactions updated: ${transactions.size}")
            if (transactions.isEmpty()) {
                binding.rvTransactions.visibility = View.GONE
                binding.illustrationTransaction.visibility = View.VISIBLE
                binding.textView.visibility = View.VISIBLE
            } else {
                binding.rvTransactions.visibility = View.VISIBLE
                binding.illustrationTransaction.visibility = View.GONE
                binding.textView.visibility = View.GONE
                adapter.updateData(transactions)
            }
        }


        transactionViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            if (!isLoading) {
                transactionViewModel.transactions.value?.let { transactions ->
                    adapter.updateData(transactions)
                }
            }
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), TabActivity::class.java)
            startActivity(intent)
            transactionViewModel.fetchTransactions()
        }
    }

    override fun onResume() {
        super.onResume()
        transactionViewModel.fetchTransactions()
    }

    private fun showDeleteConfirmationDialog(transactionId: Int) { // transactionId: Int
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Transaksi")
            .setMessage("Apakah Anda yakin ingin menghapus transaksi ini?")
            .setPositiveButton("Ya") { _, _ ->
                transactionViewModel.deleteTransaction(transactionId)
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}