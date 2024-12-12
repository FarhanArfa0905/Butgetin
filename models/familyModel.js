const { DataTypes, Model } = require('sequelize');
const sequelize = require('../config/database');
const User = require('./userModel'); // Impor User untuk relasi

class Family extends Model { }

Family.init(
  {
    name: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    relation: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    age: {  // Pastikan kolom age didefinisikan di sini
      type: DataTypes.INTEGER,
      allowNull: true, // Menandakan kolom ini boleh bernilai null
    },
  },
  {
    sequelize,
    modelName: 'Family',
  }
);

module.exports = Family;