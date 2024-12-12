const DeviceToken = require('../models/deviceToken');


// Controller untuk menyimpan device token
exports.saveDeviceToken = async (req, res) => {
  const { userId, token } = req.body;

  try {
     // Cari token yang sudah ada
     let deviceToken = await DeviceToken.findOne({ where: { userId } });
     console.log(DeviceToken); // Menampilkan model DeviceToken untuk memastikan sudah terdefinisi


    if (deviceToken) {
      // Perbarui token jika sudah ada
      deviceToken.token = token;
      await deviceToken.save();
    } else {
      // Buat token baru jika belum ada
      await DeviceToken.create({ userId, token });
    }

    res.status(200).json({ success: true, message: 'Token FCM berhasil disimpan' });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
};
  
  exports.sendNotification = async (req, res) => {
    const { userId, title, body } = req.body;
    const admin = require('../firebaseAdmin'); // Pastikan Firebase Admin SDK sudah diinisialisasi
  
    try {
      const deviceToken = await DeviceToken.findOne({ where: { userId } });
      if (!deviceToken) {
        return res.status(404).json({ success: false, message: 'Token FCM tidak ditemukan' });
      }
  
      // Siapkan payload notifikasi
      const message = {
        token: deviceToken.token,
        notification: {
          title,
          body,
        },
      };
  
      // Kirim notifikasi melalui Firebase Admin
      await admin.messaging().send(message);
      res.status(200).json({ success: true, message: 'Notifikasi berhasil dikirim' });
    } catch (error) {
      res.status(500).json({ success: false, error: error.message });
    }
  };

// const checkBudget = async (userId) => {
//     const user = await User.findByPk(userId);
//     if (user.budgetRemaining < user.budget * 0.1) {
//       const token = user.notificationToken;
//       await sendNotification({
//         token,
//         title: 'Warning: Budget Alert!',
//         body: 'Your budget is almost depleted. Please manage your expenses wisely.',
//       });
//     }
//   };
  
//   module.exports = {
//     checkBudget
//   }