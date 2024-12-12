const Budget = require('../models/budgetModel');
const User = require('../models/userModel');
const Family = require('../models/familyModel');

exports.addBudget = async (req, res) => {
  const { date, amount, category, userId, familyId } = req.body;

  try {
    if (!date|| !amount || !category || !userId ) {
      return res.status(400).json({ message: 'All fields are required' });
    }

    // Cek apakah userId ada di database
    const user = await User.findByPk(userId);
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }

    // Cek apakah familyId ada jika disertakan
    if (familyId) {
      const family = await Family.findByPk(familyId);
      if (!family) {
        return res.status(404).json({ message: 'Family not found' });
      }
    }

    const newBudget = await Budget.create({
      date,
      amount,
      category,
      userId,
      familyId,
    });

    // Jika user adalah "child", kirim notifikasi ke orang tua
    if (user.role === 'child') {
      const parent = await User.findOne({ where: { role: 'parent', familyId } }); // Cari orang tua berdasarkan familyId

      if (parent) {
        // Ambil device token dari tabel DeviceToken
        const parentDeviceToken = await DeviceToken.findOne({ where: { userId: parent.id } });

        if (parentDeviceToken) {
          // Kirim notifikasi menggunakan Firebase Admin SDK
          const message = {
            token: parentDeviceToken.token,
            notification: {
              title: 'Anggaran Baru Dibuat',
              body: `${user.fullname} telah membuat anggaran sebesar Rp ${amount}.`,
            },
          };

          // Kirim notifikasi
          await admin.messaging().send(message);
          console.log('Notifikasi berhasil dikirim ke orang tua.');
        }
      }
    }

    res.status(201).json(newBudget); // Kembalikan data anggaran yang baru
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
};

exports.getBudgets = async (req, res) => {
  const { userId, month } = req.query;

  try {
    const where = {};
    if (userId) where.userId = userId;
    if (month) where.month = month;

    const budgets = await Budget.findAll({
      where,
      include: [
        {
          model: User,
          as: 'user',
          attributes: ['id', 'fullname', 'email'],
        },
        {
          model: Family,
          as: 'family',
          attributes: ['id', 'name', 'relation', 'age'],
        },
      ],
    });

    res.status(200).json(budgets); // Kembalikan daftar anggaran
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
};

// Mengedit anggaran
exports.updateBudget = async (req, res) => {
  const { id } = req.params;
  const { date, amount, category } = req.body;

  try {
    const budget = await Budget.findByPk(id);
    if (!budget) {
      return res.status(404).json({ message: 'Budget not found' });
    }

    budget.date = date || budget.date;
    budget.amount = amount || budget.amount;
    budget.category = category || budget.category;

    await budget.save();

    res.status(200).json(budget); // Kembalikan anggaran yang telah diperbarui
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
};

// Menghapus anggaran
exports.deleteBudget = async (req, res) => {
  const { id } = req.params;

  try {
    const budget = await Budget.findByPk(id);
    if (!budget) {
      return res.status(404).json({ message: 'Budget not found' });
    }

    await budget.destroy();
    res.status(200).json({ message: 'Budget deleted successfully' });
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
};

// Dapatkan anggaran berdasarkan pengguna
exports.getBudgetsByUser = async (req, res) => {
  const { userId } = req.params;

  try {
    const budgets = await Budget.findAll({
      where: { userId },
      include: ['family'],
    });

    res.status(200).json(budgets);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};