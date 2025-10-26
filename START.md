# 🚀 BuildHub - How to Run

## ✅ Everything Runs on ONE Port!

Your project is already perfectly configured! Just run this:

```bash
npm start
```

**That's it!** Everything will run on http://localhost:5000

---

## 📋 What You Get

When you run `npm start`:
- ✅ Frontend (React App)
- ✅ Backend (API)
- ✅ Database (SQLite)
- ✅ All on: **http://localhost:5000**

---

## 🎯 Quick Commands

| Command | What It Does |
|---------|-------------|
| `npm start` | **Run unified server** (Port 5000) |
| `npm run build-and-start` | Build + Start (for fresh builds) |
| `npm run dev` | Development mode (Port 3000 + 5000) |

---

## 🏃‍♂️ Running the Project

### Step 1: Open Terminal in Project Folder
```bash
cd "d:\app labour"
```

### Step 2: Run the Server
```bash
npm start
```

### Step 3: Open Browser
Visit: http://localhost:5000

---

## 🎉 You're Done!

The application will show:
- 🎨 Landing Page
- 🔐 Login/Register
- 📊 Dashboards (Customer/Contractor/Labour)
- 🔌 API at /api/*
- 💾 SQLite Database

---

## 📝 What's Included

✅ **Frontend**: React SPA  
✅ **Backend**: Express.js API  
✅ **Database**: SQLite3  
✅ **Authentication**: JWT  
✅ **All Features**: Working  

---

## 🔧 If You Need to Rebuild

```bash
npm run build-and-start
```

This rebuilds the React app and starts the server.

---

## 📂 Project Structure

```
buildhub/
├── server.js           ← Entry point! (Run this)
├── server/             ← Backend logic
│   ├── index.js       ← Original API server
│   ├── models/
│   └── routes/
├── client/
│   ├── src/           ← React source
│   └── build/         ← Built for production
└── buildhub.db         ← SQLite database
```

---

## 🌐 URLs

| Page | URL |
|------|-----|
| Home/Landing | http://localhost:5000 |
| Login | http://localhost:5000/login |
| Register | http://localhost:5000/register |
| API Health | http://localhost:5000/api/health |

---

**That's it! Just run `npm start` and you're ready to go! 🚀**



