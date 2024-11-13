package com.victorsanchez.projectmanager.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.victorsanchez.projectmanager.entity.Task;

public interface TaskService {
	List<Task> findAll(Pageable page);

	List<Task> findByName(String name, Pageable page);

	List<Task> findByStatus(String status, Pageable page);

	List<Task> findByPriority(String priority, Pageable page);

	Task findById(int id);

	Task save(Task task);

	Task update(Task task);

	void delete(int id);

	// Buscar tareas por el usuario asignado
	List<Task> findByAssignedUserId(int userId, Pageable page);

	// Buscar tareas por el proyecto al que pertenecen
	List<Task> findByProjectId(int projectId, Pageable page);

	// Buscar tareas por la fecha de vencimiento
	List<Task> findByDueDate(Date dueDate, Pageable page);

	// Buscar tareas entre un rango de fechas de vencimiento
	List<Task> findByDueDateBetween(Date startDate, Date endDate, Pageable page);
}
