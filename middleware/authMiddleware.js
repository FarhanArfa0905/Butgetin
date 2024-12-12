const jwt = require('jsonwebtoken');
const dotenv = require('dotenv');
dotenv.config();

module.exports = (req, res, next) => {
  const authHeader = req.header('Authorization');
  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({ message: "Access Denied, no token provided" });
  }

  const token = authHeader.split(' ')[1];
  try {
    const verified = jwt.verify(token, process.env.JWT_SECRET);
    req.user = verified; // Pastikan payload token memiliki 'id'
    console.log("Token verified, user payload:", verified);
    next();
  } catch (err) {
    res.status(401).json({ message: "Invalid Token" });
  }
};