const express = require('express');
const auth = require('../middleware/auth');

const router = express.Router();

// Get all contractors
router.get('/', auth, (req, res) => {
  try {
    const db = req.app.locals.db;
    const contractors = db.prepare('SELECT id, name, email, phone, specialization, experience, average_rating FROM users WHERE role = "contractor" ORDER BY average_rating DESC').all();
    res.json(contractors);
  } catch (err) {
    console.error(err.message);
    res.status(500).send('Server error');
  }
});

module.exports = router;
