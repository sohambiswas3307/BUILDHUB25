import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import AuthContext from '../../context/AuthContext';

const LabourDashboard = () => {
  const [postings, setPostings] = useState([]);
  const [myJobs, setMyJobs] = useState([]);
  const [workHistory, setWorkHistory] = useState([]);
  const [disputes, setDisputes] = useState([]);
  const [selectedPosting, setSelectedPosting] = useState(null);
  const [applicationMessage, setApplicationMessage] = useState('');
  const [showDisputeModal, setShowDisputeModal] = useState(false);
  const [newDispute, setNewDispute] = useState({ type: 'payment', description: '' });
  const navigate = useNavigate();
  const { user, setUser } = useContext(AuthContext);

  useEffect(() => {
    fetchPostings();
    fetchMyJobs();
    fetchWorkHistory();
    fetchDisputes();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    fetchMyJobs();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [user]);

  const fetchPostings = async () => {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get('http://localhost:5000/api/labours/postings', {
        headers: { 'x-auth-token': token }
      });
      setPostings(res.data.filter(p => p.status === 'open'));
    } catch (err) {
      console.error(err);
    }
  };

  const fetchMyJobs = async () => {
    try {
      const token = localStorage.getItem('token');
      const projectsRes = await axios.get('http://localhost:5000/api/projects', {
        headers: { 'x-auth-token': token }
      });
      const projects = projectsRes.data;
      const jobs = projects.filter(p => 
        p.hiredLabours && p.hiredLabours.some(h => h.labour._id === user?.id)
      );
      setMyJobs(jobs);
    } catch (err) {
      console.error(err);
    }
  };

  const fetchWorkHistory = async () => {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get('http://localhost:5000/api/labours/work-history', {
        headers: { 'x-auth-token': token }
      });
      setWorkHistory(res.data);
    } catch (err) {
      // Work history endpoint might not exist yet
      console.log('Work history not available');
    }
  };

  const fetchDisputes = async () => {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get('http://localhost:5000/api/labours/disputes', {
        headers: { 'x-auth-token': token }
      });
      setDisputes(res.data);
    } catch (err) {
      // Disputes endpoint might not exist yet
      console.log('Disputes not available');
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
    navigate('/login');
  };

  const handleApply = async (postingId) => {
    try {
      const token = localStorage.getItem('token');
      await axios.post(`http://localhost:5000/api/labours/postings/${postingId}/apply`, {
        message: applicationMessage
      }, {
        headers: { 'x-auth-token': token }
      });
      alert('Application submitted successfully!');
      setSelectedPosting(null);
      setApplicationMessage('');
      fetchPostings();
    } catch (err) {
      alert(err.response?.data?.msg || 'Failed to submit application');
    }
  };

  const handleCreateDispute = async () => {
    try {
      const token = localStorage.getItem('token');
      await axios.post('http://localhost:5000/api/labours/disputes', newDispute, {
        headers: { 'x-auth-token': token }
      });
      alert('Dispute created successfully!');
      setShowDisputeModal(false);
      setNewDispute({ type: 'payment', description: '' });
      fetchDisputes();
    } catch (err) {
      alert(err.response?.data?.msg || 'Failed to create dispute');
    }
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
          {/* Profile Info */}
          <div className="bg-white rounded-lg shadow-md p-6 mb-6">
            <h2 className="text-2xl font-bold mb-4">My Profile</h2>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
              <div className="space-y-2">
                <div>
                  <p className="text-gray-600 text-sm">Name</p>
                  <p className="font-semibold">{user?.name}</p>
                </div>
                <div>
                  <p className="text-gray-600 text-sm">Phone</p>
                  <p className="font-semibold">{user?.phone}</p>
                </div>
              </div>
              
              <div className="space-y-2">
                <div>
                  <p className="text-gray-600 text-sm">Domain of Expertise</p>
                  <p className="font-semibold">{user?.domain_of_expertise || 'Not specified'}</p>
                </div>
                <div>
                  <p className="text-gray-600 text-sm">Skills</p>
                  <p className="font-semibold">{user?.skills?.join(', ') || 'N/A'}</p>
                </div>
                <div>
                  <p className="text-gray-600 text-sm">Hourly Rate</p>
                  <p className="font-semibold">₹{user?.hourly_rate || 0}/hr</p>
                </div>
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

            {user?.portfolio_url && (
              <div className="mt-4 pt-4 border-t">
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

          {/* Available Jobs */}
          <h2 className="text-3xl font-bold text-gray-900 mb-6">Available Jobs</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-12">
            {postings.map((posting) => {
              const hasApplied = posting.applicants.some(app => app.labour._id === user?.id);
              return (
                <div key={posting._id} className="bg-white rounded-lg shadow-md p-6">
                  <h3 className="text-xl font-semibold mb-2">{posting.title}</h3>
                  <p className="text-gray-600 mb-4">{posting.description}</p>
                  <div className="space-y-2 mb-4">
                    <p><span className="font-semibold">Rate:</span> ₹{posting.hourlyRate}/hr</p>
                    <p><span className="font-semibold">Location:</span> {posting.location}</p>
                    <p><span className="font-semibold">Duration:</span> {posting.duration}</p>
                    <p><span className="font-semibold">Skills:</span> {posting.requiredSkills.join(', ')}</p>
                  </div>
                  <button
                    onClick={() => setSelectedPosting(posting)}
                    disabled={hasApplied}
                    className={`w-full px-4 py-2 rounded-md ${
                      hasApplied
                        ? 'bg-gray-400 cursor-not-allowed'
                        : 'bg-indigo-600 text-white hover:bg-indigo-700'
                    }`}
                  >
                    {hasApplied ? 'Applied' : 'Apply'}
                  </button>
                </div>
              );
            })}
          </div>

          {postings.length === 0 && (
            <div className="text-center py-12 mb-12">
              <p className="text-gray-500 text-lg">No job postings available at the moment.</p>
            </div>
          )}

          {/* My Jobs */}
          <h2 className="text-3xl font-bold text-gray-900 mb-6">My Jobs</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {myJobs.map((job) => {
              const myJob = job.hiredLabours.find(h => h.labour._id === user?.id);
              return (
                <div key={job._id} className="bg-white rounded-lg shadow-md p-6">
                  <h3 className="text-xl font-semibold mb-2">{job.title}</h3>
                  <p className="text-gray-600 mb-4">{job.description}</p>
                  <div className="space-y-2 mb-4">
                    <p><span className="font-semibold">Rate:</span> ₹{myJob?.hourlyRate}/hr</p>
                    <p><span className="font-semibold">Status:</span> 
                      <span className={`ml-2 px-2 py-1 rounded ${
                        myJob?.status === 'active' ? 'bg-yellow-100 text-yellow-800' :
                        'bg-green-100 text-green-800'
                      }`}>
                        {myJob?.status}
                      </span>
                    </p>
                    <p><span className="font-semibold">Hours Worked:</span> {myJob?.hoursWorked || 0}</p>
                    <p><span className="font-semibold">Project Status:</span> {job.status}</p>
                  </div>
                </div>
              );
            })}
          </div>

          {myJobs.length === 0 && (
            <div className="text-center py-12">
              <p className="text-gray-500 text-lg">You haven't been assigned to any jobs yet.</p>
            </div>
          )}

          {/* Work History */}
          <div className="mt-12 mb-6">
            <h2 className="text-3xl font-bold text-gray-900 mb-6">Work History</h2>
            <div className="bg-white rounded-lg shadow-md p-6">
              {workHistory.length > 0 ? (
                <div className="overflow-x-auto">
                  <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                      <tr>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Project</th>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Hours</th>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Approved</th>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Actions</th>
                      </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                      {workHistory.map((work) => (
                        <tr key={work.id}>
                          <td className="px-4 py-3 whitespace-nowrap text-sm font-medium">{work.project_title}</td>
                          <td className="px-4 py-3 whitespace-nowrap text-sm">
                            <span className={`px-2 py-1 rounded ${
                              work.status === 'completed' ? 'bg-green-100 text-green-800' :
                              'bg-yellow-100 text-yellow-800'
                            }`}>
                              {work.status}
                            </span>
                          </td>
                          <td className="px-4 py-3 whitespace-nowrap text-sm">{work.hours_worked}</td>
                          <td className="px-4 py-3 whitespace-nowrap text-sm">
                            {work.approved_by_customer ? 'Yes ✓' : 'No'}
                          </td>
                          <td className="px-4 py-3 whitespace-nowrap text-sm">
                            {!work.approved_by_customer && work.status === 'completed' && (
                              <button 
                                onClick={() => setShowDisputeModal(true)}
                                className="text-red-600 hover:text-red-800"
                              >
                                Report Issue
                              </button>
                            )}
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              ) : (
                <p className="text-center text-gray-500 py-8">No work history available yet.</p>
              )}
            </div>
          </div>

          {/* Active Disputes */}
          {disputes.length > 0 && (
            <div className="mt-12 mb-6">
              <h2 className="text-3xl font-bold text-gray-900 mb-6">Active Disputes</h2>
              <div className="bg-white rounded-lg shadow-md p-6">
                <div className="space-y-4">
                  {disputes.filter(d => d.status === 'open').map((dispute) => (
                    <div key={dispute.id} className="border-l-4 border-red-500 pl-4 py-2">
                      <div className="flex justify-between items-start">
                        <div>
                          <p className="font-semibold">{dispute.type.toUpperCase()} - {dispute.project_title}</p>
                          <p className="text-sm text-gray-600">{dispute.description}</p>
                          <p className="text-xs text-gray-500 mt-1">Created: {new Date(dispute.created_at).toLocaleDateString()}</p>
                        </div>
                        <span className={`px-2 py-1 rounded text-xs ${
                          dispute.status === 'open' ? 'bg-red-100 text-red-800' :
                          dispute.status === 'resolved' ? 'bg-green-100 text-green-800' :
                          'bg-yellow-100 text-yellow-800'
                        }`}>
                          {dispute.status}
                        </span>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          )}
        </div>
      </div>

      {/* Application Modal */}
      {selectedPosting && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-8 max-w-md w-full mx-4">
            <h2 className="text-2xl font-bold mb-4">Apply for Job</h2>
            <div className="mb-4">
              <h3 className="font-semibold">{selectedPosting.title}</h3>
              <p className="text-gray-600 text-sm">{selectedPosting.description}</p>
              <p className="text-sm mt-2">
                <span className="font-semibold">Rate:</span> ₹{selectedPosting.hourlyRate}/hr
              </p>
            </div>
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-2">Why are you interested?</label>
                <textarea
                  className="w-full px-3 py-2 border rounded-md"
                  rows="4"
                  value={applicationMessage}
                  onChange={(e) => setApplicationMessage(e.target.value)}
                  placeholder="Tell us why you'd be a good fit for this job..."
                />
              </div>
              <div className="flex justify-end space-x-4">
                <button
                  onClick={() => setSelectedPosting(null)}
                  className="px-4 py-2 border rounded-md hover:bg-gray-100"
                >
                  Cancel
                </button>
                <button
                  onClick={() => handleApply(selectedPosting._id)}
                  className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
                >
                  Submit Application
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

export default LabourDashboard;

