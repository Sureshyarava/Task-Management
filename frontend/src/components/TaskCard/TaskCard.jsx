import React from "react";

export default function TaskCard({ task, onEdit, onDelete, onStatusChange }) {
  const dueDate = task.dueDate
    ? new Date(task.dueDate).toLocaleString([], {
        year: "numeric",
        month: "short",
        day: "numeric",
        hour: "2-digit",
        minute: "2-digit"
      })
    : "No due date";

  const priority =
    task.priority?.charAt(0).toUpperCase() + task.priority?.slice(1).toLowerCase();

  return (
    <div className="task-card-container">
      <h3 className="task-title">{task.title}</h3>
      <p className="task-desc">{task.description}</p>
      <div className="task-meta">
        <span><strong>Due:</strong> {dueDate}</span>
        <span><strong>Priority:</strong> {priority}</span>
        <span><strong>Tags:</strong> {Array.isArray(task.tags) ? task.tags.join(', ') : ''}</span>
      </div>

      <label>Status:</label>
      <select
        value={task.status}
        onChange={(e) => onStatusChange(task.id, e.target.value)}
      >
        <option value="TODO">TODO</option>
        <option value="IN_PROGRESS">IN_PROGRESS</option>
        <option value="DONE">DONE</option>
      </select>

      <div className="task-buttons">
        <button onClick={() => onEdit(task)}>Edit</button>
        <button onClick={() => onDelete(task.id)}>Delete</button>
      </div>
    </div>
  );
}
