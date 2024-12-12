const express = require('express');
const bodyParser = require('body-parser');
const syncDatabase = require('./utils/syncDatabase');
const passport = require('./config/passport');
const session = require('express-session');
const redis = require('redis');
const profileRoutes = require('./routes/profileRoutes');
const authRoutes = require('./routes/auth');
const familyRoutes = require('./routes/familyRoutes');
const budgetRoutes = require('./routes/budgetRoutes');
const notificationRoutes = require('./routes/notificationRoutes')
const transactionRoutes = require('./routes/transactionRoutes');
const recommendationRoutes = require('./routes/recommendationRoutes');
const authMiddleware = require('./middleware/authMiddleware');
const dotenv = require('dotenv');
require('./models/associationsModel');
dotenv.config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware express-session
app.use(bodyParser.json());
app.use(
  session({
    secret: 'your_secret_key', // Ganti dengan string rahasia Anda
    resave: false, // Jangan simpan sesi jika tidak diubah
    saveUninitialized: true, // Simpan sesi kosong
    cookie: { secure: false }, // Atur secure: true jika menggunakan HTTPS
  })
);

// Middleware
app.use(express.json());
app.use(passport.initialize());
app.use(passport.session());

// Routes
app.use('/auth', authRoutes);
app.use('/api', profileRoutes);
app.use('/api/family', familyRoutes);
app.use('/api', budgetRoutes);
app.use('/api', transactionRoutes);
app.use('/api', notificationRoutes);
app.use('/api', recommendationRoutes);

app.get('/protected', authMiddleware, (req, res) => {
  res.status(200).json({ message: 'Welcome to protected route', user: req.user });
});

app.get('/', (req, res) => {
  res.status(200).send('API is running successfully!');
});

// Global error handler
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({ error: 'Internal server error' });
});

// Start server with database sync
(async () => {
  await syncDatabase();
  app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
})();