const express = require('express');
const router = express.Router();
const { addBudget, getBudgets, updateBudget, deleteBudget, getBudgetsByUser } = require('../controllers/budgetController');

// Menambahkan anggaran
router.post('/budget', addBudget);

// Melihat anggaran
router.get('/budget', getBudgets);

// Mengedit anggaran
router.put('/budget/:id', updateBudget);

// Menghapus anggaran
router.delete('/budget/:id', deleteBudget);
router.get('/budget/:userId', getBudgetsByUser); // Dapatkan anggaran berdasarkan pengguna

module.exports = router;