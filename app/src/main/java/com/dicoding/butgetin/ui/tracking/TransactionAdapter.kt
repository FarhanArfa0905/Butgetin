package com.dicoding.butgetin.ui.tracking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.butgetin.R
import com.dicoding.butgetin.data.model.TransactionResponse
import com.dicoding.butgetin.databinding.ItemTransactionBinding

class TransactionAdapter(
    private var transactions: List<TransactionResponse>,
    private val onDeleteClick: (TransactionResponse) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: TransactionResponse) {
            binding.tvDate.text = transaction.date
            binding.tvCategory.text = transaction.category
            binding.tvAmount.text = transaction.amount.toString()

            // Menetapkan ikon kategori berdasarkan kategori, bukan berdasarkan isExpense
            val iconRes = R.drawable.ic_category // Tetap menggunakan ikon kategori
            binding.iconCategory.setImageResource(iconRes)

            binding.tvAmount.setTextColor(
                itemView.context.getColor(
                    if (transaction.amount < 0) R.color.expense else R.color.income // Misalnya, jika amount negatif, dianggap sebagai expense
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction)
        holder.itemView.findViewById<View>(R.id.btnDelete).setOnClickListener {
            onDeleteClick(transaction)
        }
    }

    override fun getItemCount(): Int = transactions.size

    fun updateData(newTransactions: List<TransactionResponse>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }
}
