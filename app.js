const express = require('express');
const passport = require('./config/passport');
const bodyParser = require('body-parser');
const dotenv = require('dotenv');
const authRoutes = require('./routes/auth');
const authMiddleware = require('./middleware/authMiddleware'); // Middleware JWT
const session = require('express-session');  // Menambahkan express-session
dotenv.config();

const app = express();

// Gunakan express.json() yang sudah ada di Express 4.16+
app.use(express.json());

// Inisialisasi passport tanpa express-session
app.use(session({
  secret: 'your-secret-key',  // Ganti dengan secret key yang aman
  resave: false,
  saveUninitialized: true,
}));

app.use(passport.initialize());
app.use(passport.session());  // Menggunakan session untuk Passport

// Routing untuk autentikasi
app.use('/auth', authRoutes);

// Route yang dilindungi menggunakan JWT middleware
app.get('/protected', authMiddleware, (req, res) => {
  res.status(200).json({ message: "Welcome to protected route", user: req.user });
});

app.get('/', (req, res) => {
  res.status(200).send('API is running successfully!');
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));

// Koneksi database dan sinkronisasi
const sequelize = require('./config/database');
sequelize.authenticate()
  .then(() => console.log('Database connected...'))
  .catch(err => console.error('Unable to connect to database:', err));

sequelize.sync({ alter: true })
  .then(() => console.log('Database synchronized'))
  .catch(err => console.error('Database sync error:', err));
