const express = require('express');
const auth = require('../middleware/auth');

const router = express.Router();

// Create dispute
router.post('/disputes', auth, (req, res) => {
  try {
    if (req.user.role !== 'customer') {
      return res.status(403).json({ msg: 'Only customers can create disputes' });
    }

    const db = req.app.locals.db;
    const { projectId, type, description } = req.body;
    
    // Get project to find contractor
    const project = db.prepare('SELECT * FROM projects WHERE id = ?').get(projectId);
    if (!project) {
      return res.status(404).json({ msg: 'Project not found' });
    }
    
    const stmt = db.prepare(`
      INSERT INTO disputes (customer_id, contractor_id, project_id, type, description)
      VALUES (?, ?, ?, ?, ?)
    `);
    
    const result = stmt.run(
      req.user.id,
      project.contractor_id,
      projectId,
      type,
      description
    );
    
    const newDispute = db.prepare('SELECT * FROM disputes WHERE id = ?').get(result.lastInsertRowid);
    res.json(newDispute);
  } catch (err) {
    console.error(err.message);
    res.status(500).send('Server error');
  }
});

// Get customer disputes
router.get('/disputes', auth, (req, res) => {
  try {
    const db = req.app.locals.db;
    const disputes = db.prepare('SELECT * FROM disputes WHERE customer_id = ? ORDER BY created_at DESC').all(req.user.id);
    
    // Enrich with project info
    const enrichedDisputes = disputes.map(dispute => {
      const project = db.prepare('SELECT title FROM projects WHERE id = ?').get(dispute.project_id);
      return { ...dispute, project_title: project?.title };
    });
    
    res.json(enrichedDisputes);
  } catch (err) {
    console.error(err.message);
    res.status(500).send('Server error');
  }
});

module.exports = router;

