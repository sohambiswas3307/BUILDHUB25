import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import AuthContext from '../../context/AuthContext';

const CustomerDashboard = () => {
  const [projects, setProjects] = useState([]);
  const [selectedProject, setSelectedProject] = useState(null);
  const [purchasedItems, setPurchasedItems] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [showRatingModal, setShowRatingModal] = useState(false);
  const [showItemModal, setShowItemModal] = useState(false);
  const [showDisputeModal, setShowDisputeModal] = useState(false);
  const [newProject, setNewProject] = useState({
    title: '', description: '', category: '', budget: '', location: '',
    startDate: '', endDate: ''
  });
  const [rating, setRating] = useState({ contractorId: '', rating: 5, comment: '' });
  const [newItem, setNewItem] = useState({ itemName: '', quantity: 1, unitPrice: '', category: '', supplier: '' });
  const [newDispute, setNewDispute] = useState({ type: 'payment', description: '', projectId: '' });
  const navigate = useNavigate();
  const { user, setUser } = useContext(AuthContext);

  useEffect(() => {
    fetchProjects();
  }, []);

  useEffect(() => {
    if (selectedProject) {
      fetchPurchasedItems(selectedProject._id);
    }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedProject]);

  const fetchProjects = async () => {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get('http://localhost:5000/api/projects', {
        headers: { 'x-auth-token': token }
      });
      setProjects(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
    navigate('/login');
  };

  const fetchPurchasedItems = async (projectId) => {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(`http://localhost:5000/api/projects/${projectId}/items`, {
        headers: { 'x-auth-token': token }
      });
      setPurchasedItems(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleCreateProject = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem('token');
      await axios.post('http://localhost:5000/api/projects', newProject, {
        headers: { 'x-auth-token': token }
      });
      setShowModal(false);
      setNewProject({
        title: '', description: '', category: '', budget: '', location: '',
        startDate: '', endDate: ''
      });
      fetchProjects();
    } catch (err) {
      alert(err.response?.data?.msg || 'Failed to create project');
    }
  };

  const handleAddItem = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem('token');
      const totalPrice = parseFloat(newItem.quantity) * parseFloat(newItem.unitPrice);
      await axios.post(`http://localhost:5000/api/projects/${selectedProject._id}/items`, {
        ...newItem,
        totalPrice
      }, {
        headers: { 'x-auth-token': token }
      });
      setShowItemModal(false);
      setNewItem({ itemName: '', quantity: 1, unitPrice: '', category: '', supplier: '' });
      fetchPurchasedItems(selectedProject._id);
    } catch (err) {
      alert(err.response?.data?.msg || 'Failed to add item');
    }
  };

  const handleSubmitRating = async () => {
    try {
      const token = localStorage.getItem('token');
      await axios.post(`http://localhost:5000/api/users/${rating.contractorId}/rating`, {
        rating: rating.rating,
        comment: rating.comment
      }, {
        headers: { 'x-auth-token': token }
      });
      alert('Rating submitted successfully!');
      setShowRatingModal(false);
      setRating({ contractorId: '', rating: 5, comment: '' });
      fetchProjects();
    } catch (err) {
      alert(err.response?.data?.msg || 'Failed to submit rating');
    }
  };

  const handleCreateDispute = async () => {
    try {
      const token = localStorage.getItem('token');
      await axios.post('http://localhost:5000/api/customers/disputes', newDispute, {
        headers: { 'x-auth-token': token }
      });
      alert('Dispute created successfully!');
      setShowDisputeModal(false);
      setNewDispute({ type: 'payment', description: '', projectId: '' });
      fetchProjects();
    } catch (err) {
      alert(err.response?.data?.msg || 'Failed to create dispute');
    }
  };

  const calculateTotalCost = () => {
    return purchasedItems.reduce((sum, item) => sum + (item.total_price || 0), 0);
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <nav className="bg-white shadow-lg">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between h-16">
            <div className="flex items-center space-x-4">
              <button
                onClick={() => window.history.back()}
                className="flex items-center justify-center w-10 h-10 rounded-full bg-gray-100 hover:bg-gray-200 transition-colors"
                title="Go back"
              >
                <svg className="w-6 h-6 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
                </svg>
              </button>
              <h1 className="text-2xl font-bold text-indigo-600">BuildHub</h1>
            </div>
            <div className="flex items-center space-x-4">
              <span className="text-gray-700">Welcome, {user?.name}</span>
              <button
                onClick={handleLogout}
                className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600"
              >
                Logout
              </button>
            </div>
          </div>
        </div>
      </nav>

      {/* Main Content */}
      <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        <div className="px-4 py-6 sm:px-0">
          {/* Customer Profile Info */}
          <div className="bg-white rounded-lg shadow-md p-6 mb-6">
            <h2 className="text-2xl font-bold mb-4">My Profile</h2>
            <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
              <div>
                <p className="text-gray-600 text-sm">Name</p>
                <p className="font-semibold">{user?.name}</p>
                <p className="text-gray-600 text-sm mt-2">Phone</p>
                <p className="font-semibold">{user?.phone}</p>
              </div>

              <div>
                <p className="text-gray-600 text-sm">Email</p>
                <p className="font-semibold">{user?.email}</p>
                <p className="text-gray-600 text-sm mt-2">Address</p>
                <p className="font-semibold">{user?.address}</p>
              </div>

              <div className="bg-purple-50 p-3 rounded">
                <p className="text-gray-600 text-sm">Active Projects</p>
                <p className="text-2xl font-bold text-purple-600">
                  {projects.filter(p => p.status === 'in-progress').length}
                </p>
              </div>

              <div className="bg-blue-50 p-3 rounded">
                <p className="text-gray-600 text-sm">Consultation Ratio</p>
                <p className="text-2xl font-bold text-blue-600">
                  {(user?.acceptance_per_consultation_ratio * 100).toFixed(1)}%
                </p>
              </div>
            </div>
          </div>

          <div className="flex justify-between items-center mb-6">
            <h2 className="text-3xl font-bold text-gray-900">My Projects</h2>
            <button
              onClick={() => setShowModal(true)}
              className="bg-indigo-600 text-white px-6 py-2 rounded-md hover:bg-indigo-700"
            >
              Create New Project
            </button>
          </div>

          {/* Projects Grid */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {projects.map((project) => (
              <div key={project._id} className="bg-white rounded-lg shadow-md p-6">
                <h3 className="text-xl font-semibold mb-2">{project.title}</h3>
                <p className="text-gray-600 mb-4">{project.description}</p>
                <div className="space-y-2 mb-4">
                  <p><span className="font-semibold">Budget:</span> ₹{project.budget}</p>
                  <p><span className="font-semibold">Status:</span> 
                    <span className={`ml-2 px-2 py-1 rounded ${
                      project.status === 'open' ? 'bg-blue-100 text-blue-800' :
                      project.status === 'in-progress' ? 'bg-yellow-100 text-yellow-800' :
                      'bg-green-100 text-green-800'
                    }`}>
                      {project.status}
                    </span>
                  </p>
                  <p><span className="font-semibold">Location:</span> {project.location}</p>
                  {project.contractor && (
                    <p><span className="font-semibold">Contractor:</span> {project.contractor.name}</p>
                  )}
                </div>
                <div className="flex flex-wrap gap-2 mt-2">
                  <button
                    onClick={() => {
                      setSelectedProject(project);
                      setShowItemModal(true);
                    }}
                    className="px-3 py-1 bg-green-600 text-white rounded text-sm hover:bg-green-700"
                  >
                    Add Item
                  </button>
                  {project.contractor && (
                    <button
                      onClick={() => {
                        setRating({ ...rating, contractorId: project.contractor._id });
                        setShowRatingModal(true);
                      }}
                      className="px-3 py-1 bg-yellow-600 text-white rounded text-sm hover:bg-yellow-700"
                    >
                      Rate Contractor
                    </button>
                  )}
                  <button
                    onClick={() => {
                      setNewDispute({ ...newDispute, projectId: project._id });
                      setShowDisputeModal(true);
                    }}
                    className="px-3 py-1 bg-red-600 text-white rounded text-sm hover:bg-red-700"
                  >
                    Report Issue
                  </button>
                </div>
              </div>
            ))}
          </div>

          {projects.length === 0 && (
            <div className="text-center py-12">
              <p className="text-gray-500 text-lg">No projects yet. Create your first project!</p>
            </div>
          )}
        </div>
      </div>

      {/* Purchased Items Modal */}
      {showItemModal && selectedProject && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-8 max-w-2xl w-full mx-4 max-h-96 overflow-y-auto">
            <div className="flex justify-between items-center mb-4">
              <h2 className="text-2xl font-bold">Purchased Items - {selectedProject.title}</h2>
              <button
                onClick={() => setShowItemModal(false)}
                className="text-gray-500 hover:text-gray-700"
              >
                ✕
              </button>
            </div>

            <form onSubmit={handleAddItem} className="space-y-4 mb-4">
              <div className="grid grid-cols-2 gap-4">
                <input
                  type="text"
                  placeholder="Item Name"
                  required
                  className="px-3 py-2 border rounded-md"
                  value={newItem.itemName}
                  onChange={(e) => setNewItem({ ...newItem, itemName: e.target.value })}
                />
                <input
                  type="text"
                  placeholder="Category"
                  className="px-3 py-2 border rounded-md"
                  value={newItem.category}
                  onChange={(e) => setNewItem({ ...newItem, category: e.target.value })}
                />
                <input
                  type="number"
                  placeholder="Quantity"
                  required
                  className="px-3 py-2 border rounded-md"
                  value={newItem.quantity}
                  onChange={(e) => setNewItem({ ...newItem, quantity: e.target.value })}
                />
                <input
                  type="number"
                  placeholder="Unit Price (₹)"
                  required
                  className="px-3 py-2 border rounded-md"
                  value={newItem.unitPrice}
                  onChange={(e) => setNewItem({ ...newItem, unitPrice: e.target.value })}
                />
              </div>
              <button
                type="submit"
                className="px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700"
              >
                Add Item
              </button>
            </form>

            {purchasedItems.length > 0 && (
              <div className="mt-4">
                <h3 className="font-semibold mb-2">Purchased Items:</h3>
                <div className="space-y-2">
                  {purchasedItems.map((item) => (
                    <div key={item.id} className="flex justify-between p-2 bg-gray-50 rounded">
                      <span>{item.quantity}x {item.item_name} - ₹{item.total_price}</span>
                    </div>
                  ))}
                </div>
                <p className="mt-4 font-bold">Total: ₹{calculateTotalCost()}</p>
              </div>
            )}
          </div>
        </div>
      )}

      {/* Rating Modal */}
      {showRatingModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-8 max-w-md w-full mx-4">
            <h2 className="text-2xl font-bold mb-4">Rate Contractor</h2>
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-2">Rating (1-5)</label>
                <select
                  className="w-full px-3 py-2 border rounded-md"
                  value={rating.rating}
                  onChange={(e) => setRating({ ...rating, rating: e.target.value })}
                >
                  <option value="5">5 - Excellent</option>
                  <option value="4">4 - Very Good</option>
                  <option value="3">3 - Good</option>
                  <option value="2">2 - Fair</option>
                  <option value="1">1 - Poor</option>
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium mb-2">Comments</label>
                <textarea
                  className="w-full px-3 py-2 border rounded-md"
                  rows="4"
                  value={rating.comment}
                  onChange={(e) => setRating({ ...rating, comment: e.target.value })}
                  placeholder="Share your feedback..."
                />
              </div>
              <div className="flex justify-end space-x-4">
                <button
                  onClick={() => setShowRatingModal(false)}
                  className="px-4 py-2 border rounded-md hover:bg-gray-100"
                >
                  Cancel
                </button>
                <button
                  onClick={handleSubmitRating}
                  className="px-4 py-2 bg-yellow-600 text-white rounded-md hover:bg-yellow-700"
                >
                  Submit Rating
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Dispute Modal */}
      {showDisputeModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-8 max-w-md w-full mx-4">
            <h2 className="text-2xl font-bold mb-4">Create Dispute</h2>
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-2">Dispute Type</label>
                <select
                  className="w-full px-3 py-2 border rounded-md"
                  value={newDispute.type}
                  onChange={(e) => setNewDispute({ ...newDispute, type: e.target.value })}
                >
                  <option value="payment">Payment</option>
                  <option value="quality">Quality</option>
                  <option value="timeline">Timeline</option>
                  <option value="other">Other</option>
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium mb-2">Description</label>
                <textarea
                  className="w-full px-3 py-2 border rounded-md"
                  rows="4"
                  value={newDispute.description}
                  onChange={(e) => setNewDispute({ ...newDispute, description: e.target.value })}
                  placeholder="Describe the issue..."
                />
              </div>
              <div className="flex justify-end space-x-4">
                <button
                  onClick={() => setShowDisputeModal(false)}
                  className="px-4 py-2 border rounded-md hover:bg-gray-100"
                >
                  Cancel
                </button>
                <button
                  onClick={handleCreateDispute}
                  className="px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700"
                >
                  Submit Dispute
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Create Project Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-8 max-w-2xl w-full mx-4 max-h-96 overflow-y-auto">
            <h2 className="text-2xl font-bold mb-4">Create New Project</h2>
            <form onSubmit={handleCreateProject} className="space-y-4">
              <input
                type="text"
                placeholder="Project Title"
                required
                className="w-full px-3 py-2 border rounded-md"
                value={newProject.title}
                onChange={(e) => setNewProject({ ...newProject, title: e.target.value })}
              />
              <textarea
                placeholder="Description"
                required
                className="w-full px-3 py-2 border rounded-md"
                value={newProject.description}
                onChange={(e) => setNewProject({ ...newProject, description: e.target.value })}
              />
              <div className="grid grid-cols-2 gap-4">
                <input
                  type="text"
                  placeholder="Category"
                  required
                  className="px-3 py-2 border rounded-md"
                  value={newProject.category}
                  onChange={(e) => setNewProject({ ...newProject, category: e.target.value })}
                />
                <input
                  type="number"
                  placeholder="Budget"
                  required
                  className="px-3 py-2 border rounded-md"
                  value={newProject.budget}
                  onChange={(e) => setNewProject({ ...newProject, budget: e.target.value })}
                />
                <input
                  type="text"
                  placeholder="Location"
                  required
                  className="px-3 py-2 border rounded-md"
                  value={newProject.location}
                  onChange={(e) => setNewProject({ ...newProject, location: e.target.value })}
                />
                <input
                  type="date"
                  placeholder="Start Date"
                  className="px-3 py-2 border rounded-md"
                  value={newProject.startDate}
                  onChange={(e) => setNewProject({ ...newProject, startDate: e.target.value })}
                />
              </div>
              <input
                type="date"
                placeholder="End Date"
                className="w-full px-3 py-2 border rounded-md"
                value={newProject.endDate}
                onChange={(e) => setNewProject({ ...newProject, endDate: e.target.value })}
              />
              <div className="flex justify-end space-x-4">
                <button
                  type="button"
                  onClick={() => setShowModal(false)}
                  className="px-4 py-2 border rounded-md hover:bg-gray-100"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
                >
                  Create
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default CustomerDashboard;

