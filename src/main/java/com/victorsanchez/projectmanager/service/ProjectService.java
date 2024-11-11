package com.victorsanchez.projectmanager.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.victorsanchez.projectmanager.entity.Project;

public interface ProjectService {
	List<Project> findAll(Pageable page);

	List<Project> findByName(String name, Pageable page);

	List<Project> findByStatus(String status, Pageable page);

	Project findById(int id);

	Project save(Project project);

	Project update(Project project);

	void delete(int id);

	// Buscar proyectos por el usuario que lo creó
	List<Project> findByCreatedById(int userId, Pageable page);

	// Buscar proyectos por el usuario que lo actualizó
	List<Project> findByUpdatedById(int userId, Pageable page);
}
