const express = require('express');
const router = express.Router();
const { getRecommendation, getRecommendations}= require('../controllers/recommendationController');

// Rute untuk mendapatkan rekomendasi
router.post('/recommendation', getRecommendation);

router.post('/get-recommendations', getRecommendations);

module.exports = router;
