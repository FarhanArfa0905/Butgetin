const axios = require('axios');
const { modelUrl } = require('../config/config');

async function fetchRecommendation(user_id) {
  try {
    // Kirim request ke ML API
    const response = await axios.post(
      modelUrl,  // pastikan URL ini benar
      { user_id },
      {
        headers: {
          'Content-Type': 'application/json',
        },
      }
    );

    // Kembalikan data rekomendasi
    return response.data;
  } catch (error) {
    console.error('Error fetching recommendation:', error.message);
    throw new Error('Failed to fetch recommendation from ML API');
  }
}

// Logika perhitungan rekomendasi
async function calculateRecommendation (income, expenses) {
  const savingsGoal = income * 0.2; // Contoh: rekomendasi menabung 20% dari pendapatan
  const expenseRatio = expenses / income;

  let recommendation = '';
  if (expenseRatio > 0.7) {
    recommendation = 'Pengeluaran Anda terlalu besar, coba kurangi beberapa pengeluaran.';
  } else if (expenseRatio > 0.5) {
    recommendation = 'Pengeluaran Anda cukup besar, perhatikan pengeluaran tidak penting.';
  } else {
    recommendation = 'Pengeluaran Anda terkendali, pertimbangkan untuk menambah tabungan.';
  }

  return {
    savingsGoal,
    recommendation
  };
};

module.exports = { fetchRecommendation, calculateRecommendation };
