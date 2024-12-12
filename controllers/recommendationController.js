const { fetchRecommendation, calculateRecommendation } = require('../services/recommendationService');
const Budget = require('../models/budgetModel');
const Transaction = require('../models/transactionModel');
const axios = require('axios');
const FLASK_API_URL = 'https://machinelearning-60846954364.asia-southeast1.run.app/suggest_budget';


async function getRecommendation(req, res) {
  const { userId } = req.body; // Ambil user_id dari body request

  // Validasi jika user_id tidak disediakan
  if (!userId) {
    return res.status(400).json({ error: 'User ID is required' });
  }

  try {
    // Ambil data transaksi (expense dan income) dari model Transaction
    const transactions = await Transaction.findAll({
      where: { userId: userId },
    });

    if (!transactions || transactions.length === 0) {
      return res.status(404).json({ error: 'No transactions found for the provided user ID.' });
    }

    // Hitung total income dan expense
    const { income, totalExpenses } = transactions.reduce(
      (totals, transaction) => {
        if (transaction.type === 'Income') {
          totals.income += parseFloat(transaction.amount);
        } else if (transaction.type === 'Expense') {
          totals.totalExpenses += parseFloat(transaction.amount);
        }
        return totals;
      },
      { income: 0, totalExpenses: 0 }
    );

    // Kirim data transaksi ke Flask API untuk mendapatkan rekomendasi anggaran
    const response = await axios.post(FLASK_API_URL, { userId });

    if (response.status === 200) {
      const recommendation = response.data; // Rekomendasi anggaran dari Flask
      return res.status(200).json({ income, totalExpenses, recommendation });
    } else {
      return res.status(500).json({ error: 'Error fetching recommendations from Flask' });
    }
  } catch (error) {
    console.error('Error in recommendationController:', error);
    res.status(500).json({ error: 'Internal server error ', error: error.message });
  }
}

async function getRecommendations (req, res) {
  const { user_id } = req.body;  // Ambil user_id dari request body

  if (!user_id) {
    return res.status(400).json({ error: 'User ID is required' });
  }

  try {
    // Ambil data income (budget) dari model Budget
    const budget = await Budget.find({ user_id }).sort({ date: -1 }); // Ambil budget terakhir
    if (!budget) {
      return res.status(404).json({ error: 'Income data not found' });
    }

    // Ambil data expenses (transaction) dari model Transaction
    const transactions = await Transaction.find({ user_id });  // Ambil semua transaksi untuk user
    if (!transactions || transactions.length === 0) {
      return res.status(404).json({ error: 'Expense data not found' });
    }

    console.log('Budget model:', Budget);  // Periksa apakah Budget terdefinisi
    console.log('Transaction model:', Transaction);  // Periksa apakah Transaction terdefinisi


  // Total expenses
  const expenses = transactions.reduce((total, transaction) => total + transaction.amount, 0);

  const income = budget.amount;  // Mengambil income dari budget

  // Panggil service untuk menghitung rekomendasi
  const recommendation = await calculateRecommendation(income, expenses);
  
  res.status(200).json(recommendation);
} catch (error) {
  console.error('Error in recommendationController:', error.message);
  res.status(500).json({ error: 'Internal server error' });
}
}

module.exports = { getRecommendation, getRecommendations };
