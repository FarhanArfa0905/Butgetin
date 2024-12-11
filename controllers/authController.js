const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const User = require('../models/userModel');
const dotenv = require('dotenv');


dotenv.config();

module.exports = {
  async register(req, res) {
    const { fullname, email, password, confirmPassword } = req.body;

    // Validasi confirmPassword
    if (password !== confirmPassword) {
      return res.status(400).json({ message: "Passwords do not match" });
    }

    // Cek apakah user sudah ada
    const existingUser = await User.findByEmail(email);
    if (existingUser) {
      return res.status(400).json({ message: "User already exists" });
    }

    // Registrasi user baru
    const newUser = await User.register({ fullname, email, password });
    res.status(201).json({ message: "User registered", user: { id: newUser.id, fullname: newUser.fullname, email: newUser.email } });
  },

  async login(req, res) {
    const { email, password } = req.body;

    // Cari user berdasarkan email
    const user = await User.findByEmail(email);
    if (!user) {
      return res.status(404).json({ message: "User not found" });
    }

    // Verifikasi password
    const validPassword = await bcrypt.compare(password, user.password);
    if (!validPassword) {
      return res.status(401).json({ message: "Invalid credentials" });
    }

    // Generate token
    const token = jwt.sign({ id: user.id, email: user.email, fullname: user.fullname }, process.env.JWT_SECRET, { expiresIn: '1h' });

    res.status(200).json({ message: "Logged in", token });
  },

  async googleLogin(req, res) {
    try {
      // Pastikan req.user tidak kosong
      if (!req.user) {
        return res.status(400).json({ message: 'No user data returned from Google authentication' });
      }
      // Jika login Google berhasil, req.user sudah berisi user dan token JWT dari passport
      const { user, token } = req.user;

      // Kirimkan token JWT kepada pengguna
      res.status(200).json({
        message: 'Successfully logged in with Google',
        token,
        user,  // Informasi pengguna yang login bisa dikirimkan juga (seperti nama, email, dsb)
      });
    } catch (error) {
      res.status(500).json({ message: 'Error during Google login', error });
    }
  },
};
