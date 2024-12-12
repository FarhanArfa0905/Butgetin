const { DataTypes, Model } = require('sequelize');
const sequelize = require('../config/database'); // Pastikan path ini benar

class Transaction extends Model {}

Transaction.init(
  {
    userId: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    date: {
      type: DataTypes.DATE,
      allowNull: false,
    },
    amount: {
      type: DataTypes.FLOAT,
      allowNull: false,
      validate: {
        isFloat: true, // Validasi agar hanya menerima angka
        min: 0, // Validasi agar jumlah transaksi tidak negatif
      }
    },
    category: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    type: {
      type: DataTypes.ENUM('Income', 'Expense'), // Pastikan ENUM memiliki nilai
      allowNull: false,
    },
    familyId: {
      type: DataTypes.INTEGER,
      allowNull: true,
    },
  },
  {
    sequelize,
    modelName: 'Transaction',
  }
);

module.exports = Transaction;
