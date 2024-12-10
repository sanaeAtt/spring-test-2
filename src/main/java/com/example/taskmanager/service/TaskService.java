package com.example.taskmanager.service;

import java.util.List;
import java.util.Optional;

import com.example.taskmanager.model.Task;

public interface TaskService {
    // public List<TaskDto> getTasksForUser(Long userId) ;
    public List<Task> getTasksByUser(String username);
    public Task createTask(Task task) ;
    public Optional<Task> updateTask(Long id, Task updatedTask);
    public void deleteTask(Long taskId); 
}
