const BASE_URL = "http://localhost:8080/api/v1/tasks/";

export const createTask = async (taskData) => {
  const response = await fetch(BASE_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(taskData),
  });

  if (!response.ok) {
    throw new Error("Failed to create task");
  }

  return await response.json();
};

export const fetchTasks = async (page = 0, size = 10) => {
  const response = await fetch(`${BASE_URL}?page=${page}&size=${size}`);
  
  if (!response.ok) {
    throw new Error("Failed to fetch tasks");
  }

  const data = await response.json();

  return {
    tasks: data.content
  };
};

export const updateTaskStatus = async (id, newStatus) => {
  const response = await fetch(`${BASE_URL}${id}/status`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ status: newStatus })
  });

  if (!response.ok) {
    throw new Error("Failed to update task status");
  }

  return await response.json();
};


export const updateTask = async (taskId, updatedData) => {
  const response = await fetch(`${BASE_URL}${taskId}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(updatedData),
  });

  if (!response.ok) {
    throw new Error("Failed to update task");
  }

  return await response.json();
};

export const deleteTask = async (taskId) => {
  const response = await fetch(`${BASE_URL}${taskId}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    throw new Error("Failed to delete task");
  }

  return;
};

export const searchTasks = async (text = "", page = 0, size = 10) => {
  const response = await fetch(`${BASE_URL}search?text=${encodeURIComponent(text)}`);
  if (!response.ok) {
    throw new Error("Failed to search tasks");
  }
  const data = await response.json();

  return {
    tasks: data.content
  };
};