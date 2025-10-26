const express = require('express');
const auth = require('../middleware/auth');

const router = express.Router();

// Get all labours
router.get('/', auth, (req, res) => {
  try {
    const db = req.app.locals.db;
    const labours = db.prepare('SELECT id, name, email, phone, skills, hourly_rate, average_rating, availability FROM users WHERE role = "labour" ORDER BY average_rating DESC').all();
    
    // Parse JSON fields
    const enrichedLabours = labours.map(labour => {
      if (labour.skills) {
        try {
          labour.skills = JSON.parse(labour.skills);
        } catch (e) {
          labour.skills = [];
        }
      }
      return labour;
    });
    
    res.json(enrichedLabours);
  } catch (err) {
    console.error(err.message);
    res.status(500).send('Server error');
  }
});

module.exports = router;
