const User = require('./userModel');
const Family = require('./familyModel');
const Budget = require('./budgetModel');
const DeviceToken = require('./deviceToken');
const Transaction = require('./transactionModel');

const defineAssociations = () => {
  // Definisikan relasi antara User dan Family
  User.hasMany(Family, {
    foreignKey: 'userId',
    as: 'family',  // Relasi User ke banyak Family
  });

  User.belongsTo(Family, {
    foreignKey: 'familyId',
    as: 'primaryFamily'  // Alias yang berbeda untuk hubungan 'User' ke satu 'Family'
  });

  Family.hasMany(User, {
    foreignKey: 'familyId',
    as: 'members',  // Alias yang berbeda untuk hubungan Family ke banyak User
  });

  Family.belongsTo(User, {
    foreignKey: 'userId',
    as: 'headOfFamily',  // Alias yang berbeda untuk hubungan Family ke satu User
  });

  User.hasMany(Budget, {
    foreignKey: 'userId',
    as: 'budgets',
  });

  User.hasMany(Transaction, {
    foreignKey: 'userId',
    as: 'transactions',
  });

  Transaction.belongsTo(User, {
    foreignKey: 'userId',
    as: 'user',
  });

  Budget.belongsTo(User, {
    foreignKey: 'userId',
    as: 'user',
  });

  Family.hasMany(Transaction, {
    foreignKey: 'familyId',
    as: 'transactions,'
  });

  Family.hasMany(Budget, {
    foreignKey: 'familyId',
    as: 'budgets',
  });

  Transaction.belongsTo(Family, {
    foreignKey: 'familyId',
    as: 'family',
  });

  Budget.belongsTo(Family, {
    foreignKey: 'familyId',
    as: 'family',
  });

  // Menambahkan relasi untuk DeviceToken
  User.hasMany(DeviceToken, {
    foreignKey: 'userId',
    as: 'deviceTokens',  // Jika ingin mengakses token berdasarkan pengguna
  });

  DeviceToken.belongsTo(User, {
    foreignKey: 'userId',
    as: 'user',
  });

  // Jika Anda ingin DeviceToken dihubungkan dengan Family, Anda bisa menambahkan asosiasi berikut
  Family.hasMany(DeviceToken, {
    foreignKey: 'userId',  // Asosiasi berdasarkan userId (keluarga terkait dengan user)
    as: 'deviceTokensForFamily',  // Alias yang berbeda untuk relasi DeviceToken dengan Family
  });

  DeviceToken.belongsTo(Family, {
    foreignKey: 'userId',
    as: 'family',
  });
};

module.exports = defineAssociations;
