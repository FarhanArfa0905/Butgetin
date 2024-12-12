const { Sequelize, Op } = require('sequelize');
const Transaction = require('../models/transactionModel');
const ExcelJS = require('exceljs');
const fs = require('fs');
const csv = require('csv-parser');
const User = require('../models/userModel'); // Pastikan Anda memiliki relasi ke User
const Family = require('../models/familyModel');


// Menambahkan transaksi
exports.addTransaction = async (req, res) => {
  const { date, amount, category, type, userId, familyId } = req.body;

  try {
    if (!date || !amount || !category || !userId ||!familyId ) {
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

    // Membuat transaksi baru
    const newTransaction = await Transaction.create({
      date,
      amount,
      category,
      type,
      userId,
      familyId,
    });

    // Jika user adalah "child", kirim notifikasi ke orang tua
    if (user.role === 'child') {
      const parent = await User.findOne({ where: { role: 'parent', familyId } });

      if (parent) {
        // Ambil device token dari tabel DeviceToken
        const parentDeviceToken = await DeviceToken.findOne({ where: { userId: parent.id } });

        if (parentDeviceToken) {
          // Kirim notifikasi menggunakan Firebase Admin SDK
          const message = {
            token: parentDeviceToken.token,
            notification: {
              title: 'Transaksi Baru Dibuat',
              body: `${user.fullname} telah membuat transaksi sebesar Rp ${amount}.`,
            },
          };

          // Kirim notifikasi
          await admin.messaging().send(message);
          console.log('Notifikasi berhasil dikirim ke orang tua.');
        }
      }
    }

    res.status(201).json(newTransaction); // Kembalikan data transaksi yang baru
  } catch (error) {
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};

// Mendapatkan transaksi berdasarkan filter
exports.getTransactions = async (req, res) => {
  const { userId, category, startDate, endDate } = req.query;

  try {
    const where = {};

    // Filter berdasarkan userId
    if (userId) where.userId = userId;

    // Filter berdasarkan kategori
    if (category) where.category = category;

    // Filter berdasarkan tanggal
    if (startDate && endDate) {
      where.date = {
        [Op.between]: [new Date(startDate), new Date(endDate)]
      };
    }

    const transactions = await Transaction.findAll({
      where,
      order: [['date', 'DESC']] // Urutkan berdasarkan tanggal terbaru
    });

    res.status(200).json(transactions);
  } catch (error) {
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};

// Mengedit transaksi
exports.updateTransaction = async (req, res) => {
  const { id } = req.params;
  const { date, amount, category } = req.body;

  try {
    const transaction = await Transaction.findByPk(id);
    if (!transaction) {
      return res.status(404).json({ message: 'Transaction not found' });
    }

    transaction.date = date || transaction.date;
    transaction.amount = amount || transaction.amount;
    transaction.category = category || transaction.category;

    await transaction.save();

    res.status(200).json(transaction); // Kembalikan transaksi yang telah diperbarui
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
};

// Menghapus transaksi
exports.deleteTransaction = async (req, res) => {
  const { id } = req.params;

  try {
    const transaction = await Transaction.findByPk(id);
    if (!transaction) {
      return res.status(404).json({ message: 'Transaction not found' });
    }

    await transaction.destroy();
    res.status(200).json({ message: 'Transaction deleted successfully' });
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
};

// Laporan transaksi berdasarkan periode
exports.getTransactionReport = async (req, res) => {
  const { userId, startDate, endDate } = req.query;

  try {
    // Validasi parameter query
    if (!startDate || !endDate) {
      return res.status(400).json({ message: 'Start date and end date are required' });
    }

    // Menentukan filter untuk query
    const where = {
      date: {
        [Op.between]: [new Date(startDate), new Date(endDate)],
      },
    };

    // Filter berdasarkan userId jika disediakan
    if (userId) where.userId = userId;

    // Query ke database untuk laporan
    const report = await Transaction.findAll({
      where,
      attributes: [
        'type', // Menambahkan type ke dalam hasil
        [Sequelize.fn('SUM', Sequelize.col('amount')), 'totalAmount'],
      ],
      group: ['type'],
    });

    console.log('Laporan transaksi:', report);

    // Debug hasil query (bisa dihapus setelah selesai debug)
    console.log('Report:', report);

    // Format hasil laporan
    const incomeData = report.find((r) => r.type === 'Income');
    const expenseData = report.find((r) => r.type === 'Expense');

    // Pastikan nilai `totalAmount` diambil dengan benar
    const totalIncome = incomeData ? parseFloat(incomeData.dataValues.totalAmount) : 0;
    const totalExpense = expenseData ? parseFloat(expenseData.dataValues.totalAmount) : 0;

    // Kirimkan hasil ke klien
    res.status(200).json({
      totalIncome,
      totalExpense,
    });
  } catch (error) {
    // Tangani error dan beri respons ke klien
    console.error('Error in getTransactionReport:', error);
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};

// Menambahkan fitur ekspor ke Excel
exports.exportTransactionReportToExcel = async (req, res) => {
  const { startDate, endDate, userId } = req.query;

  try {
    if (!startDate || !endDate) {
      return res.status(400).json({ message: 'Start date and end date are required' });
    }

    const where = {
      date: {
        [Op.between]: [new Date(startDate), new Date(endDate)]
      }
    };

    // Tambahkan filter userId jika disediakan
    if (userId) where.userId = userId;

    const transactions = await Transaction.findAll({
      where,
      limit: 1000 // Batasi jumlah data yang diambil untuk ekspor
    });

    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet('Transaction Report');

    worksheet.columns = [
      { header: 'ID', key: 'id' },
      { header: 'Date', key: 'date' },
      { header: 'Amount', key: 'amount' },
      { header: 'Category', key: 'category' },
      { header: 'Type', key: 'type' },
    ];

    transactions.forEach((transaction) => {
      worksheet.addRow(transaction.dataValues); // Gunakan `dataValues` untuk mengambil properti transaksi
    });

    // Mengatur header response dengan nama file dinamis
    const fileName = `transaction_report_${Date.now()}.xlsx`;
    res.setHeader('Content-Disposition', `attachment; filename="${fileName}"`);
    res.setHeader('Content-Type', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');

    await workbook.xlsx.write(res);
    res.end();
  } catch (error) {
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};


// exports.syncTransactions = async (req, res) => {
//   const { transactions } = req.body;

//   if (!transactions || !Array.isArray(transactions)) {
//     return res.status(400).json({ message: 'Data transactions tidak valid' });
//   }

//   try {
//     // Gunakan bulkCreate dengan opsi updateOnDuplicate
//     const createdTransactions = await Transaction.bulkCreate(transactions, {
//       updateOnDuplicate: ['category', 'amount', 'type'], // Update jika data duplicate
//     });

//     res.status(201).json({
//       message: 'Data transactions berhasil disinkronkan',
//       data: createdTransactions,
//     });
//   } catch (error) {
//     console.error('Error syncing transactions:', error);
//     res.status(500).json({ message: 'Server error', error });
//   }
// };

// exports.loadDataset = async (req, res) => {
//   const path = require('path');
//   const filePath = path.join(__dirname, '..', 'data', 'transactionData.csv');

//   const transactions = []; // Tempat menyimpan data sementara
//   const maxRows = 11000; // Batasi jumlah baris untuk pengujian
//   let rowCount = 0; // Variabel untuk menghitung jumlah baris yang dibaca

//   console.log('Resolved file path:', filePath);

//   try {
//     // Memastikan file path ada
//     if (!fs.existsSync(filePath)) {
//       return res.status(404).json({ message: 'File not found' });
//     }

//     // Membaca file CSV
//     fs.createReadStream(filePath)
//       .pipe(csv())
//       .on('data', (row) => {
//         if (rowCount >= maxRows) {
//           // Batasi hanya sampai maxRows
//           return;
//         }

//         transactions.push({
//           userId: row.user_id,
//           date: new Date(row.date),
//           category: row.category,
//           amount: parseInt(row.amount, 10),
//           type: row.type,
//         });

//         rowCount++;
//       })
//       .on('end', async () => {
//         try {
//           // Simpan data ke database menggunakan bulkCreate
//           if (transactions.length > 0) {
//             const createdTransactions = await Transaction.bulkCreate(transactions, {
//               updateOnDuplicate: ['category', 'amount', 'type'], // Update jika data duplikat
//             });

//             res.status(200).json({
//               message: 'Dataset berhasil dimuat ke database',
//               data: createdTransactions,
//             });
//           } else {
//             res.status(400).json({ message: 'No valid transactions found in CSV' });
//           }
//         } catch (dbError) {
//           console.error('Database Error:', dbError);
//           res.status(500).json({ message: 'Error saat menyimpan ke database', error: dbError });
//         }
//       })
//       .on('error', (error) => {
//         console.error('Error saat membaca file CSV:', error);
//         res.status(500).json({ message: 'Error saat membaca file dataset', error });
//       });
//   } catch (error) {
//     console.error('General Error:', error);
//     res.status(500).json({ message: 'Error saat memproses dataset', error });
//   }
// };


