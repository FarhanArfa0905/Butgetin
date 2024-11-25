const { DataTypes } = require('sequelize');
const bcrypt = require('bcrypt');
const sequelize = require('../config/database');

// Definisikan model User
const User = sequelize.define('User', {
  fullname: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  email: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true,
    validate: {
      isEmail: true, // Validasi email
    },
  },
  password: {
    type: DataTypes.STRING,
    allowNull: true,  // Password bisa kosong jika login dengan Google
  },
  googleId: {
    type: DataTypes.STRING,
    allowNull: true,  // Google ID bisa null jika tidak digunakan
  },
  avatar: {
    type: DataTypes.STRING,
  },
});

// Method untuk mencari user berdasarkan email
async function findByEmail(email) {
  return await User.findOne({ where: { email } });
}

// Method untuk mencari user berdasarkan ID
async function findById(id) {
  return await User.findByPk(id);
}

// Method untuk register user baru
async function register(user) {
  const { fullname, email, password, googleId } = user;

  let hashedPassword = null;

  // Jika ada password, hash password
  if (password) {
    hashedPassword = await bcrypt.hash(password, 10);
  }

  // Pastikan kita menangani kedua kasus: password atau googleId harus ada
  if (!hashedPassword && !googleId) {
    throw new Error('Password or Google ID is required');
  }

  // Cek apakah email sudah terdaftar
  const existingUser = await findByEmail(email);
  if (existingUser) {
    throw new Error('Email already in use');
  }

  const newUser = await User.create({
    fullname,
    email,
    password: hashedPassword,  // Ini hanya berlaku jika ada password
    googleId,                  // Google ID akan di-set jika menggunakan Google login
  });

  return newUser;
}

module.exports = {
  User,
  findByEmail,
  findById,
  register  // Pastikan `register` diekspor
};
