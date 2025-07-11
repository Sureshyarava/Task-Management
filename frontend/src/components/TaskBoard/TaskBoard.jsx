import React, { useEffect, useState, useCallback } from 'react';
import { fetchTasks, deleteTask, updateTaskStatus, searchTasks } from '../../hooks/useApi';
import TaskCard from '../TaskCard/TaskCard'; 
import TaskSearch from '../TaskSearch/TaskSearch';

const TaskBoard = ({ onAddClick, onEditClick }) => {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    const loadTasks = async () => {
      try {
        const data = await fetchTasks();
        const taskList = Array.isArray(data) ? data : data.tasks || [];
        setTasks(taskList);
      } catch (err) {
        console.error("Failed to fetch tasks:", err);
      }
    };
    loadTasks();
  }, []);

  const handleSearch = useCallback(async (text) => {
    try {
      if (text.trim() === "") {
        const data = await fetchTasks();
        const taskList = Array.isArray(data) ? data : data.tasks || [];
        setTasks(taskList); 
        return;
      }
      const result = await searchTasks(text);
      console.log(result);
      const taskList = Array.isArray(result) ? result : result.tasks || [];
      setTasks(taskList);
    } catch (err) {
      console.error("Search failed:", err);
    }
  }, []);

  const handleDelete = async (taskId) => {
    try {
      await deleteTask(taskId);
      setTasks(prev => prev.filter(task => task.id !== taskId));
    } catch (err) {
      console.error("Failed to delete task:", err);
    }
  };

  const handleStatusChange = async (taskId, newStatus) => {
    try {
      const updatedTask = await updateTaskStatus(taskId, newStatus);
      setTasks(prev =>
        prev.map(task =>
          task.id === taskId ? { ...task, status: updatedTask.status } : task
        )
      );
    } catch (err) {
      console.error("Failed to update status:", err);
    }
  };

  return (
    <div className="task-board">
      <h1>Task Board</h1>

      <TaskSearch onSearch={handleSearch}/>

      <button onClick={onAddClick} className="addTask">
        Add Task
      </button>

      <div className="task-columns container">
        {['TODO', 'IN_PROGRESS', 'DONE'].map(status => (
          <div key={status} className="task-column child">
            <h2>{status.replace('_', ' ')}</h2>

            {Array.isArray(tasks) &&
              tasks
                .filter(task => task.status === status)
                .map(task => (
                  <TaskCard
                    key={task.id}
                    task={task}
                    onEdit={onEditClick}
                    onDelete={handleDelete}
                    onStatusChange={handleStatusChange}
                  />
                ))}
          </div>
        ))}
      </div>
    </div>
  );
};

export default TaskBoard;
