const express = require('express');
const router = express.Router();
const {
  addTransaction,
  getTransactions,
  updateTransaction,
  deleteTransaction,
  getTransactionReport,
  exportTransactionReportToExcel,
  syncTransactions,
  loadDataset,
} = require('../controllers/transactionController');  // Sesuaikan dengan path controller Anda

// Menambahkan transaksi
router.post('/transaction', addTransaction);

// Melihat transaksi dengan filter
router.get('/transactions', getTransactions);

// Mengedit Transaksi
router.put('/transaction/:id', updateTransaction);

// Mengdelete Transaksi
router.delete('/transaction/:id', deleteTransaction);

// Mendapatkan laporan transaksi dalam periode tertentu
router.get('/transaction-report', getTransactionReport);

// Route untuk ekspor laporan transaksi ke Excel
router.get('/transactions/export', exportTransactionReportToExcel);

// router.get('/load-dataset', loadDataset); // Endpoint untuk memuat dataset

// router.post('/sync', syncTransactions);

module.exports = router;
