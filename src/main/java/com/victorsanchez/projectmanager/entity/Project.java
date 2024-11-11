package com.victorsanchez.projectmanager.entity;

import java.util.Date;
import java.util.Set;

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
import jakarta.persistence.OneToMany;
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
@Table(name = "projects")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(length = 255)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;

	@Column(nullable = false)
	private String status;

	@Column(name = "image_path")
	private String imagePath;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User createdBy;

	@ManyToOne
	@JoinColumn(name = "updated_by", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User updatedBy;

	//@OneToMany(mappedBy = "project")
	//private Set<Task> tasks;

	@Column(name = "created_at", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

}
