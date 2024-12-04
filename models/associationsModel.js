const User = require('./userModel');
const Family = require('./familyModel');
const Budget = require('./budgetModel');

const defineAssociations = () => {
  // Definisikan relasi
  User.hasMany(Family, {
    foreignKey: 'userId',
    as: 'families',
  });

  Family.belongsTo(User, {
    foreignKey: 'userId',
    as: 'user',
  });

  User.hasMany(Budget, {
    foreignKey: 'userId',
    as: 'budgets',
  });

  Budget.belongsTo(User, {
    foreignKey: 'userId',
    as: 'user',
  });

  Family.hasMany(Budget, {
    foreignKey: 'familyId',
    as: 'budgets',
  });

  Budget.belongsTo(Family, {
    foreignKey: 'familyId',
    as: 'family',
  });
};

module.exports = defineAssociations;
