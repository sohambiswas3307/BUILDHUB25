const express = require('express');
const Database = require('better-sqlite3');
const cors = require('cors');
require('dotenv').config();

const app = express();

// Middleware
app.use(cors());
app.use(express.json());

// Connect to SQLite
const db = new Database(process.env.DB_PATH || './buildhub.db');
app.locals.db = db;
console.log('Connected to SQLite database');

// Initialize database tables
const initializeDB = require('./models/initializeDB');
initializeDB(db);

// Routes
app.use('/api/auth', require('./routes/auth'));
app.use('/api/projects', require('./routes/projects'));
app.use('/api/contractors', require('./routes/contractors'));
app.use('/api/labours', require('./routes/labours'));
app.use('/api/users', require('./routes/users'));
app.use('/api', require('./routes/items'));
app.use('/api/customers', require('./routes/customer'));

// Health check
app.get('/api/health', (req, res) => {
  res.json({ status: 'OK' });
});

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));

