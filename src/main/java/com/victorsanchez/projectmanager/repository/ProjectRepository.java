package com.victorsanchez.projectmanager.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.victorsanchez.projectmanager.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	List<Project> findByNameContaining(String name, Pageable page);

	Project findByName(String name);

	List<Project> findByStatus(String status, Pageable page);

	List<Project> findByCreatedById(Integer userId, Pageable page);

	List<Project> findByUpdatedById(Integer userId, Pageable page);

	List<Project> findByDueDate(Date dueDate, Pageable page);

	List<Project> findByDueDateBetween(Date startDate, Date endDate, Pageable page);// Buscar proyectos entre dos fechas
}
