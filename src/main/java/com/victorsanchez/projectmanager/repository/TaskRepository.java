package com.victorsanchez.projectmanager.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.victorsanchez.projectmanager.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
	// Buscar tareas por nombre (con soporte para búsqueda parcial)
    List<Task> findByNameContaining(String name, Pageable page);

    // Buscar tareas por estado
    List<Task> findByStatus(String status, Pageable page);
    
 // Método para buscar tareas por ID de proyecto
    List<Task> findByProjectId(int projectId, Pageable pageable);

    // Buscar tareas por prioridad
    List<Task> findByPriority(String priority, Pageable page);

    // Buscar tareas asignadas a un usuario específico
    List<Task> findByAssignedUserId(Integer userId, Pageable page);

    // Buscar tareas creadas por un usuario específico
    List<Task> findByCreatedById(Integer userId, Pageable page);

    // Buscar tareas actualizadas por un usuario específico
    List<Task> findByUpdatedById(Integer userId, Pageable page);

    // Buscar tareas por fecha de vencimiento
    List<Task> findByDueDate(Date dueDate, Pageable page);

    // Buscar tareas entre dos fechas de vencimiento
    List<Task> findByDueDateBetween(Date startDate, Date endDate, Pageable page);
}
