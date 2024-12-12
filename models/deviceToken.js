const { Model, DataTypes } = require('sequelize');
const sequelize = require('../config/database');

class DeviceToken extends Model { }

// Inisialisasi DeviceToken model
DeviceToken.init(
  {
    userId: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    token: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true, // Token harus unik
    },
  },
  {
    sequelize,
    modelName: 'DeviceToken',
    timestamps: true, // Secara default akan menambahkan createdAt dan updatedAt
  }
);

module.exports = DeviceToken;