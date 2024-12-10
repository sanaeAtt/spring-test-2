package com.example.taskmanager.controller;

import java.util.List;

import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.response.TaskDto;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.model.Users;
import com.example.taskmanager.service.serviceImpl.TaskServiceImpl;
import com.example.taskmanager.service.serviceImpl.UserServiceImpl;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("api/tasks")
@RestController
@PreAuthorize("isAuthenticated()")
// @AllArgsConstructor
public class TaskController {
    private TaskServiceImpl taskServiceImpl;
    private UserServiceImpl userServiceImpl;

    public TaskController(TaskServiceImpl taskServiceImpl, UserServiceImpl userServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getUserTasks(@RequestParam Long userId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Task> tasks = taskServiceImpl.getTasksByUser(username);
        return ResponseEntity.ok(tasks);
    }
 
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userServiceImpl.findByUsername(username).orElseThrow();
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setStatus(TaskStatus.valueOf(taskDTO.getStatus().toUpperCase()));
        task.setUser(user);

        Task createdTask = taskServiceImpl.createTask(task);

        return ResponseEntity.ok(new TaskDto(
                createdTask.getTitle(),
                createdTask.getDescription(),
                createdTask.getDueDate(),
                createdTask.getStatus().name()
        ));
    }
    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Task> tasks = taskServiceImpl.getTasksByUser(username);
        return ResponseEntity.ok(tasks);
    }

     // Mise à Jour de Tâche
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDTO) {
        Optional<Task> updatedTask = taskServiceImpl.updateTask(id, mapTaskDTOToTask(taskDTO));
        return updatedTask.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Suppression de Tâche
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskServiceImpl.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // Méthode Utilitaire
    private Task mapTaskDTOToTask(TaskDto taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setStatus(TaskStatus.valueOf(taskDTO.getStatus().toUpperCase()));
        return task;
    }
}