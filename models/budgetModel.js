const { DataTypes, Model } = require('sequelize');
const sequelize = require('../config/database');

class Budget extends Model { }

Budget. init(
  {
    date: {
      type: DataTypes.DATE,
      allowNull: false,
    },
    amount: {
      type: DataTypes.FLOAT,
      allowNull: false,
    },
    category: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    userId: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    familyId: {
      type: DataTypes.INTEGER,
      allowNull: true,
    },
  },
{
  sequelize,
  modelName: 'Budget',
}
);

module.exports = Budget;