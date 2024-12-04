const Family = require('../models/familyModel');
const User = require('../models/userModel');

// Menambahkan anggota keluarga
exports.addFamilyMember = async (req, res) => {
  const { name, relation, age, userId } = req.body;
  console.log('Request body:', req.body); // Log body request yang diterima

  try {
    // Pastikan semua input valid
    if (!name || !relation || !age || !userId) {
      console.log('Missing fields:', { name, relation, age, userId });
      return res.status(400).json({ message: 'All fields are required' });
    }

    // Cek apakah userId ada di database
    const user = await User.findByPk(userId);
    if (!user) {
      console.log('User not found:', userId);
      return res.status(404).json({ message: 'User not found' });
    }

    console.log('User found:', user); // Log user yang ditemukan

    const newFamilyMember = await Family.create({
      name,
      relation,
      age,
      userId
    });

    console.log('New family member created:', newFamilyMember); // Log keluarga baru yang dibuat

    res.status(201).json(newFamilyMember); // Kembalikan data anggota keluarga yang baru
  } catch (error) {
    console.error("Error adding family member:", error);  // Log error detail
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};


// Mendapatkan semua anggota keluarga
exports.getAllFamilies = async (req, res) => {
  try {
    const families = await Family.findAll({
      include: {
        model: User,
        as: 'user',
        attributes: ['id', 'fullname', 'email']  // Menyertakan informasi User terkait
      }
    });
    res.status(200).json(families);
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
};

// familyController.js
exports.getFamilyByUser = async (req, res) => {
  const { userId } = req.params;

  try {
    // Mencari user berdasarkan userId dan mengikutkan keluarga
    const user = await User.findByPk(userId, {
      include: {
        model: Family,
        as: 'families', // Mengambil data keluarga dari relasi
        attributes: ['id', 'name', 'relation', 'age'],
      }
    });

    // Jika user tidak ditemukan, kembalikan respons 404
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }

    // Cek apakah data keluarga ditemukan
    console.log('User families:', user.families);

    // Pastikan families bukan array kosong
    if (user.families && user.families.length > 0) {
      return res.status(200).json(user.families);
    }

    // Mengembalikan data keluarga
    res.status(200).json(user.families);
  } catch (error) {
    // Menangani error server
    console.error(error);
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};
