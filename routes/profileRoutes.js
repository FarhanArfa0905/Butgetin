const express = require('express');
const { getProfile, updateProfile } = require('../controllers/profileController');
const authMiddleware = require('../middleware/authMiddleware');

const router = express.Router();

// Endpoint untuk mendapatkan profil pengguna
router.get('/profile', authMiddleware, getProfile);

// Endpoint untuk memperbarui profil pengguna
router.put('/profile', authMiddleware, updateProfile);

module.exports = router;
