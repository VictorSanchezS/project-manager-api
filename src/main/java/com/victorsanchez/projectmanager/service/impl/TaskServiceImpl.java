package com.victorsanchez.projectmanager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.victorsanchez.projectmanager.entity.Task;
import com.victorsanchez.projectmanager.entity.User;
import com.victorsanchez.projectmanager.exceptions.GeneralServiceException;
import com.victorsanchez.projectmanager.exceptions.NoDataFoundExeception;
import com.victorsanchez.projectmanager.exceptions.ValidateServiceException;
import com.victorsanchez.projectmanager.repository.TaskRepository;
import com.victorsanchez.projectmanager.repository.UserRepository;
import com.victorsanchez.projectmanager.service.TaskService;
import com.victorsanchez.projectmanager.validator.TaskValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Task> findAll(Pageable page) {
		try {
			return taskRepository.findAll(page).toList();
		} catch (NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findByName(String name, Pageable page) {
		try {
			return taskRepository.findByNameContaining(name, page);
		} catch (ValidateServiceException | NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findByStatus(String status, Pageable page) {
		try {
			return taskRepository.findByStatus(status, page);
		} catch (ValidateServiceException | NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findByPriority(String priority, Pageable page) {
		try {
			return taskRepository.findByPriority(priority, page);
		} catch (ValidateServiceException | NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Task findById(int id) {
		try {
			Task task = taskRepository.findById(id)
					.orElseThrow(() -> new NoDataFoundExeception("No task found with ID " + id));
			return task;
		} catch (ValidateServiceException | NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional()
	public Task save(Task task) {
		try {
			if (task.getAssignedUser() != null && task.getAssignedUser().getId() != null) {
				User user = userRepository.findById(task.getAssignedUser().getId())
						.orElseThrow(() -> new NoDataFoundExeception("Assigned user not found"));
				task.setAssignedUser(user);
			}

			if (task.getCreatedBy() == null) {
				User createdBy = userRepository.findById(1)
						.orElseThrow(() -> new NoDataFoundExeception("Creator user not found"));
				task.setCreatedBy(createdBy);
			}

			if (task.getUpdatedBy() == null) {
				task.setUpdatedBy(task.getCreatedBy());
			}

			task.setCreatedAt(new Date());
			task.setUpdatedAt(new Date());

			TaskValidator.save(task);

			Task savedTask = taskRepository.save(task);
			return savedTask;

		} catch (ValidateServiceException | NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional()
	public Task update(Task task) {
	    try {
	        // Buscar la tarea existente
	        Task existingTask = taskRepository.findById(task.getId())
	                .orElseThrow(() -> new NoDataFoundExeception("No task found with ID " + task.getId()));

	        // Validar si se ha asignado un usuario a la tarea
	        if (task.getAssignedUser() != null && task.getAssignedUser().getId() != null) {
	            // Buscar el usuario asignado
	            User user = userRepository.findById(task.getAssignedUser().getId())
	                    .orElseThrow(() -> new NoDataFoundExeception("Assigned user not found"));
	            existingTask.setAssignedUser(user);
	        }

	        // Validar la tarea antes de guardarla
	        TaskValidator.save(task);

	        // Actualizar los campos de la tarea existente
	        existingTask.setName(task.getName());
	        existingTask.setDescription(task.getDescription());
	        existingTask.setStatus(task.getStatus());
	        existingTask.setPriority(task.getPriority());
	        existingTask.setDueDate(task.getDueDate());

	        // Configurar la fecha de actualización
	        existingTask.setUpdatedAt(new Date());
	        
	        // Guardar la tarea actualizada
	        Task updatedTask = taskRepository.save(existingTask);
	        return updatedTask;

	    } catch (ValidateServiceException | NoDataFoundExeception e) {
	        log.info(e.getMessage(), e);
	        throw e;
	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        throw new GeneralServiceException(e.getMessage(), e);
	    }
	}


	@Override
	@Transactional()
	public void delete(int id) {
		try {
			Task task = taskRepository.findById(id)
					.orElseThrow(() -> new NoDataFoundExeception("No task found with ID " + id));
			taskRepository.delete(task);
			log.info("Task with ID " + id + " was deleted successfully.");
		} catch (NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findByAssignedUserId(int userId, Pageable page) {
		try {
			List<Task> tasks = taskRepository.findByAssignedUserId(userId, page);
			if (tasks.isEmpty()) {
				throw new NoDataFoundExeception("No tasks found for assigned user ID " + userId);
			}
			return tasks;
		} catch (NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findByProjectId(int projectId, Pageable page) {
		try {
			List<Task> tasks = taskRepository.findByProjectId(projectId, page);
			if (tasks.isEmpty()) {
				throw new NoDataFoundExeception("No tasks found for project ID " + projectId);
			}
			return tasks;
		} catch (NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findByDueDate(Date dueDate, Pageable page) {
		try {
			List<Task> tasks = taskRepository.findByDueDate(dueDate, page);
			if (tasks.isEmpty()) {
				throw new NoDataFoundExeception("No tasks found with due date " + dueDate);
			}
			return tasks;
		} catch (NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Task> findByDueDateBetween(Date startDate, Date endDate, Pageable page) {
		try {
			List<Task> tasks = taskRepository.findByDueDateBetween(startDate, endDate, page);
			if (tasks.isEmpty()) {
				throw new NoDataFoundExeception("No tasks found between " + startDate + " and " + endDate);
			}
			return tasks;
		} catch (NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

}
