const express = require('express');
const auth = require('../middleware/auth');

const router = express.Router();

// Get user statistics
router.get('/stats', auth, (req, res) => {
  try {
    const db = req.app.locals.db;
    const stats = {
      totalProjects: 0,
      activeProjects: 0,
      completedProjects: 0,
      totalEarnings: 0
    };

    if (req.user.role === 'customer') {
      const projects = db.prepare('SELECT * FROM projects WHERE customer_id = ?').all(req.user.id);
      stats.totalProjects = projects.length;
      stats.activeProjects = projects.filter(p => p.status === 'in-progress').length;
      stats.completedProjects = projects.filter(p => p.status === 'completed').length;
    } else if (req.user.role === 'contractor') {
      const projects = db.prepare('SELECT * FROM projects WHERE contractor_id = ?').all(req.user.id);
      stats.totalProjects = projects.length;
      stats.activeProjects = projects.filter(p => p.status === 'in-progress').length;
      stats.completedProjects = projects.filter(p => p.status === 'completed').length;
    }

    res.json(stats);
  } catch (err) {
    console.error(err.message);
    res.status(500).send('Server error');
  }
});

module.exports = router;
