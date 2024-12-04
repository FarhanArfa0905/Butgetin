const { Sequelize, DataTypes, Model } = require('sequelize');
const sequelize = require('../config/database'); // Pastikan konfigurasi sequelize benar
const bcrypt = require('bcrypt');

class User extends Model {
  static async findByEmail(email) {
    return await User.findOne({ where: { email } });
  }

  static async findById(id) {
    return await User.findByPk(id);
  }

  static async register(user) {
    const { fullname, email, password, googleId } = user;
    let hashedPassword = null;

    if (password) {
      hashedPassword = await bcrypt.hash(password, 10);
    }

    if (!hashedPassword && !googleId) {
      throw new Error('Password or Google ID is required');
    }

    const existingUser = await this.findByEmail(email);
    if (existingUser) {
      throw new Error('Email already in use');
    }

    const newUser = await User.create({
      fullname,
      email,
      password: hashedPassword,
      googleId,
    });

    return newUser;
  }
}

// Inisialisasi schema User
User.init(
  {
    fullname: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    email: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true,
      validate: {
        isEmail: true,
      },
    },
    password: {
      type: DataTypes.STRING,
      allowNull: true,
    },
    googleId: {
      type: DataTypes.STRING,
      allowNull: true,
    },
    avatar: {
      type: DataTypes.STRING,
    },
  },
  {
    sequelize,
    modelName: 'User',
  }
);

module.exports = User; // Pastikan User diekspor
