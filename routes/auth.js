const express = require('express');
const passport = require('passport');
const router = express.Router();
const authController = require('../controllers/authController');
const authMiddleware = require('../middleware/authMiddleware');

// Rute untuk register dan login
router.post('/register', authController.register);
router.post('/login', authController.login);

// Rute untuk akses yang dilindungi (gunakan middleware untuk autentikasi)
router.get('/protected', authMiddleware, (req, res) => {
  res.status(200).json({ message: 'Welcome to protected route', user: req.user });
});

// Rute untuk memulai autentikasi Google
router.get('/auth/google', passport.authenticate('google', {
  scope: ['profile', 'email'],
}));

router.get('/auth/google/callback',
  passport.authenticate('google', { failureRedirect: '/login' }),
  (req, res) => {
    // Respons jika autentikasi berhasil
    res.redirect('/dashboard');  // Redirect ke halaman dashboard atau halaman lain
  }
);


module.exports = router;
