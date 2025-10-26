const express = require('express');
const Database = require('better-sqlite3');
const cors = require('cors');
const path = require('path');
require('dotenv').config();

const app = express();

// Middleware
app.use(cors());
app.use(express.json());

// Connect to SQLite Database
const db = new Database(process.env.DB_PATH || './buildhub.db');
app.locals.db = db;
console.log('✅ Connected to SQLite database');

// Initialize database tables
const initializeDB = require('./server/models/initializeDB');
initializeDB(db);

// API Routes
app.use('/api/auth', require('./server/routes/auth'));
app.use('/api/projects', require('./server/routes/projects'));
app.use('/api/contractors', require('./server/routes/contractors'));
app.use('/api/labours', require('./server/routes/labours'));
app.use('/api/users', require('./server/routes/users'));
app.use('/api', require('./server/routes/items'));
app.use('/api/customers', require('./server/routes/customer'));

// Health check
app.get('/api/health', (req, res) => {
  res.json({ status: 'OK', message: 'BuildHub API is running' });
});

// Serve static files from React build
app.use(express.static(path.join(__dirname, 'client/build')));

// All other routes serve React app
app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, 'client/build/index.html'));
});

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`🚀 BuildHub Server running on port ${PORT}`);
  console.log(`📱 Frontend: http://localhost:${PORT}`);
  console.log(`🔌 API: http://localhost:${PORT}/api`);
  console.log(`❤️ Health: http://localhost:${PORT}/api/health`);
});

