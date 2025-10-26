import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import AuthContext from '../../context/AuthContext';

const ContractorDashboard = () => {
  const [projects, setProjects] = useState([]);
  const [selectedProject, setSelectedProject] = useState(null);
  const [bidAmount, setBidAmount] = useState('');
  const [bidDays, setBidDays] = useState('');
  const [bidMessage, setBidMessage] = useState('');
  const [showCompletionModal, setShowCompletionModal] = useState(false);
  const [completionDetails, setCompletionDetails] = useState({ projectId: '', details: '' });
  const [showDisputeModal, setShowDisputeModal] = useState(false);
  const [newDispute, setNewDispute] = useState({ type: 'payment', description: '', projectId: '' });
  const navigate = useNavigate();
  const { user, setUser } = useContext(AuthContext);

  useEffect(() => {
    fetchProjects();
  }, []);

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

  const handleBid = async (projectId) => {
    try {
      const token = localStorage.getItem('token');
      await axios.post(`http://localhost:5000/api/projects/${projectId}/bid`, {
        amount: bidAmount,
        estimatedDays: bidDays,
        message: bidMessage
      }, {
        headers: { 'x-auth-token': token }
      });
      alert('Bid placed successfully!');
      setSelectedProject(null);
      setBidAmount('');
      setBidDays('');
      setBidMessage('');
      fetchProjects();
    } catch (err) {
      alert(err.response?.data?.msg || 'Failed to place bid');
    }
  };

  const handleSubmitCompletion = async (projectId) => {
    try {
      const token = localStorage.getItem('token');
      await axios.put(`http://localhost:5000/api/projects/${projectId}/complete`, {
        details: completionDetails.details
      }, {
        headers: { 'x-auth-token': token }
      });
      alert('Project completion details submitted!');
      setShowCompletionModal(false);
      setCompletionDetails({ projectId: '', details: '' });
      fetchProjects();
    } catch (err) {
      alert(err.response?.data?.msg || 'Failed to submit completion details');
    }
  };

  const handleCreateDispute = async () => {
    try {
      const token = localStorage.getItem('token');
      await axios.post('http://localhost:5000/api/contractors/disputes', newDispute, {
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

  // Filter projects to show only those open for bidding
  const availableProjects = projects.filter(p => p.status === 'open');
  const myProjects = projects.filter(p => p.contractor && p.contractor._id === user?.id);

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
          {/* Contractor Profile */}
          <div className="bg-white rounded-lg shadow-md p-6 mb-6">
            <h2 className="text-2xl font-bold mb-4">My Profile</h2>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
              <div className="space-y-2">
                <div>
                  <p className="text-gray-600 text-sm">Name</p>
                  <p className="font-semibold">{user?.name}</p>
                </div>
                <div>
                  <p className="text-gray-600 text-sm">Specialization</p>
                  <p className="font-semibold">{user?.specialization || 'Not specified'}</p>
                </div>
                <div>
                  <p className="text-gray-600 text-sm">Domain of Expertise</p>
                  <p className="font-semibold">{user?.domain_of_expertise || 'Not specified'}</p>
                </div>
                <div>
                  <p className="text-gray-600 text-sm">License Number</p>
                  <p className="font-semibold">{user?.license_number || 'Not specified'}</p>
                </div>
              </div>

              <div className="space-y-2">
                <div>
                  <p className="text-gray-600 text-sm">Budget Range</p>
                  <p className="font-semibold">
                    ₹{user?.min_budget || 0} - ₹{user?.max_budget || 0}
                  </p>
                </div>
                {user?.portfolio_url && (
                  <div>
                    <p className="text-gray-600 text-sm">Portfolio</p>
                    <a 
                      href={user.portfolio_url} 
                      target="_blank" 
                      rel="noopener noreferrer"
                      className="text-blue-600 hover:underline"
                    >
                      {user.portfolio_url}
                    </a>
                  </div>
                )}
              </div>

              <div className="space-y-2">
                <div className="bg-green-50 p-3 rounded">
                  <p className="text-gray-600 text-sm">Customer Approval Ratio</p>
                  <p className="text-2xl font-bold text-green-600">
                    {(user?.customer_approval_ratio * 100).toFixed(1)}%
                  </p>
                </div>
                <div className="bg-blue-50 p-3 rounded">
                  <p className="text-gray-600 text-sm">Projects Completed</p>
                  <p className="text-2xl font-bold text-blue-600">{user?.projects_completed || 0}</p>
                </div>
                <div className="bg-yellow-50 p-3 rounded">
                  <p className="text-gray-600 text-sm">Rating</p>
                  <p className="text-2xl font-bold text-yellow-600">
                    {user?.average_rating?.toFixed(1) || '0.0'}/5
                  </p>
                </div>
              </div>
            </div>
          </div>

          {/* Available Projects */}
          <h2 className="text-3xl font-bold text-gray-900 mb-6">Available Projects</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-12">
            {availableProjects.map((project) => {
              const hasBid = project.bids.some(bid => bid.contractor._id === user?.id);
              return (
                <div key={project._id} className="bg-white rounded-lg shadow-md p-6">
                  <h3 className="text-xl font-semibold mb-2">{project.title}</h3>
                  <p className="text-gray-600 mb-4">{project.description}</p>
                  <div className="space-y-2 mb-4">
                    <p><span className="font-semibold">Budget:</span> ₹{project.budget}</p>
                    <p><span className="font-semibold">Location:</span> {project.location}</p>
                    <p><span className="font-semibold">Category:</span> {project.category}</p>
                  </div>
                  <button
                    onClick={() => setSelectedProject(project)}
                    disabled={hasBid}
                    className={`w-full px-4 py-2 rounded-md ${
                      hasBid
                        ? 'bg-gray-400 cursor-not-allowed'
                        : 'bg-indigo-600 text-white hover:bg-indigo-700'
                    }`}
                  >
                    {hasBid ? 'Bid Placed' : 'Place Bid'}
                  </button>
                </div>
              );
            })}
          </div>

          {/* My Projects */}
          <h2 className="text-3xl font-bold text-gray-900 mb-6">My Projects</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {myProjects.map((project) => (
              <div key={project._id} className="bg-white rounded-lg shadow-md p-6">
                <h3 className="text-xl font-semibold mb-2">{project.title}</h3>
                <p className="text-gray-600 mb-4">{project.description}</p>
                <div className="space-y-2 mb-4">
                  <p><span className="font-semibold">Budget:</span> ₹{project.budget}</p>
                  <p><span className="font-semibold">Status:</span> 
                    <span className={`ml-2 px-2 py-1 rounded ${
                      project.status === 'in-progress' ? 'bg-yellow-100 text-yellow-800' :
                      project.status === 'completed' ? 'bg-green-100 text-green-800' :
                      'bg-blue-100 text-blue-800'
                    }`}>
                      {project.status}
                    </span>
                  </p>
                  <p><span className="font-semibold">Customer:</span> {project.customer?.name}</p>
                  <p><span className="font-semibold">Progress:</span> {project.progress}%</p>
                  {project.customer_approved && (
                    <p className="text-green-600">✓ Approved by Customer</p>
                  )}
                </div>
                <div className="flex space-x-2">
                  {project.status === 'in-progress' && (
                    <button
                      onClick={() => {
                        setCompletionDetails({ projectId: project._id, details: '' });
                        setShowCompletionModal(true);
                      }}
                      className="px-3 py-1 bg-green-600 text-white rounded text-sm hover:bg-green-700"
                    >
                      Submit Completion
                    </button>
                  )}
                  {(project.status === 'in-progress' || project.status === 'completed') && (
                    <button
                      onClick={() => {
                        setNewDispute({ ...newDispute, projectId: project._id });
                        setShowDisputeModal(true);
                      }}
                      className="px-3 py-1 bg-red-600 text-white rounded text-sm hover:bg-red-700"
                    >
                      Report Issue
                    </button>
                  )}
                </div>
              </div>
            ))}
          </div>

          {myProjects.length === 0 && (
            <div className="text-center py-12">
              <p className="text-gray-500 text-lg">You don't have any assigned projects yet.</p>
            </div>
          )}
        </div>
      </div>

      {/* Bid Modal */}
      {selectedProject && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-8 max-w-md w-full mx-4">
            <h2 className="text-2xl font-bold mb-4">Place Bid</h2>
            <div className="mb-4">
              <h3 className="font-semibold">{selectedProject.title}</h3>
              <p className="text-gray-600 text-sm">{selectedProject.description}</p>
            </div>
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-2">Bid Amount (₹)</label>
                <input
                  type="number"
                  className="w-full px-3 py-2 border rounded-md"
                  value={bidAmount}
                  onChange={(e) => setBidAmount(e.target.value)}
                />
              </div>
              <div>
                <label className="block text-sm font-medium mb-2">Estimated Days</label>
                <input
                  type="number"
                  className="w-full px-3 py-2 border rounded-md"
                  value={bidDays}
                  onChange={(e) => setBidDays(e.target.value)}
                />
              </div>
              <div>
                <label className="block text-sm font-medium mb-2">Message</label>
                <textarea
                  className="w-full px-3 py-2 border rounded-md"
                  value={bidMessage}
                  onChange={(e) => setBidMessage(e.target.value)}
                />
              </div>
              <div className="flex justify-end space-x-4">
                <button
                  onClick={() => setSelectedProject(null)}
                  className="px-4 py-2 border rounded-md hover:bg-gray-100"
                >
                  Cancel
                </button>
                <button
                  onClick={() => handleBid(selectedProject._id)}
                  className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
                >
                  Submit Bid
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Completion Modal */}
      {showCompletionModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-8 max-w-2xl w-full mx-4">
            <h2 className="text-2xl font-bold mb-4">Submit Project Completion</h2>
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-2">Completion Details *</label>
                <textarea
                  className="w-full px-3 py-2 border rounded-md"
                  rows="6"
                  value={completionDetails.details}
                  onChange={(e) => setCompletionDetails({ ...completionDetails, details: e.target.value })}
                  placeholder="Provide details about the completed work, milestones achieved, materials used, and any other relevant information..."
                />
              </div>
              <div className="flex justify-end space-x-4">
                <button
                  onClick={() => setShowCompletionModal(false)}
                  className="px-4 py-2 border rounded-md hover:bg-gray-100"
                >
                  Cancel
                </button>
                <button
                  onClick={() => handleSubmitCompletion(completionDetails.projectId)}
                  className="px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700"
                >
                  Submit Completion
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
    </div>
  );
};

export default ContractorDashboard;

