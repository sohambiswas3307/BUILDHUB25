-- BuildHub Database Schema
-- SQLite Database for Construction Management Platform
-- Supports Customers, Contractors, and Labours

-- ============================================
-- USERS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role TEXT NOT NULL CHECK(role IN ('customer', 'contractor', 'labour')),
    phone TEXT NOT NULL,
    address TEXT NOT NULL,
    
    -- Contractor specific fields
    license_number TEXT DEFAULT '',
    specialization TEXT DEFAULT '',
    min_budget REAL DEFAULT 0,
    max_budget REAL DEFAULT 0,
    portfolio_url TEXT DEFAULT '',
    
    -- Labour specific fields
    experience TEXT DEFAULT '',
    domain_of_expertise TEXT DEFAULT '',
    skills TEXT DEFAULT '[]',
    hourly_rate REAL DEFAULT 0,
    availability TEXT DEFAULT 'available',
    
    -- Common metrics
    customer_approval_ratio REAL DEFAULT 1.0,
    projects_completed INTEGER DEFAULT 0,
    average_rating REAL DEFAULT 0,
    acceptance_per_consultation_ratio REAL DEFAULT 1.0,
    is_verified INTEGER DEFAULT 0,
    avatar TEXT DEFAULT '',
    
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- PROJECTS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS projects (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id INTEGER NOT NULL,
    title TEXT NOT NULL,
    description TEXT,
    budget REAL,
    status TEXT DEFAULT 'pending' CHECK(status IN ('pending', 'in_progress', 'completed', 'cancelled')),
    start_date TEXT,
    end_date TEXT,
    location TEXT,
    required_skills TEXT DEFAULT '[]',
    assigned_contractor_id INTEGER,
    completed_details TEXT DEFAULT '',
    completion_date TEXT,
    customer_approved INTEGER DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (customer_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (assigned_contractor_id) REFERENCES users(id)
);

-- ============================================
-- LABOUR WORK HISTORY TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS labour_work_history (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    labour_id INTEGER NOT NULL,
    project_id INTEGER NOT NULL,
    role TEXT,
    start_date TEXT,
    end_date TEXT,
    hours_worked REAL,
    pay_rate REAL,
    status TEXT DEFAULT 'completed',
    feedback TEXT,
    rating INTEGER,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (labour_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

-- ============================================
-- DISPUTES TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS disputes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    project_id INTEGER NOT NULL,
    customer_id INTEGER NOT NULL,
    contractor_id INTEGER,
    labour_id INTEGER,
    reason TEXT NOT NULL,
    status TEXT DEFAULT 'open' CHECK(status IN ('open', 'resolved', 'closed')),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    resolved_at TEXT,
    resolution_details TEXT,
    
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (contractor_id) REFERENCES users(id),
    FOREIGN KEY (labour_id) REFERENCES users(id)
);

-- ============================================
-- PURCHASED ITEMS TABLE
-- ============================================
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
);

-- ============================================
-- CONSULTATIONS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS consultations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id INTEGER NOT NULL,
    contractor_id INTEGER NOT NULL,
    project_id INTEGER,
    status TEXT DEFAULT 'pending' CHECK(status IN ('pending', 'accepted', 'rejected', 'completed')),
    consultation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    response_date TEXT,
    notes TEXT,
    
    FOREIGN KEY (customer_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (contractor_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

-- ============================================
-- INDEXES FOR PERFORMANCE
-- ============================================
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);
CREATE INDEX IF NOT EXISTS idx_projects_customer ON projects(customer_id);
CREATE INDEX IF NOT EXISTS idx_projects_status ON projects(status);
CREATE INDEX IF NOT EXISTS idx_labour_history_labour ON labour_work_history(labour_id);
CREATE INDEX IF NOT EXISTS idx_labour_history_project ON labour_work_history(project_id);
CREATE INDEX IF NOT EXISTS idx_disputes_project ON disputes(project_id);
CREATE INDEX IF NOT EXISTS idx_disputes_status ON disputes(status);
CREATE INDEX IF NOT EXISTS idx_purchased_items_project ON purchased_items(project_id);

