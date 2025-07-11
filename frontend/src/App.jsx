import React, { useState } from 'react';
import TaskBoard from './components/TaskBoard/TaskBoard';
import TaskForm from './components/TaskForm/TaskForm';
import ErrorBoundary from './components/ErrorBounday/ErrorBoundary';
import './App.css';

const App = () => {
  const [showForm, setShowForm] = useState(false);
  const [editingTask, setEditingTask] = useState(null);
  const [refreshKey, setRefreshKey] = useState(0);

  const handleAddClick = () => {
    setEditingTask(null);
    setShowForm(true);
  };

  const handleEditClick = (task) => {
    setEditingTask(task);
    setShowForm(true);
  };

  const handleFormClose = () => {
    setShowForm(false);
    setEditingTask(null);
  };

  const handleSave = () => {
    setRefreshKey(prev => prev + 1);
    handleFormClose();
  };

  return (
    <div className="app-container">
      <ErrorBoundary>
        {showForm ? (
          <TaskForm task={editingTask} onClose={handleFormClose} onSave={handleSave} />
        ) : (
          <TaskBoard key={refreshKey} onAddClick={handleAddClick} onEditClick={handleEditClick} />
        )}
      </ErrorBoundary>
    </div>
  );
};

export default App;
