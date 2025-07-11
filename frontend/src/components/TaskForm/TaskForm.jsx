import React, { useState, useEffect } from 'react';
import { createTask, updateTask } from '../../hooks/useApi';

const TaskForm = ({ task, onSave, onClose }) => {
  const isEdit = Boolean(task && task.id);

  const [formData, setFormData] = useState({
    title: '',
    description: '',
    status: 'TODO',
    priority: 'MEDIUM',
    dueDate: '',
    tags: '',
  });

  useEffect(() => {
    if (task) {
      setFormData({
        title: task.title || '',
        description: task.description || '',
        status: task.status || 'TODO',
        priority: task.priority || 'MEDIUM',
        dueDate: task.dueDate ? task.dueDate.slice(0, 16) : '',
        tags: task.tags ? task.tags.join(', ') : '',
      });
    }
  }, [task]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      ...formData,
      dueDate: new Date(formData.dueDate).toISOString(),
      tags: formData.tags.split(',').map(tag => tag.trim()).filter(Boolean),
    };

    try {
      if (isEdit) {
        await updateTask(task.id, {
          ...payload,
          createdAt: task.createdAt,
          actualHours: task.actualHours,
          id: task.id
        });
      } else {
        await createTask(payload);
      }
      onSave();
    } catch (err) {
      alert("Failed to save task.");
      console.error(err);
    }
  };

  return (
    <div className="task-form-wrapper">
      <form className="task-form" onSubmit={handleSubmit}>
        <h2>{isEdit ? 'Edit Task' : 'Create Task'}</h2>

        <label>Title</label>
        <input name="title" value={formData.title} onChange={handleChange} required />

        <label>Description</label>
        <textarea name="description" value={formData.description} onChange={handleChange} />

        {!isEdit &&
        <>
        <label>Status</label>
        <select name="status" value={formData.status} onChange={handleChange}>
          <option value="TODO">TODO</option>
          <option value="IN_PROGRESS">In Progress</option>
          <option value="DONE">Done</option>
        </select>
        </>
        }
        <label>Priority</label>
        <select name="priority" value={formData.priority} onChange={handleChange}>
          <option value="LOW">Low</option>
          <option value="MEDIUM">Medium</option>
          <option value="HIGH">High</option>
        </select>

        <label>Due Date</label>
        <input type="datetime-local" name="dueDate" value={formData.dueDate} onChange={handleChange} required />

        <label>Tags (comma-separated)</label>
        <input name="tags" value={formData.tags} onChange={handleChange} placeholder="e.g. urgent, backend" />

        <div className="form-buttons">
          <button type="submit">{isEdit ? 'Update' : 'Create'}</button>
          <button type="button" onClick={onClose}>Cancel</button>
        </div>
      </form>
    </div>
  );
};

export default TaskForm;
