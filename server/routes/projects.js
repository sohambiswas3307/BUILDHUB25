const express = require('express');
const auth = require('../middleware/auth');

const router = express.Router();

// Get all projects
router.get('/', auth, (req, res) => {
  try {
    const db = req.app.locals.db;
    let query = 'SELECT * FROM projects WHERE 1=1';
    const params = [];
    
    if (req.user.role === 'customer') {
      query += ' AND customer_id = ?';
      params.push(req.user.id);
    } else if (req.user.role === 'contractor') {
      query += ' AND (contractor_id = ? OR contractor_id IS NULL)';
      params.push(req.user.id);
    }
    
    query += ' ORDER BY created_at DESC';
    
    const stmt = db.prepare(query);
    const projects = stmt.all(...params);
    
    // Get associated data for each project
    const enrichedProjects = projects.map(project => {
      const customerStmt = db.prepare('SELECT id, name, email, phone FROM users WHERE id = ?');
      const customer = customerStmt.get(project.customer_id);
      
      const result = { ...project, customer };
      
      if (project.contractor_id) {
        const contractorStmt = db.prepare('SELECT id, name, email, phone FROM users WHERE id = ?');
        const contractor = contractorStmt.get(project.contractor_id);
        result.contractor = contractor;
      }
      
      return result;
    });
    
    res.json(enrichedProjects);
  } catch (err) {
    console.error(err.message);
    res.status(500).send('Server error');
  }
});

// Create a new project
router.post('/', auth, (req, res) => {
  try {
    if (req.user.role !== 'customer') {
      return res.status(403).json({ msg: 'Only customers can create projects' });
    }

    const db = req.app.locals.db;
    
    const stmt = db.prepare(`
      INSERT INTO projects (title, description, category, budget, location, start_date, end_date, customer_id, status)
      VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'open')
    `);
    
    const result = stmt.run(
      req.body.title,
      req.body.description,
      req.body.category,
      req.body.budget,
      req.body.location,
      req.body.startDate,
      req.body.endDate,
      req.user.id
    );
    
    const newProject = db.prepare('SELECT * FROM projects WHERE id = ?').get(result.lastInsertRowid);
    
    res.json(newProject);
  } catch (err) {
    console.error(err.message);
    res.status(500).send('Server error');
  }
});

// Get single project
router.get('/:id', auth, (req, res) => {
  try {
    const db = req.app.locals.db;
    const project = db.prepare('SELECT * FROM projects WHERE id = ?').get(req.params.id);
    
    if (!project) {
      return res.status(404).json({ msg: 'Project not found' });
    }
    
    res.json(project);
  } catch (err) {
    console.error(err.message);
    res.status(500).send('Server error');
  }
});

module.exports = router;
