const express = require('express');
const router = express.Router();
const familyController = require('../controllers/familyController');

// Menambahkan anggota keluarga
router.post('/add', familyController.addFamilyMember);

// Mendapatkan semua anggota keluarga
router.get('/', familyController.getAllFamilies);

// Mendapatkan keluarga berdasarkan userId
router.get('/:userId', familyController.getFamilyByUser);

module.exports = router;