package com.example.taskmanager.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.TaskService;

import lombok.AllArgsConstructor;

@Service
// @AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksByUser(String username) {
        return taskRepository.findByUserUsername(username);
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

     // Mise à Jour de la Tâche
     public Optional<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setStatus(updatedTask.getStatus());
            return taskRepository.save(task);
        });
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);

    }

    
    
}
