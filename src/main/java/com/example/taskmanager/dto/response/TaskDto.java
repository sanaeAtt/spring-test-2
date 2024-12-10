package com.example.taskmanager.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String title;
    private String description;
    private LocalDate dueDate;
    private String status;
}
