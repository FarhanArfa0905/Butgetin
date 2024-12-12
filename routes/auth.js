const express = require('express');
const passport = require('passport');
const router = express.Router();
const authController = require('../controllers/authController');
const authMiddleware = require('../middleware/authMiddleware');

// Rute untuk register dan login
router.post('/register', authController.register);
router.post('/login', authController.login);

// Logout
router.post('/logout', authController.logout);

// Rute untuk akses yang dilindungi
router.get('/protected', authMiddleware, (req, res) => {
  res.status(200).json({ message: 'Welcome to protected route', user: req.user });
});

// Rute untuk login dengan Google
router.get(
  '/google',
  passport.authenticate('google', { scope: ['profile', 'email'] })
);

router.get(
  '/google/callback',
  passport.authenticate('google', { failureRedirect: '/login', session: false }),
  (req, res) => {
    // Login berhasil
    const token = req.user.token; // Ambil token dari Passport
    res.json({
      message: 'Login successful',
      token,
      user: req.user,
    }); // Kirim token JWT
  }
);

module.exports = router;