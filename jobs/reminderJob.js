const schedule = require('node-schedule');
const DeviceToken = require('../models/deviceToken');
const User = require('../models/userModel')
const admin = require('../firebaseAdmin'); // Firebase Admin SDK

// Pengingat Harian
const sendDailyReminder = () => {
  schedule.scheduleJob('0 9 * * *', async () => {
    // Job berjalan setiap hari pukul 09:00
    try {
      const users = await User.findAll(); // Dapatkan semua pengguna
      const tokens = await DeviceToken.findAll({
        include: [{ model: User, attributes: ['id'] }],
      });
      for (const token of tokens) {
        const message = {
          token: token.token,
          notification: {
            title: 'Pengingat Harian',
            body: 'Jangan lupa mencatat transaksi hari ini!',
          },
        };
        await admin.messaging().send(message);
      }
      console.log('Pengingat harian berhasil dikirim.');
    } catch (error) {
      console.error('Error sending daily reminders:', error);
    }
  });
};

// Export fungsi
module.exports = { sendDailyReminder };
