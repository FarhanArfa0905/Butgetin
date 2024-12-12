const express = require('express');
const { saveDeviceToken, sendNotification } = require('../controllers/notificationController');
const router = express.Router();

router.post('/save-device-token', saveDeviceToken); // Simpan token
router.post('/send-notification', sendNotification); // Kirim notifikasi

module.exports = router;
