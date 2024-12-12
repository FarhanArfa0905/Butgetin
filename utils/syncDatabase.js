const sequelize = require('../config/database');
const defineAssociations = require('../models/associationsModel');

const syncDatabase = async () => {
  try {
    await sequelize.authenticate();
    console.log('Database connection established');

    // Definisikan relasi sebelum sinkronisasi
    defineAssociations();

    await sequelize.sync({ alter: true }); // Gunakan alter untuk memperbarui tanpa menghancurkan data
    console.log('Models synchronized');
  } catch (error) {
    console.error('Error synchronizing database:', error);
  }
};

module.exports = syncDatabase;