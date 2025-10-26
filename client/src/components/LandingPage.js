import React from 'react';
import { Link } from 'react-router-dom';

const LandingPage = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50">
      {/* Header */}
      <nav className="bg-white shadow-md">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between h-16 items-center">
            <h1 className="text-3xl font-bold text-indigo-600">BuildHub</h1>
            <div className="space-x-4">
              <Link to="/login" className="text-gray-700 hover:text-indigo-600 font-medium">
                Login
              </Link>
              <Link 
                to="/register" className="bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 font-medium">
                Get Started
              </Link>
            </div>
          </div>
        </div>
      </nav>

      {/* Hero Section */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="text-center mb-16">
          <h2 className="text-5xl font-bold text-gray-900 mb-4">
            One-Stop Construction Platform
          </h2>
          <p className="text-xl text-gray-600 mb-8">
            Connect Customers, Contractors, and Labours for seamless project management
          </p>
        </div>

        {/* Three Cards for User Types */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-16">
          {/* Customer Card */}
          <div className="bg-white rounded-xl shadow-lg p-8 transform transition hover:scale-105">
            <div className="text-center">
              <div className="mb-6 flex justify-center">
                <div className="w-20 h-20 bg-blue-100 rounded-full flex items-center justify-center">
                  <svg className="w-10 h-10 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
                  </svg>
                </div>
              </div>
              <h3 className="text-2xl font-bold text-gray-900 mb-4">Customer</h3>
              <p className="text-gray-600 mb-6">
                Post projects, manage construction work, and track progress with complete transparency.
              </p>
              <ul className="text-left space-y-2 mb-6 text-sm text-gray-600">
                <li>✓ Create and manage projects</li>
                <li>✓ Track purchased items and costs</li>
                <li>✓ Rate and provide feedback</li>
                <li>✓ Manage disputes efficiently</li>
              </ul>
              <Link
                to="/register"
                className="inline-block bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 font-medium"
              >
                Join as Customer
              </Link>
            </div>
          </div>

          {/* Contractor Card */}
          <div className="bg-white rounded-xl shadow-lg p-8 transform transition hover:scale-105">
            <div className="text-center">
              <div className="mb-6 flex justify-center">
                <div className="w-20 h-20 bg-green-100 rounded-full flex items-center justify-center">
                  <svg className="w-10 h-10 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                  </svg>
                </div>
              </div>
              <h3 className="text-2xl font-bold text-gray-900 mb-4">Contractor</h3>
              <p className="text-gray-600 mb-6">
                Bid on projects, showcase your expertise, and manage construction teams effectively.
              </p>
              <ul className="text-left space-y-2 mb-6 text-sm text-gray-600">
                <li>✓ Browse and bid on projects</li>
                <li>✓ Define specialty and budget range</li>
                <li>✓ Showcase work portfolio</li>
                <li>✓ Build reputation through ratings</li>
              </ul>
              <Link
                to="/register"
                className="inline-block bg-green-600 text-white px-6 py-3 rounded-lg hover:bg-green-700 font-medium"
              >
                Join as Contractor
              </Link>
            </div>
          </div>

          {/* Labour Card */}
          <div className="bg-white rounded-xl shadow-lg p-8 transform transition hover:scale-105">
            <div className="text-center">
              <div className="mb-6 flex justify-center">
                <div className="w-20 h-20 bg-yellow-100 rounded-full flex items-center justify-center">
                  <svg className="w-10 h-10 text-yellow-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
                  </svg>
                </div>
              </div>
              <h3 className="text-2xl font-bold text-gray-900 mb-4">Labour</h3>
              <p className="text-gray-600 mb-6">
                Find job opportunities, showcase your skills, and build your professional reputation.
              </p>
              <ul className="text-left space-y-2 mb-6 text-sm text-gray-600">
                <li>✓ Register with domain expertise</li>
                <li>✓ Find and apply for jobs</li>
                <li>✓ Track customer approval ratio</li>
                <li>✓ Build reputation through ratings</li>
              </ul>
              <Link
                to="/register"
                className="inline-block bg-yellow-600 text-white px-6 py-3 rounded-lg hover:bg-yellow-700 font-medium"
              >
                Join as Labour
              </Link>
            </div>
          </div>
        </div>

        {/* Features Section */}
        <div className="bg-white rounded-xl shadow-lg p-8 mb-8">
          <h3 className="text-3xl font-bold text-center text-gray-900 mb-8">Platform Features</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            <div className="text-center">
              <div className="text-4xl mb-2">🔒</div>
              <h4 className="font-semibold mb-2">Secure & Trusted</h4>
              <p className="text-sm text-gray-600">Verified users and secure transactions</p>
            </div>
            <div className="text-center">
              <div className="text-4xl mb-2">📊</div>
              <h4 className="font-semibold mb-2">Transparent</h4>
              <p className="text-sm text-gray-600">Complete transparency in all transactions</p>
            </div>
            <div className="text-center">
              <div className="text-4xl mb-2">⚡</div>
              <h4 className="font-semibold mb-2">Efficient</h4>
              <p className="text-sm text-gray-600">Streamlined project management</p>
            </div>
            <div className="text-center">
              <div className="text-4xl mb-2">🤝</div>
              <h4 className="font-semibold mb-2">Collaborative</h4>
              <p className="text-sm text-gray-600">Seamless collaboration tools</p>
            </div>
          </div>
        </div>

        {/* CTA Section */}
        <div className="text-center py-12">
          <h3 className="text-3xl font-bold text-gray-900 mb-4">Ready to Get Started?</h3>
          <p className="text-xl text-gray-600 mb-8">
            Join thousands of construction professionals today
          </p>
          <Link
            to="/register"
            className="inline-block bg-indigo-600 text-white px-8 py-4 rounded-lg text-lg font-medium hover:bg-indigo-700 shadow-lg"
          >
            Create Your Account
          </Link>
        </div>
      </div>

      {/* Footer */}
      <footer className="bg-gray-800 text-white py-8 mt-12">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center">
            <h3 className="text-2xl font-bold mb-4">BuildHub</h3>
            <p className="text-gray-400">© 2024 BuildHub. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default LandingPage;

