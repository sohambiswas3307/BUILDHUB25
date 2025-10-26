# BuildHub - Simple Start Guide

## 🚀 Quick Start

### Option 1: Simple Production Start (Recommended)
```bash
npm install
npm run build-and-start
```

This will:
1. Build the React frontend
2. Start the unified server
3. Serve both API and frontend on http://localhost:5000

### Option 2: Development Mode (Hot Reload)
```bash
npm run dev
```

This will start:
- Backend API on http://localhost:5000
- Frontend on http://localhost:3000 (with hot reload)

### Option 3: Development API Only
```bash
npm run dev:api
```

Runs only the backend API server.

## 📁 Project Structure

```
buildhub/
├── server.js              # 🆕 Unified server entry point
├── server/                # Backend logic
│   ├── index.js          # Original API server
│   ├── models/
│   ├── routes/
│   └── middleware/
├── client/                # React frontend
│   ├── src/
│   └── build/             # Production build (after npm run build)
└── buildhub.db           # SQLite database

```

## 🎯 How It Works

### Simple Start (Production)
- Single command: `npm start`
- Runs: `node server.js`
- Serves static React build from `client/build/`
- API accessible at `/api/*`
- Frontend at root `/`

### Development Mode
- Backend: http://localhost:5000 (API only)
- Frontend: http://localhost:3000 (with hot reload)
- Both run simultaneously via `concurrently`

## 🔧 Environment Variables

Create `.env` file:
```env
PORT=5000
DB_PATH=./buildhub.db
JWT_SECRET=your-secret-key-here
```

## 📝 Available Scripts

| Command | Description |
|---------|-------------|
| `npm start` | Start unified server |
| `npm run build-and-start` | Build frontend + start server |
| `npm run dev` | Development mode (both servers) |
| `npm run dev:api` | Backend only |
| `npm run client` | Frontend only |
| `npm run build` | Build React app |

## ✨ Features

✅ Single server entry point  
✅ Unified API and frontend serving  
✅ SQLite database initialization  
✅ Automatic React app routing  
✅ Production ready  
✅ Development mode available  

## 🎉 Start Building!

```bash
npm run build-and-start
# Visit: http://localhost:5000
```

