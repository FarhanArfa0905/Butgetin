const Budget = require('../models/budgetModel');

// Tambah anggaran baru
exports.addBudget = async (req, res) => {
  const { amount, category, userId, familyId } = req.body;

  try {
    if (!amount || !category || !userId) {
      return res.status(400).json({ message: 'All fields are required' });
    }

    const newBudget = await Budget.create({
      amount,
      category,
      userId,
      familyId,
    });

    res.status(201).json(newBudget);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};

// Dapatkan semua anggaran
exports.getBudgets = async (req, res) => {
  try {
    const budgets = await Budget.findAll({
      include: ['user', 'family'], // Sertakan informasi relasi
    });

    res.status(200).json(budgets);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};

// Dapatkan anggaran berdasarkan pengguna
exports.getBudgetsByUser = async (req, res) => {
  const { userId } = req.params;

  try {
    const budgets = await Budget.findAll({
      where: { userId },
      include: ['family'],
    });

    res.status(200).json(budgets);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};
