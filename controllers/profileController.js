const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const User = require('../models/userModel'); // Sesuaikan dengan lokasi model User
const Family = require('../models/familyModel');

// Handler untuk mendapatkan profil pengguna
const getProfile = async (req, res) => {
  try {
    const userId = req.user.id; // Ambil userId dari token/auth middleware

    // Menggunakan Sequelize untuk menemukan user berdasarkan ID
    const user = await User.findByPk(userId);

    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }

    // Hapus password dari objek user
    const { password, ...userData } = user.toJSON();

    // Kirim data pengguna tanpa password
    res.status(200).json(userData);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Server error', error });
  }
};

// Handler untuk memperbarui profil pengguna
const updateProfile = async (req, res) => {
  try {
    const userId = req.user.id; // Ambil userId dari token/auth middleware
    const { fullname, email, avatar } = req.body; // Ambil data yang akan diperbarui

    // Cek apakah data yang diperlukan ada
    if (!fullname || !email) {
      return res.status(400).json({ message: "Fullname and email are required" });
    }

    // Cari user berdasarkan ID
    const user = await User.findByPk(userId);

    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }

    // Update data user
    user.fullname = fullname;
    user.email = email;
    user.avatar = avatar || user.avatar; // Avatar bisa null jika tidak ada update

    // Simpan perubahan ke database
    await user.save();

    // Kirimkan data yang sudah diperbarui
    res.status(200).json({ message: 'Profile updated successfully', user });

  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Server error', error });
  }
};

module.exports = {
  getProfile,
  updateProfile,
};