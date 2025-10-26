# BuildHub - Quick Start Guide

Get Construction Connect up and running in 5 minutes!

## Prerequisites

Before you begin, ensure you have:
- ✅ Node.js (v14 or higher) installed
- ✅ MongoDB installed and running
- ✅ Git (optional)

## Installation Steps

### Step 1: Install Dependencies

Open your terminal in the project directory and run:

```bash
npm run install-all
```

This will install both backend and frontend dependencies.

### Step 2: Configure Environment

The `.env.example` file is provided. However, for security, create a `.env` file in the root directory:

**Windows (PowerShell):**
```powershell
# Create .env file
@"
MONGODB_URI=mongodb://localhost:27017/construction-connect
JWT_SECRET=my-super-secret-jwt-key-12345
PORT=5000
"@ | Out-File -FilePath .env
```

**Linux/Mac:**
```bash
cp .env.example .env
# Edit .env with your preferred editor
nano .env
```

### Step 3: Start MongoDB

Make sure MongoDB is running:

**Windows:**
```bash
# If installed as a service, it should be running automatically
# Otherwise:
mongod
```

**Linux/Mac:**
```bash
# Start MongoDB service
sudo systemctl start mongod
# or
brew services start mongodb-community
```

### Step 4: Run the Application

Start both backend and frontend servers:

```bash
npm run dev
```

You should see output like:
```
Server running on port 5000
MongoDB Connected
Starting the development server...
```

### Step 5: Access the Application

Open your browser and navigate to:
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:5000/api/health

## First Steps

### 1. Register as a User

Click "Register" and choose your role:
- **Customer**: Post projects and hire contractors/labours
- **Contractor**: Bid on projects and manage construction work
- **Labour**: Find jobs and apply for positions

Fill in the registration form with:
- Name
- Email
- Password
- Phone number
- Address
- Role

### 2. Explore Your Dashboard

Each role has a unique dashboard:
- **Customers**: Create and manage projects
- **Contractors**: Browse and bid on projects
- **Labours**: Browse and apply for job postings

### 3. Test the Platform

#### For Customers:
1. Create a new project
2. Wait for contractors to place bids
3. Accept a bid

#### For Contractors:
1. Browse available projects
2. Place a bid on a project
3. Track your accepted projects

#### For Labours:
1. Browse job postings
2. Apply for a position
3. Track your assigned work

## Troubleshooting

### Port Already in Use

If port 5000 or 3000 is already in use:

**Change backend port:**
Edit `.env`:
```
PORT=5001
```

**Change frontend port:**
Edit `client/.env` (create if doesn't exist):
```
PORT=3001
```

### MongoDB Connection Error

Make sure MongoDB is running:
```bash
# Check if MongoDB is running
mongo --eval "db.adminCommand('ismaster')"
```

If not running, start it:
```bash
# Windows
net start MongoDB

# Linux
sudo systemctl start mongod

# Mac
brew services start mongodb-community
```

### Module Not Found Errors

If you see "module not found" errors:
```bash
# Navigate to project root
cd "D:\app labour"

# Reinstall dependencies
npm install
cd client
npm install
```

### API Connection Issues

If frontend can't connect to backend:
1. Check if backend is running on port 5000
2. Visit http://localhost:5000/api/health
3. Check browser console for errors
4. Verify the proxy setting in `client/package.json`

## Development Tips

### Hot Reload

Both servers support hot reload:
- Backend changes require a restart (nodemon handles this automatically)
- Frontend changes reload automatically

### Viewing API Requests

Open browser DevTools (F12) and check:
- **Network** tab: See all API requests
- **Console** tab: See any errors or logs

### Common Commands

```bash
# Start development servers
npm run dev

# Start only backend
npm run server

# Start only frontend
npm run client

# Build for production
npm run build

# Install all dependencies
npm run install-all
```

## Project Structure Overview

```
app labour/
├── client/              # React frontend
│   ├── src/
│   │   ├── components/
│   │   │   ├── auth/
│   │   │   └── dashboards/
│   │   ├── context/
│   │   └── utils/
│   └── public/
├── server/              # Node.js backend
│   ├── models/
│   ├── routes/
│   └── middleware/
├── package.json
└── README.md
```

## Next Steps

1. ✅ Read `FEATURES.md` for complete feature list
2. ✅ Read `SETUP.md` for detailed setup
3. ✅ Read `PROJECT_SUMMARY.md` for architecture
4. ✅ Explore the dashboards
5. ✅ Create test data
6. ✅ Customize for your needs

## Need Help?

Check the following files:
- **README.md** - Project overview
- **SETUP.md** - Detailed setup instructions
- **FEATURES.md** - Feature documentation
- **PROJECT_SUMMARY.md** - Technical details

## Quick Reference

| Action | Command |
|--------|---------|
| Install | `npm run install-all` |
| Start | `npm run dev` |
| Server only | `npm run server` |
| Client only | `npm run client` |
| Build | `npm run build` |

---

**Ready to build! Start creating projects, placing bids, and hiring talent! 🎉**

