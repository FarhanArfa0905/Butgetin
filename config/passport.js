const passport = require('passport');
const jwt = require('jsonwebtoken');
const dotenv = require('dotenv');
const GoogleStrategy = require('passport-google-oauth20').Strategy;
const User = require('../models/userModel'); // Mengimpor register dan metode lainnya
dotenv.config();

passport.use(
  new GoogleStrategy(
    {
      clientID: process.env.GOOGLE_CLIENT_ID,
      clientSecret: process.env.GOOGLE_CLIENT_SECRET,
      callbackURL: process.env.GOOGLE_CALLBACK_URL, // Pastikan sesuai dengan yang ada di Google Developer Console
      scope: ['profile', 'email'],
    },
    async (accessToken, refreshToken, profile, done) => {
      try {
        let user = await User.findByEmail(profile.emails[0].value);

        if (!user) {
          // Jika user belum ada, buat user baru
          user = await User.register({
            email: profile.emails[0].value,
            googleId: profile.id,
            fullname: profile.displayName,
            avatar: profile.photos ? profile.photos[0].value : null,
          });
        }

        // Create JWT token for the user
        const token = jwt.sign(
          { email: profile.emails[0].value, id: user.id },
          process.env.JWT_SECRET,
          { expiresIn: '1h' }
        );

        return done(null, { user, token });
      } catch (error) {
        console.error('Error during Google authentication:', error);
        return done(error, false);
      }
    }
  )
);

passport.serializeUser((user, done) => {
  done(null, user.id); // Menyimpan hanya ID pengguna
});

passport.deserializeUser(async (id, done) => {
  try {
    const user = await User.findById(id); // Ambil pengguna berdasarkan ID
    done(null, user); // Kirimkan objek pengguna setelah ditemukan
  } catch (error) {
    console.error('Error during deserialization:', error);
    done(error, null);
  }
});

module.exports = passport;