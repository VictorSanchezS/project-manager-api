package com.victorsanchez.projectmanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.victorsanchez.projectmanager.entity.Project;
import com.victorsanchez.projectmanager.entity.User;
import com.victorsanchez.projectmanager.exceptions.GeneralServiceException;
import com.victorsanchez.projectmanager.exceptions.NoDataFoundExeception;
import com.victorsanchez.projectmanager.exceptions.ValidateServiceException;
import com.victorsanchez.projectmanager.repository.ProjectRepository;
import com.victorsanchez.projectmanager.repository.UserRepository;
import com.victorsanchez.projectmanager.service.ProjectService;
import com.victorsanchez.projectmanager.validator.ProjectValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Project> findAll(Pageable page) {
		try {
			return projectRepository.findAll(page).toList();
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
	public List<Project> findByName(String name, Pageable page) {
		try {
			return projectRepository.findByNameContaining(name, page);
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
	public List<Project> findByStatus(String status, Pageable page) {
		try {
			return projectRepository.findByStatus(status, page);
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
	public Project findById(int id) {
		try {
			Project record = projectRepository.findById(id)
					.orElseThrow(() -> new NoDataFoundExeception("No record exists with that ID"));
			return record;
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
	public Project save(Project project) {
		try {
			// Verificar si el usuario existe y está asignado
			if (project.getCreatedBy() == null || project.getCreatedBy().getId() == null) {
				throw new ValidateServiceException("The project must have an assigned user.");
			}

			// Buscar el usuario desde el repositorio (si es necesario)
			User user = userRepository.findById(project.getCreatedBy().getId())
					.orElseThrow(() -> new NoDataFoundExeception("Usuario no encontrado"));

			// Asignar el usuario al proyecto si no está asignado
			project.setCreatedBy(user);

			// Validar el proyecto antes de guardarlo
			ProjectValidator.save(project);

			// Si es necesario, puedes realizar otras configuraciones en el proyecto antes
			// de guardarlo
			// Ejemplo: Configuración del estado del proyecto por defecto si no está
			// presente
			if (project.getStatus() == null) {
				project.setStatus("pending"); // Establecer el estado por defecto si no se proporciona
			}

			// Guardar el proyecto
			Project savedProject = projectRepository.save(project);

			// Retornar el proyecto guardado
			return savedProject;

		} catch (ValidateServiceException | NoDataFoundExeception e) {
			// Log de errores de validación o de datos no encontrados
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			// Log de errores generales
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional()
	public Project update(Project project) {
		try {
			// Verificar si el proyecto existe
			Project existingProject = projectRepository.findById(project.getId())
					.orElseThrow(() -> new NoDataFoundExeception("No project found with ID " + project.getId()));

			// Validar el proyecto antes de realizar la actualización
			ProjectValidator.save(project);

			// Si el estado no se ha proporcionado, se podría establecer un valor por
			// defecto (opcional)
			if (project.getStatus() == null) {
				project.setStatus(existingProject.getStatus()); // Mantener el estado actual si no se proporciona uno
																// nuevo
			}

			// Si es necesario, se puede copiar otras propiedades que no estén siendo
			// modificadas
			// Por ejemplo, si no se ha cambiado la fecha de vencimiento, mantenemos la
			// anterior:
			if (project.getDueDate() == null) {
				project.setDueDate(existingProject.getDueDate()); // Mantener la fecha actual si no se proporciona una
																	// nueva
			}

			// Actualizar el proyecto con los nuevos datos
			// Asegúrate de mantener la referencia a las entidades relacionadas (por
			// ejemplo, 'updatedBy')
			existingProject.setName(project.getName());
			existingProject.setDescription(project.getDescription());
			existingProject.setStatus(project.getStatus());
			existingProject.setDueDate(project.getDueDate());
			existingProject.setImagePath(project.getImagePath());
			existingProject.setUpdatedBy(project.getUpdatedBy());

			// Guardar los cambios en la base de datos
			Project updatedProject = projectRepository.save(existingProject);

			// Retornar el proyecto actualizado
			return updatedProject;

		} catch (ValidateServiceException | NoDataFoundExeception e) {
			// Log de errores de validación o datos no encontrados
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			// Log de errores generales
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional()
	public void delete(int id) {
		try {
			// Verificar si el proyecto existe
			Project project = projectRepository.findById(id)
					.orElseThrow(() -> new NoDataFoundExeception("No project found with ID " + id));

			// Si es necesario, puedes manejar la eliminación de las tareas relacionadas u
			// otras entidades asociadas.
			// Por ejemplo, si quieres eliminar las tareas asociadas al proyecto:
			// for (Task task : project.getTasks()) {
			// taskService.delete(task.getId()); // Método delete en el servicio de tareas,
			// si lo tienes
			// }

			// Eliminar el proyecto
			projectRepository.delete(project);

			log.info("Project with ID " + id + " was deleted successfully.");

		} catch (NoDataFoundExeception e) {
			// Log si el proyecto no se encuentra
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			// Log de errores generales
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Project> findByCreatedById(int userId, Pageable page) {
		try {
			List<Project> projects = projectRepository.findByCreatedById(userId, page);
			if (projects.isEmpty()) {
				throw new NoDataFoundExeception("No projects found for the provided user ID.");
			}
			return projects;
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
	public List<Project> findByUpdatedById(int userId, Pageable page) {
		try {
			List<Project> projects = projectRepository.findByUpdatedById(userId, page);
			if (projects.isEmpty()) {
				throw new NoDataFoundExeception("No projects found for the provided user ID.");
			}
			return projects;
		} catch (NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

}
