const express = require('express');
const { addBudget, getBudgets, getBudgetsByUser } = require('../controllers/budgetController');

const router = express.Router();

router.post('/budgets', addBudget); // Tambah anggaran
router.get('/budgets', getBudgets); // Dapatkan semua anggaran
router.get('/budgets/:userId', getBudgetsByUser); // Dapatkan anggaran berdasarkan pengguna

module.exports = router;
