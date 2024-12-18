package com.victorsanchez.projectmanager.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, nullable = false, length = 255)
	private String name;

	@Column(unique = true, nullable = false)
	@Email
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(name = "email_verified_at")
	private Date emailVerifiedAt;

	@Column(name = "active", nullable = false)
	private Boolean active;

	// @OneToMany(mappedBy = "assignedUser")
	// private Set<Task> tasks;

	// @OneToMany(mappedBy = "createdBy")
	// private Set<Project> createdProjects;

	// @OneToMany(mappedBy = "updatedBy")
	// private Set<Project> updatedProjects;

	// @OneToMany(mappedBy = "createdBy")
	// private Set<Task> createdTasks;

	// @OneToMany(mappedBy = "updatedBy")
	// private Set<Task> updatedTasks;

	@Column(name = "created_at", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

}
