function initializeDB(db) {
  // Enable foreign keys
  db.pragma('foreign_keys = ON');

  // Users table
  db.exec(`
    CREATE TABLE IF NOT EXISTS users (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      name TEXT NOT NULL,
      email TEXT UNIQUE NOT NULL,
      password TEXT NOT NULL,
      role TEXT NOT NULL CHECK(role IN ('customer', 'contractor', 'labour')),
      phone TEXT NOT NULL,
      address TEXT NOT NULL,
      license_number TEXT DEFAULT '',
      experience TEXT DEFAULT '',
      specialization TEXT DEFAULT '',
      domain_of_expertise TEXT DEFAULT '',
      skills TEXT DEFAULT '[]',
      hourly_rate REAL DEFAULT 0,
      min_budget REAL DEFAULT 0,
      max_budget REAL DEFAULT 0,
      availability TEXT DEFAULT 'available',
      portfolio_url TEXT DEFAULT '',
      customer_approval_ratio REAL DEFAULT 1.0,
      projects_completed INTEGER DEFAULT 0,
      average_rating REAL DEFAULT 0,
      acceptance_per_consultation_ratio REAL DEFAULT 1.0,
      is_verified INTEGER DEFAULT 0,
      avatar TEXT DEFAULT '',
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP
    )
  `);

  // Ratings table
  db.exec(`
    CREATE TABLE IF NOT EXISTS ratings (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      user_id INTEGER NOT NULL,
      rater_id INTEGER NOT NULL,
      rating INTEGER NOT NULL CHECK(rating >= 1 AND rating <= 5),
      comment TEXT,
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
      FOREIGN KEY (rater_id) REFERENCES users(id) ON DELETE CASCADE
    )
  `);

  // Projects table
  db.exec(`
    CREATE TABLE IF NOT EXISTS projects (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      title TEXT NOT NULL,
      description TEXT NOT NULL,
      category TEXT NOT NULL,
      budget REAL NOT NULL,
      location TEXT NOT NULL,
      start_date TEXT NOT NULL,
      end_date TEXT NOT NULL,
      status TEXT DEFAULT 'open' CHECK(status IN ('open', 'in-progress', 'completed', 'cancelled')),
      customer_id INTEGER NOT NULL,
      contractor_id INTEGER,
      progress INTEGER DEFAULT 0 CHECK(progress >= 0 AND progress <= 100),
      completed_details TEXT,
      completion_date TEXT,
      customer_approved INTEGER DEFAULT 0,
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (customer_id) REFERENCES users(id) ON DELETE CASCADE,
      FOREIGN KEY (contractor_id) REFERENCES users(id)
    )
  `);

  // Bids table
  db.exec(`
    CREATE TABLE IF NOT EXISTS bids (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      project_id INTEGER NOT NULL,
      contractor_id INTEGER NOT NULL,
      amount REAL NOT NULL,
      estimated_days INTEGER NOT NULL,
      message TEXT,
      status TEXT DEFAULT 'pending' CHECK(status IN ('pending', 'accepted', 'rejected')),
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
      FOREIGN KEY (contractor_id) REFERENCES users(id) ON DELETE CASCADE
    )
  `);

  // Labour requirements table
  db.exec(`
    CREATE TABLE IF NOT EXISTS labour_requirements (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      project_id INTEGER NOT NULL,
      skill TEXT NOT NULL,
      quantity INTEGER NOT NULL,
      hourly_rate REAL NOT NULL,
      FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
    )
  `);

  // Hired labours table
  db.exec(`
    CREATE TABLE IF NOT EXISTS hired_labours (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      project_id INTEGER NOT NULL,
      labour_id INTEGER NOT NULL,
      role TEXT,
      hourly_rate REAL,
      hours_worked INTEGER DEFAULT 0,
      status TEXT DEFAULT 'active',
      FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
      FOREIGN KEY (labour_id) REFERENCES users(id) ON DELETE CASCADE
    )
  `);

  // Milestones table
  db.exec(`
    CREATE TABLE IF NOT EXISTS milestones (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      project_id INTEGER NOT NULL,
      title TEXT NOT NULL,
      description TEXT,
      due_date TEXT,
      completed INTEGER DEFAULT 0,
      FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
    )
  `);

  // Labour postings table
  db.exec(`
    CREATE TABLE IF NOT EXISTS labour_postings (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      title TEXT NOT NULL,
      description TEXT NOT NULL,
      required_skills TEXT DEFAULT '[]',
      hourly_rate REAL NOT NULL,
      duration TEXT NOT NULL,
      location TEXT NOT NULL,
      project_id INTEGER,
      posted_by INTEGER NOT NULL,
      hired_labour_id INTEGER,
      status TEXT DEFAULT 'open' CHECK(status IN ('open', 'filled', 'closed')),
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (posted_by) REFERENCES users(id) ON DELETE CASCADE,
      FOREIGN KEY (hired_labour_id) REFERENCES users(id)
    )
  `);

  // Applicants table
  db.exec(`
    CREATE TABLE IF NOT EXISTS applicants (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      posting_id INTEGER NOT NULL,
      labour_id INTEGER NOT NULL,
      message TEXT,
      status TEXT DEFAULT 'pending' CHECK(status IN ('pending', 'accepted', 'rejected')),
      applied_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (posting_id) REFERENCES labour_postings(id) ON DELETE CASCADE,
      FOREIGN KEY (labour_id) REFERENCES users(id) ON DELETE CASCADE
    )
  `);

  // Labour work history
  db.exec(`
    CREATE TABLE IF NOT EXISTS labour_work_history (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      labour_id INTEGER NOT NULL,
      project_id INTEGER NOT NULL,
      status TEXT DEFAULT 'active' CHECK(status IN ('active', 'completed', 'cancelled')),
      approved_by_customer INTEGER DEFAULT 0,
      customer_feedback TEXT,
      started_at DATETIME,
      completed_at DATETIME,
      hours_worked INTEGER DEFAULT 0,
      FOREIGN KEY (labour_id) REFERENCES users(id) ON DELETE CASCADE,
      FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
    )
  `);

  // Disagreements/Disputes
  db.exec(`
    CREATE TABLE IF NOT EXISTS disputes (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      labour_id INTEGER,
      contractor_id INTEGER,
      project_id INTEGER NOT NULL,
      customer_id INTEGER NOT NULL,
      type TEXT NOT NULL CHECK(type IN ('payment', 'quality', 'timeline', 'other')),
      description TEXT NOT NULL,
      status TEXT DEFAULT 'open' CHECK(status IN ('open', 'resolved', 'escalated')),
      resolution TEXT,
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      resolved_at DATETIME,
      FOREIGN KEY (labour_id) REFERENCES users(id),
      FOREIGN KEY (contractor_id) REFERENCES users(id),
      FOREIGN KEY (customer_id) REFERENCES users(id),
      FOREIGN KEY (project_id) REFERENCES projects(id)
    )
  `);

  // Purchased items for projects
  db.exec(`
    CREATE TABLE IF NOT EXISTS purchased_items (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      project_id INTEGER NOT NULL,
      item_name TEXT NOT NULL,
      quantity INTEGER NOT NULL DEFAULT 1,
      unit_price REAL NOT NULL,
      total_price REAL NOT NULL,
      category TEXT,
      supplier TEXT,
      purchase_date TEXT,
      notes TEXT,
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
    )
  `);

  // Project consultations/timeline
  db.exec(`
    CREATE TABLE IF NOT EXISTS consultations (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      project_id INTEGER NOT NULL,
      type TEXT NOT NULL CHECK(type IN ('bid', 'proposal', 'meeting', 'review')),
      contractor_id INTEGER,
      notes TEXT,
      status TEXT DEFAULT 'pending' CHECK(status IN ('pending', 'accepted', 'rejected')),
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
      FOREIGN KEY (contractor_id) REFERENCES users(id)
    )
  `);

  console.log('Database tables initialized');
}

module.exports = initializeDB;

