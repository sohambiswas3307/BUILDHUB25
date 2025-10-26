const express = require('express');
const auth = require('../middleware/auth');

const router = express.Router();

// Get purchased items for a project
router.get('/projects/:projectId/items', auth, (req, res) => {
  try {
    const db = req.app.locals.db;
    const items = db.prepare('SELECT * FROM purchased_items WHERE project_id = ?').all(req.params.projectId);
    res.json(items);
  } catch (err) {
    console.error(err.message);
    res.status(500).send('Server error');
  }
});

// Add purchased item
router.post('/projects/:projectId/items', auth, (req, res) => {
  try {
    const db = req.app.locals.db;
    const { itemName, quantity, unitPrice, totalPrice, category, supplier } = req.body;
    
    const stmt = db.prepare(`
      INSERT INTO purchased_items (project_id, item_name, quantity, unit_price, total_price, category, supplier)
      VALUES (?, ?, ?, ?, ?, ?, ?)
    `);
    
    const result = stmt.run(
      req.params.projectId,
      itemName,
      quantity,
      unitPrice,
      totalPrice,
      category || '',
      supplier || ''
    );
    
    const newItem = db.prepare('SELECT * FROM purchased_items WHERE id = ?').get(result.lastInsertRowid);
    res.json(newItem);
  } catch (err) {
    console.error(err.message);
    res.status(500).send('Server error');
  }
});

module.exports = router;

