const admin = require('firebase-admin');
const serviceAccount = require('./config/butgetin-notification-firebase-adminsdk-eztvo-72a9de920c.json'); // Path ke file JSON Anda

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

module.exports = admin;