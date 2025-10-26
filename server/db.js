// Database helper functions

function getUserById(db, id) {
  const stmt = db.prepare('SELECT * FROM users WHERE id = ?');
  const user = stmt.get(id);
  if (user) {
    // Parse JSON fields
    if (user.skills) {
      try {
        user.skills = JSON.parse(user.skills);
      } catch (e) {
        user.skills = [];
      }
    }
  }
  return user;
}

function getUserByEmail(db, email) {
  const stmt = db.prepare('SELECT * FROM users WHERE email = ?');
  const user = stmt.get(email);
  if (user) {
    if (user.skills) {
      try {
        user.skills = JSON.parse(user.skills);
      } catch (e) {
        user.skills = [];
      }
    }
  }
  return user;
}

function createUser(db, userData) {
  // Build dynamic INSERT statement based on role
  let fields = ['name', 'email', 'password', 'role', 'phone', 'address', 'license_number'];
  let values = [];
  
  // Add role-specific fields
  if (userData.role === 'labour') {
    fields.push('skills', 'hourly_rate', 'domain_of_expertise', 'portfolio_url');
  } else if (userData.role === 'contractor') {
    fields.push('specialization', 'domain_of_expertise', 'portfolio_url', 'min_budget', 'max_budget');
  }
  
  const placeholders = '?'.repeat(fields.length).split('').join(', ');
  const query = `INSERT INTO users (${fields.join(', ')}) VALUES (${placeholders})`;
  
  // Prepare values array
  values.push(
    userData.name,
    userData.email,
    userData.password,
    userData.role,
    userData.phone,
    userData.address,
    userData.licenseNumber || ''
  );
  
  if (userData.role === 'labour') {
    const skills = Array.isArray(userData.skills) ? JSON.stringify(userData.skills) : '[]';
    values.push(skills, userData.hourlyRate || 0, userData.domainOfExpertise || '', userData.portfolioUrl || '');
  } else if (userData.role === 'contractor') {
    values.push(
      userData.specialization || '',
      userData.domainOfExpertise || '',
      userData.portfolioUrl || '',
      userData.minBudget || 0,
      userData.maxBudget || 0
    );
  }
  
  const stmt = db.prepare(query);
  const result = stmt.run(...values);
  return getUserById(db, result.lastInsertRowid);
}

function getProjects(db, filters = {}) {
  let query = 'SELECT * FROM projects WHERE 1=1';
  const params = [];
  
  if (filters.customerId) {
    query += ' AND customer_id = ?';
    params.push(filters.customerId);
  }
  
  if (filters.contractorId) {
    query += ' AND contractor_id = ?';
    params.push(filters.contractorId);
  }
  
  query += ' ORDER BY created_at DESC';
  
  const stmt = db.prepare(query);
  return stmt.all(...params);
}

function createProject(db, projectData) {
  const stmt = db.prepare(`
    INSERT INTO projects (title, description, category, budget, location, start_date, end_date, customer_id, status)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'open')
  `);
  
  const result = stmt.run(
    projectData.title,
    projectData.description,
    projectData.category,
    projectData.budget,
    projectData.location,
    projectData.startDate,
    projectData.endDate,
    projectData.customerId
  );
  
  return result.lastInsertRowid;
}

function updateUserRating(db, userId) {
  const stmt = db.prepare('SELECT AVG(rating) as avg FROM ratings WHERE user_id = ?');
  const result = stmt.get(userId);
  
  const updateStmt = db.prepare('UPDATE users SET average_rating = ? WHERE id = ?');
  updateStmt.run(result.avg || 0, userId);
}

module.exports = {
  getUserById,
  getUserByEmail,
  createUser,
  getProjects,
  createProject,
  updateUserRating
};

