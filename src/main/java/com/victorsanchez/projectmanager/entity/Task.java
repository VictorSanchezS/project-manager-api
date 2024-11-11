package com.victorsanchez.projectmanager.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(length = 255)
	private String description;

	@Column(name = "image_path")
	private String imagePath;

	@Column(nullable = false)
	private String status;

	@Column(nullable = false)
	private String priority;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;

	@ManyToOne
	@JoinColumn(name = "assigned_user_id", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User assignedUser;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User createdBy;

	@ManyToOne
	@JoinColumn(name = "updated_by", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User updatedBy;

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Project project;

	@Column(name = "created_at", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

}
