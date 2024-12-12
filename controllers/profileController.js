const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const User = require('../models/userModel'); // Sesuaikan dengan lokasi model User
const Family = require('../models/familyModel');

// Handler untuk mendapatkan profil pengguna
const getProfile = async (req, res) => {
  try {
    const userId = req.user.id; // Ambil userId dari token/auth middleware

    // Cari pengguna dan informasi keluarga yang terhubung
    const user = await User.findByPk(userId, {
      include: [{ model: Family, as: 'family' }],
    });


    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }

    // Hapus password dari objek user
    const { password, ...userData } = user.toJSON();

    // Kirimkan data pengguna beserta keluarga yang terhubung
    res.status(200).json({ ...userData, family: user.family || null });
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

// Handler untuk menghapus profil pengguna
const deleteProfile = async (req, res) => {
  try {
    const userId = req.user.id; // Ambil userId dari token/auth middleware

    // Cari user berdasarkan ID
    const user = await User.findByPk(userId);

    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }

    // Hapus data keluarga terkait jika perlu (jika ada relasi)
    if (user.familyId) {
      const family = await Family.findByPk(user.familyId);
      if (family) {
        await family.destroy(); // Hapus keluarga jika diperlukan
      }
    }

    // Hapus data pengguna
    await user.destroy();

    // Kirimkan response bahwa profil berhasil dihapus
    res.status(200).json({ message: 'Profile deleted successfully' });

  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Server error', error });
  }
};

module.exports = {
  getProfile,
  updateProfile,
  deleteProfile, // Pastikan fungsi deleteProfile diekspor
};
