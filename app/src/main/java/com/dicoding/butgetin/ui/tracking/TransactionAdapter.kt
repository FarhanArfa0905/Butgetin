package com.dicoding.butgetin.ui.tracking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.butgetin.R
import com.dicoding.butgetin.api.Transaction
import com.dicoding.butgetin.databinding.ItemTransactionBinding

class TransactionAdapter(private var transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.binding.tvDate.text = transaction.date
        holder.binding.tvCategory.text = transaction.category
        holder.binding.tvAmount.text = if (transaction.isExpense) {
            "-${transaction.amount}"
        } else {
            "+${transaction.amount}"
        }

        holder.binding.tvAmount.setTextColor(
            holder.itemView.context.getColor(
                if (transaction.isExpense) R.color.expense else R.color.income
            )
        )
    }

    override fun getItemCount(): Int = transactions.size

    fun updateData(newTransactions: List<Transaction>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }
}